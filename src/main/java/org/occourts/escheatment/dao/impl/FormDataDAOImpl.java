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

import org.occourts.escheatment.Constants;
import org.occourts.escheatment.dao.FormDataDAO;
import org.occourts.escheatment.dao.LawfulOwnerDAO;
import org.occourts.escheatment.model.FormData;
import org.occourts.escheatment.model.LawfulOwner;
import org.occourts.escheatment.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

/**
* FormDataDAOImpl provides methods to return data required by PDF forms
* $Revision: 4519 $     
* $Author: cbarrington $ 
* $Date: 2018-09-04 13:38:45 -0700 (Tue, 04 Sep 2018) $    
*/

public class FormDataDAOImpl implements FormDataDAO {

	private DataSource dataSource;
	JdbcTemplate template;
	
	@Autowired
	WorkQueueDataDAOImpl wqdatadao;	

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public FormData fetchFormDataByTrustId(long trustId, String userName) {
		
			
			String SQL = "select c.display_case_num, \n" + 
					"       c.case_id, \n" + 
					"       c.short_title, \n" + 
					"       cl.description as court_location, \n" + 
					"       a.street_num || ' ' || decode(a.street_direction,null,'',a.street_direction || ' ') || a.street_name || ' ' || a.street_type as court_addr_line1, \n" +
					"       a.city || ', ' || a.state || ' ' || a.zip_code as court_addr_line2, \n" +
					"       t.trust_num, \n" + 
					"       TO_CHAR(t.create_dt,'mm/dd/yyyy') create_dt, \n" + 
					"       t.bal, \n" + 
					"       rtv.description as trust_type, \n" + 
					"       (select eu.first_name || ' ' || eu.last_name from escheatment.users eu where eu.user_name = ?) clerk_name, \n" + 
					"       case \n" + 
					"         when c.case_cat_id in (200000,20003) then \n" + 
					"           'Probate' \n" + 
					"         else \n" + 
					"           'Civil' \n" + 
					"         end case_cat, \n" +
					"       '11/22/3333' claim_date \n" + 
					"from v3owner01.case c, \n" + 
					"v3owner01.court_location cl, \n" + 
					"v3owner01.court_loc_addr_assoc claa, \n" + 
					"v3owner01.address a, \n" +
					"v3owner01.trust t, \n" + 
					"v3owner01.fee f, \n" + 
					"v3owner01.ref_table_value rtv, \n" + 
					"v3owner01.filing_name_fee_type_lkp ff \n" + 
					"where c.case_id = f.case_id and \n" + 
					" c.asgn_court_loc_id = cl.court_loc_id and \n" + 
					" claa.court_loc_id = cl.court_loc_id and \n" + 
					" claa.addr_id = a.addr_id and \n" +
					" a.addr_type_id = 67548 and" +
					" t.trust_id = f.trust_id and \n" + 
					" ff.filing_fee_type_id = f.filing_fee_type_id and \n" + 
					" ff.fee_type_id = rtv.code_id and \n" + 
					" t.trust_id = ?";						
			FormData formData = new FormData();
			
			Map row = DataAccessUtils.singleResult(template.queryForList(SQL, new Object[] { userName, trustId }));
			if (row != null) {
				formData.setCaseId(Double.parseDouble(row.get("case_id").toString()));
				formData.setCaseNumber((row.get("display_case_num") != null ? row.get("display_case_num") : "").toString());
				formData.setCaseTitle((row.get("short_title") != null ? row.get("short_title") : "").toString());
				formData.setCaseCategory((row.get("case_cat") != null ? row.get("case_cat") : "").toString());
				formData.setClaimDate((row.get("claim_date") != null ? row.get("claim_date") : "").toString());
				formData.setClerkName((row.get("clerk_name") != null ? row.get("clerk_name") : "").toString());
				formData.setTrustAmount((BigDecimal) (row.get("bal") != null ? row.get("bal") : BigDecimal.ZERO));
				formData.setTrustDate((row.get("create_dt") != null ? row.get("create_dt") : "").toString());
				formData.setTrustNumber((row.get("trust_num") != null ? row.get("trust_num") : "").toString());
				formData.setTrustType((row.get("trust_type") != null ? row.get("trust_type") : "").toString());	
				formData.setCourtLocation((row.get("court_location") != null ? row.get("court_location") : "").toString());
				formData.setCourtAddrLine1((row.get("court_addr_line1") != null ? row.get("court_addr_line1") : "").toString());
				formData.setCourtAddrLine2((row.get("court_addr_line2") != null ? row.get("court_addr_line2") : "").toString());
			}
			return formData;
		}

	@Override
	public boolean insertIntoNoticeSent(final FormData formData, final LawfulOwner lawfulOwner, final String createdByUserName) {
		
		if (formData.getNoticeId() == 0 || lawfulOwner.getId() == null || formData.getDocumentId() == null) {
			return false;
		}
		
		final String insertIntoNoticeSentSql = "INSERT INTO escheatment.notice_sent (notice_sent_id,notice_id,owner_id,notice_send_dt,document_id,create_user_id,create_dt,update_user_id,update_dt) values (escheatment.notice_sent_seq.nextval,?,?,?,?,?,?,?,?)";
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(insertIntoNoticeSentSql);
				ps.setInt(1, formData.getNoticeId());
				ps.setLong(2, lawfulOwner.getId().longValue());
				ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
				ps.setLong(4, formData.getDocumentId());
				ps.setString(5, createdByUserName);
				ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(7, createdByUserName);
				ps.setDate(8, new java.sql.Date(System.currentTimeMillis()));				
				return ps;					
			}
			
		});	
		
		//update trust status
		if (formData.getNoticeId() == Constants.NOTICE_OF_UNCLAIMED_FUNDS_NOTICE_ID) {
			wqdatadao.MarkAsNoticeSent(formData.getTrustId(), createdByUserName);
		}
		
		
		return true;
	}

	}

