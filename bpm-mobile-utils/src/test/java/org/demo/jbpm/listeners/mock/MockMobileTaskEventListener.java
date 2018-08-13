package org.demo.jbpm.listeners.mock;

import org.demo.jbpm.listeners.MobileTaskEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MockMobileTaskEventListener extends MobileTaskEventListener {
	
	private static final Logger logger = LoggerFactory.getLogger(MockMobileTaskEventListener.class);
	
	@Override
	public String sendTaskEvent(String requestPayload) {

		logger.info("Sending Task Event Listener Object to Endpoint..");

		return "200";
	}

}
