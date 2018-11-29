package org.occourts.escheatment.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.occourts.escheatment.dao.LawfulOwnerDAO;
import org.occourts.escheatment.model.LawfulOwner;
import org.occourts.escheatment.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

/**
* LawfulOwnerDAOImpl provides methods to return Lawful Owner data
* $Revision: 4519 $     
* $Author: cbarrington $ 
* $Date: 2018-09-04 13:38:45 -0700 (Tue, 04 Sep 2018) $    
*/

public class LawfulOwnerDAOImpl implements LawfulOwnerDAO {

	private DataSource dataSource;
	JdbcTemplate template;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public void deleteAllLawfulOwnersByTrustId(final long trustId) {
		final String lawfulOwnerDeleteSql = "DELETE escheatment.lawful_owner WHERE trust_id = ?";

		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(lawfulOwnerDeleteSql);
				ps.setLong(1, trustId);				
				return ps;					
			}
			
		});		
	}
	
	public boolean addLawfulOwners(final String createdByUserName, List<LawfulOwner> owners) {
		
		final String lawfulOwnerInsertSql = "INSERT INTO escheatment.lawful_owner (owner_id,owner_name_1,owner_name_2,owner_addr_1,owner_addr_2,owner_addr_3,trust_id,clock_index,under_review,create_user_id,create_dt,update_user_id,update_dt) values (escheatment.lawful_owner_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?)";
		//loop through to insert to DB
		for (final LawfulOwner owner : owners) {
			template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(lawfulOwnerInsertSql);
					ps.setString(1, owner.getNameLine1());
					ps.setString(2, owner.getNameLine2());
					ps.setString(3, owner.getAddressLine1());
					ps.setString(4, owner.getAddressLine2());
					ps.setString(5, owner.getAddressLine3());
					ps.setLong(6, owner.getTrustId().longValue());
					ps.setInt(7, owner.getClockIndex().intValue());		
					ps.setString(8, Character.toString('N'));
					ps.setString(9, createdByUserName);
					ps.setDate(10, new java.sql.Date(System.currentTimeMillis()));
					ps.setString(11, createdByUserName);
					ps.setDate(12, new java.sql.Date(System.currentTimeMillis()));				
					return ps;					
				}				
			});
		}
		
		return true;
	}
	
	public List<LawfulOwner> fetchAllLawfulOwnersByTrustId(long trustId) {
		
			List<LawfulOwner> ownerDataList = new ArrayList<LawfulOwner>();
			String SQL = "select owner_id, \n" + 
					"       owner_name_1, \n" + 
					"       owner_name_2, \n" + 
					"       owner_addr_1, \n" + 
					"       owner_addr_2, \n" + 
					"       owner_addr_3, \n" + 
					"       trust_id, \n" + 
					"       clock_index, \n" + 
					"       decode(under_review,'N',null,'Y','disabled') as under_review, \n" +
					"       create_user_id, \n" + 
					"       create_dt, \n" + 
					"       update_user_id, \n" + 
					"       update_dt \n" + 
					"from ESCHEATMENT.lawful_owner \n" + 
					"where trust_id = ? \n" + 
					"order by owner_id";
			List<Map<String, Object>> rows = template.queryForList(SQL, new Object[] { trustId });
			LawfulOwner ownerData = new LawfulOwner();
			
			for (Map row : rows) {
				ownerData = new LawfulOwner();
				ownerData.setId((BigDecimal) (row.get("owner_id") != null ? row.get("owner_id") : BigDecimal.ZERO));
				ownerData.setNameLine1((row.get("owner_name_1") != null ? row.get("owner_name_1") : "").toString());
				ownerData.setNameLine2((row.get("owner_name_2") != null ? row.get("owner_name_2") : "").toString());
				ownerData.setAddressLine1((row.get("owner_addr_1") != null ? row.get("owner_addr_1") : "").toString());
				ownerData.setAddressLine2((row.get("owner_addr_2") != null ? row.get("owner_addr_2") : "").toString());
				ownerData.setAddressLine3((row.get("owner_addr_3") != null ? row.get("owner_addr_3") : "").toString());
				ownerData.setTrustId((BigDecimal) (row.get("trust_id") != null ? row.get("trust_id") : BigDecimal.ZERO));
				ownerData.setClockIndex((BigDecimal) (row.get("clock_index") != null ? row.get("clock_index") : BigDecimal.ZERO));
				ownerData.setUnderReview((row.get("under_review") != null ? row.get("under_review") : "").toString());
				ownerData.setCreateUserDate((Timestamp)(row.get("create_dt")));
				ownerData.setCreateUserId((String) (row.get("create_user_id")));
				ownerData.setUpdateUserDate((Timestamp) (row.get("update_dt")));
				ownerData.setUpdateUserId((String) (row.get("update_user_id")));
				ownerDataList.add(ownerData);
			}
			return ownerDataList;
		}

	public LawfulOwner fetchLawfulOwnerByLawfulOwnerId(long lawfulOwnerId) {
		
		String SQL = "SELECT lo.owner_id,\n" +
				"  lo.owner_name_1,\n" + 
				"  lo.owner_name_2,\n" + 
				"  lo.owner_addr_1,\n" + 
				"  lo.owner_addr_2,\n" +
				"  lo.owner_addr_3,\n" +
				"  lo.trust_id,\n" +
				"  lo.clock_index,\n" +
				"  decode(under_review,'N',null,'Y','disabled') as under_review, \n" +
				"  lo.create_dt,\n" + 
				"  lo.create_user_id,\n" + 
				"  lo.update_dt,\n" + 
				"  lo.update_user_id\n" + 
				"FROM escheatment.lawful_owner lo \n" + 
				"WHERE lo.owner_id = ?";	
		
		LawfulOwner owner = new LawfulOwner();
		Map row = DataAccessUtils.singleResult(template.queryForList(SQL, new Object[] { lawfulOwnerId }));
		if (row != null) {
			owner.setId((BigDecimal) (row.get("owner_id") != null ? row.get("owner_id") : BigDecimal.ZERO));
			owner.setAddressLine1((row.get("owner_addr_1") != null ? row.get("owner_addr_1") : "").toString());
			owner.setAddressLine2((row.get("owner_addr_2") != null ? row.get("owner_addr_2") : "").toString());
			owner.setAddressLine3((row.get("owner_addr_3") != null ? row.get("owner_addr_3") : "").toString());
			owner.setNameLine1((row.get("owner_name_1") != null ? row.get("owner_name_1") : "").toString());
			owner.setNameLine2((row.get("owner_name_2") != null ? row.get("owner_name_2") : "").toString());
			owner.setTrustId((BigDecimal) (row.get("trust_id") != null ? row.get("trust_id") : BigDecimal.ZERO));
			owner.setClockIndex((BigDecimal) (row.get("clock_index") != null ? row.get("clock_index") : BigDecimal.ZERO));
			owner.setUnderReview((row.get("under_review") != null ? row.get("under_review") : "").toString());
			owner.setCreateUserDate((Timestamp)(row.get("create_dt")));
			owner.setCreateUserId((String) (row.get("create_user_id")));
			owner.setUpdateUserDate((Timestamp) (row.get("update_dt")));
			owner.setUpdateUserId((String) (row.get("update_user_id")));			
		}
		return owner;
	}
	
	public int getNextClockIndex(long trustId) {
		String SQL = "select nvl(max(clock_index),0) from escheatment.lawful_owner where trust_id = ?";		
		Integer iClockIndex = (Integer) this.template.queryForObject(SQL, new Object[] {trustId}, Integer.class);
		return iClockIndex + 1;
	}	
	
}

