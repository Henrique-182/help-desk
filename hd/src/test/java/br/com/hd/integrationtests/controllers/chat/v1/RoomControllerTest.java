package br.com.hd.integrationtests.controllers.chat.v1;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import br.com.hd.integrationtests.data.vo.chat.room.v1.RoomCreationVO;
import br.com.hd.integrationtests.data.vo.chat.room.v1.RoomUpdateVO;
import br.com.hd.integrationtests.data.vo.chat.room.v1.RoomVO;
import br.com.hd.integrationtests.data.vo.chat.room.v1.RoomWrapperVO;
import br.com.hd.integrationtests.mocks.chat.room.v1.RoomCreationMock;
import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.hd.model.chat.room.v1.RoomStatus;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class RoomControllerTest extends AbstractIntegrationTest {
	
	private static RequestSpecification specification;
	private static RequestSpecification specification2;
	private static ObjectMapper mapper;
	
	private static AccountCredentialsVO credentials;
	private static AccountCredentialsVO credentials2;
	private static RoomVO room;
	private static RoomVO room2;
	private static RoomCreationVO roomCreation;

	@BeforeAll
	public static void setup() {
		mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		credentials = new AccountCredentialsVO("MANAGER", "MANAGER#@!312");
		credentials2 = new AccountCredentialsVO("COMMON_USER", "COMMON_USER#@!312"); 
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
				.setBasePath("/v1/room")
				.setPort(TestConfigs.SERVER_PORT)
				.setContentType(TestConfigs.CONTENT_TYPE_JSON)
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getAccessToken())
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(0)
	void testAuthentication2() {
		
		TokenVO tokenVO = given()
				.basePath("/v1/auth/signin")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(credentials2)
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
		
		specification2 = new RequestSpecBuilder()
				.setBasePath("/v1/room")
				.setPort(TestConfigs.SERVER_PORT)
				.setContentType(TestConfigs.CONTENT_TYPE_JSON)
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getAccessToken())
				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	void testCreateByCustomer() throws JsonMappingException, JsonProcessingException {
		roomCreation = RoomCreationMock.vo();
		
		var content = given().spec(specification2)
				.body(roomCreation)
				.when()
					.post("/byCustomer")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		RoomVO createdRoom = mapper.readValue(content, RoomVO.class);
		room = createdRoom;
		
		assertTrue(createdRoom.getKey() > 0);
		
		assertEquals(5, createdRoom.getCode());
		assertEquals(RoomStatus.Open, createdRoom.getStatus());
		assertEquals("Normal", createdRoom.getPriority().getDescription());
		assertNull(createdRoom.getReason());
		assertNull(createdRoom.getSolution());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(createdRoom.getCreateDatetime()));
		assertNull(createdRoom.getCloseDatetime());
		assertEquals(3, createdRoom.getCustomer().getKey());
		assertNull(createdRoom.getEmployee());
		assertEquals(2, createdRoom.getSector().getKey());
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/room/5\"}"));
	}
	
	@Test
	@Order(2)
	void testCreateByEmployee() throws JsonMappingException, JsonProcessingException {
		roomCreation = RoomCreationMock.vo();
		
		var content = given().spec(specification)
				.body(roomCreation)
				.when()
					.post("/byEmployee")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		RoomVO createdRoom = mapper.readValue(content, RoomVO.class);
		room2 = createdRoom;
		
		assertTrue(createdRoom.getKey() > 0);
		
		assertEquals(6, createdRoom.getCode());
		assertEquals(RoomStatus.Chatting, createdRoom.getStatus());
		assertEquals("Normal", createdRoom.getPriority().getDescription());
		assertNull(createdRoom.getReason());
		assertNull(createdRoom.getSolution());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(createdRoom.getCreateDatetime()));
		assertNull(createdRoom.getCloseDatetime());
		assertEquals(3, createdRoom.getCustomer().getKey());
		assertEquals(2, createdRoom.getEmployee().getKey());
		assertEquals(2, createdRoom.getSector().getKey());
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/room/6\"}"));
	}
	
	@Test
	@Order(3)
	void testFindByCode() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("code", room.getCode())
				.when()
					.get("/{code}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		RoomVO persistedRoom = mapper.readValue(content, RoomVO.class);
		
		assertEquals(5L, persistedRoom.getKey());
		assertEquals(5, persistedRoom.getCode());
		assertEquals(RoomStatus.Open, persistedRoom.getStatus());
		assertEquals("Normal", persistedRoom.getPriority().getDescription());
		assertNull(persistedRoom.getReason());
		assertNull(persistedRoom.getSolution());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(persistedRoom.getCreateDatetime()));
		assertNull(persistedRoom.getCloseDatetime());
		assertEquals("COMMON_USER", persistedRoom.getCustomer().getUsername());
		assertNull(persistedRoom.getEmployee());
		assertEquals("Sector B", persistedRoom.getSector().getDescription());
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/room/5\"}"));
	}
	
	@Test
	@Order(4)
	void testFindBySectorAndStatus() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("sectorKey", room.getSector().getKey())
				.queryParam("status1", room.getStatus())
				.when()
					.get("/bySector/{sectorKey}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		RoomWrapperVO wrapper = mapper.readValue(content, RoomWrapperVO.class);
		
		List<RoomVO> entityList = wrapper.getRooms();
		
		RoomVO roomZero = entityList.get(0);
		
		assertEquals(5L, roomZero.getKey());
		assertEquals(5, roomZero.getCode());
		assertEquals(RoomStatus.Open, roomZero.getStatus());
		assertEquals("Normal", roomZero.getPriority().getDescription());
		assertNull(roomZero.getReason());
		assertNull(roomZero.getSolution());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(roomZero.getCreateDatetime()));
		assertNull(roomZero.getCloseDatetime());
		assertEquals("COMMON_USER", roomZero.getCustomer().getUsername());
		assertNull(roomZero.getEmployee());
		assertEquals("Sector B", roomZero.getSector().getDescription());
		
		assertTrue(content.contains("{\"rel\":\"self\",\"href\":\"http://localhost:8888/v1/room/5\"}"));
	}
	
	@Test
	@Order(5)
	void testFindBySectorAndEmployeeAndStatus() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("sectorKey", room2.getSector().getKey())
				.queryParam("status1", room2.getStatus())
				.when()
					.get("/bySectorAndEmployee/{sectorKey}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		RoomWrapperVO wrapper = mapper.readValue(content, RoomWrapperVO.class);
		
		List<RoomVO> entityList = wrapper.getRooms();
		
		RoomVO roomZero = entityList.get(0);
		
		assertEquals(6L, roomZero.getKey());
		assertEquals(6, roomZero.getCode());
		assertEquals(RoomStatus.Chatting, roomZero.getStatus());
		assertEquals("Normal", roomZero.getPriority().getDescription());
		assertNull(roomZero.getReason());
		assertNull(roomZero.getSolution());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(roomZero.getCreateDatetime()));
		assertNull(roomZero.getCloseDatetime());
		assertEquals("COMMON_USER", roomZero.getCustomer().getUsername());
		assertEquals("MANAGER", roomZero.getEmployee().getUsername());
		assertEquals("Sector B", roomZero.getSector().getDescription());
		
		assertTrue(content.contains("{\"rel\":\"self\",\"href\":\"http://localhost:8888/v1/room/6\"}"));
	}
	
	@Test
	@Order(6)
	void testUpdateReasonAndPriority() throws JsonMappingException, JsonProcessingException {
		
		RoomUpdateVO data = new RoomUpdateVO();
		data.setReason("Reason1");
		data.setSolution("Solution1");
		data.setPriority("VERY HIGH");
		
		var content = given().spec(specification)
				.pathParam("code", room2.getCode())
				.body(data)
				.when()
					.patch("/reasonAndSolutionAndPriority/{code}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		RoomVO updatedRoom = mapper.readValue(content, RoomVO.class);
		
		assertEquals(6L, updatedRoom.getKey());
		assertEquals(6, updatedRoom.getCode());
		assertEquals(RoomStatus.Chatting, updatedRoom.getStatus());
		assertEquals("Very High", updatedRoom.getPriority().getDescription());
		assertEquals("Reason1", updatedRoom.getReason());
		assertEquals("Solution1", updatedRoom.getSolution());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(updatedRoom.getCreateDatetime()));
		assertNull(updatedRoom.getCloseDatetime());
		assertEquals("COMMON_USER", updatedRoom.getCustomer().getUsername());
		assertEquals("MANAGER", updatedRoom.getEmployee().getUsername());
		assertEquals("Sector B", updatedRoom.getSector().getDescription());

		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/room/6\"}"));
	}
	
	@Test
	@Order(7)
	void testUpdateStatusByCode() throws JsonMappingException, JsonProcessingException {
		
		RoomUpdateVO data = new RoomUpdateVO();
		data.setStatus(RoomStatus.Closed);
		data.setReason("Reason2");
		data.setSolution("Solution2");
		
		var content = given().spec(specification)
				.pathParam("code", room2.getCode())
				.body(data)
				.when()
					.patch("/status/{code}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		RoomVO updatedRoom = mapper.readValue(content, RoomVO.class);
		room2 = updatedRoom;
		
		assertEquals(6L, updatedRoom.getKey());
		assertEquals(6, updatedRoom.getCode());
		assertEquals(RoomStatus.Closed, updatedRoom.getStatus());
		assertEquals("Very High", updatedRoom.getPriority().getDescription());
		assertEquals("Reason2", updatedRoom.getReason());
		assertEquals("Solution2", updatedRoom.getSolution());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(updatedRoom.getCreateDatetime()));
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(updatedRoom.getCloseDatetime()));
		assertEquals("COMMON_USER", updatedRoom.getCustomer().getUsername());
		assertEquals("MANAGER", updatedRoom.getEmployee().getUsername());
		assertEquals("Sector B", updatedRoom.getSector().getDescription());
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/room/6\"}"));
	}
	
	@Test
	@Order(8)
	void testEmployeeEnterRoomByCode() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.pathParam("code", room.getCode())
				.when()
					.patch("/enterRoom/{code}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		RoomVO updatedRoom = mapper.readValue(content, RoomVO.class);
		room = updatedRoom;
		
		assertEquals(5L, updatedRoom.getKey());
		assertEquals(5, updatedRoom.getCode());
		assertEquals(RoomStatus.Chatting, updatedRoom.getStatus());
		assertEquals("Normal", updatedRoom.getPriority().getDescription());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(updatedRoom.getCreateDatetime()));
		assertNull(updatedRoom.getCloseDatetime());
		assertEquals("COMMON_USER", updatedRoom.getCustomer().getUsername());
		assertEquals(2L, updatedRoom.getEmployee().getKey());
		assertEquals("Sector B", updatedRoom.getSector().getDescription());
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/room/5\"}"));
	}
	
	@Test
	@Order(9)
	void testTransferRoomByCode() throws JsonMappingException, JsonProcessingException {
		
		RoomUpdateVO data = new RoomUpdateVO();
		data.setEmployeeKey(2L);
		data.setSectorKey(3L);
		
		var content = given().spec(specification)
				.pathParam("code", room.getCode())
				.body(data)
				.when()
					.patch("/transferRoom/{code}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		RoomVO updatedRoom = mapper.readValue(content, RoomVO.class);
		room = updatedRoom;
		
		assertEquals(5L, updatedRoom.getKey());
		assertEquals(5, updatedRoom.getCode());
		assertEquals(RoomStatus.Transferred, updatedRoom.getStatus());
		assertEquals("Normal", updatedRoom.getPriority().getDescription());
		assertNull(updatedRoom.getReason());
		assertNull(updatedRoom.getSolution());
		assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), new SimpleDateFormat("yyyy-MM-dd").format(updatedRoom.getCreateDatetime()));
		assertNull(updatedRoom.getCloseDatetime());
		assertEquals("COMMON_USER", updatedRoom.getCustomer().getUsername());
		assertEquals(2L, updatedRoom.getEmployee().getKey());
		assertEquals(3L, updatedRoom.getSector().getKey());
		
		assertTrue(content.contains("\"self\":{\"href\":\"http://localhost:8888/v1/room/5\"}"));
	}
	
	@Test
	@Order(10)
	void testDeleteByCode() throws JsonMappingException, JsonProcessingException {
		
		given().spec(specification)
			.pathParam("code", room.getCode())
			.when()
				.delete("/{code}")
			.then()
				.statusCode(204);
		
		given().spec(specification)
			.pathParam("code", room2.getCode())
			.when()
				.delete("/{code}")
			.then()
				.statusCode(204);
	}
	
}
