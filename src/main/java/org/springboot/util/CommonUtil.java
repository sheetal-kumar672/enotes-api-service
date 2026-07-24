package org.springboot.util;

import org.springboot.handler.GenricResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonUtil {
	
	public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status)
	{
		GenricResponse response = new GenricResponse();
				response.setResponseStatus(status);
				response.setStatus("success");
				response.setMessage("success");	
				response.setData(data);
				
			return response.create();		
	}

	public static ResponseEntity<?> createBuildResponseMessage( String message,HttpStatus status)
	{
		GenricResponse response = new GenricResponse();
				response.setResponseStatus(status);
				response.setStatus("success");
				response.setMessage(message);
						
			return response.create();		
	}
	
	public static ResponseEntity<?> createErrorResponse(Object data, HttpStatus status)
	{
		GenricResponse response = new GenricResponse();
				response.setResponseStatus(status);
				response.setStatus("failed");
				response.setMessage("failed");
				response.setData(data);
					
			return response.create();		
	}
	
	public static ResponseEntity<?> createErrorResponseMessage(String message, HttpStatus status)
	{
		GenricResponse response = new GenricResponse();
				response.setResponseStatus(status);
				response.setStatus("failed");
				response.setMessage(message);
				
				
			return response.create();
				
				
	}
	
	private static GenricResponse GenricResponse() {
		return null;
	}

}
