package org.demo.jbpm.listeners;

import java.util.Date;
import org.demo.jbpm.models.DemoProcessNotification;
import org.demo.jbpm.utils.RestPost;
import org.demo.jbpm.utils.RestConstants.EventType;
import org.demo.jbpm.utils.RestConstants.PayloadType;
import org.kie.api.event.process.ProcessCompletedEvent;
import org.kie.api.event.process.ProcessEventListener;
import org.kie.api.event.process.ProcessNodeLeftEvent;
import org.kie.api.event.process.ProcessNodeTriggeredEvent;
import org.kie.api.event.process.ProcessStartedEvent;
import org.kie.api.event.process.ProcessVariableChangedEvent;
import org.kie.api.runtime.process.NodeInstance;
import org.kie.api.runtime.process.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


public class DemoProcessEventListener implements ProcessEventListener{
	
	private static final Logger logger = LoggerFactory.getLogger(DemoProcessEventListener.class.getName());
	
	ProcessInstance procInst;
	String procInstanceId;
	NodeInstance nodeInst;
	
	
	public void afterNodeLeft(ProcessNodeLeftEvent event) {
		// TODO Auto-generated method stub
		 procInst = event.getProcessInstance();
		 nodeInst = event.getNodeInstance();
		
	}

	public void afterNodeTriggered(ProcessNodeTriggeredEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void afterProcessCompleted(ProcessCompletedEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void afterProcessStarted(ProcessStartedEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void afterVariableChanged(ProcessVariableChangedEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNodeLeft(ProcessNodeLeftEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNodeTriggered(ProcessNodeTriggeredEvent event) {
		
		 procInst = event.getProcessInstance();
		 nodeInst = event.getNodeInstance();
		 String nodeName = nodeInst.getNodeName();
	
		if(!nodeName.isEmpty() && nodeName.toUpperCase().startsWith("NOTIFY CUSTOMER")){
			
			String requestPayload = this.createProcessEventJSONObj(nodeInst, procInst, "BeforeTaskActivatedEvent", "Action Required");
			String sendAlertResponse = new RestPost().PostTaskRestAlert(requestPayload, EventType.NOTIFY, PayloadType.JSON);
			logger.info("PROCESS INSTANCE STATE :  = " + procInst.getState());
			logger.info("RESPONSE MESSAGE :  = " + sendAlertResponse);
		}
	}

	public void beforeProcessCompleted(ProcessCompletedEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void beforeProcessStarted(ProcessStartedEvent event) {
		// TODO Auto-generated method stub
		
	}

	public void beforeVariableChanged(ProcessVariableChangedEvent event) {
		// Implement Status Change Event
		
	}
	
	private String createProcessEventJSONObj(NodeInstance nodeInst, ProcessInstance procInst ,String alertName, String alertMessage){
		
		Gson gson = new Gson();
		DemoProcessNotification processAlertData = new DemoProcessNotification();

		processAlertData.setAlertDate(new Date());
		processAlertData.setAlertMessage("test alertMessage");
		processAlertData.setAlertName("test alertName");
		processAlertData.setCaseId(procInst.getParentProcessInstanceId());
		

		return gson.toJson(processAlertData);	
	}

}
