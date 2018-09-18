package org.occourts.escheatment.model;

/**
* WorkQueueData contains get and set methods for the WorkQueueData object. The object is used to store
* information for the various fields in a work queue
* $Revision: 4520 $     
* $Author: cbarrington $ 
* $Date: 2018-09-06 10:48:55 -0700 (Thu, 06 Sep 2018) $    
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
}
