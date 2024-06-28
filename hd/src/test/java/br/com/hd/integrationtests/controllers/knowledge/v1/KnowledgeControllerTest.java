package br.com.hd.integrationtests.controllers.knowledge.v1;

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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.hd.configs.v1.TestConfigs;
import br.com.hd.data.vo.auth.v1.TokenVO;
import br.com.hd.integrationtests.data.vo.auth.v1.AccountCredentialsVO;
import br.com.hd.integrationtests.data.vo.knowledge.v1.KnowledgeVO;
import br.com.hd.integrationtests.data.vo.knowledge.v1.KnowledgeWrapperVO;
import br.com.hd.integrationtests.mocks.knowledge.v1.KnowledgeMock;
import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.hd.model.knowledge.v1.SoftwareKnwl;
import br.com.hd.model.knowledge.v1.TagKnwl;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class KnowledgeControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static AccountCredentialsVO credentials;
	private static KnowledgeVO knowledge;
	
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
				.setBasePath("/v1/knowledge")
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
		knowledge = KnowledgeMock.vo();
		
		var content = given().spec(specification)
				.body(knowledge)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		KnowledgeVO createdKnowledge = mapper.readValue(content, KnowledgeVO.class);
		knowledge = createdKnowledge;
				
		assertTrue(createdKnowledge.getKey() > 0);
		
		assertEquals("Title0", createdKnowledge.getTitle());
		assertEquals("Content0", createdKnowledge.getContent());
		
		assertEquals("Software A", createdKnowledge.getSoftware().getDescription());
		
		assertTrue(content.contains("\"knowledgeVOList\":{\"href\":\"http://localhost:8888/v1/knowledge?pageNumber=0&pageSize=10&sortBy=title&direction=asc\"}"));
	}
	
	@Test
	@Order(2)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("id", knowledge.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		KnowledgeVO persistedKnowledge = mapper.readValue(content, KnowledgeVO.class);
		
		assertEquals(knowledge.getKey(), persistedKnowledge.getKey());
		assertEquals("Title0", persistedKnowledge.getTitle());
		assertEquals("Content0", persistedKnowledge.getContent());
		
		assertEquals("Software A", persistedKnowledge.getSoftware().getDescription());
		
		assertTrue(content.contains("\"knowledgeVOList\":{\"href\":\"http://localhost:8888/v1/knowledge?pageNumber=0&pageSize=10&sortBy=title&direction=asc\"}"));
	}
	
	@Test
	@Order(3)
	void testUpdateById() throws JsonMappingException, JsonProcessingException {
		
		knowledge.setTitle(knowledge.getKey() + "Title");
		knowledge.setContent(knowledge.getKey() + "Content");
		
		SoftwareKnwl software = new SoftwareKnwl();
		software.setId(1L);
		
		knowledge.setSoftware(software);
		
		List<TagKnwl> topicList = new ArrayList<>();
		
		TagKnwl topic = new TagKnwl();
		topic.setId(1L);
		TagKnwl topic2 = new TagKnwl();
		topic2.setId(2L);
		
		topicList.add(topic);
		topicList.add(topic2);
		
		knowledge.setTags(topicList);
		
		var content = given().spec(specification)
				.pathParam("id", knowledge.getKey())
				.body(knowledge)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		KnowledgeVO updatedKnowledge = mapper.readValue(content, KnowledgeVO.class);
		knowledge = updatedKnowledge;
		
		assertEquals(knowledge.getKey(), updatedKnowledge.getKey());
		assertEquals(knowledge.getKey() + "Title", updatedKnowledge.getTitle());
		assertEquals(knowledge.getKey() + "Content", updatedKnowledge.getContent());
		assertEquals(1L, updatedKnowledge.getSoftware().getId());
		
		assertEquals("Tag A", updatedKnowledge.getTags().get(0).getDescription());
		assertEquals("Tag B", updatedKnowledge.getTags().get(1).getDescription());
		
		assertTrue(content.contains("\"knowledgeVOList\":{\"href\":\"http://localhost:8888/v1/knowledge?pageNumber=0&pageSize=10&sortBy=title&direction=asc\"}"));
	}
	
	@Test
	@Order(4)
	void testDeleteById() {
		
		given().spec(specification)
			.pathParam("id", knowledge.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(5)
	void testFindPageable() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		var wrapper = mapper.readValue(content, KnowledgeWrapperVO.class);
		
		List<KnowledgeVO> resultList = wrapper.getEmbedded().getKnowledges();
	
		KnowledgeVO knowledgeZero = resultList.get(0);
		
		assertEquals(1, knowledgeZero.getKey());
		assertEquals("Title Knowledge A", knowledgeZero.getTitle());
		assertEquals("Content Knowledge A", knowledgeZero.getContent());
		
		assertEquals("Tag D", knowledgeZero.getTags().get(0).getDescription());
		assertEquals("Tag F", knowledgeZero.getTags().get(1).getDescription());
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
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/knowledge/1\"}"));
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/knowledge/2\"}"));
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/knowledge?page=0&size=10&sort=title,asc\"}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":6,\"totalPages\":1,\"number\":0}"));
	}
	
	
}
