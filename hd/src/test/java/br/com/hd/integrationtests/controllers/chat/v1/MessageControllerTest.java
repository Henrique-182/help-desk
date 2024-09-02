package br.com.hd.integrationtests.controllers.chat.v1;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.hd.configs.v1.TestConfigs;
import br.com.hd.data.vo.auth.v1.TokenVO;
import br.com.hd.integrationtests.data.vo.auth.v1.AccountCredentialsVO;
import br.com.hd.integrationtests.data.vo.chat.message.v1.MessageCreationVO;
import br.com.hd.integrationtests.data.vo.chat.message.v1.MessageUpdateVO;
import br.com.hd.integrationtests.data.vo.chat.message.v1.MessageVO;
import br.com.hd.integrationtests.mocks.chat.message.v1.MessageCreationVOMock;
import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.hd.model.chat.message.v1.MessageType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class MessageControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static AccountCredentialsVO credentials;
	private static MessageVO message;
	private static MessageCreationVO messageCreation;
	private static MessageUpdateVO messageUpdate;

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
				.setBasePath("/v1/message")
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
		messageCreation = MessageCreationVOMock.vo(4L);
		
		var content = given().spec(specification)
				.body(messageCreation)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		MessageVO createdMessage = mapper.readValue(content, MessageVO.class);
		message = createdMessage;
		
		assertTrue(createdMessage.getKey() > 0);
		
		assertEquals(10L, createdMessage.getKey());
		assertEquals(MessageType.TEXT, createdMessage.getType());
		assertEquals("Content4", createdMessage.getContent());
		assertEquals(1, createdMessage.getUser().getKey());
		assertEquals(4, createdMessage.getRoom().getKey());
		assertEquals(
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
			new SimpleDateFormat("yyyy-MM-dd").format(createdMessage.getCreateDatetime())
		);
		assertNull(createdMessage.getUpdateDatetime());
		assertNull(createdMessage.getDeleteDatetime());
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/message/10\"}"));
	}
	
	@Test
	@Order(2)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("id", message.getKey())
				.when()
					.get("/{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		MessageVO persistedMessage = mapper.readValue(content, MessageVO.class);
		
		assertNotNull(persistedMessage);
		
		assertEquals(10L, persistedMessage.getKey());
		assertEquals(MessageType.TEXT, persistedMessage.getType());
		assertEquals("Content4", persistedMessage.getContent());
		assertEquals(1, persistedMessage.getUser().getKey());
		assertEquals(4, persistedMessage.getRoom().getKey());
		assertEquals(
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
			new SimpleDateFormat("yyyy-MM-dd").format(persistedMessage.getCreateDatetime())
		);
		assertNull(persistedMessage.getUpdateDatetime());
		assertNull(persistedMessage.getDeleteDatetime());
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/message/10\"}"));
	}
	
	@Test
	@Order(3)
	void testUpdateById() throws JsonMappingException, JsonProcessingException {
		messageUpdate = new MessageUpdateVO();
		messageUpdate.setContent(message.getKey() + "Content");
		
		var content = given().spec(specification)
				.pathParam("id", message.getKey())
				.body(messageUpdate)
				.when()
					.patch("/{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		MessageVO updatedMessage = mapper.readValue(content, MessageVO.class);
		message = updatedMessage;
		
		assertNotNull(updatedMessage);
		
		assertEquals(10L, updatedMessage.getKey());
		assertEquals(MessageType.TEXT, updatedMessage.getType());
		assertEquals("10Content", updatedMessage.getContent());
		assertEquals(1, updatedMessage.getUser().getKey());
		assertEquals(4, updatedMessage.getRoom().getKey());
		assertEquals(
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
			new SimpleDateFormat("yyyy-MM-dd").format(updatedMessage.getCreateDatetime())
		);
		assertEquals(
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
			new SimpleDateFormat("yyyy-MM-dd").format(updatedMessage.getUpdateDatetime())
		);
		assertNull(updatedMessage.getDeleteDatetime());
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/message/10\"}"));
	}
	
	@Test
	@Order(4)
	void testSoftDeleteById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("id", message.getKey())
				.body(messageUpdate)
				.when()
					.delete("/{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		MessageVO updatedMessage = mapper.readValue(content, MessageVO.class);
		message = updatedMessage;
		
		assertNotNull(updatedMessage);
		
		assertEquals(10L, updatedMessage.getKey());
		assertEquals(MessageType.TEXT, updatedMessage.getType());
		assertEquals("10Content", updatedMessage.getContent());
		assertEquals(1, updatedMessage.getUser().getKey());
		assertEquals(4, updatedMessage.getRoom().getKey());
		assertEquals(
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
			new SimpleDateFormat("yyyy-MM-dd").format(updatedMessage.getCreateDatetime())
		);
		assertEquals(
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
			new SimpleDateFormat("yyyy-MM-dd").format(updatedMessage.getUpdateDatetime())
		);
		assertEquals(
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()), 
			new SimpleDateFormat("yyyy-MM-dd").format(updatedMessage.getDeleteDatetime())
		);
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/message/10\"}"));
	}
	
	@Test
	@Order(5)
	void testSoftDeleteById_WithInvalidArgumentsException() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("id", 1L)
				.body(messageUpdate)
				.when()
					.delete("/{id}")
				.then()
					.statusCode(400)
				.extract()
					.body()
					.asString();
		
		assertTrue(content.contains("The user (ADM) can't delete the message because he didn't send it !"));
	}
	
}
