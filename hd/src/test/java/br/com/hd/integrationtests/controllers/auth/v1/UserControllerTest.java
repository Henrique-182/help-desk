package br.com.hd.integrationtests.controllers.auth.v1;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.hd.configs.v1.TestConfigs;
import br.com.hd.data.vo.auth.v1.TokenVO;
import br.com.hd.integrationtests.data.vo.auth.v1.AccountCredentialsVO;
import br.com.hd.integrationtests.data.vo.auth.v1.UserVO;
import br.com.hd.integrationtests.data.vo.auth.v1.WrapperUserVO;
import br.com.hd.integrationtests.mocks.auth.v1.AccountCredentialsMock;
import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.hd.model.auth.v1.Permission;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class UserControllerTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static AccountCredentialsVO credentials;
	private static AccountCredentialsVO newCredentials;
	private static UserVO user;

	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		credentials = new AccountCredentialsVO("ADM", "ADMINISTRATOR#@!312");
	}
	
	@Test
	@Order(0)
	void testAuthentication() {
		
		TokenVO tokenVO = given()
				.basePath("/v1/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(credentials)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.as(TokenVO.class);
		
		assertNotNull(tokenVO);
		assertNotNull(tokenVO.getUsername());
		assertNotNull(tokenVO.getAuthenticated());
		assertNotNull(tokenVO.getCreated());
		assertNotNull(tokenVO.getExpiration());
		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
		
		specification = new RequestSpecBuilder()
				.setBasePath("/v1/user")
				.setPort(TestConfigs.SERVER_PORT)
				.setContentType(TestConfigs.CONTENT_TYPE_JSON)
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getAccessToken())
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	void testCreate() throws JsonMappingException, JsonProcessingException {
		
		newCredentials = AccountCredentialsMock.vo();
		
		var content = given().spec(specification)
				.body(newCredentials)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		UserVO createdUser = mapper.readValue(content, UserVO.class);
		user = createdUser;
		
		assertEquals(2, createdUser.getKey());
		assertEquals("Username0", createdUser.getUsername());
		assertEquals("Fullname0", createdUser.getFullname());
		assertTrue(createdUser.getAccountNonExpired());
		assertTrue(createdUser.getAccountNonLocked());
		assertTrue(createdUser.getCredentialsNonExpired());
		assertTrue(createdUser.getEnabled());
		assertEquals(3, createdUser.getPermissions().get(0).getId());
		assertTrue(content.contains("\"userVOList\":{\"href\":\"http://localhost:8888/v1/user?pageNumber=0&pageSize=10&sortBy=username&direction=asc\"}"));
	}
	
	@Test
	@Order(2)
	void testGetTokenFromCreatedUser() {
		
		TokenVO tokenVO = given()
				.basePath("/v1/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(newCredentials)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.as(TokenVO.class);
		
		assertNotNull(tokenVO);
		assertNotNull(tokenVO.getUsername());
		assertNotNull(tokenVO.getAuthenticated());
		assertNotNull(tokenVO.getCreated());
		assertNotNull(tokenVO.getExpiration());
		assertNotNull(tokenVO.getAccessToken());
		assertNotNull(tokenVO.getRefreshToken());
	}
	
	@Test
	@Order(3)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				 .pathParam("id", user.getKey())
				 .when()
			 		 .get("{id}")
				 .then()
					 .statusCode(200)
				 .extract()
					 .body()
					 .asString();
		
		UserVO persistedUser = mapper.readValue(content, UserVO.class);
		
		assertEquals(2, persistedUser.getKey());
		assertEquals("Username0", persistedUser.getUsername());
		assertEquals("Fullname0", persistedUser.getFullname());
		assertTrue(persistedUser.getAccountNonExpired());
		assertTrue(persistedUser.getAccountNonLocked());
		assertTrue(persistedUser.getCredentialsNonExpired());
		assertTrue(persistedUser.getEnabled());
		assertEquals(3, persistedUser.getPermissions().get(0).getId());
		assertTrue(content.contains("\"userVOList\":{\"href\":\"http://localhost:8888/v1/user?pageNumber=0&pageSize=10&sortBy=username&direction=asc\"}"));
	}
	
	@Test
	@Order(4)
	void testUpdateById() throws JsonMappingException, JsonProcessingException {
		
		List<Permission> permissionList = new ArrayList<>();
		permissionList.add(new Permission(2));
		user.setPermissions(permissionList);
		
		var content = given().spec(specification)
				 .pathParam("id", user.getKey())
				 .body(user)
				 .when()
			 		 .put("{id}")
				 .then()
					 .statusCode(200)
				 .extract()
					 .body()
					 .asString();
		
		UserVO updatedUser = mapper.readValue(content, UserVO.class);
		user = updatedUser;
		
		assertEquals(2, updatedUser.getKey());
		assertEquals("Username0", updatedUser.getUsername());
		assertEquals("Fullname0", updatedUser.getFullname());
		assertTrue(updatedUser.getAccountNonExpired());
		assertTrue(updatedUser.getAccountNonLocked());
		assertTrue(updatedUser.getCredentialsNonExpired());
		assertTrue(updatedUser.getEnabled());
		assertEquals(2, updatedUser.getPermissions().get(0).getId());
		assertTrue(content.contains("\"userVOList\":{\"href\":\"http://localhost:8888/v1/user?pageNumber=0&pageSize=10&sortBy=username&direction=asc\"}"));
	}
	
	@Test
	@Order(5)
	void testFindCustomPageable() throws JsonMappingException, JsonProcessingException {
		
		String permission = "MANAGER";
		
		var content = given().spec(specification)
				.queryParam("permission", permission)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		WrapperUserVO wrapper = mapper.readValue(content, WrapperUserVO.class);
		
		List<UserVO> resultList = wrapper.getEmbedded().getUsers();
		
		UserVO userOne = resultList.get(0);
		
		assertEquals(1, userOne.getKey());
		assertEquals("ADM", userOne.getUsername());
		assertEquals("ADM", userOne.getFullname());
		assertTrue(userOne.getAccountNonExpired());
		assertTrue(userOne.getAccountNonLocked());
		assertTrue(userOne.getCredentialsNonExpired());
		assertTrue(userOne.getEnabled());
		assertEquals(2, userOne.getPermissions().get(1).getId());
		
		UserVO userTwo = resultList.get(1);
		
		assertEquals(2, userTwo.getKey());
		assertEquals("Username0", userTwo.getUsername());
		assertEquals("Fullname0", userTwo.getFullname());
		assertTrue(userTwo.getAccountNonExpired());
		assertTrue(userTwo.getAccountNonLocked());
		assertTrue(userTwo.getCredentialsNonExpired());
		assertTrue(userTwo.getEnabled());
		assertEquals(2, userTwo.getPermissions().get(0).getId());
	}
	
	@Test
	@Order(6)
	void testHATEOAS() {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/user/1\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/user/2\"}"));
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/user?page=0&size=10&sort=username,asc\"}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":2,\"totalPages\":1,\"number\":0}"));
	}
	
	@Test
	@Order(7)
	void testDeleteById() throws JsonMappingException, JsonProcessingException {
		
		given().spec(specification)
			 .pathParam("id", user.getKey())
			 .when()
		 		 .delete("{id}")
			 .then()
				 .statusCode(204);
		
	}
	
}
