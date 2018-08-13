package org.jbpm.demo.test.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.kie.api.runtime.process.ProcessInstance.STATE_ABORTED;
import static org.kie.api.runtime.process.ProcessInstance.STATE_ACTIVE;
import static org.kie.api.runtime.process.ProcessInstance.STATE_COMPLETED;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jbpm.demo.test.model.TestHarnessProcessObject;
import org.kie.server.api.model.instance.NodeInstance;
import org.kie.server.api.model.instance.TaskInstance;
import org.kie.server.api.model.instance.TaskSummary;
import org.kie.server.client.QueryServicesClient;
import org.kie.server.client.ProcessServicesClient;
import org.kie.server.client.UserTaskServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reusable Integration Testing Class for both Integration Tests and BDD Integration Testseclipse-javadoc:☂=bpms-example-integration-test/%5C/Users%5C/ajarrett%5C/.m2%5C/repository%5C/org%5C/kie%5C/server%5C/kie-server-client%5C/6.5.0.Final-redhat-19%5C/kie-server-client-6.5.0.Final-redhat-19.jar%3Corg
 * @author ajarrett
 */
public class BPMSIntegrationTestSteps {
  
  private static Logger LOGGER = LoggerFactory.getLogger(BPMSIntegrationTestSteps.class);
  
  public final static ProcessServicesClient processClient = KieServicesClientHelper.getInstance().getKieServicesClient(JBPMTestUtils.remoteClasses()).getServicesClient(ProcessServicesClient.class);
  public final static QueryServicesClient queryClient = KieServicesClientHelper.getInstance().getKieServicesClient(JBPMTestUtils.remoteClasses()).getServicesClient(QueryServicesClient.class);
  public Map<String, UserTaskServicesClient> userTaskServiceClients;
  
  public BPMSIntegrationTestSteps() {
  }
  
  /**
   * Maintains Map<String, UserTaskServicesClient> of Task Clients
   * @param userId
   * @return
   */
  private UserTaskServicesClient obtainUserTaskServiceClient(String userId){

    if (userTaskServiceClients == null){
      userTaskServiceClients = new HashMap<String, UserTaskServicesClient>();
    }
    
    // Contains User Task Service Clients for each required user
    if (!userTaskServiceClients.containsKey(userId)){
      UserTaskServicesClient userTaskServicesClient = KieServicesClientHelper.getInstance().getKieServicesClientForUser(userId, JBPMTestUtils.remoteClasses()).getServicesClient(UserTaskServicesClient.class);
      userTaskServiceClients.put(userId, userTaskServicesClient);
    }
    
    return userTaskServiceClients.get(userId);
  }
  
  /**
   * 
   * @param containerId
   * @param processId
   * @param processVariables
   * @return
   */
  public long startProcessInstance(String containerId, String processId, Map<String, Object> processVariables ){
    
    LOGGER.info("Starting new Process Instance with ContainerID: {}, ProcessID: {} and Process Variables: {}", containerId, processId, processVariables);
    
    long processInstanceId = processClient.startProcess(containerId, processId, processVariables);
    assertEquals(STATE_ACTIVE, processClient.getProcessInstance(containerId, processInstanceId).getState().intValue());
    assertNotNull(processInstanceId);
    return processInstanceId;
  }
  
  public void assertProcessComplete(String containerId, long processInstanceId) {
    assertEquals(STATE_COMPLETED, processClient.getProcessInstance(containerId, processInstanceId).getState().intValue());
  }
  
  public void assertProcessActive(String containerId, long processInstanceId) {
    assertEquals(STATE_ACTIVE, processClient.getProcessInstance(containerId, processInstanceId).getState().intValue());
  }
  
  public void assertProcessAborted(String containerId, long processInstanceId) {
    assertEquals(STATE_ABORTED, processClient.getProcessInstance(containerId, processInstanceId).getState().intValue());
  }
  
  public void assertNodeActive(String containerId, long processInstanceId, String nodeId) {
    List<NodeInstance> nodes = queryClient.findActiveNodeInstances(processInstanceId, 0, 1000);
    assertNotNull(nodes);
    assertTrue(nodes.size() > 0);
    assertEquals(nodeId, nodes.get(0).getName());
  }
  
  public void assertTaskActive(TestHarnessProcessObject thpo) {
    UserTaskServicesClient userTaskServicesClient = this.obtainUserTaskServiceClient(thpo.getExpectedTaskOwner());
    
    // Obtain a list of all User Tasks  
    List<TaskSummary> taskList = userTaskServicesClient.findTasksAssignedAsPotentialOwner(thpo.getExpectedTaskOwner(), 0, 1000);
    assertTrue(taskList.size() > 0);
    assertEquals(thpo.getExpectedTaskName(), taskList.get(0).getName());
  }
  
  /**
   * 
   * @param containerId
   * @param processInstanceId
   */
  public void abortProcessInstance(String containerId, long processInstanceId){
    LOGGER.info("Aborting Process Instance with ContainerID: {}, Process Instance ID: {}", containerId, processInstanceId);
    
    assertEquals(STATE_ACTIVE, processClient.getProcessInstance(containerId, processInstanceId).getState().intValue());
    processClient.abortProcessInstance(containerId, processInstanceId);
    assertEquals(STATE_ABORTED, processClient.getProcessInstance(containerId, processInstanceId).getState().intValue());
  }
  
  /**
   * 
   * @param humanTaskSteps
   * @param containerId
   * @throws InterruptedException
   */
  public void cycleHumanTasks(LinkedList<TestHarnessProcessObject> humanTaskSteps, String containerId) throws InterruptedException {
    LOGGER.info("Cycling through tasks defined in the Onboarding Steps Object Map");
    boolean lastTask = false;
    
    for (int i = 0; i < humanTaskSteps.size(); i++){
      LOGGER.info("############### CYCLING TASK : {} of {}", i+1, humanTaskSteps.size());
      
      TestHarnessProcessObject thpo = humanTaskSteps.get(i);
      LOGGER.info("TASK OPERATION :: Task Number: {}, with expected Task Name: {}", i, thpo.getExpectedTaskName());
      
      if (i == humanTaskSteps.size()-1){
        lastTask = true;
      }
      
      this.prepareTaskCompletion(thpo, containerId, lastTask);
    }
  }
  
  /**
   * 
   * @param thpo
   * @param containerId
   */
  public void prepareTaskCompletion(TestHarnessProcessObject thpo, String containerId, boolean lastTask) {  
    
    UserTaskServicesClient userTaskServicesClient = this.obtainUserTaskServiceClient(thpo.getExpectedTaskOwner());
      
    // Obtain a list of all User Tasks
    List<TaskSummary> taskList = userTaskServicesClient.findTasksAssignedAsPotentialOwner(thpo.getExpectedTaskOwner(), 0, 1000);
    assertTrue(taskList.size() > 0);
    assertEquals(thpo.getExpectedTaskName(), taskList.get(0).getName());
  
    // Claim Task with Passed Data
   //this.claimHumanTask(userTaskServicesClient, containerId, taskList.get(0).getId(), thpo.getExpectedTaskOwner());
    
    // Start Task with Passed Data
    this.startHumanTask(userTaskServicesClient, containerId, taskList.get(0).getId(), thpo.getExpectedTaskOwner());
    
    // Complete Task with Passed Data
    this.completeHumanTask(userTaskServicesClient, containerId, taskList.get(0).getId(), thpo.getExpectedTaskOwner(), thpo.getTaskParams(), lastTask);
  }
  
  /**
   * 
   * @param containerId
   * @param taskId
   * @param userId
   * @param taskParams
   * @param expectedTaskName
   */
  public void claimHumanTask(UserTaskServicesClient userTaskServicesClient, String containerId, long taskId, String userId, Map<String, Object> taskParams, String expectedTaskName) {
    TaskInstance task = userTaskServicesClient.getTaskInstance(containerId, taskId);
    assertEquals(task.getName(), expectedTaskName);
    
    LOGGER.info("TASK OPERATION  ::: Claiming User Task: {}, for userId: {} ", task.getName(), userId);
  
    // Cycle Task Actions
    userTaskServicesClient.claimTask(containerId, taskId, userId);
    assertEquals(task.getStatus(), "Reserved");
  }  
  
  /**
   * 
   * @param containerId
   * @param taskId
   * @param userId
   */
  public void claimHumanTask(UserTaskServicesClient userTaskServicesClient, String containerId, long taskId, String userId) {
    LOGGER.info("TASK OPERATION  ::: Claiming User Task with Id: {}, for userId: {} ", taskId, userId);
  
    // Cycle Task Actions
    userTaskServicesClient.claimTask(containerId, taskId, userId);
    TaskInstance task = userTaskServicesClient.getTaskInstance(containerId, taskId);
    assertEquals(task.getStatus(), "Reserved");
  }
  
  /**
   * 
   * @param containerId
   * @param taskId
   * @param userId
   */
  public void startHumanTask(UserTaskServicesClient userTaskServicesClient, String containerId, long taskId, String userId) {
    TaskInstance task = userTaskServicesClient.getTaskInstance(containerId, taskId);
    assertNotNull(task);
    
    LOGGER.info("TASK OPERATION  ::: Starting User Task: {}, for userId: {} ", task.getName(), userId);
  
    // Cycle Task Actions
    userTaskServicesClient.startTask(containerId, taskId, userId);
    task = userTaskServicesClient.getTaskInstance(containerId, taskId);
    assertEquals(task.getStatus(), "InProgress");
  }
  
  /**
   * 
   * @param containerId
   * @param taskId
   * @param userId
   * @param taskParams
   */
  public void completeHumanTask(UserTaskServicesClient userTaskServicesClient, String containerId, long taskId, String userId, Map<String, Object> taskParams, boolean lastTask) {
    LOGGER.info("TASK OPERATION  ::: Completing User Task with Id: {}, for userId: {} ", taskId, userId);
  
    // Cycle Task Actions
    userTaskServicesClient.completeTask(containerId, taskId, userId, taskParams);
    
    // Prevent No Session ID Context found error due to process completing
    if (!lastTask){
      TaskInstance task = userTaskServicesClient.getTaskInstance(containerId, taskId);
      assertEquals(task.getStatus(), "Completed");
    }
  }
}
