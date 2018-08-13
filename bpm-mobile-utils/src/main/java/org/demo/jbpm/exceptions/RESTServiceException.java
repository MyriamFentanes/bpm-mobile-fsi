package org.demo.jbpm.exceptions;

/**
 * Dedicated exception to provide information about failed REST service invocation.
 * This exception can be thrown when there was successful connection but response code 
 * is different than 2XX codes.
 *
 */
public class RESTServiceException extends RuntimeException {

	private static final long serialVersionUID = -2461370884711166642L;

	private int status;
	private String response;
	private String endpoint;
	
	public RESTServiceException(int status, String response, String endpoint) {
		super("Unsuccessful response from REST server (status " + status +", endpoint " + endpoint +", response " + response +"");
		this.status = status;
		this.response = response;
		this.endpoint = endpoint;
	}

	public int getStatus() {
		return status;
	}

	public String getResponse() {
		return response;
	}

	public String getEndoint() {
		return this.endpoint;
	}
	
	
}
