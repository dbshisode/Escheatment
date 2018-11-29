package org.occourts.escheatment.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class LawfulOwner {

	BigDecimal id;
	String nameLine1;
	String nameLine2;
	String addressLine1;
	String addressLine2;
	String addressLine3;
	BigDecimal trustId;
	BigDecimal clockIndex;
	String createUserId;
	Timestamp createUserDate;
	String updateUserId;
	Timestamp updateUserDate;
	String documentId;
	String underReview;
	
	public BigDecimal getId() {
		return id;
	}
	public void setId(BigDecimal id) {
		this.id = id;
	}
	public String getNameLine1() {
		return nameLine1;
	}
	public void setNameLine1(String nameLine1) {
		this.nameLine1 = nameLine1;
	}
	public String getNameLine2() {
		return nameLine2;
	}
	public void setNameLine2(String nameLine2) {
		this.nameLine2 = nameLine2;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public BigDecimal getTrustId() {
		return trustId;
	}
	public void setTrustId(BigDecimal trustId) {
		this.trustId = trustId;
	}
	public BigDecimal getClockIndex() {
		return clockIndex;
	}
	public void setClockIndex(BigDecimal clockIndex) {
		this.clockIndex = clockIndex;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Timestamp getCreateUserDate() {
		return createUserDate;
	}
	public void setCreateUserDate(Timestamp createUserDate) {
		this.createUserDate = createUserDate;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Timestamp getUpdateUserDate() {
		return updateUserDate;
	}
	public void setUpdateUserDate(Timestamp updateUserDate) {
		this.updateUserDate = updateUserDate;
	}
	public String getDocumentId() {
		return documentId;
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
