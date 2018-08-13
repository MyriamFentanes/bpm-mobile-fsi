package org.demo.jbpm.workitems;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.drools.core.util.StringUtils;
import org.jbpm.process.workitem.AbstractLogOrThrowWorkItemHandler;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Basic Email WorkItemHandler to send Emails via an Email Provider 
 * @author ajarrett
 *
 */
public class EmailProviderWorkItemHandler extends AbstractLogOrThrowWorkItemHandler {

	public EmailProviderWorkItemHandler() {
	}

	private static final Logger logger = LoggerFactory.getLogger(EmailProviderWorkItemHandler.class);

	private final String username = "bpm.mobile.demo@gmail.com";
	private final String password = "1!Redhat2017";
	private Properties emailProps = null;

	private Properties initialiseEmail(String emailProvider) {

		try {

			logger.info("Initialising Email props");
			emailProps = new Properties();

			logger.debug(emailProvider + ".properties");
			InputStream input = new FileInputStream(emailProvider + ".properties");
			emailProps.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return emailProps;
	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		// Obtain Email params
		String emailBody = (String) workItem.getParameter("Body");
		String emailSubject = (String) workItem.getParameter("Subject");
		String emailTo = (String) workItem.getParameter("Recipient");
		String emailProvider = (String) workItem.getParameter("EmailProvider");
		
		// Default to GMail
		if (StringUtils.isEmpty(emailProvider)){
			emailProvider = "gmail";
		}
		
		// Pure Offline Demo Mode - Testing on a plane :D
		if (emailProvider.equals("offline")){
			logger.info("=========================== EMAIL WIH OFFLINE DEMO START =========================== ");
			logger.info("=========================== Sending Email to {} ==========================", emailTo);
			logger.info("=========================== Sending Email with subject {} ==========================", emailSubject);
			logger.info("=========================== Sending Email with Body {} ==========================", emailBody);
			logger.info("=========================== EMAIL WIH OFFLINE DEMO END  =========================== ");
			manager.completeWorkItem(workItem.getId(), null);
			return;
		}

		// Check basic Host & Port are available...
		logger.debug("Using email provider props: " + emailProvider);
		if (!StringUtils.isEmpty(emailProvider)) {
			emailProps = this.initialiseEmail(emailProvider);
			if (StringUtils.isEmpty(emailProps.getProperty("mail.smtp.host"))
					|| StringUtils.isEmpty(emailProps.getProperty("mail.smtp.port"))) {
				logger.error("Email Props not loaded properly.... Aborting WorkItem");
				this.abortWorkItem(workItem, manager);
			}

		}
		
		Session session = null;
		boolean sessionAuth = Boolean.parseBoolean(emailProps.getProperty("mail.smtp.auth"));
		if (sessionAuth) {
			logger.info("Creating Session with Auth Vars due to sessionAuth being {}", sessionAuth);
			// Create Session & Auth with SMTP Server
			session = Session.getInstance(emailProps, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
		} else {
			logger.info("Creating Session without Auth Vars due to sessionAuth being {}", sessionAuth);
			session = Session.getInstance(emailProps);
		}

		logger.info("Attempting to send Email to Recipient: {} using the email provider {} ", emailTo, emailProvider);
		try {

			// Setup Message props
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("bpm-fis-demo@redhat.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
			message.setSubject(emailSubject);
			//message.setText(emailBody);

			// Send HTML Message Body Type
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(emailBody, "text/html; charset=utf-8");
			
			// Add HTML Messge Payload to MultiPart
			Multipart multiPart = new MimeMultipart("alternative");
			multiPart.addBodyPart(htmlPart);
			message.setContent(multiPart);

			// Send Message
			logger.debug("Sending Email Message to {} ", emailTo);
			Transport.send(message);

			logger.info("Successfully sent message to recipient : " + emailTo);

		} catch (AddressException e) {
			logger.info("Uh oh! AddressException Error.. Unable to resolve recipient address");
			e.printStackTrace();
		} catch (MessagingException e) {
			logger.info("Eek! MeessageException.. Unable to send Email ");
			e.printStackTrace();
		}
		
		// Currently not passing any details back to BPM
		manager.completeWorkItem(workItem.getId(), null);

	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}

}
