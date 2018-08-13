package org.jbpm.demo.test.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Test Object for Storing Task Definitions.
 * @author ajarrett
 *
 */
public class TestHarnessProcessObject implements Serializable {

	private static final long serialVersionUID = 1L;

	public TestHarnessProcessObject() {
	}
	
	public TestHarnessProcessObject(String expectedTaskName, String expectedTaskOwner, String expectedTaskGroupId, Map<String, Object> taskParams) {
		super();
		this.expectedTaskName = expectedTaskName;
		this.expectedTaskOwner = expectedTaskOwner;
		this.expectedTaskGroupId = expectedTaskGroupId;
		this.taskParams = taskParams;
	}
	
	public TestHarnessProcessObject(String expectedTaskName, String expectedTaskOwner, String expectedTaskGroupId, Map<String, Object> taskParams, Map<String, Object> processParams) {
		super();
		this.expectedTaskName = expectedTaskName;
		this.expectedTaskOwner = expectedTaskOwner;
		this.expectedTaskGroupId = expectedTaskGroupId;
		this.taskParams = taskParams;
		this.processParams = processParams;
	}

	private String expectedTaskName;
	private String expectedTaskOwner;
	private String expectedTaskGroupId;
	private Map<String, Object> taskParams;
	private Map<String, Object> processParams;

	public String getExpectedTaskName() {
		return expectedTaskName;
	}

	public void setExpectedTaskName(String expectedTaskName) {
		this.expectedTaskName = expectedTaskName;
	}

	public String getExpectedTaskOwner() {
		return expectedTaskOwner;
	}

	public void setExpectedTaskOwner(String expectedTaskOwner) {
		this.expectedTaskOwner = expectedTaskOwner;
	}

	public Map<String, Object> getTaskParams() {
		return taskParams;
	}

	public void setTaskParams(Map<String, Object> taskParams) {
		this.taskParams = taskParams;
	}

	public String getExpectedTaskGroupId() {
		return expectedTaskGroupId;
	}

	public void setExpectedTaskGroupId(String expectedTaskGroupId) {
		this.expectedTaskGroupId = expectedTaskGroupId;
	}

	public Map<String, Object> getProcessParams() {
		return processParams;
	}

	public void setProcessParams(Map<String, Object> processParams) {
		this.processParams = processParams;
	}

}
