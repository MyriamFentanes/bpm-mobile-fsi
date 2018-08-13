package org.demo.jbpm.listeners;

import java.util.Date;
import org.demo.jbpm.models.DemoTaskEvent;
import org.demo.jbpm.utils.RestPost;
import org.demo.jbpm.utils.RestConstants.EventType;
import org.demo.jbpm.utils.RestConstants.PayloadType;
import org.kie.api.task.TaskEvent;
import org.kie.api.task.TaskLifeCycleEventListener;
import org.kie.api.task.model.TaskData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class MobileTaskEventListener implements TaskLifeCycleEventListener {

	private static final Logger logger = LoggerFactory.getLogger(MobileTaskEventListener.class);

	private RestPost restUtils;

	@Override
	public void afterTaskActivatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void afterTaskAddedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskClaimedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskCompletedEvent(TaskEvent event) {
		// TODO Auto-generated method stub
		logger.info("After Task Completed Event Triggered...");
		
		System.getProperty("org.demo.jbpm.mobile.emailProvider");

		String requestPayload = this.createProcessTaskJSONObj(event, "AfterTaskCompletedEvent", "Action Required");
		String result = sendTaskEvent(requestPayload);

		logger.info("AfterTaskCompletedEvent : Task Id - " + event.getTask().getId());
		logger.info("AfterTaskCompletedEvent : Process Id - " + event.getTask().getTaskData().getProcessInstanceId() + " " + result);
	}

	@Override
	public void afterTaskDelegatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskExitedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskFailedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskForwardedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskNominatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskReleasedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskResumedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskSkippedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskStartedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskStoppedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTaskSuspendedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskActivatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskAddedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskClaimedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskCompletedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskDelegatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskExitedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskFailedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskForwardedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskNominatedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskReleasedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskResumedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskSkippedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskStartedEvent(TaskEvent event) {
		logger.info("Before Task Started Event Triggered...");

		String requestPayload = this.createProcessTaskJSONObj(event, "BeforeTaskStartedEvent", "Action Required");
		String result = sendTaskEvent(requestPayload);

		logger.info("BeforeTaskStartedEvent : Task Id - " + event.getTask().getId());
		logger.info("BeforeTaskStartedEvent : Process Id - " + event.getTask().getTaskData().getProcessInstanceId()
				+ " " + result);

	}

	@Override
	public void beforeTaskStoppedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTaskSuspendedEvent(TaskEvent arg0) {
		// TODO Auto-generated method stub

	}

	private String createProcessTaskJSONObj(TaskEvent event, String alertName, String alertMessage) {

		logger.info("Building new Serialised TaskEvent Object.");

		Gson gson = new Gson();
		TaskData taskData = event.getTask().getTaskData();
		DemoTaskEvent eventTask = new DemoTaskEvent();

		eventTask.setAlertDate(new Date());
		eventTask.setAlertMessage("test alertMessage");
		eventTask.setAlertName("test alertName");

		eventTask.setTaskCaseId(String.valueOf(event.getTask().getTaskData().getProcessInstanceId()));
		eventTask.setTaskId(event.getTask().getId().toString());
		eventTask.setTaskName(event.getTask().getName());
		eventTask.setTaskOwner(taskData.getActualOwner().toString());
		eventTask.setTaskExpiryDate(taskData.getExpirationTime());
		eventTask.setTaskCreatedDate(taskData.getCreatedOn());

		return gson.toJson(eventTask);

	}

	
	public String sendTaskEvent(String requestPayload) {

		logger.info("Sending Task Event Listener Object to Endpoint..");

		if (restUtils == null) {
			restUtils = new RestPost();
		}

		return restUtils.PostTaskRestAlert(requestPayload, EventType.NOTIFY, PayloadType.JSON);
	}

}
