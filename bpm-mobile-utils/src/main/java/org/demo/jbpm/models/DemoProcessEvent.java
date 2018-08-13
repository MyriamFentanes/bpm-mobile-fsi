package org.demo.jbpm.models;

import java.util.Date;

public class DemoProcessEvent {
	
	private String alertName;
	private String alertMessage;
	private Date alertDate;
	private DemoTaskEvent task;
	private Long caseId;
	
	public String getAlertName() {
		return alertName;
	}

	public void setAlertName(String alertName) {
		this.alertName = alertName;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public DemoTaskEvent getTask() {
		return task;
	}

	public void setTask(DemoTaskEvent task) {
		this.task = task;
	}
	
	public Date getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}
	
	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}
	
}
