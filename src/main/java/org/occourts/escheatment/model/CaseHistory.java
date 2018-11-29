package org.occourts.escheatment.model;

import java.io.Serializable;
import java.util.Date;

public class CaseHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer caseHistId;
	private Integer changedInd;
	private String shortText;
	private String longEntry;
	private String sealedInd;
	private Integer caseHistEntryId;
	private Integer externalSourceId;
	private Integer caseId;
	private String externalSourceCcd;
	private String displayOoverrideInd;
	private Integer parentEentryId;
	private Date externalDt;
	private Integer lineNum;
	private Integer sequenceNum;
	private String strikeInd;
	private String errorInd;
	private Date createDt;
	private String createUserId;
	private Date updateDt;
	private String updateUuserId;
	private Integer version;
	private Integer securityLevel;
	private Integer nonLeadCaseId;
	private String displayCaseNum;
	private String macAddr;

	public CaseHistory() {
		super();
	}
	
	public Integer getCaseHistId() {
		return caseHistId;
	}

	public void setCaseHistId(Integer caseHistId) {
		this.caseHistId = caseHistId;
	}

	public Integer getChangedInd() {
		return changedInd;
	}

	public void setChangedInd(Integer changedInd) {
		this.changedInd = changedInd;
	}

	public String getShortText() {
		return shortText;
	}

	public void setShortText(String shortText) {
		this.shortText = shortText;
	}

	public String getLongEntry() {
		return longEntry;
	}

	public void setLongEntry(String longEntry) {
		this.longEntry = longEntry;
	}

	public String getSealedInd() {
		return sealedInd;
	}

	public void setSealedInd(String sealedInd) {
		this.sealedInd = sealedInd;
	}

	public Integer getCaseHistEntryId() {
		return caseHistEntryId;
	}

	public void setCaseHistEntryId(Integer caseHistEntryId) {
		this.caseHistEntryId = caseHistEntryId;
	}

	public Integer getExternalSourceId() {
		return externalSourceId;
	}

	public void setExternalSourceId(Integer externalSourceId) {
		this.externalSourceId = externalSourceId;
	}

	public Integer getCaseId() {
		return caseId;
	}

	public void setCaseId(Integer caseId) {
		this.caseId = caseId;
	}

	public String getExternalSourceCcd() {
		return externalSourceCcd;
	}

	public void setExternalSourceCcd(String externalSourceCcd) {
		this.externalSourceCcd = externalSourceCcd;
	}

	public String getDisplayOoverrideInd() {
		return displayOoverrideInd;
	}

	public void setDisplayOoverrideInd(String displayOoverrideInd) {
		this.displayOoverrideInd = displayOoverrideInd;
	}

	public Integer getParentEentryId() {
		return parentEentryId;
	}

	public void setParentEentryId(Integer parentEentryId) {
		this.parentEentryId = parentEentryId;
	}

	public Date getExternalDt() {
		return externalDt;
	}

	public void setExternalDt(Date externalDt) {
		this.externalDt = externalDt;
	}

	public Integer getLineNum() {
		return lineNum;
	}

	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}

	public Integer getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(Integer sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public String getStrikeInd() {
		return strikeInd;
	}

	public void setStrikeInd(String strikeInd) {
		this.strikeInd = strikeInd;
	}

	public String getErrorInd() {
		return errorInd;
	}

	public void setErrorInd(String errorInd) {
		this.errorInd = errorInd;
	}

	public Date getCreateDt() {
		return createDt;
	}

	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateUuserId() {
		return updateUuserId;
	}

	public void setUpdateUuserId(String updateUuserId) {
		this.updateUuserId = updateUuserId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(Integer securityLevel) {
		this.securityLevel = securityLevel;
	}

	public Integer getNonLeadCaseId() {
		return nonLeadCaseId;
	}

	public void setNonLeadCaseId(Integer nonLeadCaseId) {
		this.nonLeadCaseId = nonLeadCaseId;
	}

	public String getDisplayCaseNum() {
		return displayCaseNum;
	}

	public void setDisplayCaseNum(String displayCaseNum) {
		this.displayCaseNum = displayCaseNum;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
