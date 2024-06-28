package br.com.hd.integrationtests.controllers.knowledge.v1;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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
import br.com.hd.integrationtests.data.vo.knowledge.v1.TagVO;
import br.com.hd.integrationtests.data.vo.knowledge.v1.TagWrapperVO;
import br.com.hd.integrationtests.mocks.knowledge.v1.TagMock;
import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class TagControllerTest extends AbstractIntegrationTest {

	
	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static AccountCredentialsVO credentials;
	private static TagVO tag;
	
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
				.setBasePath("/v1/tag")
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
		tag = TagMock.vo();
		
		var content = given().spec(specification)
				.body(tag)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		TagVO createdTopic = mapper.readValue(content, TagVO.class);
		tag = createdTopic;
		
		assertTrue(createdTopic.getKey() > 0);
		
		assertEquals("Description0", createdTopic.getDescription());
		
		assertTrue(content.contains("\"tagVOList\":{\"href\":\"http://localhost:8888/v1/tag?pageNumber=0&pageSize=10&sortBy=name&direction=asc\"}"));
	}
	
	@Test
	@Order(2)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("id", tag.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		TagVO persistedTopic = mapper.readValue(content, TagVO.class);
		
		assertEquals(tag.getKey(), persistedTopic.getKey());
		assertEquals("Description0", persistedTopic.getDescription());
		
		assertTrue(content.contains("\"tagVOList\":{\"href\":\"http://localhost:8888/v1/tag?pageNumber=0&pageSize=10&sortBy=name&direction=asc\"}"));
	}
	
	@Test
	@Order(3)
	void testUpdateById() throws JsonMappingException, JsonProcessingException {
		tag.setDescription(tag.getKey() + "Description");
		
		var content = given().spec(specification)
				.pathParam("id", tag.getKey())
				.body(tag)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		TagVO updatedTopic = mapper.readValue(content, TagVO.class);
		tag = updatedTopic;
		
		assertEquals(tag.getKey(), updatedTopic.getKey());
		assertEquals(tag.getKey() + "Description", updatedTopic.getDescription());
		
		assertTrue(content.contains("\"tagVOList\":{\"href\":\"http://localhost:8888/v1/tag?pageNumber=0&pageSize=10&sortBy=name&direction=asc\"}"));
	}
	
	@Test
	@Order(4)
	void testDeleteById() {

		given().spec(specification)
			.pathParam("id", tag.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
		
	}
	
	@Test
	@Order(5)
	void testFindCustomPageable() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		TagWrapperVO wrapper = mapper.readValue(content, TagWrapperVO.class);
		
		List<TagVO> resultList = wrapper.getEmbedded().getTags();
		
		TagVO topicOne = resultList.get(0);
		
		assertEquals(1, topicOne.getKey());
		assertEquals("Tag A", topicOne.getDescription());
		
		TagVO topicTwo = resultList.get(1);
		
		assertEquals(2, topicTwo.getKey());
		assertEquals("Tag B", topicTwo.getDescription());
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
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/tag/1\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/tag/2\"}"));
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/tag?page=0&size=10&sort=description,asc\"}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":6,\"totalPages\":1,\"number\":0}"));
	}
	
}
