package org.occourts.escheatment.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.occourts.escheatment.dao.PublicationDAO;
import org.occourts.escheatment.model.Publication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.stereotype.Repository;

@Repository
public class PublicationDAOImpl implements PublicationDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(PublicationDAOImpl.class);

	private static final String GET_SEQUENCE_NEXT_VALUE = "SELECT ESCHEATMENT.PUBLICATION_SEQ.NEXTVAL FROM DUAL";
	private static final String SQL_INSERT = "INSERT INTO ESCHEATMENT.PUBLICATION( PUBLICATION_ID, "
			+ "FINALIZED, PUBLICATION_DT, OUTPUT_PDF, CREATE_USER_ID, CREATE_DT, "
			+ "UPDATE_USER_ID, UPDATE_DT) VALUES(?,?,?,?,?,?,?,?)";

	private static final String SQL_SELECT = "SELECT PUBLICATION_ID, FINALIZED, PUBLICATION_DT, OUTPUT_PDF, CREATE_USER_ID, "
			+ "CREATE_DT, UPDATE_USER_ID, UPDATE_DT FROM ESCHEATMENT.PUBLICATION WHERE PUBLICATION_ID=?";

	@Autowired
	private DefaultLobHandler defaultLobHandler;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	public void initialize() {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public long addPublication(final Publication publication) {
		if (publication == null) {
			LOGGER.warn("Publication is not valid.");
			return 0;
		}
		LOGGER.info("Adding new publication...");
		final long nextBatchId = getNextPublicationId();
		final java.sql.Date rightNow = new java.sql.Date(Calendar.getInstance().getTimeInMillis());

		jdbcTemplate.execute(SQL_INSERT, new AbstractLobCreatingPreparedStatementCallback(defaultLobHandler) {
			protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
				ps.setLong(1, nextBatchId);
				ps.setString(2, publication.getFinalized());
				ps.setDate(3, rightNow);
				lobCreator.setBlobAsBytes(ps, 4, publication.getOutputPdf());
				ps.setString(5, publication.getCreateUserId());
				ps.setDate(6, rightNow);
				ps.setString(7, publication.getUpdateUserId());
				ps.setDate(8, rightNow);
			}
		});

		// TODO: AOP or declarative transaction
		try {
			jdbcTemplate.getDataSource().getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nextBatchId;
	}

	@Override
	public Publication findPublication(long publicationId) {
		List<Publication> publications = jdbcTemplate.query(SQL_SELECT, new Object[] { publicationId },
				new RowMapper<Publication>() {
					@Override
					public Publication mapRow(ResultSet rs, int rowNum) throws SQLException {
						Publication record = new Publication();
						record.setPublicationId(rs.getLong("PUBLICATION_ID"));
						record.setFinalized(rs.getString("FINALIZED"));
						record.setPublicationDt(transformToJavaDate(rs.getDate("PUBLICATION_DT")));
						byte[] thePdf = defaultLobHandler.getBlobAsBytes(rs, "OUTPUT_PDF");
						record.setOutputPdf(thePdf);
						record.setCreateUserId(rs.getString("CREATE_USER_ID"));
						record.setCreateDt(transformToJavaDate(rs.getDate("CREATE_DT")));
						record.setUpdateUserId(rs.getString("UPDATE_USER_ID"));
						record.setUpdateDt(transformToJavaDate(rs.getDate("UPDATE_DT")));
						return record;
					}
				});
		return publications == null || publications.isEmpty() ? null : publications.get(0);
	}

	private long getNextPublicationId() {
		return this.jdbcTemplate.queryForObject(String.format(GET_SEQUENCE_NEXT_VALUE), Long.class);
	}

	// TODO: Move to utility class
	private static Date transformToJavaDate(java.sql.Date date) {
		if (date == null)
			return null;
		Calendar theDate = Calendar.getInstance();
		theDate.setTime(date);
		return theDate.getTime();
	}

}
