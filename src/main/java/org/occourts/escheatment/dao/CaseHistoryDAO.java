package org.occourts.escheatment.dao;

import org.occourts.escheatment.model.CaseHistory;
import org.occourts.escheatment.model.LawfulOwner;

public interface CaseHistoryDAO {
	
	public Long addCaseHistoryEntry(CaseHistory caseHistory);
	public Long addCaseHistoryEntry(Double caseId, Double docId, String docName, LawfulOwner lawfulOwner, String userName);
}
