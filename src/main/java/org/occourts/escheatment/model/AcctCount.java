package org.occourts.escheatment.model;

/**
* AcctCount contains get and set methods for the AcctCount object. The object is used to display
* work queue item counts in the application navigation tabs
* $Revision: 4500 $     
* $Author: cbarrington $ 
* $Date: 2018-08-14 15:57:31 -0700 (Tue, 14 Aug 2018) $    
*/

public class AcctCount {
	private int acctReviewCount;
	private int acctPendingCount;
	private int acctOnHoldCount;
	private int acctUnderReviewCount;
	
	public int getAcctReviewCount() {
		return acctReviewCount;
	}
	public void setAcctReviewCount(int acctReviewCount) {
		this.acctReviewCount = acctReviewCount;
	}
	public int getAcctPendingCount() {
		return acctPendingCount;
	}
	public void setAcctPendingCount(int acctPendingCount) {
		this.acctPendingCount = acctPendingCount;
	}
	public int getAcctOnHoldCount() {
		return acctOnHoldCount;
	}
	public void setAcctOnHoldCount(int acctOnHoldCount) {
		this.acctOnHoldCount = acctOnHoldCount;
	}
	public int getAcctUnderReviewCount() {
		return acctUnderReviewCount;
	}
	public void setAcctUnderReviewCount(int acctUnderReviewCount) {
		this.acctUnderReviewCount = acctUnderReviewCount;
	}

	
}
