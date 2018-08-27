package org.occourts.escheatment.dao;

import java.util.List;

import org.occourts.escheatment.model.WorkQueueData;

/**
* WorkQueueDataDAO is an interface for Work Queue data
* $Revision: 4500 $     
* $Author: cbarrington $ 
* $Date: 2018-08-14 15:57:31 -0700 (Tue, 14 Aug 2018) $    
*/

public interface WorkQueueDataDAO {

	public int getOpsActiveCount();
	public int getOpsSentCount();
	public int getOpsReviewCount();
	public String MarkAsActive();
	
	
	public List<WorkQueueData> fetchOpsReviewData();	
	
}