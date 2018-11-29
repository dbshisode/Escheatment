package org.occourts.escheatment.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

public class RoaData {

	private BigDecimal documentId;
	private String docId;
	private String dms;
	private String externalSourceCd;
	private BigDecimal lineNum;
	private String docName;
	private String filingParty;
	private Timestamp externalDt;
	private BigDecimal securityLevel;
	private String otherText;
	
	public BigDecimal getDocumentId() {
		return documentId;
	}
	public void setDocumentId(BigDecimal documentId) {
		this.documentId = documentId;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getDms() {
		return dms;
	}
	public void setDms(String dms) {
		this.dms = dms;
	}
	public String getExternalSourceCd() {
		return externalSourceCd;
	}
	public void setExternalSourceCd(String externalSourceCd) {
		this.externalSourceCd = externalSourceCd;
	}
	public BigDecimal getLineNum() {
		return lineNum;
	}
	public void setLineNum(BigDecimal lineNum) {
		this.lineNum = lineNum;
	}
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getFilingParty() {
		return filingParty;
	}
	public void setFilingParty(String filingParty) {
		this.filingParty = filingParty;
	}
	public Timestamp getExternalDt() {
		return externalDt;
	}
	public void setExternalDt(Timestamp externalDt) {
		this.externalDt = externalDt;
	}
	public BigDecimal getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(BigDecimal securityLevel) {
		this.securityLevel = securityLevel;
	}
	public String getOtherText() {
		return otherText;
	}
	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}
	
}
