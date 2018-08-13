package org.demo.jbpm.workitems.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import org.demo.jbpm.workitems.EmailProviderWorkItemHandler;
import org.drools.core.process.instance.impl.DefaultWorkItemManager;
import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

public class GMailWorkItemHandlerTest {


	private static final Logger logger = LoggerFactory.getLogger(GMailWorkItemHandlerTest.class);
	
    private Wiser wiser;
    private Properties emailProps; 
    
    @Before
    public void setUp() throws Exception {
    	
		logger.info("Initialising Email props");
		emailProps = new Properties();
		InputStream input = new FileInputStream("src/test/resources/wiser.properties");
		emailProps.load(input);

        wiser = new Wiser();
        wiser.setPort(1025);
        wiser.setHostname(emailProps.getProperty("mail.smtp.host"));
        wiser.start();
    }

    @After
    public void tearDown() throws Exception {
        if( wiser != null ) { 
            wiser.getMessages().clear();
            wiser.stop();
            wiser = null;
        }
    }
    
	@Test
	public void testSingleRecipient() throws MessagingException, IOException {
			EmailProviderWorkItemHandler handler = new EmailProviderWorkItemHandler();
	        
	        WorkItemImpl workItem = new WorkItemImpl();
	        workItem.setParameter( "Recipient", "test@gmail.com" );
	        workItem.setParameter( "Subject", "Test Subject 1" );
	        workItem.setParameter( "Body", "<h1>Hello JUnit Test</h1>");
	        workItem.setParameter("EmailProvider", "src/test/resources/wiser");
	          
	        WorkItemManager manager = new DefaultWorkItemManager(null);
	        handler.executeWorkItem( workItem, manager );
	        
	        MimeMessage msg = (( WiserMessage  ) wiser.getMessages().get( 0 )).getMimeMessage();
	        Address[] recipients = msg.getRecipients(RecipientType.TO);
	        
	        assertEquals(1, this.wiser.getMessages().size());
	        assertEquals(workItem.getParameter("Subject"), this.wiser.getMessages().get(0).getMimeMessage().getSubject());
	        assertEquals(workItem.getParameter("Recipient"), recipients[0].toString() );
	        assertEquals( "bpm-fis-demo@redhat.com", ((InternetAddress)msg.getFrom()[0]).getAddress() );
	        assertEquals( "bpm-fis-demo@redhat.com", ((InternetAddress)msg.getReplyTo()[0]).getAddress() );
	        
	        assertNull( msg.getRecipients( RecipientType.CC ) );
	        assertNull( msg.getRecipients( RecipientType.BCC ) );
	}

}
