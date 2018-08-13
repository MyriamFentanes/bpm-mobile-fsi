package org.demo.jbpm.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import org.demo.jbpm.utils.RestConstants.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoadConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(LoadConfig.class.getName());
	
	private Properties prop = null;
	private String restURL = null;
	
	public String getRestEventURL(EventType eventType) {
		
		String propPath = System.getProperty("bpm.demo.props.location") + "/bpmDemoProps.properties";
		
		if (!propPath.isEmpty()){
			InputStream is = null;
		    try {
		         this.prop = new Properties();
		         is = this.getClass().getResourceAsStream("/bpmDemoProps.properties");
		         prop.load(is);
		         
		         switch(eventType){
		         	case NOTIFY:
		         		restURL = prop.getProperty("mobile.rest.push.notify");
		         		logger.info("Rest URL: " + restURL);
		         		break;
		         	
		         	case TASK_EVENT:
		         		restURL = prop.getProperty("mobile.rest.pull.notify");
		         		logger.info("Rest URL: " + restURL);
		         		break;
		         		
		         	default:
		         		restURL = prop.getProperty("mobile.rest.url");
		         		logger.info("Rest URL: " + restURL);
		         		break;
		         }

		    } catch (FileNotFoundException e) {
		         e.printStackTrace();
		    } catch (IOException e) {
		         e.printStackTrace();
		   }
		}
		else {
			try {
				this.prop = new Properties();
				URL url = new URL(propPath);
				prop.load(url.openStream());
				restURL = prop.getProperty("mobile.rest.url");
			} catch (FileNotFoundException e) {
		         e.printStackTrace();
		   } catch (IOException e) {
		         e.printStackTrace();
		   }
		}
		return restURL;
	}
}

