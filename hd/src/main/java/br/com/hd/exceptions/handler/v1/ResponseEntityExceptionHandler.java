package br.com.hd.exceptions.handler.v1;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import br.com.hd.exceptions.auth.v1.InvalidJwtAuthenticationException;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.exceptions.response.v1.ExceptionResponse;

@ControllerAdvice
@RestController
public class ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, WebRequest webRequest) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleResourceNotFoundExceptions(Exception exception, WebRequest webRequest) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RequiredObjectIsNullException.class)
	public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception exception, WebRequest webRequest) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public final ResponseEntity<ExceptionResponse> handleValidationExceptions(Exception exception, WebRequest webRequest) {
		
		int firstIndex = exception.getMessage().lastIndexOf("default message [") + "default message [".length();
		int lastIndex = exception.getMessage().lastIndexOf("]") - 1;
		
		// default field [Field can't be null!]] -> Field can't be null!
		String exceptionMessage = exception.getMessage().substring(firstIndex, lastIndex);
		
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exceptionMessage, webRequest.getDescription(false));
		
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidJwtAuthenticationException.class)
	public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthenticationExceptions(Exception exception, WebRequest webRequest) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
	}
	
}
