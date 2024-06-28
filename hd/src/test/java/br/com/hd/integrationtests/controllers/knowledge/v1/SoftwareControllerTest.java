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
import br.com.hd.integrationtests.data.vo.knowledge.v1.SoftwareVO;
import br.com.hd.integrationtests.data.vo.knowledge.v1.SoftwareWrapperVO;
import br.com.hd.integrationtests.mocks.knowledge.v1.SoftwareMock;
import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class SoftwareControllerTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static AccountCredentialsVO credentials;
	private static SoftwareVO software;
	
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
				.setBasePath("/v1/software")
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
		software = SoftwareMock.vo();

		var content = given().spec(specification)
				.body(software)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();

		SoftwareVO createdSoftware = mapper.readValue(content, SoftwareVO.class);
		software = createdSoftware;

		assertTrue(createdSoftware.getKey() > 0);

		assertEquals("Description0", createdSoftware.getDescription());
		assertTrue(content.contains("\"_links\":{\"softwareVOList\":{\"href\":\"http://localhost:8888/v1/software?pageNumber=0&pageSize=10&sortBy=name&direction=asc\"}}}"));
	}

	@Test
	@Order(2)
	void testFindById() throws JsonMappingException, JsonProcessingException {

		var content = given().spec(specification)
				.pathParam("id", software.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();

		SoftwareVO persistedCompany = mapper.readValue(content, SoftwareVO.class);

		assertEquals(software.getKey(), persistedCompany.getKey());
		assertEquals("Description0", persistedCompany.getDescription());
		assertTrue(content.contains("\"_links\":{\"softwareVOList\":{\"href\":\"http://localhost:8888/v1/software?pageNumber=0&pageSize=10&sortBy=name&direction=asc\"}}}"));
	}

	@Test
	@Order(3)
	void testUpdateById() throws JsonMappingException, JsonProcessingException {
		software.setDescription(software.getKey() + "Description");

		var content = given().spec(specification)
				.pathParam("id", software.getKey())
					.body(software)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();

		SoftwareVO updatedCompany = mapper.readValue(content, SoftwareVO.class);

		assertEquals(software.getKey(), updatedCompany.getKey());
		assertEquals("4Description", updatedCompany.getDescription());
		assertTrue(content.contains("\"_links\":{\"softwareVOList\":{\"href\":\"http://localhost:8888/v1/software?pageNumber=0&pageSize=10&sortBy=name&direction=asc\"}}}"));
	}

	@Test
	@Order(4)
	void testDeleteById() {

		given().spec(specification)
			.pathParam("id", software.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}

	@Test
	@Order(5)
	void testFindAll() throws JsonMappingException, JsonProcessingException {
		Integer pageNumber = 0;
		Integer pageSize = 10;
		String direction = "desc";

		var content = given().spec(specification)
				.queryParam("pageNumber", pageNumber)
				.queryParam("pageSize", pageSize)
				.queryParam("direction", direction)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();

		SoftwareWrapperVO wrapper = mapper.readValue(content, SoftwareWrapperVO.class);

		List<SoftwareVO> resultList = wrapper.getEmbedded().getSoftwares();

		SoftwareVO softwareOne = resultList.get(0);

		assertEquals(3, softwareOne.getKey());
		assertEquals("Software C", softwareOne.getDescription());
	}

	@Test
	@Order(6)
	void testHATEOAS() throws JsonMappingException, JsonProcessingException {
		Integer pageNumber = 0;
		Integer pageSize = 10;
		String direction = "desc";

		var content = given().spec(specification)
				.queryParam("pageNumber", pageNumber)
				.queryParam("pageSize", pageSize)
				.queryParam("direction", direction)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();

		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v1/software/1\"}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v1/software/2\"}}"));

		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v1/software?pageNumber=0&pageSize=10&direction=desc&page=0&size=10&sort=description,desc\"}}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":3,\"totalPages\":1,\"number\":0}"));
	}
	
}
