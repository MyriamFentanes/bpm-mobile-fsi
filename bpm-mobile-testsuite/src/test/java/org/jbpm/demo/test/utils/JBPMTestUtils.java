package org.jbpm.demo.test.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.*;
import org.demo.jbpm.models.Address;
import org.demo.jbpm.models.Application;
import org.demo.jbpm.models.CompanyDetails;
import org.demo.jbpm.models.CreditDetails;
import org.demo.jbpm.models.Demographics;
import org.demo.jbpm.models.Email;
import org.demo.jbpm.models.EmploymentDetails;
import org.demo.jbpm.models.ExistingLoanDetails;
import org.demo.jbpm.models.ExpenseDetails;
import org.demo.jbpm.models.FinancialInformation;
import org.demo.jbpm.models.IncomeDetails;
import org.demo.jbpm.models.Name;
import org.demo.jbpm.models.PersonalDetails;
import org.demo.jbpm.models.Phone;
import org.demo.jbpm.models.Status;
import org.demo.jbpm.models.StatusList;
import org.jbpm.demo.test.model.TestHarnessProcessObject;
import org.kie.api.runtime.manager.audit.VariableInstanceLog;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Reusable Test Utility Methods
 * 
 * @author ajarrett
 *
 */
public class JBPMTestUtils {

  private static final Logger logger = LoggerFactory.getLogger(JBPMTestUtils.class);

  /**
   * Audit Assertions
   * 
   * @param intanceLogList
   * @param variableName
   * @param variableValue
   */
  public void assertAuditValueOfVariable(List<? extends VariableInstanceLog> intanceLogList, String variableName, String variableValue) {
    for (VariableInstanceLog variable : intanceLogList) {
      logger.info("ID : " + variable.getVariableId() + " Value : " + variable.getValue());
      if (variable.getVariableId().equals(variableName))
        assertEquals(true, variable.getValue().equals(variableValue));
    }
  }

  /**
   * Loop over the Task Steps list and complete tasks
   * 
   * @param onboardingSteps
   * @throws InterruptedException
   */
  public void runProcessTasks(LinkedList<TestHarnessProcessObject> onboardingSteps, TaskService taskService) throws InterruptedException {
    logger.info("Cycling through tasks defined in the Onboarding Steps Object Map");
    int i = 0;
    for (TestHarnessProcessObject thpo : onboardingSteps) {
      logger.info("Processing Task : " + i + " :: " + thpo.getExpectedTaskName());
      this.prepareTaskCompletion(thpo, taskService);
      i++;
    }
    logger.info("Completed Cycling through tasks defined in the Onboarding Steps Object Map");
  }

  /**
   * Assumes 1 Active Task is attached to a user. Update model if you need to pass a param with
   * expected active tasks
   * 
   * @param testHarnessProcessObject
   */
  public void prepareTaskCompletion(TestHarnessProcessObject thpo, TaskService taskService) {

    // Obtain a list of all User Tasks
    List<TaskSummary> taskList = taskService.getTasksAssignedAsPotentialOwner(thpo.getExpectedTaskOwner(), "en-UK");
    assertTrue(taskList.size() > 0);
    assertEquals(thpo.getExpectedTaskName(), taskList.get(0).getName());

    // Complete Task with Passed Data
    this.fullCycleCompleteTask(taskService, taskList.get(0).getId(), thpo.getExpectedTaskOwner(), thpo.getTaskParams(),
        thpo.getExpectedTaskName());

  }

  /**
   * Util method to Claim, Start and Complete a Task with/without Task Params
   * 
   * @param localTaskService
   * @param taskId
   * @param userId
   * @param groupids
   * @param taskParams
   */
  public void fullCycleCompleteTask(TaskService taskService, long taskId, String userId, Map<String, Object> taskParams, String taskName) {
    logger.info("Task Cycling Starting: " + taskName);

    // Cycle Task Actions
    taskService.claim(taskId, userId);
    assertEquals(taskService.getTaskById(taskId).getTaskData().getStatus().toString(), "Reserved");
    taskService.start(taskId, userId);
    assertEquals(taskService.getTaskById(taskId).getTaskData().getStatus().toString(), "InProgress");
    taskService.complete(taskId, userId, taskParams);
    assertEquals(taskService.getTaskById(taskId).getTaskData().getStatus().toString(), "Completed");
    logger.info("Task Cycling Complete: " + taskName);
  }

  public static Class<?>[] remoteClasses() {
    Class<?>[] remoteClasses = new Class<?>[16];
    remoteClasses[0] = Address.class;
    remoteClasses[1] = Application.class;
    remoteClasses[2] = CompanyDetails.class;
    remoteClasses[3] = CreditDetails.class;
    remoteClasses[4] = Demographics.class;
    remoteClasses[5] = Email.class;
    remoteClasses[6] = EmploymentDetails.class;
    remoteClasses[7] = ExistingLoanDetails.class;
    remoteClasses[8] = ExpenseDetails.class;
    remoteClasses[9] = FinancialInformation.class;
    remoteClasses[10] = IncomeDetails.class;
    remoteClasses[11] = Name.class;
    remoteClasses[12] = PersonalDetails.class;
    remoteClasses[13] = Phone.class;
    remoteClasses[14] = Status.class;
    remoteClasses[15] = StatusList.class;
    return remoteClasses;
  }

}
