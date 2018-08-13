package org.jbpm.demo.test.wih;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author ajarrett
 *
 */
public class DummyEmailWIHImpl implements WorkItemHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(DummyEmailWIHImpl.class);

  @Override
  public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
    logger.info("Email WIH invoked");
    manager.completeWorkItem(workItem.getId(), null);
  }

  @Override
  public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
    // TODO Auto-generated method stub
  }

}
