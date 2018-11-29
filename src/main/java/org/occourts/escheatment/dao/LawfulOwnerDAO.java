package org.occourts.escheatment.dao;

import java.util.List;

import org.occourts.escheatment.model.LawfulOwner;

/**
* LawfulOwnerDAO is an interface for Lawful Owner data
* $Revision: 4511 $     
* $Author: cbarrington $ 
* $Date: 2018-08-24 08:26:17 -0700 (Fri, 24 Aug 2018) $    
*/

public interface LawfulOwnerDAO {
	public List<LawfulOwner> fetchAllLawfulOwnersByTrustId(long trustId);
	public LawfulOwner fetchLawfulOwnerByLawfulOwnerId(long lawfulOwnerId);
	public int getNextClockIndex(long trustId);
}


