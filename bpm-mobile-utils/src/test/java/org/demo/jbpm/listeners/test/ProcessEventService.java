package org.demo.jbpm.listeners.test;
//package demo.jbpm.listener.test;
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.ResponseBuilder;
//
//import org.jboss.logging.Logger;
//
//
//
//public class ProcessEventService {
//	
//	private static final Logger logger = Logger.getLogger(ProcessEventService.class.getName());	
//		
//    @POST
//    @Path("/task")
//    @Consumes("application/json")
//    public Response post(String name) {
//    	
//        logger.info("Invoking ProcessEvent Service: ");
//     
//    	ResponseBuilder response = Response.ok();
//    	logger.info("RESPONSE PAYLOAD 1 " + response.build().getStatus() );
//    	
//        
//    	return response.build();
//		
//    }
//
//}
//
//
