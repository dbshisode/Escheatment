package org.occourts.escheatment.model;

import java.math.BigDecimal;

/**
* WorkQueueData contains get and set methods for the WorkQueueData object. The object is used to store
* information for the various fields in a work queue
* $Revision: 4549 $     
* $Author: cbarrington $ 
* $Date: 2018-11-02 15:03:43 -0700 (Fri, 02 Nov 2018) $    
*/

public class WorkQueueData {

	   String displayCaseNum;
	    String caseTitle;
	    String trustType;
	    String depositDate;
	    Float balance = 0.0f;
	    Float origAmt = 0.0f;
	    String comments;
	    Long trustId;
	    Long trustNum;
	    Long caseId;
	    int commentCnt;
	    String ownerName;
	    String ownerAddress;
	    int ownerCnt;
	    String documentId;
	    Long lawfulOwnerId;
	    String underReview;

	    public String getCaseTitle() {
	        return caseTitle;
	    }

	    public void setCaseTitle(String caseTitle) {
	        this.caseTitle = caseTitle;
	    }

	    public String getTrustType() {
	        return trustType;
	    }

	    public void setTrustType(String trustType) {
	        this.trustType = trustType;
	    }

	    public String getDepositDate() {
	        return depositDate;
	    }

	    public void setDepositDate(String DepositDate) {
	        this.depositDate = DepositDate;
	    }

	    public Float getBalance() {
	        return balance;
	    }

	    public void setBalance(Float Balance) {
	        this.balance = Balance;
	    }

	    public Long getTrustNum() {
	        return trustNum;
	    }

	    public void setTrustNum(Long trustNum) {
	        this.trustNum = trustNum;
	    }

	    public String getDisplayCaseNum() {
	        return displayCaseNum;
	    }

	    public void setDisplayCaseNum(String displayCaseNum) {
	        this.displayCaseNum = displayCaseNum;
	    }

	    public Float getOrigAmt() {
	        return origAmt;
	    }

	    public void setOrigAmt(Float origAmt) {
	        this.origAmt = origAmt;
	    }

	    public Long getTrustId() {
	        return trustId;
	    }

	    public void setTrustId(Long trustId) {
	        this.trustId = trustId;
	    }
	    
	    public String getComments() {
	        return comments;
	    }

	    public void setComments(String comments) {
	        this.comments = comments;
	    }

		public int getCommentCnt() {
			return commentCnt;
		}

		public void setCommentCnt(int commentCnt) {
			this.commentCnt = commentCnt;
		}

		public Long getCaseId() {
			return caseId;
		}

		public void setCaseId(Long caseId) {
			this.caseId = caseId;
		}

		public String getOwnerName() {
			return ownerName;
		}

		public void setOwnerName(String ownerName) {
			this.ownerName = ownerName;
		}

		public String getOwnerAddress() {
			return ownerAddress;
		}

		public void setOwnerAddress(String ownerAddress) {
			this.ownerAddress = ownerAddress;
		}

		public int getOwnerCnt() {
			return ownerCnt;
		}

		public void setOwnerCnt(int ownerCnt) {
			this.ownerCnt = ownerCnt;
		}

		public String getDocumentId() {
			return documentId;
		}

		public Long getLawfulOwnerId() {
			return lawfulOwnerId;
		}

		public void setLawfulOwnerId(Long lawfulOwnerId) {
			this.lawfulOwnerId = lawfulOwnerId;
		}

		public void setDocumentId(String documentId) {
			this.documentId = documentId;
		}

		public String getUnderReview() {
			return underReview;
		}

		public void setUnderReview(String underReview) {
			this.underReview = underReview;
		}

}
