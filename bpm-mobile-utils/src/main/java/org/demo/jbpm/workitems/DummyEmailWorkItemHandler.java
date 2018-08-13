package org.demo.jbpm.workitems;

import org.jbpm.process.workitem.AbstractLogOrThrowWorkItemHandler;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DummyEmailWorkItemHandler extends AbstractLogOrThrowWorkItemHandler {

	public DummyEmailWorkItemHandler() {
	}

	private static final Logger logger = LoggerFactory.getLogger(DummyEmailWorkItemHandler.class);


	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {

		logger.info("Completing EMAIL Work Item Handler");
		
		// Currently not passing any details back to BPM
		manager.completeWorkItem(workItem.getId(), null);

	}

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		// TODO Auto-generated method stub

	}

}
