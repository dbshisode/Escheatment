package org.occourts.escheatment.dao;

import java.util.List;

import org.occourts.escheatment.model.WorkQueueData;

public interface WorkQueueDataDAO {

	public int getOpsActiveCount();
	public int getOpsSentCount();
	public int getOpsReviewCount();
	public String MarkAsActive();
	
	
	public List<WorkQueueData> fetchOpsReviewData();	
	
}