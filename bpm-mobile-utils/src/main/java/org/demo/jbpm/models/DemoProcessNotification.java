package org.demo.jbpm.models;

public class DemoProcessNotification extends DemoProcessEvent {
	private DemoProcessNotificationData data;
	
	public DemoProcessNotificationData getData() {
		return data;
	}
	public void setData(DemoProcessNotificationData data) {
		this.data = data;
	}

	
}
