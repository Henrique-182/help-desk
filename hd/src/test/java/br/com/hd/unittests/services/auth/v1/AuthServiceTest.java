package br.com.hd.unittests.services.auth.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import br.com.hd.data.vo.auth.v1.AccountCredentialsVO;
import br.com.hd.data.vo.auth.v1.TokenVO;
import br.com.hd.model.auth.v1.User;
import br.com.hd.repositories.auth.v1.UserRepository;
import br.com.hd.services.auth.jwt.v1.AuthService;
import br.com.hd.services.auth.jwt.v1.JwtTokenProvider;
import br.com.hd.unittests.mocks.auth.v1.AccountCredentialsMock;
import br.com.hd.unittests.mocks.auth.v1.TokenMock;
import br.com.hd.unittests.mocks.auth.v1.UserMock;
import jakarta.servlet.http.HttpServletRequest;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
	
	@Autowired
	@InjectMocks
	private AuthService service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private AuthenticationManager manager;
	
	@Mock
	private JwtTokenProvider provider;
	
	@Test
	void testSignin() {
		
		AccountCredentialsVO credentialsMock = AccountCredentialsMock.vo();
		
		User userMock = UserMock.entity();
		
		TokenVO tokenMock = TokenMock.vo();
		
		when(
			manager.authenticate(any(UsernamePasswordAuthenticationToken.class))
		)
		.thenReturn(null);
		
		when(repository.findByUsername(anyString())).thenReturn(userMock);
		
		when(provider.createAccessToken(anyString(), anyList())).thenReturn(tokenMock);
		
		TokenVO token = service.signin(credentialsMock);
		
		assertNotNull(token);
		
		assertEquals("Username0", token.getUsername());
		assertTrue(token.getAuthenticated());
		assertEquals(new Date(0), token.getCreated());
		assertEquals(new Date(0), token.getExpiration());
		assertEquals("Access Token0", token.getAccessToken());
		assertEquals("Refresh Token0", token.getRefreshToken());
	}
	
	@Test
	void testRefresh() {
		
		User userMock = UserMock.entity();
		
		String username = "Username0";
		String refreshToken = "Bearer refreshToken";
		
		TokenVO tokenMock = TokenMock.vo();
		
		when(repository.findByUsername(anyString())).thenReturn(userMock);
		
		when(provider.refreshToken(refreshToken)).thenReturn(tokenMock);
		
		TokenVO token = service.refresh(username, refreshToken);
		
		assertNotNull(token);
		
		assertEquals("Username0", token.getUsername());
		assertTrue(token.getAuthenticated());
		assertEquals(new Date(0), token.getCreated());
		assertEquals(new Date(0), token.getExpiration());
		assertEquals("Access Token0", token.getAccessToken());
		assertEquals("Refresh Token0", token.getRefreshToken());
	}
	
	@Test
	void testValidateValidToken() {
		
		String accessToken = "accessToken";
		
		HttpServletRequest requestMock = mock(HttpServletRequest.class);
		
		when(provider.resolveToken(any(HttpServletRequest.class))).thenReturn(accessToken);
		
		when(provider.validateToken(accessToken)).thenReturn(true);
		
		Boolean isValid = service.validate(requestMock);
		
		assertTrue(isValid);
	}
	
	@Test
	void testValidateInvalidToken() {
		
		String accessToken = "";
		
		HttpServletRequest requestMock = mock(HttpServletRequest.class);
		
		when(provider.resolveToken(any(HttpServletRequest.class))).thenReturn(accessToken);
		
		when(provider.validateToken(accessToken)).thenReturn(false);
		
		Boolean isValid = service.validate(requestMock);
		
		assertFalse(isValid);
	}

}
