package org.occourts.escheatment.dao.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.occourts.escheatment.dao.DocumentDAO;
import org.occourts.escheatment.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/**
* DocumentDAOImpl contains methods that query the database for all data necessary to 
* retrieve an image from the DMS
* $Revision: 4523 $     
* $Author: cbarrington $ 
* $Date: 2018-09-14 09:26:52 -0700 (Fri, 14 Sep 2018) $    
*/

public class DocumentDAOImpl implements DocumentDAO {

	private DataSource dataSource;
	JdbcTemplate template;

	@Autowired
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}

	public Document fetchDocumentInfo(Long documentId) {
		String SQL = "select d.dms_id,   \n" + 
				"        case  \n" + 
				"         when d.dms_id in (90010122,90010123) then  \n" + 
				"           'Probate' \n" + 
				"         else  \n" + 
				"           'Civil'  \n" + 
				"        end dms_cd,  \n" + 
				"        d.doc_id \n" + 
				"from v3owner01.document d \n" + 
				"where d.document_id = ?";
		Document document = new Document();
		Map row = DataAccessUtils.singleResult(template.queryForList(SQL, new Object[] { documentId }));
		if (row != null) {
			document.setDocId(Long.parseLong(row.get("doc_id").toString()));	
			document.setDmsId(Long.parseLong(row.get("dms_id").toString()));
			document.setDmsCd(row.get("dms_cd").toString());
		}
		
		return document;
	}	
	
		

}
