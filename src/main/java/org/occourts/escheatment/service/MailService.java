package org.occourts.escheatment.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.occourts.escheatment.model.EmailData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${smtp.sender}") 
	private String sender;

	public boolean sendEmail(EmailData emailData) {
		boolean multipart = false;
		try {
			LOGGER.info("About to send email...");
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, multipart);
			helper.setTo(emailData.getRecipients());
			helper.setText(emailData.getBody(), emailData.isHtml());
			helper.setSubject(emailData.getSubject());
			helper.setFrom(emailData.getSender()==null?sender:emailData.getSender());
			mailSender.send(msg);
			return true;
		} catch (MessagingException e) {
			LOGGER.error("An exception has occured => ",e);
		}
		return false;
	}
}
