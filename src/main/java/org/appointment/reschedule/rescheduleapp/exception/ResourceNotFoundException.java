package org.appointment.reschedule.rescheduleapp.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	
	public ResourceNotFoundException(HttpStatus status, String message,Throwable e) {
		super(message);
	}
	

	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}
}
