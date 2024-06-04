package br.com.hd.exceptions.generic.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNullException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequiredObjectIsNullException() {
		super("It is not possible to persist a null object!");
	}

	public RequiredObjectIsNullException(String message) {
		super(message);
	}
	
}
