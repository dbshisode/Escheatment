package org.occourts.escheatment.bo;

import org.occourts.escheatment.dao.impl.WorkQueueDataDAOImpl;
import org.occourts.escheatment.model.AcctCount;
import org.occourts.escheatment.model.OpsCount;
import org.springframework.beans.factory.annotation.Autowired;

/**
* WorkQueueDataBO contains a method to return an OpsCount object that contains
* a count of items in each work queue, to be displayed in tab navigation in the UI
* $Revision: 4547 $     
* $Author: cbarrington $ 
* $Date: 2018-10-25 15:53:00 -0700 (Thu, 25 Oct 2018) $    
*/

public class WorkQueueDataBO {
	@Autowired
	WorkQueueDataDAOImpl wqdatadao;
	
	public OpsCount getOpsWorkQueueDataCount() {
		OpsCount opsCount = new OpsCount();
		int opsReviewCount = wqdatadao.getOpsReviewCount();
		int opsActiveCount = wqdatadao.getOpsActiveCount();
		int opsSentCount = wqdatadao.getOpsSentCount();
		opsCount.setOpsActiveCount(opsActiveCount);
		opsCount.setOpsReviewCount(opsReviewCount);
		opsCount.setOpsSentCount(opsSentCount);
		return opsCount;
	}
	
	public AcctCount getAcctWorkQueueDataCount() {
		AcctCount acctCount = new AcctCount();		
		int acctReviewCount = wqdatadao.getAcctReviewCount();
		int acctPendingCount = wqdatadao.getAcctPendingCount();
		int acctOnHoldCount = wqdatadao.getAcctOnHoldCount();
		int acctUnderReviewCount = wqdatadao.getAcctUnderReviewCount();
		acctCount.setAcctReviewCount(acctReviewCount);
		acctCount.setAcctPendingCount(acctPendingCount);
		acctCount.setAcctOnHoldCount(acctOnHoldCount);
		acctCount.setAcctUnderReviewCount(acctUnderReviewCount);
		return acctCount;
	}	

}
