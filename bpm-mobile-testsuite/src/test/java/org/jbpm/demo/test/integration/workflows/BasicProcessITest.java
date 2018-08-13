package org.jbpm.demo.test.integration.workflows;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
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
import org.jbpm.demo.test.model.TestHarnessProcessObject;
import org.jbpm.demo.test.utils.BPMSIntegrationTestSteps;
import org.jbpm.demo.test.utils.KieServicesClientHelper;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.server.api.model.KieContainerResource;
import org.kie.server.api.model.ReleaseId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicProcessITest {
 
  private static Logger LOGGER = LoggerFactory.getLogger(BasicProcessITest.class);

  private static final String CONTAINER_ID = System.getProperty("kie.server.containerId", "integration.test.container.id");
  private static final String PROCESS_ID = "org.jbpm.demo.NewApplication";
  private static final String PROJECT_VERSION = System.getProperty("kie.kjar.artifact.version", "0.0.1-SNAPSHOT");
  private static final String PROJECT_GROUP_ID = System.getProperty("kie.kjar.artifact.groupid", "org.jbpm.demo");
  private static final String PROJECT_ARTIFACT_ID = System.getProperty("kie.kjar.artifact.id", "bpm-mobile-workflows");

  private static BPMSIntegrationTestSteps integrationTestSteps;

  @BeforeClass
  public static void deploy() throws FileNotFoundException, IOException, XmlPullParserException {
    KieContainerResource containerResource = new KieContainerResource(CONTAINER_ID, new ReleaseId(PROJECT_GROUP_ID, PROJECT_ARTIFACT_ID, PROJECT_VERSION));
    KieServicesClientHelper.getInstance().getKieServicesClient().createContainer(CONTAINER_ID, containerResource);
    integrationTestSteps = new BPMSIntegrationTestSteps();
  }

  @AfterClass
  public static void undeploy() {
    
    // Cleanup after test if fails
    List<org.kie.server.api.model.instance.ProcessInstance> pis = integrationTestSteps.queryClient.findProcessInstancesByContainerId(CONTAINER_ID, Arrays.asList(ProcessInstance.STATE_ACTIVE), 0, 1000);
    for (org.kie.server.api.model.instance.ProcessInstance p : pis){
      if (p.getState().equals(ProcessInstance.STATE_ACTIVE)){
        integrationTestSteps.processClient.abortProcessInstance(CONTAINER_ID, p.getId());
      }
    }
    
    // Undeploy Container
    KieServicesClientHelper.getInstance().getKieServicesClient().disposeContainer(CONTAINER_ID);
  }
 
  @Test
  public void basicIntegrationTest_HappyPath() throws Exception {

    // Provide the initial Process Start Vars
    Map<String, Object> processParams = new HashMap<String, Object>();
    processParams.put("application", this.newDummyApplicationObject());

    // Create new Process Instances
    final Long processInstanceId = integrationTestSteps.startProcessInstance(CONTAINER_ID, PROCESS_ID, processParams);
    LOGGER.info("Process instance id: " + processInstanceId + " process id: " + PROCESS_ID);

    // Define Process Steps, Owners and Params
    LinkedList<TestHarnessProcessObject> onboardingSteps = new LinkedList<TestHarnessProcessObject>();
    onboardingSteps.add(new TestHarnessProcessObject("Assign Internal Owner", "john", "group1", simpleTaskPayload("assignedTo", "john")));
    onboardingSteps.add(new TestHarnessProcessObject("Application Check", "john", "group1", simpleTaskPayload("applicationCheck", true)));
    onboardingSteps.add(new TestHarnessProcessObject("Request Additional Documentation", "john", "group1", simpleTaskPayload("docsProvided", true)));
    onboardingSteps.add(new TestHarnessProcessObject("Final Approval", "john", "group1", simpleTaskPayload("finalApproval", true)));
    
    // Run through Expected Human Task Steps
    integrationTestSteps.cycleHumanTasks(onboardingSteps, CONTAINER_ID);
    
    // Assert the Process has Been Completed
    integrationTestSteps.assertProcessComplete(CONTAINER_ID, processInstanceId);
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
  
  private Map<String, Object> simpleTaskPayload(String key, Object value){
    Map<String, Object> params = new HashMap<String, Object>();
    params.put(key, value);
    return params;
  }
  
}
