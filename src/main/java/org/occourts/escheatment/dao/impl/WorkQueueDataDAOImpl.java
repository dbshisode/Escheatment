package org.occourts.escheatment.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.occourts.escheatment.dao.WorkQueueDataDAO;
import org.occourts.escheatment.model.WorkQueueData;
import org.springframework.jdbc.core.JdbcTemplate;

/**
* WorkQueueDataDAOImpl contains methods that query the database for counts of items in work queues, 
* and all data necessary to display the content of a work queue
* $Revision: 4500 $     
* $Author: cbarrington $ 
* $Date: 2018-08-14 15:57:31 -0700 (Tue, 14 Aug 2018) $    
*/

public class WorkQueueDataDAOImpl implements WorkQueueDataDAO {

	private DataSource dataSource;
	JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
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

	public String MarkAsActive() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getOpsReviewCount() {
		String SQL = "select count(*) as cnt from escheatment.trust where status_id = 1 and marked_as_active = 'N'";
		Integer iOpsReviewCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iOpsReviewCount;
	}

	public List<WorkQueueData> fetchOpsReviewData() {
		List<WorkQueueData> workQueueDataList = new ArrayList<WorkQueueData>();
		String SQL = "select c.display_case_num,\n" + 
		             "       c.short_title,\n" + 
				     "       t.trust_id,\n" +
				     "       t.trust_num,\n" + 
				     "       TO_CHAR(t.create_dt,'mm/dd/yyyy') create_dt,\n" + 
				     "       t.orig_amt,\n" + "       t.bal,\n" + 
				     "       rtv.description as trust_type,\n" + 
				     "       (select listagg('<b>[' || (select u.first_name || ' ' || u.last_name from escheatment.users u where u.user_id = ct.user_id) || ' ' || ct.comment_dt || ']</b><br>' || ct.comment_text, '<br>') within group (order by ct.comment_dt) from escheatment.comments ct where ct.trust_id = t.trust_id) comments\n" + 
				     "from v3owner01.case c,\n" + 
				     "     v3owner01.trust t,\n" + 
				     "     v3owner01.fee f,\n" + 
				     "     v3owner01.ref_table_value rtv,\n" +
				     "     v3owner01.filing_name_fee_type_lkp ff,\n" + 
				     "     escheatment.trust et\n" + 
				     "where c.case_id = f.case_id and\n" +
				     "      t.trust_id = f.trust_id and\n" + 
				     "      et.trust_id = t.trust_id and\n"	+
				     "      et.status_id = 1 and\n" +
				     "      et.marked_as_active = 'N' and\n" +
				     "      ff.filing_fee_type_id = f.filing_fee_type_id and\n" +
				     "      ff.fee_type_id = rtv.code_id";
		List<Map<String, Object>> rows = template.queryForList(SQL);
		WorkQueueData wqData = new WorkQueueData();
		for (Map row : rows) {
			wqData = new WorkQueueData();
			wqData.setBalance(Float.parseFloat(row.get("bal").toString()));
			wqData.setCaseTitle((row.get("short_title") != null ? row.get("short_title") : "-").toString());
			wqData.setComments((row.get("comments") != null ? row.get("comments") : "-").toString());
			wqData.setDepositDate((row.get("create_dt") != null ? row.get("create_dt") : "-").toString());
			wqData.setDisplayCaseNum(
					(row.get("display_case_num") != null ? row.get("display_case_num") : "-").toString());
			wqData.setOrigAmt(Float.parseFloat(row.get("orig_amt").toString()));
			wqData.setTrustId(Long.parseLong(row.get("trust_id").toString()));
			wqData.setTrustNum(Long.parseLong(row.get("trust_num").toString()));
			wqData.setTrustType((row.get("trust_type") != null ? row.get("trust_type") : "-").toString());
			workQueueDataList.add(wqData);
		}
		return workQueueDataList;
	}

}
