package org.occourts.escheatment.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.occourts.escheatment.dao.WorkQueueDataDAO;
import org.occourts.escheatment.model.WorkQueueData;
import org.springframework.jdbc.core.JdbcTemplate;

public class WorkQueueDataDAOImpl implements WorkQueueDataDAO {
	
	static String mockedData = "{\r\n" + 
			"    \"wqdata\": [\r\n" + 
			"         {\r\n" + 
			"              \"bal\": \"1230.00\",\r\n" + 
			"              \"short_title\": \"Asset Protection\",\r\n" + 
			"              \"comments\": \"Asset Protection Trust\",\r\n" + 
			"              \"create_dt\": \"01-01-2018\",\r\n" + 
			"              \"display_case_num\": \"A12345\",\r\n" + 
			"              \"orig_amt\": \"2000\",\r\n" + 
			"              \"trust_id\": \"12345\",\r\n" + 
			"              \"trust_num\": \"1234567890\",\r\n" + 
			"              \"trust_type\": \"Asset Protection\"\r\n" + 
			"         },\r\n" + 
			"         {\r\n" + 
			"              \"bal\": \"230.00\",\r\n" + 
			"              \"short_title\": \"Charitable\",\r\n" + 
			"              \"comments\": \"Charitable Trust\",\r\n" + 
			"              \"create_dt\": \"01-01-2018\",\r\n" + 
			"              \"display_case_num\": \"A12345\",\r\n" + 
			"              \"orig_amt\": \"3000\",\r\n" + 
			"              \"trust_id\": \"12346\",\r\n" + 
			"              \"trust_num\": \"1234567891\",\r\n" + 
			"              \"trust_type\": \"Charitable\"\r\n" + 
			"         }		 \r\n" + 
			"    ]\r\n" + 
			"}"; 

	private DataSource dataSource;
	private JdbcTemplate template;

	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public int getOpsActiveCount() {
		/*String SQL = "select count(*) as cnt from escheatment.trust where status_id = 1 and marked_as_active = 'Y'";
		Integer iOpsActiveCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iOpsActiveCount;*/
		return 2;
	}

	public int getOpsSentCount() {
		/*String SQL = "select count(*) as cnt from escheatment.trust where status_id = 2";
		Integer iOpsSentCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iOpsSentCount;*/
		return 2;
	}

	public String MarkAsActive() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getOpsReviewCount() {
		/*String SQL = "select count(*) as cnt from escheatment.trust where status_id = 1 and marked_as_active = 'N'";
		Integer iOpsReviewCount = (Integer) this.template.queryForObject(SQL, Integer.class);
		return iOpsReviewCount;*/
		return 2;
	}

	public List<WorkQueueData> fetchOpsReviewData() {
		List<WorkQueueData> workQueueDataList = new ArrayList<WorkQueueData>();
		/*String SQL = "select c.display_case_num,\n" + 
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
		}*/
		//Reading mocked Data instead:
		WorkQueueData wqData = new WorkQueueData();		
		JSONObject obj = new JSONObject(mockedData);
		JSONArray arr = obj.getJSONArray("wqdata");
		for (int i = 0; i < arr.length(); i++)
		{
			wqData = new WorkQueueData();
			wqData.setBalance(Float.parseFloat(arr.getJSONObject(i).getString("bal")));
			wqData.setCaseTitle((arr.getJSONObject(i).getString("short_title") != null ? arr.getJSONObject(i).getString("short_title") : "-").toString());
			wqData.setComments((arr.getJSONObject(i).getString("comments") != null ? arr.getJSONObject(i).getString("comments") : "-").toString());
			wqData.setDepositDate((arr.getJSONObject(i).getString("create_dt") != null ? arr.getJSONObject(i).getString("create_dt") : "-").toString());
			wqData.setDisplayCaseNum(
					(arr.getJSONObject(i).getString("display_case_num") != null ? arr.getJSONObject(i).getString("display_case_num") : "-").toString());
			wqData.setOrigAmt(Float.parseFloat(arr.getJSONObject(i).getString("orig_amt").toString()));
			wqData.setTrustId(Long.parseLong(arr.getJSONObject(i).getString("trust_id").toString()));
			wqData.setTrustNum(Long.parseLong(arr.getJSONObject(i).getString("trust_num").toString()));
			wqData.setTrustType((arr.getJSONObject(i).getString("trust_type") != null ? arr.getJSONObject(i).getString("trust_type") : "-").toString());
			workQueueDataList.add(wqData);
		}
		return workQueueDataList;
	}

}
