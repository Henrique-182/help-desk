package br.com.hd.integrationtests.repositories.chat.v1;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.hd.model.chat.room.v1.Room;
import br.com.hd.model.chat.room.v1.RoomStatus;
import br.com.hd.repositories.chat.v1.RoomRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class RoomRepositoryTest extends AbstractIntegrationTest {
	
	@Autowired
	private RoomRepository repository;
	
	@Test
	@Order(1)
	void testFindByCode() {
		
		Integer code = 4;
		
		Room persistedEntity = repository.findByCode(code).get();
		
		assertNotNull(persistedEntity);
		
		assertEquals(4L, persistedEntity.getId());
		assertEquals(4, persistedEntity.getCode());
		assertEquals(RoomStatus.Closed, persistedEntity.getStatus());
		assertEquals("Normal", persistedEntity.getPriority().getDescription());
		assertEquals("Cliente solicitou ajuda em....", persistedEntity.getReason());
		assertEquals("Cliente ajudado no quesito...", persistedEntity.getSolution());
		assertEquals("COMMON_USER", persistedEntity.getCustomer().getUsername());
		assertEquals("MANAGER", persistedEntity.getEmployee().getUsername());
		assertEquals("Sector B", persistedEntity.getSector().getDescription());
	}
	
	@Test
	@Order(2)
	void testCountByUserKeyAndRoomKey() {
		
		Long employeeKey = 2L;
		Long roomKey = 4L;
		
		long count = repository.countByUserKeyAndRoomKey(employeeKey, roomKey);
		
		assertNotNull(count);
		
		assertEquals(1, count);
	}
	
	@Test
	@Order(3)
	void testFindBySectorAndStatus() {
		
		Long sectorId = 1L;
		
		List<RoomStatus> statusList = List.of(RoomStatus.Chatting, RoomStatus.Transferred); 
		
		List<Room> entityList = repository.findBySectorKeyAndStatusIn(sectorId, statusList);
		
		assertNotNull(entityList);
		
		Room roomZero = entityList.get(0);
		
		assertNotNull(roomZero);
		
		assertEquals(1L, roomZero.getId());
		assertEquals(1, roomZero.getCode());
		assertEquals(RoomStatus.Transferred, roomZero.getStatus());
		assertEquals("Normal", roomZero.getPriority().getDescription());
		assertNull(roomZero.getReason());
		assertNull(roomZero.getSolution());
		assertEquals("COMMON_USER", roomZero.getCustomer().getUsername());
		assertEquals("MANAGER", roomZero.getEmployee().getUsername());
		assertEquals("Sector A", roomZero.getSector().getDescription());
		
		Room roomOne = entityList.get(1);
		
		assertNotNull(roomOne);
		
		assertEquals(3L, roomOne.getId());
		assertEquals(3, roomOne.getCode());
		assertEquals(RoomStatus.Chatting, roomOne.getStatus());
		assertEquals("Normal", roomOne.getPriority().getDescription());
		assertNull(roomOne.getReason());
		assertNull(roomOne.getSolution());
		assertEquals("COMMON_USER", roomOne.getCustomer().getUsername());
		assertEquals("MANAGER", roomOne.getEmployee().getUsername());
		assertEquals("Sector A", roomOne.getSector().getDescription());
	}
	
	@Test
	@Order(4)
	void testFindBySectorAndEmployeeAndStatus() {
		
		Long sectorId = 2L;
		
		Long employeeId = 2L;
		
		List<RoomStatus> status = List.of(RoomStatus.Closed);
		
		List<Room> entityList = repository.findBySectorKeyAndEmployeeKeyAndStatusIn(sectorId, employeeId, status);
		
		Room roomZero = entityList.get(0);
		
		assertNotNull(roomZero);
		
		assertEquals(4L, roomZero.getId());
		assertEquals(4, roomZero.getCode());
		assertEquals(RoomStatus.Closed, roomZero.getStatus());
		assertEquals("Normal", roomZero.getPriority().getDescription());
		assertEquals("Cliente solicitou ajuda em....", roomZero.getReason());
		assertEquals("Cliente ajudado no quesito...", roomZero.getSolution());
		assertEquals("COMMON_USER", roomZero.getCustomer().getUsername());
		assertEquals("MANAGER", roomZero.getEmployee().getUsername());
		assertEquals("Sector B", roomZero.getSector().getDescription());
	}
	
	@Test
	@Order(5)
	void testFindMaxCode() {
		
		int code = repository.findMaxCode();
		
		assertNotNull(code);
		
		assertEquals(4, code);
	}
	
}
