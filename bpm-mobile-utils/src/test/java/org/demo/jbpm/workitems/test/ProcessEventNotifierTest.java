package org.demo.jbpm.workitems.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;

import org.drools.core.process.instance.impl.WorkItemImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;


public class ProcessEventNotifierTest {

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

	@Test
	public void processEventNotifierTest(){
//		ProcessEventNotifier handler = new ProcessEventNotifier();
//        
//        WorkItemImpl workItem = new WorkItemImpl();
//        workItem.setParameter( "AlertData", new Date() );
//        workItem.setParameter( "AlertName", "" );
//        workItem.setParameter( "AlertMessage", "Appointment" );
//
//        WorkItemManager manager = new TestWorkItemManager(null);
//        handler.executeWorkItem( workItem, manager );
//        
//        String result = (String) workItem.getResult("Result");
//  
//        assertNotNull("result cannot be null",
//                      result);
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
