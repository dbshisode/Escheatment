package org.occourts.escheatment.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class Comments {

	private BigDecimal commentId;
	private Timestamp commentDate;
	private String commentText;
	private BigDecimal trustId;
	private BigDecimal userId;
	private String userName;
	private String createUserId;
	private Date createDt;
	private String updateUserId;
	private Date updateDt;
	private BigDecimal userCommentRole;
	
	public BigDecimal getCommentId() {
		return commentId;
	}
	public void setCommentId(BigDecimal commentId) {
		this.commentId = commentId;
	}
	public Timestamp getCommentDate() {
		return commentDate;
	}
	public void setCommentDate(Timestamp commentDate) {
		this.commentDate = commentDate;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public BigDecimal getTrustId() {
		return trustId;
	}
	public void setTrustId(BigDecimal trustId) {
		this.trustId = trustId;
	}
	public BigDecimal getUserId() {
		return userId;
	}
	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateDt() {
		return createDt;
	}
	public void setCreateDt(Date createDt) {
		this.createDt = createDt;
	}
	public String getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Date getUpdateDt() {
		return updateDt;
	}
	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}
	public BigDecimal getUserCommentRole() {
		return userCommentRole;
	}
	public void setUserCommentRole(BigDecimal userCommentRole) {
		this.userCommentRole = userCommentRole;
	}
}
