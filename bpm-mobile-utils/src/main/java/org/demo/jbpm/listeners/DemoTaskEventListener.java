package org.demo.jbpm.listeners;


import org.kie.api.task.TaskEvent;
import org.kie.api.task.model.TaskData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.demo.jbpm.models.DemoTaskEvent;
import org.demo.jbpm.utils.RestPost;
import org.demo.jbpm.utils.RestConstants.EventType;
import org.demo.jbpm.utils.RestConstants.PayloadType;
import org.jbpm.services.task.events.DefaultTaskEventListener;

import java.util.Date;

import com.google.gson.Gson;

public class DemoTaskEventListener extends DefaultTaskEventListener  {

	private static final Logger logger = LoggerFactory.getLogger(DemoTaskEventListener.class.getName());
	private Long processId;
	private Long taskId;
	
	@Override
	public void beforeTaskActivatedEvent(TaskEvent event) {
		// intentionally left blank
		String requestPayload = this.createProcessTaskJSONObj(event, "BeforeTaskActivatedEvent", "Action Required");

		String result = new RestPost().PostTaskRestAlert(requestPayload, EventType.TASK_EVENT, PayloadType.JSON);
		processId = event.getTask().getTaskData().getProcessInstanceId();
		taskId = event.getTask().getId();
		
		logger.info("BeforeTaskActivatedEvent : Task Id - " + taskId);
		logger.info("BeforeTaskActivatedEvent : Process ID - " + processId + " " + result);
	}
	
	@Override
	public void beforeTaskDelegatedEvent(TaskEvent event) {
		String requestPayload = this.createProcessTaskJSONObj(event, "BeforeTaskDelegatedEvent", "Action Required");

		String result = new RestPost().PostTaskRestAlert(requestPayload, EventType.NOTIFY, PayloadType.JSON);
		
		processId = event.getTask().getTaskData().getProcessInstanceId();
		taskId = event.getTask().getId();
		
		logger.info("BeforeTaskDelegatedEvent : Task Id - " + taskId);
		logger.info("BeforeTaskDelegatedEvent : Process Id - " + processId + " " + result);
	}
	
	@Override
	public void afterTaskAddedEvent(TaskEvent event) {
		String requestPayload = this.createProcessTaskJSONObj(event, "AfterTaskAddedEvent", "Action Required");
	
		String result = new RestPost().PostTaskRestAlert(requestPayload, EventType.NOTIFY, PayloadType.JSON);
		processId = event.getTask().getTaskData().getProcessInstanceId();
		taskId = event.getTask().getId();
		
		logger.info("AfterTaskAddedEvent : Process Id - " + processId + " " + result);
		logger.info("AfterTaskAddedEvent : Task Id - " + taskId);
	}
	
	@Override
	public void beforeTaskStartedEvent(TaskEvent event) {

		String requestPayload = this.createProcessTaskJSONObj(event, "BeforeTaskStartedEvent", "Action Required");
		String result = new RestPost().PostTaskRestAlert(requestPayload, EventType.NOTIFY, PayloadType.JSON);
		
		processId = event.getTask().getTaskData().getProcessInstanceId();
		
		logger.info("BeforeTaskStartedEvent: Process ID - " + processId + " " + result);
		logger.info("BeforeTaskStartedEvent: Starting task - " + taskId);
	}
	
	private String createProcessTaskJSONObj(TaskEvent event, String alertName, String alertMessage){
		
		Gson gson = new Gson();
		TaskData taskData = event.getTask().getTaskData();
		DemoTaskEvent eventTask = new DemoTaskEvent();	
		
		eventTask.setAlertDate(new Date());
		eventTask.setAlertMessage("test alertMessage");
		eventTask.setAlertName("test alertName");
	
		eventTask.setTaskCaseId(processId.toString());
		eventTask.setTaskId(event.getTask().getId().toString());
		eventTask.setTaskName(event.getTask().getName());
		eventTask.setTaskOwner(taskData.getActualOwner().toString());
		eventTask.setTaskExpiryDate(taskData.getExpirationTime());
		eventTask.setTaskCreatedDate(taskData.getCreatedOn());
		
		return gson.toJson(eventTask);
		
	}
		
}
