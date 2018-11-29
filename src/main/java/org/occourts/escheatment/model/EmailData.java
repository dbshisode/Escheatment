package org.occourts.escheatment.model;

public class EmailData {
	private String sender;
	private String[] recipients;
	private String subject;
	private String body;
	private boolean html;
	
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String[] getRecipients() {
		return recipients;
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

}
