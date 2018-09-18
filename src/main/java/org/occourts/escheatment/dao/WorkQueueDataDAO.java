package org.occourts.escheatment.dao;

import java.util.List;

import org.occourts.escheatment.model.WorkQueueData;

/**
* WorkQueueDataDAO is an interface for Work Queue data
* $Revision: 4523 $     
* $Author: cbarrington $ 
* $Date: 2018-09-14 09:26:52 -0700 (Fri, 14 Sep 2018) $    
*/

public interface WorkQueueDataDAO {

	public int getOpsActiveCount();
	public int getOpsSentCount();
	public int getOpsReviewCount();
	public boolean MarkAsActive(long trustId,String updatedByUserName);
	
	
	public List<WorkQueueData> fetchOpsReviewData();	
	
}