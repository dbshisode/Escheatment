package org.occourts.escheatment.dao;

import org.occourts.escheatment.model.Document;

/**
* DocumentDAO is an interface for document data
* $Revision: 4523 $     
* $Author: cbarrington $ 
* $Date: 2018-09-14 09:26:52 -0700 (Fri, 14 Sep 2018) $    
*/

public interface DocumentDAO {

	public Document fetchDocumentInfo(Long documentId);
	
}