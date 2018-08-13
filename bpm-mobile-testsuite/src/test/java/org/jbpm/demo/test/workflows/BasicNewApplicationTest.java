package org.jbpm.demo.test.workflows;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.jbpm.demo.test.wih.DummyEmailWIHImpl;
import org.jbpm.test.JbpmJUnitBaseTestCase;
import org.junit.Test;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.manager.RuntimeEngine;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.TaskSummary;
import org.kie.internal.runtime.manager.context.ProcessInstanceIdContext;

public class BasicNewApplicationTest extends JbpmJUnitBaseTestCase {
  private static final String PROCESS_ID = "org.jbpm.demo.NewApplication";
  private static final String PROCESS_DEFINITION = "org/demo/jbpm/NewApplication.bpmn2";
  private static final String USER = "bpmsAdmin";
  private static final String LOCALE = Locale.UK.toLanguageTag();

  public BasicNewApplicationTest() {
    super(true, true);
  }

  @Test
  public void testProcess() throws ParseException {
    createRuntimeManager(Strategy.PROCESS_INSTANCE, "runtime-manager", PROCESS_DEFINITION);
    RuntimeEngine runtimeEngine = getRuntimeEngine(ProcessInstanceIdContext.get());
    KieSession ksession = runtimeEngine.getKieSession();
    TaskService taskService = runtimeEngine.getTaskService();

    ksession.getWorkItemManager().registerWorkItemHandler("Email", new DummyEmailWIHImpl());

    Map<String, Object> processParams = new HashMap<String, Object>();
    processParams.put("application", this.newDummyApplicationObject());

    // Start process
    ProcessInstance processInstance = ksession.startProcess(PROCESS_ID, processParams);
    assertProcessInstanceActive(processInstance.getId());

    // Complete human task 1
    {
      assertNodeTriggered(processInstance.getId(), "Assign Internal Owner");

      Map<String, Object> humanTaskParameters = new HashMap<String, Object>();
      humanTaskParameters.put("assignedTo", "john");

      List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(USER, LOCALE);
      assertNotNull(list);
      assertTrue(list.size() > 0);
      TaskSummary task = list.get(0);
      taskService.start(task.getId(), USER);
      taskService.complete(task.getId(), USER, humanTaskParameters);

    }
    {
      // assertNodeTriggered(processInstance.getId(), "Application Check");
      // assertNodeTriggered(processInstance.getId(), "Check underage person Gateway");
      // assertNodeTriggered(processInstance.getId(), "Exclusive Gateway 2");
    }
    // Complete human task 2
    {
      assertNodeTriggered(processInstance.getId(), "Application Check");

      Map<String, Object> humanTaskParameters = new HashMap<String, Object>();
      humanTaskParameters.put("applicationCheck", true);

      taskService = runtimeEngine.getTaskService();
      List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(USER, LOCALE);
      TaskSummary task = list.get(0);
      taskService.start(task.getId(), USER);
      taskService.complete(task.getId(), USER, humanTaskParameters);
    }
    {
      assertNodeTriggered(processInstance.getId(), "Request Additional Documentation");

      Map<String, Object> humanTaskParameters = new HashMap<String, Object>();
      humanTaskParameters.put("docsProvided", true);

      taskService = runtimeEngine.getTaskService();
      List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(USER, LOCALE);
      TaskSummary task = list.get(0);
      taskService.start(task.getId(), USER);
      taskService.complete(task.getId(), USER, humanTaskParameters);
    }

    {
      assertNodeTriggered(processInstance.getId(), "Final Approval");

      Map<String, Object> humanTaskParameters = new HashMap<String, Object>();
      humanTaskParameters.put("finalApproval", true);

      taskService = runtimeEngine.getTaskService();
      List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(USER, LOCALE);
      TaskSummary task = list.get(0);
      taskService.start(task.getId(), USER);
      taskService.complete(task.getId(), USER, humanTaskParameters);
    }
    assertProcessInstanceCompleted(processInstance.getId());
  }

  private Application newDummyApplicationObject() throws ParseException {

    String date_str = "15-09-1972";
    DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Date date = formatter.parse(date_str);

    // Personal Details
    Name name = new Name(1L, "Mr", "Matthew", "James", "Hayden", "Matt Hayden");
    Demographics demographics = new Demographics(1L, "MALE", date, "Sydney", "AU", "AU");
    Address address = new Address(1L, "HOME_ADDRESS", "40A Orchard Road", "#99-99 Macdonald House", "Orchard Avenue 2", "Street 65", "AU");
    Email email = new Email(1L, "matt.hayden@gmail.com", "approved");
    Phone phone = new Phone(1L, "MOBILE", "0061", "353", "64042321", "100", true, true);
    PersonalDetails personalDetails = new PersonalDetails(name, demographics, Arrays.asList(address), email, phone);

    // Financial History
    ExpenseDetails expenseDetails = new ExpenseDetails(1L, "COSTS_OF_LIVING", new BigDecimal(590.25), "MONTHLY");
    IncomeDetails incomeDetails = new IncomeDetails(1L, "DECLARED_FIXED", new BigDecimal(7590.25), new BigDecimal(1590.25), "MONTHLY", "Rent");
    ExistingLoanDetails existingLoanDetails = new ExistingLoanDetails(1L, "STUDENT_LOAN", new BigDecimal(250.25), new BigDecimal(5000.25), "JOINT", "KINROS CORPORATION", new BigDecimal(10000));
    FinancialInformation financialInformation = new FinancialInformation(1L, true, true, Arrays.asList(expenseDetails),
        Arrays.asList(incomeDetails), Arrays.asList(existingLoanDetails));

    // Additional Personal Details
    EmploymentDetails employmentDetails = new EmploymentDetails(1L, "IRL Bank", "Accountant", "employed", 5);
    CompanyDetails companyDetails = new CompanyDetails(1L, "RedHat", 5, 123123123);
    CreditDetails creditDetails = new CreditDetails(1L, 2300, true, 5000);

    return new Application(1L, financialInformation, Arrays.asList(employmentDetails), personalDetails, creditDetails, companyDetails);
  }
}
