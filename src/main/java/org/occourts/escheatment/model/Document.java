package org.occourts.escheatment.model;

public class Document {

	private long docId;
	private long dmsId;
	private String dmsCd;
	
	public long getDocId() {
		return docId;
	}
	public void setDocId(long docId) {
		this.docId = docId;
	}
	public long getDmsId() {
		return dmsId;
	}
	public void setDmsId(long dmsId) {
		this.dmsId = dmsId;
	}
	public String getDmsCd() {
		return dmsCd;
	}
	public void setDmsCd(String dmsCd) {
		this.dmsCd = dmsCd;
	}
}
