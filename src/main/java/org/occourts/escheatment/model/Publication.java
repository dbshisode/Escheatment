package org.occourts.escheatment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Publication implements Serializable {
	private static final long serialVersionUID = 1L;
	private long publicationId;
	private BigDecimal batchId;
	private Date createDt;
	private String createUserId;
	private String finalized;
	private byte[] outputPdf;
	private Date publicationDt;
	private Date updateDt;
	private String updateUserId;

	public long getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(long publicationId) {
		this.publicationId = publicationId;
	}

	public BigDecimal getBatchId() {
		return batchId;
	}

	public void setBatchId(BigDecimal batchId) {
		this.batchId = batchId;
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

	public String getFinalized() {
		return finalized;
	}

	public void setFinalized(String finalized) {
		this.finalized = finalized;
	}

	public byte[] getOutputPdf() {
		return outputPdf;
	}

	public void setOutputPdf(byte[] outputPdf) {
		this.outputPdf = outputPdf;
	}

	public Date getPublicationDt() {
		return publicationDt;
	}

	public void setPublicationDt(Date publicationDt) {
		this.publicationDt = publicationDt;
	}

	public Date getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(Date updateDt) {
		this.updateDt = updateDt;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}