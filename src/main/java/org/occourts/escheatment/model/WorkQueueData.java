package org.occourts.escheatment.model;

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
}
