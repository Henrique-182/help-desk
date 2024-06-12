package br.com.hd.controllers.auth.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hd.data.vo.auth.v1.AccountCredentialsVO;
import br.com.hd.services.auth.jwt.v1.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping(path = "/v1/auth")
public class AuthController {

	@Autowired
	private AuthService service;
	
	@SuppressWarnings("rawtypes")
	@PostMapping(path = "/signin")
	public ResponseEntity signin(@Valid @RequestBody AccountCredentialsVO data) {

		ResponseEntity token = service.signin(data);
		
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request. Token is Null!");
		
		return token;
	}
	
	@PutMapping(path = "/refresh/{username}")
	public ResponseEntity<?> refresh(
		@NotNull @NotBlank @PathVariable("username") String username, 
		@RequestHeader("Authorization") @Valid @NotBlank String refreshToken
	) {
		
		ResponseEntity<?> token = service.refresh(username, refreshToken);
		
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		return token;
	}
	
}
