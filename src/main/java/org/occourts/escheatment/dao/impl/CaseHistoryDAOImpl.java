package org.occourts.escheatment.dao.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.occourts.escheatment.dao.CaseHistoryDAO;
import org.occourts.escheatment.model.CaseHistory;
import org.occourts.escheatment.model.LawfulOwner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

@Repository
public class CaseHistoryDAOImpl implements CaseHistoryDAO {
	private static Logger LOGGER = LoggerFactory.getLogger(CaseHistoryDAOImpl.class);
	private static final String PACKAGE_NAME = "case_util_pkg";
	private static final String SCHEMA_NAME = "v3owner01";
	private static final String PROC_NAME_ADD_CASE_HIST_ENTRY = "p_add_case_hist_entry";

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplateV3;

	@PostConstruct
	public void initialize() {
		this.jdbcTemplateV3 = new JdbcTemplate(dataSource);
	}

	@Override
	public Long addCaseHistoryEntry(CaseHistory caseHistory) {
		try {
			SimpleJdbcCall procAddCaseHistEntry = new SimpleJdbcCall(jdbcTemplateV3).withCatalogName(PACKAGE_NAME)
					.withSchemaName(SCHEMA_NAME).withProcedureName(PROC_NAME_ADD_CASE_HIST_ENTRY);
			SqlParameterSource params = new MapSqlParameterSource().addValue("a_case_id", 100)
					.addValue("a_doc_id", 100);
			Map outParams = procAddCaseHistEntry.execute(params);
			LOGGER.info("a_result = [{}]", outParams.get("a_result"));
		} catch (Exception e) {
			LOGGER.error("Exception has occured while calling procedure [{}]", PROC_NAME_ADD_CASE_HIST_ENTRY);
			LOGGER.error("Exception is : ", e);
		}
		return 0l;
	}

	@Override
	public Long addCaseHistoryEntry(Double caseId, Double docId, String docName, LawfulOwner lawfulOwner, String userName) {
			
		Long documentId = null;
		BigDecimal bd = null;
		try {
			SimpleJdbcCall procAddCaseHistEntry = new SimpleJdbcCall(jdbcTemplateV3).withCatalogName(PACKAGE_NAME)
					.withSchemaName(SCHEMA_NAME).withProcedureName(PROC_NAME_ADD_CASE_HIST_ENTRY);
			SqlParameterSource params = new MapSqlParameterSource().addValue("a_case_id", caseId)
					.addValue("a_doc_id", docId)
					.addValue("a_user_name", userName)
					.addValue("a_doc_name", docName + " (" + lawfulOwner.getNameLine1() + ")");
			Map outParams = procAddCaseHistEntry.execute(params);			
			Set set = outParams.entrySet();
			Iterator i = set.iterator();
			
			while(i.hasNext()) {
				Map.Entry me = (Map.Entry)i.next();				         
		         bd = (BigDecimal) me.getValue();		         
		         documentId = bd.longValue();
			}	
			
			//LOGGER.info("a_result = [{}]", outParams.get("a_result"));
		} catch (Exception e) {
			LOGGER.error("Exception has occured while calling procedure [{}]", PROC_NAME_ADD_CASE_HIST_ENTRY);
			LOGGER.error("Exception is : ", e);
		}
		return documentId;
	}

}
