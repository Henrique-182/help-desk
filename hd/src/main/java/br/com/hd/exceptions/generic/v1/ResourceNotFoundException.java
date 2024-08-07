package br.com.hd.exceptions.generic.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(Long id) {
		super("No records found for the id (" + id + ") !");
	}
	
}
