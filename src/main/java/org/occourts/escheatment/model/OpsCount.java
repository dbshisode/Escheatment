package org.occourts.escheatment.model;

/**
* OpsCount contains get and set methods for the OpsCount object. The object is used to display
* work queue item counts in the application navigation tabs
* $Revision: 4500 $     
* $Author: cbarrington $ 
* $Date: 2018-08-14 15:57:31 -0700 (Tue, 14 Aug 2018) $    
*/

public class OpsCount {
	private int opsReviewCount;
	private int opsActiveCount;
	private int opsSentCount;
	
	public int getOpsReviewCount() {
		return opsReviewCount;
	}
	public void setOpsReviewCount(int opsReviewCount) {
		this.opsReviewCount = opsReviewCount;
	}
	public int getOpsActiveCount() {
		return opsActiveCount;
	}
	public void setOpsActiveCount(int opsActiveCount) {
		this.opsActiveCount = opsActiveCount;
	}
	public int getOpsSentCount() {
		return opsSentCount;
	}
	public void setOpsSentCount(int opsSentCount) {
		this.opsSentCount = opsSentCount;
	}
	
}
