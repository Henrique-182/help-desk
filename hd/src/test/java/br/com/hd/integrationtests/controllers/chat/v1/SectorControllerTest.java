package br.com.hd.integrationtests.controllers.chat.v1;

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
import br.com.hd.integrationtests.data.vo.chat.sector.v1.SectorVO;
import br.com.hd.integrationtests.data.vo.chat.sector.v1.SectorWrapperVO;
import br.com.hd.integrationtests.data.vo.chat.sector.v1.SimpleSectorVO;
import br.com.hd.integrationtests.data.vo.chat.sector.v1.SimpleSectorWrapperVO;
import br.com.hd.integrationtests.data.vo.chat.sector.v1.UserSctrWrapperVO;
import br.com.hd.integrationtests.mocks.chat.sector.v1.SectorMock;
import br.com.hd.integrationtests.mocks.chat.sector.v1.UserSctrMock;
import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.hd.model.chat.sector.v1.UserSctr;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class SectorControllerTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static ObjectMapper mapper;
	
	private static AccountCredentialsVO credentials;
	private static SectorVO sector;
	
	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		credentials = new AccountCredentialsVO("MANAGER", "MANAGER#@!312");
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
				.setBasePath("/v1/sector")
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
		sector = SectorMock.vo();
		
		var content = given().spec(specification)
				.body(sector)
				.when()
					.post()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		SectorVO createdSector = mapper.readValue(content, SectorVO.class);
		sector = createdSector;
		
		assertTrue(createdSector.getKey() > 0);
		
		assertEquals("Description0", createdSector.getDescription());
		assertEquals("COMMON_USER", createdSector.getCustomers().get(0).getUsername());
		assertEquals("MANAGER", createdSector.getEmployees().get(0).getUsername());
		
		assertTrue(content.contains("\"sectorVOList\":{\"href\":\"http://localhost:8888/v1/sector?pageNumber=0&pageSize=10&sortBy=description&direction=asc\"}"));
	}
	
	@Test
	@Order(2)
	void testFindById() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("id", sector.getKey())
				.when()
					.get("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		SectorVO persistedSector = mapper.readValue(content, SectorVO.class);
		
		assertEquals(4, persistedSector.getKey());
		assertEquals("Description0", persistedSector.getDescription());
		assertEquals("COMMON_USER", persistedSector.getCustomers().get(0).getUsername());
		assertEquals("MANAGER", persistedSector.getEmployees().get(0).getUsername());
		
		assertTrue(content.contains("\"sectorVOList\":{\"href\":\"http://localhost:8888/v1/sector?pageNumber=0&pageSize=10&sortBy=description&direction=asc\"}"));
	}
	
	@Test
	@Order(3)
	void testUpdateById() throws JsonMappingException, JsonProcessingException {
		sector.setDescription(sector.getKey() + "Description");
		sector.setEmployees(List.of(UserSctrMock.entity(2L)));
		
		var content = given().spec(specification)
				.pathParam("id", sector.getKey())
				.body(sector)
				.when()
					.put("{id}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		SectorVO updatedSector = mapper.readValue(content, SectorVO.class);
		sector = updatedSector;
		
		assertEquals(4, updatedSector.getKey());
		assertEquals("4Description", updatedSector.getDescription());
		assertEquals("COMMON_USER", updatedSector.getCustomers().get(0).getUsername());
		assertEquals("MANAGER", updatedSector.getEmployees().get(0).getUsername());
		
		assertTrue(content.contains("\"sectorVOList\":{\"href\":\"http://localhost:8888/v1/sector?pageNumber=0&pageSize=10&sortBy=description&direction=asc\"}"));
	}
	
	@Test
	@Order(4)
	void testDeleteById() {

		given().spec(specification)
			.pathParam("id", sector.getKey())
			.when()
				.delete("{id}")
			.then()
				.statusCode(204);
		
	}
	
	@Test
	@Order(5)
	void testFindCustomPageable() throws JsonMappingException, JsonProcessingException {
		String description = "sector";
		
		var content = given().spec(specification)
				.queryParam("description", description)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		SectorWrapperVO wrapper = mapper.readValue(content, SectorWrapperVO.class);
		
		List<SectorVO> resultList = wrapper.getEmbedded().getSectors();
		
		SectorVO sectorZero = resultList.get(0);
		
		assertEquals(1, sectorZero.getKey());
		assertEquals("Sector A", sectorZero.getDescription());
		assertEquals("COMMON_USER", sectorZero.getCustomers().get(0).getUsername());
		assertEquals("MANAGER", sectorZero.getEmployees().get(0).getUsername());
		
		SectorVO sectorOne = resultList.get(1);
		
		assertEquals(2, sectorOne.getKey());
		assertEquals("Sector B", sectorOne.getDescription());
		assertEquals("COMMON_USER", sectorOne.getCustomers().get(0).getUsername());
		assertEquals("MANAGER", sectorOne.getEmployees().get(0).getUsername());
	}
	
	@Test
	@Order(6)
	void testFindUsersBySector() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("type", "Employee")
				.pathParam("id", 1L)
				.when()
				.get("/bySector/{type}/{id}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		
		UserSctrWrapperVO wrapper = mapper.readValue(content, UserSctrWrapperVO.class);
		
		List<UserSctr> list = wrapper.getUsers();
		
		UserSctr userZero = list.get(0);
		
		assertEquals(2, userZero.getKey());
		assertEquals("MANAGER", userZero.getUsername());
		assertEquals("Employee", userZero.getType().getDescription());
		assertEquals(true, userZero.getEnabled());
		assertEquals("MANAGER", userZero.getPermissions().get(0).getDescription());
	}
	
	@Test
	@Order(7)
	void testFindUsersByType() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("type", "Customer")
				.when()
				.get("/byType/{type}")
				.then()
				.statusCode(200)
				.extract()
				.body()
				.asString();
		
		UserSctrWrapperVO wrapper = mapper.readValue(content, UserSctrWrapperVO.class);
		
		List<UserSctr> list = wrapper.getUsers();
		
		UserSctr userZero = list.get(0);
		
		assertEquals(3, userZero.getKey());
		assertEquals("COMMON_USER", userZero.getUsername());
		assertEquals("Customer", userZero.getType().getDescription());
		assertEquals(true, userZero.getEnabled());
		assertEquals("COMMON_USER", userZero.getPermissions().get(0).getDescription());
	}
	
	@Test
	@Order(8)
	void testFindSectorsByUser() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.when()
					.get("/byUser")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		SimpleSectorWrapperVO wrapper = mapper.readValue(content, SimpleSectorWrapperVO.class);
		
		List<SimpleSectorVO> resultList = wrapper.getSectors();
		
		SimpleSectorVO sectorZero = resultList.get(0);
		
		assertEquals(1, sectorZero.getKey());
		assertEquals("Sector A", sectorZero.getDescription());
		
		assertTrue(sectorZero.getLinks().toString().contains("<http://localhost:8888/v1/sector/1>;rel=\"self\""));
		
		SimpleSectorVO sectorOne = resultList.get(1);
		
		assertEquals(2, sectorOne.getKey());
		assertEquals("Sector B", sectorOne.getDescription());
		
		assertTrue(sectorOne.getLinks().toString().contains("<http://localhost:8888/v1/sector/2>;rel=\"self\""));
	}
	
	@Test
	@Order(9)
	void testHATEOAS() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();

		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v1/sector/1\"}}"));
		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v1/sector/2\"}}"));

		assertTrue(content.contains("\"_links\":{\"self\":{\"href\":\"http://localhost:8888/v1/sector?page=0&size=10&sort=description,asc\"}}"));
		assertTrue(content.contains("\"page\":{\"size\":10,\"totalElements\":3,\"totalPages\":1,\"number\":0}"));
	}

}
