package org.occourts.escheatment.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
* User contains get and set methods for the User object. The object is used to store
* information for the authenticated user
* $Revision: 4511 $     
* $Author: cbarrington $ 
* $Date: 2018-08-24 08:26:17 -0700 (Fri, 24 Aug 2018) $    
*/

public class User {
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public BigDecimal getUserFunctionalArea() {
		return userFunctionalArea;
	}

	public void setUserFunctionalArea(BigDecimal userFunctionalArea) {
		this.userFunctionalArea = userFunctionalArea;
	}

	public BigDecimal getUserRoleAdmin() {
		return userRoleAdmin;
	}

	public void setUserRoleAdmin(BigDecimal userRoleAdmin) {
		this.userRoleAdmin = userRoleAdmin;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	private String userName;
	private String password;
	private BigDecimal userId;
	private BigDecimal userFunctionalArea;
	private BigDecimal userRoleAdmin;
	private String firstName;
	private String middleName;
	private String lastName;
	private Timestamp createDate;
	private String createdBy;
	private Timestamp updateDate;
	private String updatedBy;
	private String active;

	// Setter and Getter methods

}