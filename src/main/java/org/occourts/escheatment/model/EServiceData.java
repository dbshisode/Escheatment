package org.occourts.escheatment.model;

import java.io.Serializable;
import java.util.Date;

public class EServiceData implements Serializable {
	private static final long serialVersionUID = 1L;
	private Double caseId;
	private String recipientName;
	private String recipientAddressLine1;
	private String recipientAddressLine2;
	private Double collageDocumentId;
	private String userName;
	private String documentName;
	private Date documentDate;

	public Double getCaseId() {
		return caseId;
	}

	public void setCaseId(Double caseId) {
		this.caseId = caseId;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getRecipientAddressLine1() {
		return recipientAddressLine1;
	}

	public void setRecipientAddressLine1(String recipientAddressLine1) {
		this.recipientAddressLine1 = recipientAddressLine1;
	}

	public String getRecipientAddressLine2() {
		return recipientAddressLine2;
	}

	public void setRecipientAddressLine2(String recipientAddressLine2) {
		this.recipientAddressLine2 = recipientAddressLine2;
	}

	public Double getCollageDocumentId() {
		return collageDocumentId;
	}

	public void setCollageDocumentId(Double collageDocumentId) {
		this.collageDocumentId = collageDocumentId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
