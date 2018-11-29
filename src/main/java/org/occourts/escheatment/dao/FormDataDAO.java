package org.occourts.escheatment.dao;

import org.occourts.escheatment.model.FormData;
import org.occourts.escheatment.model.LawfulOwner;

/**
* FormDataDAO is an interface for data required by PDF forms
* $Revision: 4511 $     
* $Author: cbarrington $ 
* $Date: 2018-08-24 08:26:17 -0700 (Fri, 24 Aug 2018) $    
*/

public interface FormDataDAO {
	public FormData fetchFormDataByTrustId(long trustId, String userName);
	public boolean insertIntoNoticeSent(FormData formData, LawfulOwner lawfulOwner, String createdByUserName);
}


