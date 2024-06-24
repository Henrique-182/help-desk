package br.com.hd.integrationtests.repositories.auth.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.hd.model.auth.v1.User;
import br.com.hd.repositories.auth.v1.UserCustomRepository;
import br.com.hd.repositories.auth.v1.UserRepository;
import jakarta.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class UserRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private EntityManager manager;
	
	private static UserCustomRepository customRepository;
	
	@Test
	@Order(1)
	void testFindByUsername() {
		
		String username = "ADM";
		
		User persistedUser = repository.findByUsername(username);
		
		assertNotNull(persistedUser);
		
		assertEquals(1, persistedUser.getId());
		assertEquals("ADM", persistedUser.getUsername());
		assertEquals("ADM", persistedUser.getFullname());
		assertTrue(persistedUser.getAccountNonExpired());
		assertTrue(persistedUser.getAccountNonLocked());
		assertTrue(persistedUser.getCredentialsNonExpired());
		assertTrue(persistedUser.getEnabled());
		assertEquals("ADMIN", persistedUser.getPermissions().get(0).getDescription());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	@Order(2)
	void testFindCustomPageable() {
		
		customRepository = new UserCustomRepository(manager);
		
		Map<String, Object> queryParams = Map.of("name", "", "permission", "");
		
		Pageable pageable = PageRequest.of(0, 10, Sort.by(Direction.ASC, "username"));
		
		Map<String, Object> resultMap = customRepository.findCustomPageable(queryParams, pageable);
		
		List<User> resultList = (List<User>) resultMap.get("resultList");
		
		assertNotNull(resultList);
		
		User userOne = resultList.get(0);
		
		assertNotNull(userOne);
		assertEquals(1, userOne.getId());
		assertEquals("ADM", userOne.getUsername());
		assertEquals("ADM", userOne.getFullname());
		assertTrue(userOne.getAccountNonExpired());
		assertTrue(userOne.getAccountNonLocked());
		assertTrue(userOne.getCredentialsNonExpired());
		assertTrue(userOne.getEnabled());
		assertEquals("ADMIN", userOne.getPermissions().get(0).getDescription());
		
		long totalElements = (long) resultMap.get("totalElements");
		
		assertNotNull(totalElements);
		
		assertEquals(3, totalElements);
	}
	
}
