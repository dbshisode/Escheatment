package org.occourts.escheatment.dao.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.occourts.escheatment.dao.RoaDataDAO;
import org.occourts.escheatment.model.RoaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
* RoaDataDAOImpl contains methods that query the database for all data necessary to 
* display the content of the Register of Actions for a case
* $Revision: 4523 $     
* $Author: cbarrington $ 
* $Date: 2018-09-14 09:26:52 -0700 (Fri, 14 Sep 2018) $    
*/

public class RoaDataDAOImpl implements RoaDataDAO {

	private DataSource dataSource;
	JdbcTemplate template;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public List<RoaData> fetchRoaData(String caseNum, String date1, String date2) {		
		
		String SQL = "select d.document_id, \n" + 
				"		    case \n" + 
				"             when d.dms_id in (90010122,90010123) then \n" + 
				"               'Probate' \n" + 
				"             else \n" + 
				"               'Civil' \n" + 
				"           end dms, \n" + 
				"			d.doc_id, \n" + 
				"			d.external_source_cd, \n" + 
				"			ch.line_num, \n" + 
				"			case \n" + 
				"				when ch.external_source_cd = 'FILING' then \n" + 
				"					(select rtv.description from filing f, ref_table_value rtv where rtv.table_name = 'FILING_NAME' and rtv.code_id = f.filing_name_id and f.filing_id = ch.external_source_id) \n" + 
				"				when ch.external_source_cd = 'MINUTE' then \n" + 
				"					case \n" + 
				"						 when ch.short_text like 'Minutes%' then 'Minute Order' \n" + 
				"						 else ch.short_text \n" + 
				"					end \n" + 
				"				when ch.external_source_cd = 'EVENT' then ch.short_text \n" + 
				"				else \n" + 
				"					decode( \n" + 
				"						decode(d.name,null,(select name from v3owner01.output_form where output_form_id = d.form_id),d.name) , \n" + 
				"						null, ch.short_text,decode(d.name,null,(select name from v3owner01.output_form where output_form_id = d.form_id),d.name) ) \n" + 
				"			end doc_name, \n" + 
				"			CASE \n" + 
				"			   WHEN INSTR (UPPER (ch.short_text), 'FILED BY ') > 0 \n" + 
				"			   THEN \n" + 
				"				  INITCAP (TRIM (SUBSTR (UPPER (ch.short_text), INSTR (UPPER (ch.short_text), ' FILED BY ', -1, 1) + 10, LENGTH (ch.short_text)))) \n" + 
				"			   ELSE \n" + 
				"				   NULL \n" + 
				"			END filing_party, \n" + 
				"			trunc(ch.external_dt) as external_dt, \n" + 
				"			d.security_level, \n" + 
				"			(select f.other_name from v3owner01.filing f where f.filing_id = ch.external_source_id and ch.external_source_cd = 'FILING') other_text \n" + 
				"		from \n" + 
				"			v3owner01.document d, \n" + 
				"			v3owner01.case_hist_doc_assoc chda, \n" + 
				"			v3owner01.case_history ch, \n" + 
				"           v3owner01.case c \n" + 
				"		where \n" + 
				"			d.document_id = (select max(b.document_id) \n" + 
				"							 from v3owner01.document d2, v3owner01.case_hist_doc_assoc b \n" + 
				"							 where d2.document_id = b.document_id and b.case_hist_id = chda.case_hist_id) and			 \n" + 
				"			UPPER (ch.short_text) NOT LIKE '%VACATED%' and \n" + 
				"			UPPER (ch.short_text) NOT LIKE '%ERROR%' and \n" + 
				"			ch.strike_ind = 'N' and \n" + 
				"			ch.error_ind = 'N' and \n" + 
				"			ch.line_num IS NOT NULL and \n" + 
				"			chda.document_id = d.document_id and \n" + 
				"			ch.case_hist_id = chda.case_hist_id and	\n" + 
				"           TRUNC(ch.external_dt) >= TO_DATE(?,'MM/DD/YYYY') and \n" +
				"           TRUNC(ch.external_dt) <= TO_DATE(?,'MM/DD/YYYY') and \n" +
				"			ch.case_id = c.case_id and \n" + 
				"			c.display_case_num = ? \n" +
				"		order by trunc(external_dt) desc, line_num desc";
		List<Map<String, Object>> rows = template.queryForList(SQL, new Object[] { date1, date2, caseNum });
		RoaData roaData = new RoaData();
		List<RoaData> roaDataList = new ArrayList<RoaData>();
		for (Map row : rows) {
			roaData = new RoaData();
			roaData.setDocumentId((BigDecimal)(row.get("document_id")));
			roaData.setDocId((String)(row.get("doc_id")));
			roaData.setDms((String)(row.get("dms")));
			roaData.setExternalSourceCd((String)(row.get("external_source_cd")));
			roaData.setLineNum((BigDecimal)(row.get("line_num")));
			roaData.setDocName((String)(row.get("doc_name")));
			roaData.setFilingParty((String)(row.get("filing_party")));
			roaData.setExternalDt((Timestamp)(row.get("external_dt")));
			roaData.setSecurityLevel((BigDecimal)(row.get("security_level")));
			roaData.setOtherText((String)(row.get("other_text")));
			roaDataList.add(roaData);
		}
		return roaDataList;
	}

}
