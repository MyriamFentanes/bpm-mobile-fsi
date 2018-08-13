package org.demo.jbpm.workitems;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jbpm.process.workitem.AbstractLogOrThrowWorkItemHandler;
import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.google.gson.JsonElement;
 

public class NewApplicationValidatorWorkItemHandler extends AbstractLogOrThrowWorkItemHandler {


	private ClassLoader classloader;
	private static final Logger logger = LoggerFactory.getLogger(NewApplicationValidatorWorkItemHandler.class);
	
	public NewApplicationValidatorWorkItemHandler(ClassLoader cl){
		  
		 this.classloader = cl;
		 
	}
	
	public NewApplicationValidatorWorkItemHandler(){
		  
		 
	}
	
	public void abortWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
		// TODO Auto-generated method stub
		
	}
	
	public void executeWorkItem(WorkItem workItem, WorkItemManager workItemManager) {
	    
		//Response param list
		Map<String, Object> params = new HashMap<String, Object>();
		
		String requestString = (String) workItem.getParameter("RequestString");
	    String className = (String) workItem.getParameter("ClassName");
	    boolean companyDetails = false;
	    boolean financialInfo = false;
	    boolean incomeDetails = false;	
	    boolean existingLoanDetails = false;	   
	    boolean personalDetails = false;
	    boolean demographics = false;
	    boolean creditDetails = false;
	    boolean email = false;
	    boolean phone = false;
	    boolean employmentDetails = false;
	    
	    String appType = "Personal";
	    
	    JsonParser parser = new JsonParser();
	    try{
	    	//Gson gson = new GsonBuilder().setPrettyPrinting().create();
	   
	    	JsonElement jsonElement = parser.parse(requestString);
	    	JsonObject jsonObj = jsonElement.getAsJsonObject();
	    	
	    	logger.debug("Is Array: " + jsonElement.isJsonArray() +" Is Null: "+ jsonElement.isJsonNull() + " Json Object: " + jsonElement.isJsonObject());
	    	
	    	
	    		
	    	if(jsonObj.has("application")){
	    		logger.debug("Application member found");	    
		    	
	    	    if(!jsonObj.getAsJsonObject("application").getAsJsonObject("personalDetails").isJsonNull())
		    	{
		    		personalDetails = true;
		    		logger.debug("Personal Details: " + personalDetails); 
		    	}
		    
		    	if (!jsonObj.getAsJsonObject("application").getAsJsonObject("personalDetails").getAsJsonObject("demographics").isJsonNull())
		    	{
		    		demographics = true;
		    		logger.debug("Demographics: " + demographics); 
		    	}
		    	
		    	if (!jsonObj.getAsJsonObject("application").getAsJsonObject("personalDetails").getAsJsonObject("email").isJsonNull())
		    	{
		    		email = true;
		    		logger.debug("Email: " + demographics); 
		    	}
		    	
		    	
		    	if (!jsonObj.getAsJsonObject("application").getAsJsonObject("financialInformation").isJsonNull())
		    	{
		    		financialInfo  = true;
		    		logger.debug("Financial Info: " + financialInfo); 
		    	}
		    	
		    	Set<Entry<String, JsonElement>> cred = jsonObj.getAsJsonObject("application").entrySet();
		    	
		    	 Iterator<Entry<String, JsonElement>> flavoursIter = cred.iterator();
		    	 while (flavoursIter.hasNext()){
		    		
		    		 JsonElement data = flavoursIter.next().getValue();
		    		 JsonObject json = data.getAsJsonObject();
		    		 System.out.println(data);
		    		 
		    		 Iterator<Entry<String, JsonElement>> cred1 = json.entrySet().iterator();
		    		 while (cred1.hasNext()){
		    			 System.out.println(cred1.next().getValue());
		    		 }
		    	 }
		    	    
		    	if (!jsonObj.getAsJsonObject("application").getAsJsonObject("financialInformation").getAsJsonObject("creditDetails").isJsonNull())
		    	{
		    		creditDetails = true;
		    		logger.debug("Credit Details: " + creditDetails); 
		    	}
		    	
		    	if (!jsonObj.getAsJsonObject("application").getAsJsonObject("companyDetails").isJsonNull())
		    	{
		    		companyDetails = true;
		    		appType = "Commercial";
		    		logger.debug("Credit Details: " + companyDetails); 
		    	}
		    	
	    	}
	    	

	    	
	    	/**if (className != null && className.length() >0){
	    		Class<?> type = loadClass(className);
		    	type = gson.fromJson(requestString, type.getClass());
		    	System.out.println(type);
	    	}**/
	    	
	    	params.put("CompanyDetails", companyDetails);	
	    	params.put("FinancialInfo", financialInfo);
	    	params.put("IncomeDetails", incomeDetails);
	    	params.put("ExistingLoanInfo", existingLoanDetails);
	    	params.put("ApplicationType", appType);	    	
	    }
        
	    finally{
	    	
	    }
	    
        workItemManager.completeWorkItem(workItem.getId(), params);
	    
		
	}
	private Class<?> loadClass(String clazz) {
        try {
            return Class.forName(clazz, false, classloader);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Can't load type " + clazz);
        }
    }
}
