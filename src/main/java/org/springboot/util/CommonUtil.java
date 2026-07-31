package org.springboot.util;

import org.apache.commons.io.FilenameUtils;
import org.jspecify.annotations.Nullable;
import org.springboot.handler.GenricResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

	public static String getContentType(String originalFileName) {
		
		String extension = FilenameUtils.getExtension(originalFileName);
		
		switch(extension)
		{
		case "pdf":
			return "application/pdf";
		case "xlsx":
			return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
		case "txt":
			return "text/plan";
		case "png":
			return "image/png";
		case "jpeg":
			return "image/jpeg";
		default:
			return "application/octet-stream";
		}

	}
	
	

}
