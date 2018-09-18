package org.occourts.escheatment.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.occourts.escheatment.dao.WorkQueueDataDAO;
import org.occourts.escheatment.model.User;
import org.occourts.escheatment.model.WorkQueueData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

/**
* WorkQueueDataDAOImpl contains methods that query the database for counts of items in work queues, 
* and all data necessary to display the content of a work queue
* $Revision: 4523 $     
* $Author: cbarrington $ 
* $Date: 2018-09-14 09:26:52 -0700 (Fri, 14 Sep 2018) $    
*/

public class WorkQueueDataDAOImpl implements WorkQueueDataDAO {

	private DataSource dataSource;
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
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

	public int getOpsReviewCount() {
		String SQL = "select count(*) as cnt from escheatment.trust where status_id = 1 and marked_as_active = 'N'";
		Integer iOpsReviewCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iOpsReviewCount;
	}

	public List<WorkQueueData> fetchOpsReviewData() {
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
				"        null as ownerName, \n" +
				"        null as ownerAddress, \n" +
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
				"       et.status_id = 1 and \n" + 
				"       et.marked_as_active = 'N' and \n" + 
				"       ff.filing_fee_type_id = f.filing_fee_type_id and \n" + 
				"       ff.fee_type_id = rtv.code_id" +
				" order by t.trust_id desc";
		List<Map<String, Object>> rows = template.queryForList(SQL);
		WorkQueueData wqData = new WorkQueueData();
		for (Map row : rows) {
			wqData = new WorkQueueData();
			wqData.setCaseId(Long.parseLong(row.get("case_id").toString()));
			wqData.setBalance(Float.parseFloat(row.get("bal").toString()));
			wqData.setCaseTitle((row.get("short_title") != null ? row.get("short_title") : "").toString());
			wqData.setOwnerName((row.get("ownerName") != null ? row.get("ownerName") : "").toString());
			wqData.setOwnerAddress((row.get("ownerAddress") != null ? row.get("ownerAddress") : "").toString());
			wqData.setComments((row.get("comments") != null ? row.get("comments") : "").toString());
			wqData.setDepositDate((row.get("create_dt") != null ? row.get("create_dt") : "").toString());
			wqData.setDisplayCaseNum(
					(row.get("display_case_num") != null ? row.get("display_case_num") : "").toString());
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
