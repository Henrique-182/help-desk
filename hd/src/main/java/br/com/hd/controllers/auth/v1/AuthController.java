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
import br.com.hd.data.vo.auth.v1.TokenVO;
import br.com.hd.services.auth.jwt.v1.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Tag(name = "Auth", description = "Endpoints for Managing Auth")
@RestController
@RequestMapping(path = "/v1/auth")
public class AuthController {

	@Autowired
	private AuthService service;
	
	@Operation(
		summary = "Authenticates a User",
		description = "Authenticates a User and returns a token",
		tags = "Auth",
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = TokenVO.class))),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
		}
	)
	@SuppressWarnings("rawtypes")
	@PostMapping(path = "/signin")
	public ResponseEntity signin(@Valid @RequestBody AccountCredentialsVO data) {

		TokenVO token = service.signin(data);
		
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request. Token is Null!");
		
		return ResponseEntity.ok(token);
	}
	
	@Operation(
		summary = "Refresh Token",
		description = "Refresh Token for Authenticated User and Returns a Token",
		tags = "Auth",
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = TokenVO.class))),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
		}
	)
	@PutMapping(path = "/refresh/{username}")
	public ResponseEntity<?> refresh(
		@NotNull @NotBlank @PathVariable("username") String username, 
		@RequestHeader("Authorization") @Valid @NotBlank String refreshToken
	) {
		
		TokenVO token = service.refresh(username, refreshToken);
		
		if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
		
		return ResponseEntity.ok(token);
	}
	
}
