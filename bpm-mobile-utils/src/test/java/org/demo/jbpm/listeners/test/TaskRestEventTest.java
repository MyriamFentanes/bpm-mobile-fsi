package org.demo.jbpm.listeners.test;
//package demo.jbpm.listener.test;
//
//import static org.junit.Assert.*;
//
//import java.util.Date;
//import java.util.List;
//
//import org.jbpm.services.task.identity.JBossUserGroupCallbackImpl;
//import org.jbpm.test.JBPMHelper;
//import org.jbpm.test.JbpmJUnitBaseTestCase;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.kie.api.runtime.KieSession;
//import org.kie.api.runtime.manager.RuntimeEngine;
//import org.kie.api.runtime.manager.RuntimeEnvironment;
//import org.kie.api.runtime.manager.RuntimeEnvironmentBuilder;
//import org.kie.api.runtime.manager.RuntimeManager;
//import org.kie.api.runtime.manager.RuntimeManagerFactory;
//import org.kie.api.runtime.process.ProcessInstance;
//import org.kie.api.task.TaskService;
//import org.kie.api.task.model.TaskSummary;
//import org.kie.api.KieServices;
//import org.kie.api.io.ResourceType;
//import org.kie.internal.io.ResourceFactory;
//import org.kie.internal.runtime.manager.context.EmptyContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.apache.cxf.endpoint.Server;
//import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
//import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
//import com.google.gson.Gson;
//
//import demo.jbpm.listeners.model.DemoTaskEvent;
//import demo.jbpm.wih.utils.RestPost;
//import demo.jbpm.wih.utils.RestConstants.EventType;
//import demo.jbpm.wih.utils.RestConstants.PayloadType;
//
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.Persistence;
//
//public class TaskRestEventTest extends JbpmJUnitBaseTestCase {
//	
//	private static Logger logger = LoggerFactory.getLogger(TaskRestEventTest.class.getName());
//	private static Server server;
//	
//	public TaskRestEventTest(){
//		super(true,true);
//	}
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//		
//		 
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}
//
//	@Before
//	public void setUp() throws Exception {
//		
//		
//	}
//
//	@After
//	public void tearDown() throws Exception {
//		
//		logger.info("SERVER STATUS 3: " + server.isStarted());
//		server.destroy();
//		logger.info("SERVER STATUS 4: " + server.isStarted());
//	}
//	
//	@Test
//	public void testProcessSuccess(){
//		
//		KieServices ks = KieServices.Factory.get();
//		//KieContainer kContainer = ks.getKieClasspathContainer();
//		
//		JBPMHelper.startH2Server();
//		JBPMHelper.setupDataSource();
//		LOGGER.info("DATASOURCE SET-UP");
//		
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("org.jbpm.persistence.jpa");
//		RuntimeEnvironment environment = RuntimeEnvironmentBuilder.Factory.get().newDefaultBuilder()
//				.entityManagerFactory(emf)
//				.userGroupCallback(new JBossUserGroupCallbackImpl("classpath:/usergroups.properties"))
//				.addAsset(ResourceFactory
//				.newClassPathResource("com/example/testproject/testproject/simple1.bpmn2"), ResourceType.BPMN2)
//				.get();
//		
//		logger.info("RUNTIME ENVIRONMENT BUILT");
//	
//		RuntimeManager manager = RuntimeManagerFactory.Factory.get().newPerProcessInstanceRuntimeManager(environment);
//		
//		logger.info("RUNTIME MANAGER BUILT");
//
//		RuntimeEngine runtimeEngine = manager.getRuntimeEngine(EmptyContext.get());
//
//		assertNotNull(runtimeEngine);
//		
//		KieSession ksession = runtimeEngine.getKieSession();
//		
//		logger.info("KSESSION BUILT");
//	
//	    ksession.getWorkItemManager().registerWorkItemHandler("Human Task", new TestWorkItemHandler());
//		ProcessInstance processInstance = ksession.startProcess("TestProject.simple1");
//		Long procInstId = processInstance.getId();
//		
//		
//		logger.info("PROCESS INSTANCE STARTED : PID = " + procInstId);
//		
//		TaskService taskService = runtimeEngine.getTaskService();
//		
//		
//        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner("group1", "en-UK");
//        logger.info("LIST SIZE: " + list.size()); 
//        for (TaskSummary taskSummary : list) { 
//        	logger.info("TASK LIST DETAILS: taskId = " + taskSummary.getName()); 
//    
//            } 
//         
//		manager.disposeRuntimeEngine(runtimeEngine);
//
//	}
//	
//	@Test
//	public void testEventTask() {
//		Gson gson = new Gson();
//		
//		DemoTaskEvent eventTask = new DemoTaskEvent();		 
//		eventTask.setAlertDate(new Date());
//		eventTask.setAlertMessage("test alertMessage");
//		eventTask.setAlertName("test alertName");
//		eventTask.setTaskCaseId("taskCaseId");
//		eventTask.setTaskId("taskId");
//		eventTask.setTaskName("taskName");
//		eventTask.setTaskExpiryDate( new Date() );
//
//		String requestPayload = gson.toJson(eventTask);
//		logger.info("REQUEST PAYLOAD = " + requestPayload);
//		try {
//		
//		/* This code is meant to start up a basic server but this needs to be completed...
//		 * 
//		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
//	    sf.setResourceClasses(ProcessEventService.class);
//	    sf.setResourceProvider(ProcessEventService.class, 
//	            new SingletonResourceProvider(new ProcessEventService()));
//	    sf.setAddress("http://localhost:8089");
//	
//	    server = sf.create();
//	    server.start();
//			
//	    LOGGER.info("Server started on address: " + sf.getAddress());
//	    */
//
//		RestPost restPost = new RestPost();
//	
//		String result = restPost.PostTaskRestAlert(requestPayload, EventType.NOTIFY, PayloadType.JSON);
//		logger.info("Post Response: " + result);
//		}
//		
//		catch (Exception e){
//			logger.info("EXCEPTION :  " + e.toString());
//		}
//		
//	}
//
//}
