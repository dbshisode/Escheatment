package org.occourts.escheatment.bo;

import org.occourts.escheatment.dao.impl.WorkQueueDataDAOImpl;
import org.occourts.escheatment.model.OpsCount;
import org.springframework.beans.factory.annotation.Autowired;

/**
* WorkQueueDataBO contains a method to return an OpsCount object that contains
* a count of items in each work queue, to be displayed in tab navigation in the UI
* $Revision: 4500 $     
* $Author: cbarrington $ 
* $Date: 2018-08-14 15:57:31 -0700 (Tue, 14 Aug 2018) $    
*/

public class WorkQueueDataBO {
	@Autowired
	WorkQueueDataDAOImpl wqdatadao;
	
	public OpsCount getWorkQueueDataCount() {
		OpsCount opsCount = new OpsCount();
		int opsReviewCount = wqdatadao.getOpsReviewCount();
		int opsActiveCount = wqdatadao.getOpsActiveCount();
		int opsSentCount = wqdatadao.getOpsSentCount();
		opsCount.setOpsActiveCount(opsActiveCount);
		opsCount.setOpsReviewCount(opsReviewCount);
		opsCount.setOpsSentCount(opsSentCount);
		return opsCount;
	}

}
