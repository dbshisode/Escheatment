package org.occourts.escheatment.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.occourts.escheatment.dao.WorkQueueDataDAO;
import org.occourts.escheatment.model.FormData;
import org.occourts.escheatment.model.LawfulOwner;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.model.WorkQueueData;
import org.occourts.escheatment.Constants;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

/**
* WorkQueueDataDAOImpl contains methods that query the database for counts of items in work queues, 
* and all data necessary to display the content of a work queue
* $Revision: 4692 $     
* $Author: cbarrington $ 
* $Date: 2018-11-21 11:12:05 -0800 (Wed, 21 Nov 2018) $    
*/

public class WorkQueueDataDAOImpl implements WorkQueueDataDAO {

	private DataSource dataSource;
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	public int getCountByTrustNum(String trustNumber) {
		String SQL = "select count(*) from escheatment.trust where trust_num = ?";
		int count = (Integer) this.template.queryForObject(SQL, new Object[] {trustNumber}, Integer.class);
		return count;
	}		
	
	public long getTrustNumByTrustId(long trustId) {
		String SQL = "select trust_num from v3owner01.trust where trust_id = ?";
		long trustNum = (Long) this.template.queryForObject(SQL, new Object[] {trustId}, Long.class);
		return trustNum;
	}	

	public int getOpsActiveCount() {
		String SQL = "select count(*) as cnt from escheatment.trust where status_id = 1 and marked_as_active = 'Y'";
		Integer iOpsActiveCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iOpsActiveCount;
	}

	public int getOpsSentCount() {
		String SQL = "select count(*) as cnt from escheatment.trust where status_id = 2";
		Integer iOpsSentCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iOpsSentCount;
	}

	public boolean MarkAsActive(final long trustId, final String updatedByUserName) {
		
		//update escheatment
		final String escheatmentUpdateSql = "UPDATE escheatment.trust\n" + 
				"SET marked_as_active = 'Y',\n" + 
				"    update_dt = ?,\n" + 
				"    update_user_id = ?\n" + 
				"WHERE trust_id = ?";
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(escheatmentUpdateSql);				
				ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));	
				ps.setString(2, updatedByUserName);
				ps.setLong(3, trustId);
				return ps;					
			}
			
		});	
		
		//update V3
		final String v3UpdateSql = "UPDATE v3owner01.trust\n" + 
				"SET status_CD = 'ACTIVE',\n" + 
				"    update_dt = ?,\n" + 
				"    update_user_id = ?\n" + 
				"WHERE trust_id = ?";
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(v3UpdateSql);				
				ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));	
				ps.setString(2, updatedByUserName);
				ps.setLong(3, trustId);
				return ps;					
			}
			
		});			
		return true;
	}

	public boolean MarkAsNoticeSent(final long trustId, final String updatedByUserName) {
		
		//update escheatment
		final String escheatmentUpdateSql = "UPDATE escheatment.trust\n" + 
				"SET status_id = ?,\n" + 
				"    update_dt = ?,\n" + 
				"    update_user_id = ?\n" + 
				"WHERE trust_id = ?";
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(escheatmentUpdateSql);	
				ps.setInt(1, Constants.NOTICE_OF_UNCLAIMED_FUNDS_SENT_STATUS_ID);
				ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));	
				ps.setString(3, updatedByUserName);
				ps.setLong(4, trustId);
				return ps;					
			}
			
		});	
					
		return true;
	}	
	
	public int getOpsReviewCount() {
		String SQL = "select count(*) as cnt from escheatment.trust where status_id = 1 and marked_as_active = 'N'";
		Integer iOpsReviewCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iOpsReviewCount;
	}

	public List<WorkQueueData> fetchOpsReviewData(String markedAsActive, int statusId) {
		List<WorkQueueData> workQueueDataList = new ArrayList<WorkQueueData>();
		String SQL = "select c.case_id, \n" + 
		        "        c.display_case_num, \n" + 
				"        c.short_title, \n" + 
				"        t.trust_id, \n" + 
				"        t.trust_num, \n" + 
				"        TO_CHAR(t.create_dt,'mm/dd/yyyy') create_dt, \n" + 
				"        t.orig_amt, \n" + 
				"        t.bal, \n" + 
				"        rtv.description as trust_type, \n" + 
				"        (select lo.owner_name_1 || decode(lo.owner_name_2,null,'','<br/>' || lo.owner_name_2) from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as owner_name, \n" +
				"        (select '<br/>' || lo.owner_addr_1 || '<br/>' || decode(lo.owner_addr_2,null,'',lo.owner_addr_2 || '<br/>') || lo.owner_addr_3 from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as owner_address, \n" +
				"        (select nvl(max(lo.owner_id),'0') from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as lawful_owner_id, \n" +				
				"        (select count(*) from escheatment.lawful_owner lo where lo.trust_id = t.trust_id) as owner_cnt, \n" +
				"        (select max(ns.document_id) from escheatment.notice_sent ns where ns.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as document_id, " +
				"        (select '[' || u.first_name || ' ' || u.last_name || ' ' || TO_CHAR(ct.comment_dt,'mm/dd/yy') || ' ' || TO_CHAR(ct.comment_dt,'HH:MI AM') || ']<br>' || ct.comment_text || '<br>' comments \n" + 
				"        from escheatment.comments ct, \n" + 
				"             escheatment.users u \n" + 
				"        where u.user_id = ct.user_id and \n" + 
				"              ct.trust_id = t.trust_id and \n" + 
				"              ct.comment_id = (select max(ct2.comment_id) from escheatment.comments ct2 where ct2.trust_id = ct.trust_id)) comments, \n" + 
				"        (select count(*) from escheatment.comments ct3 where ct3.trust_id = et.trust_id) comment_cnt \n" + 
				" from v3owner01.case c, \n" + 
				"      v3owner01.trust t, \n" + 
				"      v3owner01.fee f, \n" + 
				"      v3owner01.ref_table_value rtv, \n" + 
				"      v3owner01.filing_name_fee_type_lkp ff,\n" + 
				"      escheatment.trust et \n" + 
				" where c.case_id = f.case_id and \n" + 
				"       t.trust_id = f.trust_id and \n" + 
				"       et.trust_id = t.trust_id and \n" + 
				"       et.status_id = ? and \n" + 
				"       et.marked_as_active = ? and \n" + 				
				"       ff.filing_fee_type_id = f.filing_fee_type_id and \n" + 
				"       ff.fee_type_id = rtv.code_id \n" +
				" order by t.trust_id desc";
		List<Map<String, Object>> rows = template.queryForList(SQL, new Object[] { statusId, markedAsActive });
		WorkQueueData wqData = new WorkQueueData();
		for (Map row : rows) {
			wqData = new WorkQueueData();
			wqData.setCaseId(Long.parseLong(row.get("case_id").toString()));
			wqData.setBalance(Float.parseFloat(row.get("bal").toString()));
			wqData.setCaseTitle((row.get("short_title") != null ? row.get("short_title") : "").toString());
			wqData.setLawfulOwnerId(Long.parseLong(row.get("lawful_owner_id").toString()));			
			wqData.setOwnerName((row.get("owner_name") != null ? row.get("owner_name") : "").toString());
			wqData.setOwnerAddress((row.get("owner_address") != null ? row.get("owner_address") : "").toString());
			wqData.setOwnerCnt(Integer.parseInt(row.get("owner_cnt").toString()));			
			wqData.setDocumentId((row.get("document_id") != null ? row.get("document_id") : "").toString());
			wqData.setComments((row.get("comments") != null ? row.get("comments") : "").toString());
			wqData.setDepositDate((row.get("create_dt") != null ? row.get("create_dt") : "").toString());
			wqData.setDisplayCaseNum((row.get("display_case_num") != null ? row.get("display_case_num") : "").toString());
			wqData.setOrigAmt(Float.parseFloat(row.get("orig_amt").toString()));
			wqData.setTrustId(Long.parseLong(row.get("trust_id").toString()));
			wqData.setTrustNum(Long.parseLong(row.get("trust_num").toString()));
			wqData.setTrustType((row.get("trust_type") != null ? row.get("trust_type") : "").toString());
			wqData.setCommentCnt(Integer.parseInt(row.get("comment_cnt").toString()));
			workQueueDataList.add(wqData);
		}
		return workQueueDataList;
	}
	
	public List<WorkQueueData> fetchAcctReviewData(String onHold, String underReview, int statusId) {
		List<WorkQueueData> workQueueDataList = new ArrayList<WorkQueueData>();
		String SQL = "select c.case_id, \n" + 
		        "        c.display_case_num, \n" + 
				"        c.short_title, \n" + 
				"        t.trust_id, \n" + 
				"        t.trust_num, \n" + 
				"        TO_CHAR(t.create_dt,'mm/dd/yyyy') create_dt, \n" + 
				"        t.orig_amt, \n" + 
				"        t.bal, \n" + 
				"        rtv.description as trust_type, \n" + 
				"        decode(lo.under_review,'N',null,'Y','disabled') as under_review, \n" +
				"        (select lo.owner_name_1 || decode(lo.owner_name_2,null,'','<br/>' || lo.owner_name_2) from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as owner_name, \n" +
				"        (select '<br/>' || lo.owner_addr_1 || '<br/>' || decode(lo.owner_addr_2,null,'',lo.owner_addr_2 || '<br/>') || lo.owner_addr_3 from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as owner_address, \n" +
				"        (select nvl(max(lo.owner_id),'0') from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as lawful_owner_id, \n" +				
				"        (select count(*) from escheatment.lawful_owner lo where lo.trust_id = t.trust_id) as owner_cnt, \n" +
				"        (select min(ns.document_id) from escheatment.notice_sent ns where ns.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as document_id, " +
				"        (select '[' || u.first_name || ' ' || u.last_name || ' ' || TO_CHAR(ct.comment_dt,'mm/dd/yy') || ' ' || TO_CHAR(ct.comment_dt,'HH:MI AM') || ']<br>' || ct.comment_text || '<br>' comments \n" + 
				"        from escheatment.comments ct, \n" + 
				"             escheatment.users u \n" + 
				"        where u.user_id = ct.user_id and \n" + 
				"              ct.trust_id = t.trust_id and \n" + 
				"              ct.comment_id = (select max(ct2.comment_id) from escheatment.comments ct2 where ct2.trust_id = ct.trust_id)) comments, \n" + 
				"        (select count(*) from escheatment.comments ct3 where ct3.trust_id = et.trust_id) comment_cnt \n" + 
				" from v3owner01.case c, \n" + 
				"      v3owner01.trust t, \n" + 
				"      v3owner01.fee f, \n" + 
				"      v3owner01.ref_table_value rtv, \n" + 
				"      v3owner01.filing_name_fee_type_lkp ff,\n" + 
				"      escheatment.trust et, \n" +
				"      escheatment.lawful_owner lo \n" +
				" where c.case_id = f.case_id and \n" + 
				"       t.trust_id = f.trust_id and \n" + 
				"       et.trust_id = t.trust_id and \n" + 
				"       lo.trust_id = t.trust_id and \n" +
				"       lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id) and \n" +
				"       et.status_id = ? and \n" + 
				"       et.on_hold_ind = ? and \n" + 				
				"       ff.filing_fee_type_id = f.filing_fee_type_id and \n" + 
				"       ff.fee_type_id = rtv.code_id and \n";
		
				if (underReview == Constants.MARKED_UNDER_REVIEW_YES) {
					SQL += 	"      (select count(*) \n" + 
							"        from escheatment.lawful_owner lo \n" + 
							"        where lo.trust_id = t.trust_id and \n" + 
							"              lo.under_review = 'Y' \n" + 
							"       ) > 0 \n";
				} else {
					SQL += 	"(select count(*) from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.under_review = 'Y') < \n" +
				            "(select count(*) from escheatment.lawful_owner lo where lo.trust_id = t.trust_id) \n";
				}
				
				SQL += " order by t.trust_id desc";				
				
		List<Map<String, Object>> rows = template.queryForList(SQL, new Object[] { statusId, onHold });
		WorkQueueData wqData = new WorkQueueData();
		for (Map row : rows) {
			wqData = new WorkQueueData();
			wqData.setCaseId(Long.parseLong(row.get("case_id").toString()));
			wqData.setBalance(Float.parseFloat(row.get("bal").toString()));
			wqData.setCaseTitle((row.get("short_title") != null ? row.get("short_title") : "").toString());
			wqData.setLawfulOwnerId(Long.parseLong(row.get("lawful_owner_id").toString()));			
			wqData.setOwnerName((row.get("owner_name") != null ? row.get("owner_name") : "").toString());
			wqData.setOwnerAddress((row.get("owner_address") != null ? row.get("owner_address") : "").toString());
			wqData.setOwnerCnt(Integer.parseInt(row.get("owner_cnt").toString()));
			wqData.setUnderReview((row.get("under_review") != null ? row.get("under_review") : "").toString());
			wqData.setDocumentId((row.get("document_id") != null ? row.get("document_id") : "").toString());
			wqData.setComments((row.get("comments") != null ? row.get("comments") : "").toString());
			wqData.setDepositDate((row.get("create_dt") != null ? row.get("create_dt") : "").toString());
			wqData.setDisplayCaseNum((row.get("display_case_num") != null ? row.get("display_case_num") : "").toString());
			wqData.setOrigAmt(Float.parseFloat(row.get("orig_amt").toString()));
			wqData.setTrustId(Long.parseLong(row.get("trust_id").toString()));
			wqData.setTrustNum(Long.parseLong(row.get("trust_num").toString()));
			wqData.setTrustType((row.get("trust_type") != null ? row.get("trust_type") : "").toString());
			wqData.setCommentCnt(Integer.parseInt(row.get("comment_cnt").toString()));
			workQueueDataList.add(wqData);
		}
		return workQueueDataList;
	}	

	public boolean MarkIdentifiedForEscheatment(final long trustId, final String updatedByUserName) {
		//update escheatment
		final String escheatmentUpdateSql = "UPDATE escheatment.trust\n" + 
				"SET marked_as_active = 'N',\n" + 
				"    update_dt = ?,\n" + 
				"    update_user_id = ?\n" + 
				"WHERE trust_id = ?";
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(escheatmentUpdateSql);				
				ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));	
				ps.setString(2, updatedByUserName);
				ps.setLong(3, trustId);
				return ps;					
			}
			
		});	
		
		//update V3
		final String v3UpdateSql = "UPDATE v3owner01.trust\n" + 
				"SET status_CD = 'IDENTIFIED FOR ESCHEATMENT',\n" + 
				"    update_dt = ?,\n" + 
				"    update_user_id = ?\n" + 
				"WHERE trust_id = ?";
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(v3UpdateSql);				
				ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));	
				ps.setString(2, updatedByUserName);
				ps.setLong(3, trustId);
				return ps;					
			}
			
		});			
		return true;

	}
	
	public List<LawfulOwner> fetchAdditionalLawfulOwners(Long trustId) {
		List<LawfulOwner> lawfulOwners = new ArrayList<LawfulOwner>();
		String SQL = "select lo.owner_id, \n" +
		        "       lo.owner_name_1, \n" + 				
				"       lo.owner_name_2, \n" + 
				"       lo.owner_addr_1, \n" + 
				"       lo.owner_addr_2, \n" + 
				"       lo.owner_addr_3, \n" + 
				"       decode(under_review,'N',null,'Y','disabled') as under_review, \n" +
				"       (select ns.document_id from escheatment.notice_sent ns where ns.owner_id = lo.owner_id) as document_id " +
				"from escheatment.lawful_owner lo  \n" + 
				"where lo.trust_id = ? and  \n" + 
				"      lo.owner_id > (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = ?)";
		List<Map<String, Object>> rows = template.queryForList(SQL, new Object[] { trustId, trustId });
		LawfulOwner lawfulOwner = new LawfulOwner();
		for (Map row : rows) {
			lawfulOwner = new LawfulOwner();
			lawfulOwner.setId((BigDecimal) (row.get("owner_id") != null ? row.get("owner_id") : BigDecimal.ZERO));
			lawfulOwner.setAddressLine1((row.get("owner_addr_1") != null ? row.get("owner_addr_1") : "").toString());
			lawfulOwner.setAddressLine2((row.get("owner_addr_2") != null ? row.get("owner_addr_2") : "").toString());
			lawfulOwner.setAddressLine3((row.get("owner_addr_3") != null ? row.get("owner_addr_3") : "").toString());
			lawfulOwner.setNameLine1((row.get("owner_name_1") != null ? row.get("owner_name_1") : "").toString());
			lawfulOwner.setNameLine2((row.get("owner_name_2") != null ? row.get("owner_name_2") : "").toString());
			lawfulOwner.setDocumentId((row.get("document_id") != null ? row.get("document_id") : "").toString());
			lawfulOwner.setUnderReview((row.get("under_review") != null ? row.get("under_review") : "").toString());
			lawfulOwners.add(lawfulOwner);
		}
		return lawfulOwners;
	}

	public int getAcctReviewCount() {
		String SQL = "select count(*) " +
				  "   from escheatment.trust et \n" +
				  "   where et.status_id = 2 and \n" +
				  "         et.on_hold_ind = 'N' and \n" +
				  "         (select count(*) from escheatment.lawful_owner lo where lo.trust_id = et.trust_id and lo.under_review = 'Y') < \n" + 
				  "			(select count(*) from escheatment.lawful_owner lo where lo.trust_id = et.trust_id)";
		Integer iAcctReviewCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iAcctReviewCount;
	}

	public int getAcctPendingCount() {
		String SQL = "select count(*) as cnt from escheatment.trust where status_id in (4,5) and on_hold_ind = 'N'";
		Integer iAcctPendingCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iAcctPendingCount;
	}

	public int getAcctOnHoldCount() {
		String SQL = "select count(*) as cnt from escheatment.trust where on_hold_ind = 'Y'";
		Integer iAcctOnHoldCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iAcctOnHoldCount;
	}

	public int getAcctUnderReviewCount() {
		String SQL = "select count(*) as cnt from escheatment.lawful_owner where under_review = 'Y'";
		Integer iAcctUnderReviewCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iAcctUnderReviewCount;
	}

	public FormData fetchTrustInfoByTrustNum(String trustNum) {
		
		String SQL = "select c.display_case_num, \n" + 
				"       c.short_title, \n" + 
				"       cl.description as court_location, \n" + 
				"       a.street_num || ' ' || decode(a.street_direction,null,'',a.street_direction || ' ') || a.street_name || ' ' || a.street_type as court_addr_line1, \n" +
				"       a.city || ', ' || a.state || ' ' || a.zip_code as court_addr_line2, \n" +
				"       t.trust_id, \n" + 
				"       t.trust_num, \n" + 
				"       TO_CHAR(t.create_dt,'mm/dd/yyyy') create_dt, \n" + 
				"       t.bal, \n" + 
				"       rtv.description as trust_type, \n" + 
				//"       (select eu.first_name || ' ' || eu.last_name from escheatment.users eu where eu.user_name = ?) clerk_name, \n" + 
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
				" t.trust_num = ?";						
		
		FormData formData = new FormData();
		
		Map row = DataAccessUtils.singleResult(template.queryForList(SQL, new Object[] { trustNum }));
		if (row != null) {
			formData.setCaseNumber((row.get("display_case_num") != null ? row.get("display_case_num") : "").toString());
			formData.setCaseTitle((row.get("short_title") != null ? row.get("short_title") : "").toString());
			formData.setClaimDate((row.get("claim_date") != null ? row.get("claim_date") : "").toString());
			//formData.setClerkName((row.get("clerk_name") != null ? row.get("clerk_name") : "").toString());
			formData.setTrustAmount((BigDecimal) (row.get("bal") != null ? row.get("bal") : BigDecimal.ZERO));
			formData.setTrustDate((row.get("create_dt") != null ? row.get("create_dt") : "").toString());
			formData.setTrustId(Long.parseLong(row.get("trust_id").toString()));
			formData.setTrustNumber((row.get("trust_num") != null ? row.get("trust_num") : "").toString());
			formData.setTrustType((row.get("trust_type") != null ? row.get("trust_type") : "").toString());	
			formData.setCourtLocation((row.get("court_location") != null ? row.get("court_location") : "").toString());
			formData.setCourtAddrLine1((row.get("court_addr_line1") != null ? row.get("court_addr_line1") : "").toString());
			formData.setCourtAddrLine2((row.get("court_addr_line2") != null ? row.get("court_addr_line2") : "").toString());
		}
		return formData;
	}

	public void insertEscheatmentTrust(final Long trustId, final String trustNumber, final int statusId, final String userName) {
		
		final String trustInsertSql = "INSERT INTO escheatment.trust (TRUST_ID,TRUST_NUM,MARKED_AS_ACTIVE,STATUS_ID,DELETE_IND,ON_HOLD_IND,CREATE_USER_ID,CREATE_DT,UPDATE_USER_ID,UPDATE_DT) values (?,?,?,?,?,?,?,?,?,?)";

		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(trustInsertSql);
				ps.setLong(1, trustId);
				ps.setString(2, trustNumber);
				ps.setString(3, "N"); 
				ps.setInt(4, statusId);
				ps.setString(5, "N"); 
				ps.setString(6, "N");
				ps.setString(7, userName);
				ps.setDate(8, new java.sql.Date(System.currentTimeMillis()));
				ps.setString(9, userName);
				ps.setDate(10, new java.sql.Date(System.currentTimeMillis()));				
				return ps;					
			}			
		});
			
	}

	public boolean MarkOnHold(final long trustId, final String updatedByUserName) {

		final String escheatmentUpdateSql = "UPDATE escheatment.trust\n" + 
				"SET on_hold_ind = 'Y',\n" + 
				"    update_dt = ?,\n" + 
				"    update_user_id = ?\n" + 
				"WHERE trust_id = ?";
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(escheatmentUpdateSql);				
				ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));	
				ps.setString(2, updatedByUserName);
				ps.setLong(3, trustId);
				return ps;					
			}
			
		});	
					
		return true;
	}

	public boolean SentForReview(final long trustId, final long lawfulOwnerId, final String updatedByUserName) {

		final String escheatmentUpdateSql = "UPDATE escheatment.lawful_owner \n" + 
				"SET under_review = 'Y', \n" + 
				"    update_dt = ?, \n" + 
				"    update_user_id = ? \n" + 
				"WHERE trust_id = ? and \n" +
				"      owner_id = ?";
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(escheatmentUpdateSql);								
				ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));	
				ps.setString(2, updatedByUserName);
				ps.setLong(3, trustId);
				ps.setLong(4, lawfulOwnerId);
				return ps;					
			}
			
		});	
					
		return true;
	}

	public boolean MarkReadyForPublication(List<WorkQueueData> workQueueData, final String updatedByUserName) {

		for (final WorkQueueData wqData : workQueueData) {
			final String escheatmentUpdateSql = "UPDATE escheatment.trust\n" + 
					"SET status_id = ?,\n" + 
					"    update_dt = ?,\n" + 
					"    update_user_id = ?\n" + 
					"WHERE trust_id = ?";
			
			template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(escheatmentUpdateSql);				
					ps.setInt(1, Constants.READY_FOR_PUBLICATION_STATUS_ID);
					ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));	
					ps.setString(3, updatedByUserName);
					ps.setLong(4, wqData.getTrustId());
					return ps;					
				}
				
			});
		};
		
		return true;
	}
	
	public boolean ReturnPendingItemsToReviewStatus(final String updatedByUserName) {

		final String escheatmentUpdateSql = "UPDATE escheatment.trust\n" + 
				"SET status_id = ?,\n" + 
				"    update_dt = ?,\n" + 
				"    update_user_id = ?\n" + 
				"WHERE status_id = ?";
		
		template.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(escheatmentUpdateSql);				
				ps.setInt(1, Constants.NOTICE_OF_UNCLAIMED_FUNDS_SENT_STATUS_ID);
				ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));	
				ps.setString(3, updatedByUserName);
				ps.setLong(4, Constants.READY_FOR_PUBLICATION_STATUS_ID);
				return ps;					
			}
			
		});
		
		return true;
	}	
	
	public boolean FinalizeList(final String updatedByUserName) {

		//get pending trusts
		List<WorkQueueData> pendingTrusts = fetchAcctReviewData(Constants.MARKED_ON_HOLD_NO,Constants.MARKED_UNDER_REVIEW_NO,Constants.READY_FOR_PUBLICATION_STATUS_ID);			
		
		//generate PDF		
		try {
			File file = new File("c:\\temp\\output.v3pdf");
			final FileInputStream input = new FileInputStream(file);		
			
			//insert into escheatment.publication
			final String publicationInsertSql = "INSERT INTO escheatment.publication (PUBLICATION_ID,FINALIZED,OUTPUT_PDF,CREATE_USER_ID,CREATE_DT,UPDATE_USER_ID,UPDATE_DT) values (escheatment.publication_seq.nextval,?,?,?,?,?,?)";
			GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
			final String idColumn = "PUBLICATION_ID";
			
			template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(publicationInsertSql, new String[] {idColumn});
					ps.setString(1, Constants.FINALIZED_YES); 
					ps.setBinaryStream(2, input);
					ps.setString(3, updatedByUserName);
					ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
					ps.setString(5, updatedByUserName);
					ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));				
					return ps;					
				}			
			}, keyHolder);
			
			BigDecimal newPublicationId = (BigDecimal) keyHolder.getKeys().get(idColumn);
			final long newPublicationIdLong = newPublicationId.longValue();			
			
			//insert into escheatment.publication_trust_assoc (loop)
			for (final WorkQueueData trust : pendingTrusts) {
				final String publicationTrustAssocInsertSql = "INSERT INTO escheatment.publication_trust_assoc (PUB_ASSOC_ID,TRUST_ID,PUBLICATION_ID) values (escheatment.pub_trust_assoc_seq.nextval,?,?)";

				template.update(new PreparedStatementCreator() {
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement ps = con.prepareStatement(publicationTrustAssocInsertSql);
						ps.setLong(1, trust.getTrustId()); 
						ps.setLong(2, newPublicationIdLong);								
						return ps;					
					}			
				});					
			}
			
			//update statuses
			final String escheatmentUpdateSql = "UPDATE escheatment.trust\n" + 
					"SET status_id = ?,\n" + 
					"    update_dt = ?,\n" + 
					"    update_user_id = ?\n" + 
					"WHERE status_id = ?";
			
			template.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement ps = con.prepareStatement(escheatmentUpdateSql);				
					ps.setInt(1, Constants.FINALIZED_STATUS_ID);
					ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));	
					ps.setString(3, updatedByUserName);
					ps.setLong(4, Constants.READY_FOR_PUBLICATION_STATUS_ID);
					return ps;					
				}
				
			});	
			
			return true;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
				
	}		
	
	public WorkQueueData fetchWorkQueueDataByTrustId(Long trustId) {
		String SQL = "select c.case_id, \n" + 
		        "        c.display_case_num, \n" + 
				"        c.short_title, \n" + 
				"        t.trust_id, \n" + 
				"        t.trust_num, \n" + 
				"        TO_CHAR(t.create_dt,'mm/dd/yyyy') create_dt, \n" + 
				"        t.orig_amt, \n" + 
				"        t.bal, \n" + 
				"        rtv.description as trust_type, \n" + 
				"        decode(lo.under_review,'N',null,'Y','disabled') as under_review, \n" +
				"        (select lo.owner_name_1 || decode(lo.owner_name_2,null,'','<br/>' || lo.owner_name_2) from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as owner_name, \n" +
				"        (select '<br/>' || lo.owner_addr_1 || '<br/>' || decode(lo.owner_addr_2,null,'',lo.owner_addr_2 || '<br/>') || lo.owner_addr_3 from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as owner_address, \n" +
				"        (select nvl(max(lo.owner_id),'0') from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as lawful_owner_id, \n" +				
				"        (select count(*) from escheatment.lawful_owner lo where lo.trust_id = t.trust_id) as owner_cnt, \n" +
				"        (select max(ns.document_id) from escheatment.notice_sent ns where ns.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as document_id, " +
				"        (select '[' || u.first_name || ' ' || u.last_name || ' ' || TO_CHAR(ct.comment_dt,'mm/dd/yy') || ' ' || TO_CHAR(ct.comment_dt,'HH:MI AM') || ']<br>' || ct.comment_text || '<br>' comments \n" + 
				"        from escheatment.comments ct, \n" + 
				"             escheatment.users u \n" + 
				"        where u.user_id = ct.user_id and \n" + 
				"              ct.trust_id = t.trust_id and \n" + 
				"              ct.comment_id = (select max(ct2.comment_id) from escheatment.comments ct2 where ct2.trust_id = ct.trust_id)) comments, \n" + 
				"        (select count(*) from escheatment.comments ct3 where ct3.trust_id = et.trust_id) comment_cnt \n" + 
				" from v3owner01.case c, \n" + 
				"      v3owner01.trust t, \n" + 
				"      v3owner01.fee f, \n" + 
				"      v3owner01.ref_table_value rtv, \n" + 
				"      v3owner01.filing_name_fee_type_lkp ff,\n" + 
				"      escheatment.trust et, \n" +
				"      escheatment.lawful_owner lo \n" +
				" where c.case_id = f.case_id and \n" + 
				"       t.trust_id = f.trust_id and \n" + 
				"       et.trust_id = t.trust_id and \n" + 
				"       lo.trust_id = t.trust_id and \n" +
				"       lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id) and \n" +
				"       ff.filing_fee_type_id = f.filing_fee_type_id and \n" + 
				"       ff.fee_type_id = rtv.code_id and \n" +
				"       t.trust_id = ? \n" +
				" order by t.trust_id desc";				
		WorkQueueData wqData = null;
		Map row = DataAccessUtils.singleResult(template.queryForList(SQL, new Object[] { trustId }));
		if (row != null) {
			wqData = new WorkQueueData();
			wqData.setCaseId(Long.parseLong(row.get("case_id").toString()));
			wqData.setBalance(Float.parseFloat(row.get("bal").toString()));
			wqData.setCaseTitle((row.get("short_title") != null ? row.get("short_title") : "").toString());
			wqData.setLawfulOwnerId(Long.parseLong(row.get("lawful_owner_id").toString()));			
			wqData.setOwnerName((row.get("owner_name") != null ? row.get("owner_name") : "").toString());
			wqData.setOwnerAddress((row.get("owner_address") != null ? row.get("owner_address") : "").toString());
			wqData.setOwnerCnt(Integer.parseInt(row.get("owner_cnt").toString()));
			wqData.setUnderReview((row.get("under_review") != null ? row.get("under_review") : "").toString());
			wqData.setDocumentId((row.get("document_id") != null ? row.get("document_id") : "").toString());
			wqData.setComments((row.get("comments") != null ? row.get("comments") : "").toString());
			wqData.setDepositDate((row.get("create_dt") != null ? row.get("create_dt") : "").toString());
			wqData.setDisplayCaseNum((row.get("display_case_num") != null ? row.get("display_case_num") : "").toString());
			wqData.setOrigAmt(Float.parseFloat(row.get("orig_amt").toString()));
			wqData.setTrustId(Long.parseLong(row.get("trust_id").toString()));
			wqData.setTrustNum(Long.parseLong(row.get("trust_num").toString()));
			wqData.setTrustType((row.get("trust_type") != null ? row.get("trust_type") : "").toString());
			wqData.setCommentCnt(Integer.parseInt(row.get("comment_cnt").toString()));			
		}
		return wqData;
	}	
	
	public int getPublicationPendingCount() {
		String SQL = "select count(*) from escheatment.publication p where p.publication_dt is null and p.finalized = 'Y'";
		Integer iPubPendingCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iPubPendingCount;
	}
	
	public List<WorkQueueData> fetchPublicationBatchesPendingPubDate(String finalized,int batchNum) {
		List<WorkQueueData> workQueueDataList = new ArrayList<WorkQueueData>();
		String SQL = "select p.publication_id,\n" + 
				"       p.create_dt,\n" + 
				"       c.case_id,  \n" +
				"       c.display_case_num,  \n" + 
				"       c.short_title,  \n" + 
				"       cl.description as court_location,  \n" + 
				"       a.street_num || ' ' || decode(a.street_direction,null,'',a.street_direction || ' ') || a.street_name || ' ' || a.street_type as court_addr_line1, \n" + 
				"       a.city || ', ' || a.state || ' ' || a.zip_code as court_addr_line2, \n" + 
				"        (select lo.owner_name_1 || decode(lo.owner_name_2,null,'','<br/>' || lo.owner_name_2) from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as owner_name, \n" +
				"        (select '<br/>' || lo.owner_addr_1 || '<br/>' || decode(lo.owner_addr_2,null,'',lo.owner_addr_2 || '<br/>') || lo.owner_addr_3 from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as owner_address, \n" +								
				"        (select nvl(max(lo.owner_id),'0') from escheatment.lawful_owner lo where lo.trust_id = t.trust_id and lo.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as lawful_owner_id, \n" +
				"        (select count(*) from escheatment.lawful_owner lo where lo.trust_id = t.trust_id) as owner_cnt, \n" +
				"        (select min(ns.document_id) from escheatment.notice_sent ns where ns.owner_id = (select min(lo2.owner_id) from escheatment.lawful_owner lo2 where lo2.trust_id = t.trust_id)) as document_id, " +
				"        (select '[' || u.first_name || ' ' || u.last_name || ' ' || TO_CHAR(ct.comment_dt,'mm/dd/yy') || ' ' || TO_CHAR(ct.comment_dt,'HH:MI AM') || ']<br>' || ct.comment_text || '<br>' comments \n" + 
				"        from escheatment.comments ct, \n" + 
				"             escheatment.users u \n" + 
				"        where u.user_id = ct.user_id and \n" + 
				"              ct.trust_id = t.trust_id and \n" + 
				"              ct.comment_id = (select max(ct2.comment_id) from escheatment.comments ct2 where ct2.trust_id = ct.trust_id)) comments, \n" + 
				"        (select count(*) from escheatment.comments ct3 where ct3.trust_id = t.trust_id) comment_cnt, \n" + 				
				"       t.trust_id,  \n" + 
				"       t.trust_num,  \n" + 
				"       t.orig_amt, \n" + 
				"       TO_CHAR(t.create_dt,'mm/dd/yyyy') create_dt,  \n" + 
				"       t.bal,  \n" + 
				"       rtv.description as trust_type \n" + 
				"from escheatment.publication p,\n" + 
				"     escheatment.publication_trust_assoc pta,\n" + 
				"     v3owner01.case c,  \n" + 
				"     v3owner01.court_location cl,  \n" + 
				"     v3owner01.court_loc_addr_assoc claa,  \n" + 
				"     v3owner01.address a, \n" + 
				"     v3owner01.trust t,  \n" + 
				"     v3owner01.fee f,  \n" + 
				"     v3owner01.ref_table_value rtv,  \n" + 
				"     v3owner01.filing_name_fee_type_lkp ff     \n" + 
				"where p.publication_id = pta.publication_id and\n" + 
				"      p.publication_dt is null and\n" + 
				"      p.finalized = ? and\n" + 
				"      c.case_id = f.case_id and  \n" + 
				"      c.asgn_court_loc_id = cl.court_loc_id and  \n" + 
				"      claa.court_loc_id = cl.court_loc_id and  \n" + 
				"      claa.addr_id = a.addr_id and \n" + 
				"      a.addr_type_id = 67548 and\n" + 
				"      t.trust_id = f.trust_id and  \n" + 
				"      ff.filing_fee_type_id = f.filing_fee_type_id and  \n" + 
				"      ff.fee_type_id = rtv.code_id and  \n" + 
				"      t.trust_id = pta.trust_id and \n" +
				"      p.publication_id = (select min(publication_id) + ? from escheatment.publication p where p.publication_dt is null and p.finalized = ?)";
		List<Map<String, Object>> rows = template.queryForList(SQL, new Object[] { finalized,batchNum,finalized });
		WorkQueueData wqData = new WorkQueueData();
		for (Map row : rows) {
			wqData = new WorkQueueData();
			wqData.setCaseId(Long.parseLong(row.get("case_id").toString()));
			wqData.setBalance(Float.parseFloat(row.get("bal").toString()));
			wqData.setCaseTitle((row.get("short_title") != null ? row.get("short_title") : "").toString());
			wqData.setLawfulOwnerId(Long.parseLong(row.get("lawful_owner_id").toString()));			
			wqData.setOwnerName((row.get("owner_name") != null ? row.get("owner_name") : "").toString());
			wqData.setOwnerAddress((row.get("owner_address") != null ? row.get("owner_address") : "").toString());
			wqData.setOwnerCnt(Integer.parseInt(row.get("owner_cnt").toString()));			
			wqData.setDocumentId((row.get("document_id") != null ? row.get("document_id") : "").toString());
			wqData.setComments((row.get("comments") != null ? row.get("comments") : "").toString());
			wqData.setDepositDate((row.get("create_dt") != null ? row.get("create_dt") : "").toString());
			wqData.setDisplayCaseNum((row.get("display_case_num") != null ? row.get("display_case_num") : "").toString());
			wqData.setOrigAmt(Float.parseFloat(row.get("orig_amt").toString()));
			wqData.setTrustId(Long.parseLong(row.get("trust_id").toString()));
			wqData.setTrustNum(Long.parseLong(row.get("trust_num").toString()));
			wqData.setTrustType((row.get("trust_type") != null ? row.get("trust_type") : "").toString());
			wqData.setCommentCnt(Integer.parseInt(row.get("comment_cnt").toString()));
			workQueueDataList.add(wqData);
		}
		return workQueueDataList;
	}	

}
