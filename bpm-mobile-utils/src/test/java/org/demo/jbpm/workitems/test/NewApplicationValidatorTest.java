package org.demo.jbpm.workitems.test;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import org.demo.jbpm.workitems.NewApplicationValidatorWorkItemHandler;
import org.drools.core.process.instance.impl.DefaultWorkItemManager;
import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kie.api.runtime.process.WorkItemManager;
import org.subethamail.wiser.WiserMessage;

import com.google.gson.JsonObject;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;


public class NewApplicationValidatorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
			
	}

	@After
	public void tearDown() throws Exception {
	}

	@Ignore
	@Test
	public void testNewApplicationValidtor() throws IOException, ParseException {
		 	
		JSONParser parser = new JSONParser();
		InputStream is = getClass().getResourceAsStream("/NewApplication.json");
		Reader reader = new InputStreamReader(is);
		Object obj = parser.parse(reader);
		JSONObject jsonObject = (JSONObject) obj;
			 
		
		NewApplicationValidatorWorkItemHandler handler = new NewApplicationValidatorWorkItemHandler();
		
	    WorkItemImpl workItem = new WorkItemImpl();
	        
	    WorkItemManager manager = new DefaultWorkItemManager(null);
	    workItem.setParameter( "RequestString",  jsonObject.toString());
	    handler.executeWorkItem( workItem, manager );
	    
	    boolean companyDetails = false;
		boolean financialInfo = false;
		boolean incomeDetails = false;	
		boolean existingLoanInfo = false;	    
		String appType = "Personal";
		
		System.out.println("WIH Response " + workItem.getParameter( "CompanyDetails" ));
	    assertEquals( workItem.getParameter( "CompanyDetails" ), companyDetails );
	    assertEquals( workItem.getParameter( "FinancialInfo" ), financialInfo );
	    assertEquals( workItem.getParameter( "IncomeDetails" ), incomeDetails );
	    assertEquals( workItem.getParameter( "ExistingLoanInfo" ), existingLoanInfo );
	    assertEquals( workItem.getParameter( "ApplicationType" ),appType );	        

	      
	}

}
