package org.demo.jbpm.workitems;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.demo.jbpm.models.DemoProcessNotification;
import org.demo.jbpm.models.DemoProcessNotificationData;
import org.demo.jbpm.utils.RestPost;
import org.demo.jbpm.utils.RestConstants.EventType;
import org.demo.jbpm.utils.RestConstants.PayloadType;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

public class ProcessEventNotifier {

	private static Logger logger = LoggerFactory.getLogger(ProcessEventNotifier.class);

	public ProcessEventNotifier() {

	}

	public void executeWorkItem(WorkItem workItem, WorkItemManager workItemMgr) {

		//Extract variables from the work item handler
		final String alertData = (String) workItem.getParameter("AlertData");
		final String alertName = (String) workItem.getParameter("AlertName");
		final String alertMessage = (String) workItem.getParameter("AlertMessage");

		//Create results object
		Map<String, Object> results = new HashMap<String, Object>();

		//get the class load passed by the work item handler.
		//this.classLoader = this.getClass().getClassLoader();

		logger.info("Sending Request....");
		
		//Create request object
		String request = this.createProcessNotificationJSONObj(workItem.getProcessInstanceId(), alertName, alertMessage, alertData);
		
		// Send rest get request
		String response = new RestPost().PostTaskRestAlert(request,EventType.NOTIFY, PayloadType.JSON );
		
		//Extract rest response values
		
		logger.info("Printing Response: " + response.toString());


		results.put("Status", response);

		logger.info("Results:" + results.get("Result"));
		logger.info("Status:" + results.get("Status"));

		workItemMgr.completeWorkItem(workItem.getId(), results);

	}

	protected void close(HttpClient httpClient) throws IOException {
		((CloseableHttpClient) httpClient).close();
	}

	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		// TODO Auto-generated method stub

	}

	private String createProcessNotificationJSONObj(Long procInst ,String alertName, String alertMessage, String alertData ){
		
		Gson gson = new Gson();
		DemoProcessNotification  processAlertData = new DemoProcessNotification();
		DemoProcessNotificationData procNotifyData = new DemoProcessNotificationData();
		procNotifyData.setData(alertData);

		processAlertData.setAlertDate(new Date());
		processAlertData.setAlertMessage(alertMessage);
		processAlertData.setAlertName(alertName);
		processAlertData.setCaseId(procInst);
		processAlertData.setData(procNotifyData);

		return gson.toJson(processAlertData);	
	}
}