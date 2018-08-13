package org.demo.jbpm.workitems;

import java.io.IOException;
import java.io.StringReader;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.demo.jbpm.exceptions.RESTServiceException;
import org.jbpm.process.workitem.AbstractLogOrThrowWorkItemHandler;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;


public class CompanyInformationWorkItem extends AbstractLogOrThrowWorkItemHandler{

	private static Logger logger = LoggerFactory.getLogger(CompanyInformationWorkItem.class);
	private ClassLoader classLoader;
	
	public CompanyInformationWorkItem(){
		
	}
	public CompanyInformationWorkItem(ClassLoader cl){
		this.classLoader = cl;
	}
	
	public void executeWorkItem(WorkItem workItem, WorkItemManager workItemMgr) {
		
		
		logger.debug("Its started....");
		boolean handleException = false;
		final String companyName = (String) workItem.getParameter("Operation");
        final String endpointAddress = (String) workItem.getParameter("Endpoint");
        final String authorizationToken = (String) workItem.getParameter("AuthorizationToken");   
        final String resultClass = (String) workItem.getParameter("ResultClass");
        String handleExceptionStr = (String) workItem.getParameter("HandleResponseErrors");
        final long workItemId = workItem.getId();
        
		Integer connectTimeout = 60000; //time in milliseconds
		Integer readTimeout = 60000; // time in milliseconds
		String urlStr = "https://api.companieshouse.gov.uk/search/companies?q=06346759";
		this.classLoader = this.getClass().getClassLoader();
		
		Map<String, Object> results = new HashMap<String, Object>();
		
		CloseableHttpClient httpClient = getHttpClient(readTimeout,connectTimeout);
		
   
        Map<String,Object> params = workItem.getParameters();
        
        if (handleExceptionStr != null) {
        	handleException = Boolean.parseBoolean(handleExceptionStr);
        }
        
        for(Map.Entry<String, Object> entry : params.entrySet()){
            logger.info("Value passed in for WorkItemId: " + workItemId + " : " + entry.getKey() + " " + entry.getValue().toString());
        }
       
        try 
		{
			//Create Authorization token
			String encoding = Base64.getEncoder().encodeToString(("n3WlEet8k_gHjao_StBTknsK4D7zUikO1ErIvUQ4").getBytes());
			
			HttpGet getRequest = new HttpGet(urlStr);
			getRequest.addHeader("accept", "application/json");
			getRequest.addHeader("Authorization", "Basic "+ encoding);
			
			logger.info("Sending Request" + getRequest.toString());
			//Send rest get request
			HttpResponse response = httpClient.execute(getRequest);
			StatusLine statusLine = response.getStatusLine();
        	int responseCode = statusLine.getStatusCode();
        	logger.info("Printing Response: " + response.toString());
        	HttpEntity respEntity = response.getEntity();
        	String responseBody = null;
        	String contentType = null;
        	
        	if(respEntity != null) {
        		responseBody = EntityUtils.toString(respEntity);
        		logger.debug("Output from rest get request: "+ responseBody);
        		
        		if (respEntity.getContentType() != null){
        			contentType = respEntity.getContentType().getValue();
        		}
        	}
        	if (responseCode >=200 && responseCode <300){
        		
        		postProcessResult(responseBody, resultClass, contentType, results);
        		results.put("StatusMsg", "request to endpoint " + urlStr + " successfully completed " + statusLine.getReasonPhrase());
        	
        	} else {
	        	if (handleException) {
	        		new RESTServiceException(responseCode, responseBody, urlStr);
	        	} else {
		            logger.warn("Unsuccessful response from REST server (status: {}, endpoint: {}, response: {}", 
		                    responseCode, urlStr, responseBody);
		            results.put("StatusMsg", "endpoint " + urlStr + " could not be reached: " + responseBody);
	        	}
	        }
        	
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatusLine().getStatusCode());
			}
			
			results.put("Status", responseCode);
			
			logger.info("Results:" + results.get("Result"));
			logger.info("Results:" + results.get("Status"));
			
	        workItemMgr.completeWorkItem(workItem.getId(), results); 
			
		 } catch (ClientProtocolException pe) {

			pe.printStackTrace();
			
		 } catch (IOException ioe) {

			ioe.printStackTrace();
		 }
		
		finally {
    	    
			try { 
    	        close(httpClient);
    	    } catch( Exception e ) { 
    	        e.printStackTrace();
    	    }
		}
       
	}
	
	protected void close(HttpClient httpClient) throws IOException{
		((CloseableHttpClient) httpClient).close();
	}

	public void abortWorkItem(WorkItem arg0, WorkItemManager arg1) {
		// TODO Auto-generated method stub
		
	}
	protected void postProcessResult(String result, String resultClass, String contentType, Map<String, Object> results) {
      
		logger.info("postProcessResultObject: "+  result + " " + resultClass );
		if (!StringUtils.isEmpty(resultClass) && !StringUtils.isEmpty(contentType)) {
            try {
                Class<?> clazz = Class.forName(resultClass, true, classLoader);
                
                Object resultObject = transformResult(clazz, contentType, result);
                                
                results.put("Result", resultObject);
            } catch (Throwable e) {
                throw new RuntimeException("Unable to transform respose to object", e);
            }
        } else {
        
            results.put("Result", result);
        }
    }
	
	protected Object transformResult(Class<?> clazz, String contentType, String content) throws Exception {
        
        if (contentType.toLowerCase().contains("application/json")) {
            ObjectMapper mapper = new ObjectMapper();
            
            return mapper.readValue(content, clazz);
        } else if (contentType.toLowerCase().contains("application/xml")) {
            StringReader result = new StringReader(content);
            JAXBContext jaxbContext = JAXBContext.newInstance(new Class[]{clazz});
            
            return jaxbContext.createUnmarshaller().unmarshal(result);
        }
        logger.warn("Unable to find transformer for content type '{}' to handle for content '{}'", contentType, content);
        // unknown content type, returning string representation
        return content;
        
    }
	
	protected CloseableHttpClient getHttpClient(Integer readTimeout, Integer connectTimeout){
			
			
		RequestConfig config = RequestConfig.custom()
					.setConnectTimeout(connectTimeout)
					.setSocketTimeout(6000)
					.setConnectionRequestTimeout(6000)
					.build();

		HttpClientBuilder clientBuilder = HttpClientBuilder.create().setDefaultRequestConfig(config);
			
		CloseableHttpClient httpClient = clientBuilder.build();
			

		return httpClient;
		
	}

}
