package br.com.hd.unittests.services.auth.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.hd.data.vo.auth.v1.AccountCredentialsVO;
import br.com.hd.data.vo.auth.v1.UserVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.auth.v1.UserMapper;
import br.com.hd.model.auth.v1.User;
import br.com.hd.repositories.auth.v1.UserRepository;
import br.com.hd.services.auth.jwt.v1.UserService;
import br.com.hd.unittests.mocks.auth.v1.PermissionMock;
import br.com.hd.unittests.mocks.auth.v1.UserMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	@Autowired
	@InjectMocks
	private UserService service;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private UserMapper mapper;
	
	@Test
	void testLoadByUsername() {
		
		String username = "username0";
		User mockEntity = UserMock.entity();
		
		when(repository.findByUsername(anyString())).thenReturn(mockEntity);
		
		UserDetails persistedEntity = service.loadUserByUsername(username);
		
		assertNotNull(persistedEntity);
		
		assertEquals("Username0", persistedEntity.getUsername());
		assertTrue(persistedEntity.isAccountNonExpired());
		assertTrue(persistedEntity.isAccountNonLocked());
		assertTrue(persistedEntity.isCredentialsNonExpired());
		assertTrue(persistedEntity.isEnabled());
		assertTrue(persistedEntity.getAuthorities().contains(PermissionMock.entity(0)));
	}
	
	@Test
	void testFindById() {
		Long id = 0L;
		User mockEntity = UserMock.entity();
		UserVO mockVO = UserMock.vo();
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		UserVO persistedUser = service.findById(id);
		
		assertEquals(0, persistedUser.getKey());
		assertEquals("Username0", persistedUser.getUsername());
		assertEquals("Fullname0", persistedUser.getFullname());
		assertEquals(true, persistedUser.getAccountNonExpired());
		assertEquals(true, persistedUser.getAccountNonLocked());
		assertEquals(true, persistedUser.getCredentialsNonExpired());
		assertEquals(true, persistedUser.getEnabled());
		assertEquals("Description0", persistedUser.getPermissions().get(0).getDescription());
		assertTrue(persistedUser.getLinks().toString().contains("</v1/user?pageNumber=0&pageSize=10&sortBy=username&direction=asc>;rel=\"userVOList\""));
	}
	
	@Test
	void testFindByIdWithResourceNotFoundException() {
		Long id = 10L;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ")!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testCreate() {
		AccountCredentialsVO data = new AccountCredentialsVO("username0", "fullname0", "password0");
		User mockEntity = UserMock.entity();
		UserVO mockVO = UserMock.vo();
		
		when(repository.save(any(User.class))).thenReturn(mockEntity);
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		UserVO createdUser = service.create(data);
		
		assertEquals(0, createdUser.getKey());
		assertEquals("Username0", createdUser.getUsername());
		assertEquals("Fullname0", createdUser.getFullname());
		assertEquals(true, createdUser.getAccountNonExpired());
		assertEquals(true, createdUser.getAccountNonLocked());
		assertEquals(true, createdUser.getCredentialsNonExpired());
		assertEquals(true, createdUser.getEnabled());
		assertEquals("Description0", createdUser.getPermissions().get(0).getDescription());
		assertTrue(createdUser.getLinks().toString().contains("</v1/user?pageNumber=0&pageSize=10&sortBy=username&direction=asc>;rel=\"userVOList\""));
	}
	
	@Test
	void testCreateWithRequiredObjectIsNullException() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not possible to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateById() {
		Long id = 0L;
		User mockEntity = UserMock.entity();
		User persistedEntity = mockEntity;
		UserVO mockVO = UserMock.vo();
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		UserVO updatedUser = service.updateById(id, mockVO);
		
		assertEquals(0, updatedUser.getKey());
		assertEquals("Username0", updatedUser.getUsername());
		assertEquals("Fullname0", updatedUser.getFullname());
		assertEquals(true, updatedUser.getAccountNonExpired());
		assertEquals(true, updatedUser.getAccountNonLocked());
		assertEquals(true, updatedUser.getCredentialsNonExpired());
		assertEquals(true, updatedUser.getEnabled());
		assertEquals("Description0", updatedUser.getPermissions().get(0).getDescription());
		assertTrue(updatedUser.getLinks().toString().contains("</v1/user?pageNumber=0&pageSize=10&sortBy=username&direction=asc>;rel=\"userVOList\""));
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Long id = null;
		UserVO mockVO = UserMock.vo();
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateByIdWithRequiredObjectIsNullException() {
		Long id = 1L;
		UserVO mockVO = null;
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "It is not possible to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void deleteById() {
		Long id = 0L;
		User mockEntity = UserMock.entity();
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		
		service.deleteById(id);
	}
	
	@Test
	void testDeleteByIdWithResourceNotFoundException() {
		Long id = 10L;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}

}
