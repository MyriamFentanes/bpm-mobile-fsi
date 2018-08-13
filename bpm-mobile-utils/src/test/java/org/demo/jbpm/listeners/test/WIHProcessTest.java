package org.demo.jbpm.listeners.test;

import java.util.HashMap;
import java.util.List;
import org.demo.jbpm.listeners.mock.MockMobileTaskEventListener;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.manager.RuntimeManager;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskLifeCycleEventListener;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;
import org.kie.internal.task.api.EventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class WIHProcessTest extends JbpmJUnitBaseTestCase  {
	
	private static final Logger logger = LoggerFactory.getLogger(WIHProcessTest.class.getName());
	
	public WIHProcessTest() {
		super(true, true);
	}
	
    private static final String BPM_PROCESS = "com/sample/sample.bpmn2";
    private static final String FIRST_NODE_NAME = "Script 1";
	
	@Test
	public void testProcessSuccessKYCCheckSuccessPath() {

		ProcessInstance processInstance = createNewKYCProcessInstance();
		assertTrue(processInstance.getId() > 0);

		// Create KSession and Register WIH's
		RuntimeEngine runtimeEngine = getRuntimeEngine(ProcessInstanceIdContext.get(processInstance.getId()));
		KieSession ksession = runtimeEngine.getKieSession();
		// ksession.getWorkItemManager().registerWorkItemHandler("JMSEmailWorkItemHandler", jmsWIH);
	    
		// Create Task Service and Register Listener
		TaskService taskService = runtimeEngine.getTaskService();
		((EventService<TaskLifeCycleEventListener>) taskService).registerTaskEventListener(new MockMobileTaskEventListener());
		
		assertProcessInstanceActive(processInstance.getId(), ksession);
		assertNodeTriggered(processInstance.getId(), "Script 1");
		
		// john execute Task 1
		List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("john", "en-UK");
		TaskSummary task = list.get(0);
		System.out.println("John is executing task " + task.getName());
		taskService.start(task.getId(), "john");
		taskService.complete(task.getId(), "john", null);

		assertNodeTriggered(processInstance.getId(), "User Task 1");
		
		// mary execute Task 2
		list = taskService.getTasksAssignedAsPotentialOwner("mary", "en-UK");
		assertTrue(list.size() > 0);
		
		task = list.get(0);
		System.out.println("Mary is executing task " + task.getName());
		taskService.start(task.getId(), "mary");
		taskService.complete(task.getId(), "mary", null);
		assertNodeTriggered(processInstance.getId(), "User Task 2");

		assertProcessInstanceCompleted(processInstance.getId(), ksession);
		
		manager.disposeRuntimeEngine(runtimeEngine);
		manager.close();

	}

	// Create New Process Instance
	private ProcessInstance createNewKYCProcessInstance() {
        
        RuntimeManager manager = createRuntimeManager(Strategy.PROCESS_INSTANCE, "sample-process", BPM_PROCESS);
		RuntimeEngine runtimeEngine = getRuntimeEngine(ProcessInstanceIdContext.get());
		KieSession ksession = runtimeEngine.getKieSession();
		TaskService taskService = runtimeEngine.getTaskService();
		
		//ksession.getWorkItemManager().registerWorkItemHandler("JMSEmailWorkItemHandler", jmsWIH);
		ProcessInstance processInstance = ksession.startProcess("com.sample.bpmn.hello",
				new HashMap<String, Object>() {
					{
						put("appType", "bpm-fis-demo");
						put("appStatus", "test-001");
						put("appStartTime", "2017-12-31 09:00:00");
					}
				});

		assertProcessInstanceActive(processInstance.getId(), ksession);
		assertNodeTriggered(processInstance.getId(), FIRST_NODE_NAME);
		assertProcessVarExists(processInstance, "appType", "appStatus", "appStartTime");

		manager.disposeRuntimeEngine(runtimeEngine);
		return processInstance;
	}

}
