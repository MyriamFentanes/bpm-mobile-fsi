package org.demo.jbpm.models;

import java.util.Date;

public class DemoTaskEvent extends DemoProcessEvent   {
	
	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public Date getTaskCreatedDate() {
		return taskCreatedDate;
	}

	public void setTaskCreatedDate(Date taskCreatedDate) {
		this.taskCreatedDate = taskCreatedDate;
	}

	public Date getTaskExpiryDate() {
		return taskExpiryDate;
	}

	public void setTaskExpiryDate(Date taskExpiryDate) {
		this.taskExpiryDate = taskExpiryDate;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskCaseId() {
		return taskCaseId;
	}

	public void setTaskCaseId(String taskCaseId) {
		this.taskCaseId = taskCaseId;
	}

	private String taskId;
	private String taskName;
	private String taskCaseId;
	private String taskOwner;
	private Date taskCreatedDate;
	private Date taskExpiryDate;
	private String taskStatus;
	
}
