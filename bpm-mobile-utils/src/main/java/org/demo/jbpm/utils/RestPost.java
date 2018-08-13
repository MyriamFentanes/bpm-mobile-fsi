package org.demo.jbpm.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.demo.jbpm.utils.RestConstants.EventType;
import org.demo.jbpm.utils.RestConstants.PayloadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RestPost {

	private static final Logger logger = LoggerFactory.getLogger(RestPost.class.getName());

	public String PostTaskRestAlert(String jsonRequest, EventType eventType, PayloadType payloadType ) {

		//Create response object
		String response = null;
		//HttpResponse response;
		//Determine URL
		String url = new LoadConfig().getRestEventURL(eventType);

		//Set connection timeouts
		Integer connectTimeout = 600000; // time in milliseconds
		Integer readTimeout = 600000; // time in milliseconds

		CloseableHttpClient httpClient = getHttpClient(readTimeout, connectTimeout);

		HttpPost request = new HttpPost(url);

		try {
			StringEntity stringEntity = new StringEntity(jsonRequest);

			request.getRequestLine();
			request.setEntity(stringEntity);
			
			switch (payloadType){
				case JSON :
					request.addHeader("Content-Type", "application/json");
					break;
				case XML :
					request.addHeader("Content-Type", "application/xml");
					break;
				default:
					request.addHeader("Content-Type", "application/json");		
					break;
			}
			
	
			HttpResponse httpResponse = httpClient.execute(request);

			logger.info("Request Message : " + request.toString());			
			logger.info("Response Code : " + httpResponse.getStatusLine().getStatusCode());

			//Validate the response
			HttpEntity respEntity = httpResponse.getEntity();
			String responseBody = null;
			
			StatusLine statusLine = httpResponse.getStatusLine();
			int responseCode = statusLine.getStatusCode();

			if (respEntity != null) {
				responseBody = EntityUtils.toString(respEntity);
				logger.debug("Output from rest get request: " + responseBody);

			}
			if (responseCode >= 200 && responseCode < 300) {

				response = "Successful response from REST server (status: {}, endpoint: {}, response: {}" + responseCode + " " + responseBody;
				logger.info(response);
			}

			if (responseCode != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + httpResponse.getStatusLine().getStatusCode() + " " + httpResponse.toString());
			}

		}
		
		catch (ClientProtocolException e) {

			logger.error("Error: " + e.toString());
			e.printStackTrace();
		} 
		
		catch (IOException e) {

			logger.error("Error: " + e.toString());
			e.printStackTrace();
		}	
		finally {

			try {
				close(httpClient);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return response;
	}	

	protected CloseableHttpClient getHttpClient(Integer readTimeout, Integer connectTimeout){


		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(connectTimeout)
				.setSocketTimeout(6000)
				.setConnectionRequestTimeout(6000)
				.build();

		HttpClientBuilder clientBuilder = HttpClientBuilder.create().setDefaultRequestConfig(config);

		CloseableHttpClient closeableHttpClient = clientBuilder.build();


		return closeableHttpClient;

	}
	protected void close(HttpClient httpClient) throws IOException{
		((CloseableHttpClient) httpClient).close();
	}
}
