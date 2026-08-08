package org.springboot.exception;
import java.util.Map;

public class ValidationException extends RuntimeException {
	
	private Map<String, Object> errors;

	public ValidationException(Map<String, Object> errors) {
		super("Validation failed");
		this.errors = errors;
	}

	public Map<String, Object> getErrors()
	{
		return errors;
	}
}
