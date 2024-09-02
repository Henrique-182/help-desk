package br.com.hd.unittests.services.chat.v1;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.hd.data.vo.chat.room.v1.RoomCreationVO;
import br.com.hd.data.vo.chat.room.v1.RoomUpdateVO;
import br.com.hd.data.vo.chat.room.v1.RoomVO;
import br.com.hd.exceptions.generic.v1.InvalidArgumentsException;
import br.com.hd.mappers.chat.v1.RoomMapper;
import br.com.hd.model.chat.room.v1.Room;
import br.com.hd.model.chat.room.v1.RoomStatus;
import br.com.hd.model.chat.room.v1.SectorRoom;
import br.com.hd.model.chat.room.v1.UserRoom;
import br.com.hd.repositories.chat.v1.RoomRepository;
import br.com.hd.services.chat.v1.RoomService;
import br.com.hd.unittests.mocks.chat.room.v1.RoomMock;
import br.com.hd.unittests.mocks.chat.room.v1.SectorRoomMock;
import br.com.hd.unittests.mocks.chat.room.v1.UserRoomMock;
import br.com.hd.util.service.v1.ServiceUtil;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {
	
	@Autowired
	@InjectMocks
	private RoomService service;
	
	@Mock
	private RoomRepository repository;
	
	@Mock
	private ServiceUtil util;
	
	@Mock
	private RoomMapper mapper;
	
	@Test
	void testFindByCode() {
		
		Integer code = 1;
		
		Room mockEntity = RoomMock.entity(code);
		RoomVO mockVO = RoomMock.vo(code);
		
		when(repository.findByCode(code)).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		RoomVO persistedRoom = service.findByCode(code);
		
		assertNotNull(persistedRoom);
		
		assertEquals(1L, persistedRoom.getKey());
		assertEquals(1, persistedRoom.getCode());
		assertEquals(RoomStatus.Closed, persistedRoom.getStatus());
		assertEquals(new Date(code), persistedRoom.getCreateDatetime());
		assertEquals(new Date(code + code), persistedRoom.getCloseDatetime());
		assertEquals("Username1", persistedRoom.getCustomer().getUsername());
		assertEquals("Username0", persistedRoom.getEmployee().getUsername());
		assertEquals("Description0", persistedRoom.getSector().getDescription());
		assertEquals("Content0", persistedRoom.getMessages().get(0).getContent());
		
		assertTrue(persistedRoom.toString().contains("</v1/room/1>;rel=\"self\""));
	}
	
	@Test
	void testCreateByCustomer() {
		
		Long customerKey = 1L;
		
		Long sectorKey = 1L;
		
		RoomCreationVO data = new RoomCreationVO();
		data.setCustomerKey(customerKey);
		data.setSectorKey(sectorKey);
		
		Room mockEntity = RoomMock.entity();
		mockEntity.setEmployee(null);
		mockEntity.setCustomer(UserRoomMock.entity(customerKey));
		mockEntity.setSector(SectorRoomMock.entity(sectorKey));
		mockEntity.setStatus(RoomStatus.Open);
		
		Room updatedEntity = mockEntity;
		
		RoomVO mockVO = RoomMock.vo();
		mockVO.setEmployee(null);
		mockVO.setCustomer(UserRoomMock.entity(customerKey));
		mockVO.setSector(SectorRoomMock.entity(sectorKey));
		mockVO.setStatus(RoomStatus.Open);
		
		
		when(util.customerExists(customerKey)).thenReturn(true);
		when(util.sectorExists(sectorKey)).thenReturn(true);
		when(repository.save(any(Room.class))).thenReturn(updatedEntity);
		when(mapper.toVO(updatedEntity)).thenReturn(mockVO);
		
		RoomVO createdRoom = service.createByCustomer(data);
		
		assertNotNull(createdRoom);
		
		assertEquals(0L, createdRoom.getKey());
		assertEquals(0, createdRoom.getCode());
		assertEquals(RoomStatus.Open, createdRoom.getStatus());
		assertEquals(new Date(0), createdRoom.getCreateDatetime());
		assertEquals(new Date(0), createdRoom.getCloseDatetime());
		assertEquals("Username1", createdRoom.getCustomer().getUsername());
		assertNull(createdRoom.getEmployee());
		assertEquals("Description1", createdRoom.getSector().getDescription());
		assertEquals("Content0", createdRoom.getMessages().get(0).getContent());
		
		assertTrue(createdRoom.toString().contains("</v1/room/0>;rel=\"self\""));
	}
	
	@Test
	void testCreateByEmployee() {
		
		Long employeeKey = 2L;
		
		Long customerKey = 1L;
		
		Long sectorKey = 1L;
		
		RoomCreationVO data = new RoomCreationVO();
		data.setCustomerKey(customerKey);
		data.setEmployeeKey(employeeKey);
		data.setSectorKey(sectorKey);
		
		Room mockEntity = RoomMock.entity();
		mockEntity.setEmployee(UserRoomMock.entity(employeeKey));
		mockEntity.setCustomer(UserRoomMock.entity(customerKey));
		mockEntity.setSector(SectorRoomMock.entity(sectorKey));
		
		Room updatedEntity = mockEntity;
		
		RoomVO mockVO = RoomMock.vo();
		mockVO.setEmployee(UserRoomMock.entity(employeeKey));
		mockVO.setCustomer(UserRoomMock.entity(customerKey));
		mockVO.setSector(SectorRoomMock.entity(sectorKey));
		
		when(util.employeeExists(employeeKey)).thenReturn(true);
		when(util.customerExists(customerKey)).thenReturn(true);
		when(util.sectorExists(sectorKey)).thenReturn(true);
		when(repository.save(any(Room.class))).thenReturn(updatedEntity);
		when(mapper.toVO(updatedEntity)).thenReturn(mockVO);
		
		RoomVO createdRoom = service.createByEmployee(data);
		
		assertNotNull(createdRoom);
		
		assertEquals(0L, createdRoom.getKey());
		assertEquals(0, createdRoom.getCode());
		assertEquals(RoomStatus.Chatting, createdRoom.getStatus());
		assertEquals(new Date(0), createdRoom.getCreateDatetime());
		assertEquals(new Date(0), createdRoom.getCloseDatetime());
		assertEquals("Username1", createdRoom.getCustomer().getUsername());
		assertEquals("Username2", createdRoom.getEmployee().getUsername());
		assertEquals("Description1", createdRoom.getSector().getDescription());
		assertEquals("Content0", createdRoom.getMessages().get(0).getContent());
		
		assertTrue(createdRoom.toString().contains("</v1/room/0>;rel=\"self\""));
	}
	
	@Test
	void testCreateByEmployeeWithInvalidArgumentsException() {
		
		Long employeeKey = 1L;
		
		Long customerKey = 1L;
		
		Long sectorKey = 1L;
		
		RoomCreationVO data = new RoomCreationVO();
		data.setCustomerKey(customerKey);
		data.setEmployeeKey(employeeKey);
		data.setSectorKey(sectorKey);
		
		Exception exception = assertThrows(InvalidArgumentsException.class, () -> {
			service.createByEmployee(data);
		});
		
		String expectedMessage = "It is not possible to create the room. Employee and Customer are equal !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateStatusByCode() {
		
		Integer code = 0;
		
		RoomStatus status = RoomStatus.Paused;
		
		RoomUpdateVO data = new RoomUpdateVO();
		data.setStatus(status);
		
		
		Room mockEntity = RoomMock.entity();
		mockEntity.setStatus(status);
		
		Room updatedEntity = mockEntity;
		
		RoomVO mockVO = RoomMock.vo();
		mockVO.setStatus(status);
		
		when(repository.findByCode(code)).thenReturn(Optional.of(mockEntity));
		when(repository.save(mockEntity)).thenReturn(updatedEntity);
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		RoomVO updatedRoom = service.updateStatusByCode(code, data);
		
		assertNotNull(updatedRoom);
		
		assertEquals(0L, updatedRoom.getKey());
		assertEquals(0, updatedRoom.getCode());
		assertEquals(RoomStatus.Paused, updatedRoom.getStatus());
		assertEquals(new Date(0), updatedRoom.getCreateDatetime());
		assertEquals(new Date(0), updatedRoom.getCloseDatetime());
		assertEquals("Username1", updatedRoom.getCustomer().getUsername());
		assertEquals("Username0", updatedRoom.getEmployee().getUsername());
		assertEquals("Description0", updatedRoom.getSector().getDescription());
		assertEquals("Content0", updatedRoom.getMessages().get(0).getContent());
		
		assertTrue(updatedRoom.toString().contains("</v1/room/0>;rel=\"self\""));
	}
	
	@Test
	void testUpdateEmployeeAndStatusByCode() {
		
		Integer code = 0;
		
		UserRoom employeeMock = UserRoomMock.entity(10L);
		
		RoomStatus status = RoomStatus.Transferred;
		
		RoomUpdateVO data = new RoomUpdateVO();
		data.setStatus(status);
		data.setEmployeeKey(employeeMock.getKey());
		
		Room mockEntity = RoomMock.entity();
		mockEntity.setStatus(status);
		mockEntity.setEmployee(employeeMock);
		
		Room updatedEntity = mockEntity;
		
		RoomVO mockVO = RoomMock.vo();
		mockVO.setStatus(status);
		mockVO.setEmployee(employeeMock);

		when(repository.findByCode(code)).thenReturn(Optional.of(mockEntity));
		when(util.employeeExists(employeeMock.getKey())).thenReturn(true);
		when(repository.save(mockEntity)).thenReturn(updatedEntity);
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		RoomVO updatedRoom = service.updateEmployeeAndStatusByCode(code, data);
		
		assertNotNull(updatedRoom);
		
		assertEquals(0L, updatedRoom.getKey());
		assertEquals(0, updatedRoom.getCode());
		assertEquals(RoomStatus.Transferred, updatedRoom.getStatus());
		assertEquals(new Date(0), updatedRoom.getCreateDatetime());
		assertEquals(new Date(0), updatedRoom.getCloseDatetime());
		assertEquals("Username1", updatedRoom.getCustomer().getUsername());
		assertEquals("Username10", updatedRoom.getEmployee().getUsername());
		assertEquals("Description0", updatedRoom.getSector().getDescription());
		assertEquals("Content0", updatedRoom.getMessages().get(0).getContent());
		
		assertTrue(updatedRoom.toString().contains("</v1/room/0>;rel=\"self\""));
	}
	
	@Test
	void testUpdateEmployeeAndSectorAndStatusByCode() {
		
		Integer code = 0;
		
		UserRoom employeeMock = UserRoomMock.entity(10L);
		
		SectorRoom sectorMock = SectorRoomMock.entity(5L);
		
		RoomStatus status = RoomStatus.Transferred;
		
		RoomUpdateVO data = new RoomUpdateVO();
		data.setStatus(status);
		data.setEmployeeKey(employeeMock.getKey());
		data.setSectorKey(sectorMock.getKey());
		
		Room mockEntity = RoomMock.entity();
		mockEntity.setStatus(status);
		mockEntity.setEmployee(employeeMock);
		mockEntity.setSector(sectorMock);
		
		Room updatedEntity = mockEntity;
		
		RoomVO mockVO = RoomMock.vo();
		mockVO.setStatus(status);
		mockVO.setEmployee(employeeMock);
		mockVO.setSector(sectorMock);

		
		when(repository.findByCode(code)).thenReturn(Optional.of(mockEntity));
		when(util.employeeExists(employeeMock.getKey())).thenReturn(true);
		when(util.sectorExists(sectorMock.getKey())).thenReturn(true);
		when(repository.save(mockEntity)).thenReturn(updatedEntity);
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		RoomVO updatedRoom = service.updateEmployeeAndSectorAndStatusByCode(code, data);
		
		assertNotNull(updatedRoom);
		
		assertEquals(0L, updatedRoom.getKey());
		assertEquals(0, updatedRoom.getCode());
		assertEquals(RoomStatus.Transferred, updatedRoom.getStatus());
		assertEquals(new Date(0), updatedRoom.getCreateDatetime());
		assertEquals(new Date(0), updatedRoom.getCloseDatetime());
		assertEquals("Username1", updatedRoom.getCustomer().getUsername());
		assertEquals("Username10", updatedRoom.getEmployee().getUsername());
		assertEquals("Description5", updatedRoom.getSector().getDescription());
		assertEquals("Content0", updatedRoom.getMessages().get(0).getContent());
		
		assertTrue(updatedRoom.toString().contains("</v1/room/0>;rel=\"self\""));
	}
	
}
