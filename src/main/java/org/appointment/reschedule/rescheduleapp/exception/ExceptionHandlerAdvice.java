package org.appointment.reschedule.rescheduleapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler{
	
	
	@ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(ResourceNotFoundException e, WebRequest request) {
		System.out.println("IN e8888888888888888888 "+e.getMessage());
		 return error(HttpStatus.NOT_FOUND, e);
		
       	
    }
	
	/*@ExceptionHandler({ AccessDeniedException.class })
	    public ResponseEntity<Object> handleAccessDeniedException(
	      Exception ex, WebRequest request) {
	        return new ResponseEntity<Object>(
	          "Access denied message here", new HttpHeaders(), HttpStatus.FORBIDDEN);
	    }*/
	private ResponseEntity<String> error(HttpStatus status, Exception e) {
        return ResponseEntity.status(status).body(e.getMessage());
    }

}
