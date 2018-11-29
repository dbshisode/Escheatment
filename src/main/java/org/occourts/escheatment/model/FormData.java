package org.occourts.escheatment.model;

import java.math.BigDecimal;

public class FormData {
	private String caseNumber;
	private String caseTitle;
	private Long trustId;
	private String trustNumber;
	private String trustType;
	private String trustDate;
	private BigDecimal trustAmount;
	private String clerkName;
	private String claimDate;
	private String courtLocation;
	private String courtAddrLine1;
	private String courtAddrLine2;
	private String claimSubmitDate;
	private int rejectReason;
	private String otherRejectReason;
	private String caseCategory;
	private Double caseId;
	private String formDisplayName; 
	private String formPdfFullPath; 
	private int noticeId;
	private Long documentId;
	
	public String getCaseNumber() {
		return caseNumber;
	}
	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}
	public String getCaseTitle() {
		return caseTitle;
	}
	public void setCaseTitle(String caseTitle) {
		this.caseTitle = caseTitle;
	}
	public String getTrustNumber() {
		return trustNumber;
	}
	public void setTrustNumber(String trustNumber) {
		this.trustNumber = trustNumber;
	}
	public String getTrustType() {
		return trustType;
	}
	public void setTrustType(String trustType) {
		this.trustType = trustType;
	}
	public String getTrustDate() {
		return trustDate;
	}
	public void setTrustDate(String trustDate) {
		this.trustDate = trustDate;
	}
	public BigDecimal getTrustAmount() {
		return trustAmount;
	}
	public void setTrustAmount(BigDecimal trustAmount) {
		this.trustAmount = trustAmount;
	}
	public String getClerkName() {
		return clerkName;
	}
	public void setClerkName(String clerkName) {
		this.clerkName = clerkName;
	}
	public String getClaimDate() {
		return claimDate;
	}
	public void setClaimDate(String claimDate) {
		this.claimDate = claimDate;
	}
	public String getCourtLocation() {
		return courtLocation;
	}
	public void setCourtLocation(String courtLocation) {
		this.courtLocation = courtLocation;
	}
	public String getCourtAddrLine1() {
		return courtAddrLine1;
	}
	public void setCourtAddrLine1(String courtAddrLine1) {
		this.courtAddrLine1 = courtAddrLine1;
	}
	public String getCourtAddrLine2() {
		return courtAddrLine2;
	}
	public void setCourtAddrLine2(String courtAddrLine2) {
		this.courtAddrLine2 = courtAddrLine2;
	}
	public Long getTrustId() {
		return trustId;
	}
	public void setTrustId(Long trustId) {
		this.trustId = trustId;
	}
	public String getClaimSubmitDate() {
		return claimSubmitDate;
	}
	public void setClaimSubmitDate(String claimSubmitDate) {
		this.claimSubmitDate = claimSubmitDate;
	}
	public int getRejectReason() {
		return rejectReason;
	}
	public void setRejectReason(int rejectReason) {
		this.rejectReason = rejectReason;
	}
	public String getOtherRejectReason() {
		return otherRejectReason;
	}
	public void setOtherRejectReason(String otherRejectReason) {
		this.otherRejectReason = otherRejectReason;
	}
	public String getCaseCategory() {
		return caseCategory;
	}
	public void setCaseCategory(String caseCategory) {
		this.caseCategory = caseCategory;
	}
	public Double getCaseId() {
		return caseId;
	}
	public void setCaseId(Double caseId) {
		this.caseId = caseId;
	}
	public String getFormDisplayName() {
		return formDisplayName;
	}
	public void setFormDisplayName(String formDisplayName) {
		this.formDisplayName = formDisplayName;
	}
	public String getFormPdfFullPath() {
		return formPdfFullPath;
	}
	public void setFormPdfFullPath(String formPdfFullPath) {
		this.formPdfFullPath = formPdfFullPath;
	}
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
}
