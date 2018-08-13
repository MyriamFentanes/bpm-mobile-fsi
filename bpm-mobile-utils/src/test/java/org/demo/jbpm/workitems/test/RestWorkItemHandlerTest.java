package org.demo.jbpm.workitems.test;

import static org.junit.Assert.*;
import org.demo.jbpm.workitems.CompanyInformationWorkItem;
import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;

import java.util.Map;


public class RestWorkItemHandlerTest {

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
	public void testGETOperation() {
        CompanyInformationWorkItem handler = new CompanyInformationWorkItem();
        
        WorkItemImpl workItem = new WorkItemImpl();
        workItem.setParameter( "HandleResponseErrors", "True" );
        
        
		final String companyName = (String) workItem.getParameter("Operation");
        final String endpointAddress = (String) workItem.getParameter("Endpoint");
        final String authorizationToken = (String) workItem.getParameter("AuthorizationToken");   
        final String resultClass = (String) workItem.getParameter("ResultClass");
        String handleExceptionStr = (String) workItem.getParameter("HandleResponseErrors");
        
        
        WorkItemManager manager = new TestWorkItemManager(workItem);
        handler.executeWorkItem(workItem, manager);
        
        String result = (String) workItem.getResult("Result");
        
        assertNotNull("result cannot be null", result);
        int responseCode = (Integer) workItem.getResult("Status");
        assertNotNull(responseCode);
        assertEquals(200, responseCode);
        String responseMsg = (String) workItem.getResult("StatusMsg");
        assertNotNull(responseMsg);
    }

		private class TestWorkItemManager implements WorkItemManager {
        
	        private WorkItem workItem;
	        
	        TestWorkItemManager(WorkItem workItem) {
	            this.workItem = workItem;
	        }

	        public void completeWorkItem(long id, Map<String, Object> results) {
	            ((WorkItemImpl)workItem).setResults(results);
	            
	        }

	        public void abortWorkItem(long id) {
	            
	        }

	        public void registerWorkItemHandler(String workItemName, WorkItemHandler handler) {
	            
	        }
    }
}
