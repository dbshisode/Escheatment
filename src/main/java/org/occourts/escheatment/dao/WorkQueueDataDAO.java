package org.occourts.escheatment.dao;

import java.util.List;

import org.occourts.escheatment.model.FormData;
import org.occourts.escheatment.model.LawfulOwner;
import org.occourts.escheatment.model.WorkQueueData;

/**
* WorkQueueDataDAO is an interface for Work Queue data
* $Revision: 4692 $     
* $Author: cbarrington $ 
* $Date: 2018-11-21 11:12:05 -0800 (Wed, 21 Nov 2018) $    
*/

public interface WorkQueueDataDAO {
	
	//Operations
	public int getOpsActiveCount();
	public int getOpsSentCount();
	public int getOpsReviewCount();
	public boolean MarkAsActive(long trustId,String updatedByUserName);
	public boolean MarkIdentifiedForEscheatment(long trustId,String updatedByUserName);
	public List<WorkQueueData> fetchOpsReviewData(String markedAsActive, int statusId);	
	
	//Accounting
	public int getAcctReviewCount();
	public int getAcctPendingCount();
	public int getAcctOnHoldCount();
	public int getAcctUnderReviewCount();
	public FormData fetchTrustInfoByTrustNum(String trustNum);
	public int getCountByTrustNum(String trustNumber);
	public void insertEscheatmentTrust(Long trustId, String trustNumber, int statusId, String userName);
	public boolean MarkOnHold(long trustId,String updatedByUserName);
	public boolean SentForReview(long trustId,long lawfulOwnerId,String updatedByUserName);
	public List<WorkQueueData> fetchAcctReviewData(String onHold, String underReview, int statusId);
	public boolean MarkReadyForPublication(List<WorkQueueData> workQueueData,String updatedByUserName);
	public boolean ReturnPendingItemsToReviewStatus(String updatedByUserName);
	public int getPublicationPendingCount();
	public List<WorkQueueData> fetchPublicationBatchesPendingPubDate(String finalized,int batchNum);
	
	//shared
	public List<LawfulOwner> fetchAdditionalLawfulOwners(Long trustId);
	public WorkQueueData fetchWorkQueueDataByTrustId(Long trustId);
	
}