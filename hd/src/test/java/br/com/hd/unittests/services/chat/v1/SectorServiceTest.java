package br.com.hd.unittests.services.chat.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.hd.data.vo.chat.sector.v1.SectorVO;
import br.com.hd.data.vo.chat.sector.v1.SimpleSectorVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.chat.v1.SectorMapper;
import br.com.hd.model.auth.v1.User;
import br.com.hd.model.chat.sector.v1.Sector;
import br.com.hd.model.chat.sector.v1.UserSctr;
import br.com.hd.repositories.chat.v1.SectorRepository;
import br.com.hd.services.chat.v1.SectorService;
import br.com.hd.unittests.mocks.auth.v1.UserMock;
import br.com.hd.unittests.mocks.chat.sector.v1.SectorMock;
import br.com.hd.unittests.mocks.chat.sector.v1.UserSctrMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class SectorServiceTest {
	
	@Autowired
	@InjectMocks
	private SectorService service;
	
	@Mock
	private SectorRepository repository;
	
	@Mock
	private SectorMapper mapper;
	
	@Test
	void testFindById() {
		
		Long id = 1L;
		
		Sector mockEntity = SectorMock.entity(id);
		SectorVO mockVO = SectorMock.vo(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		SectorVO persistedSector = service.findById(id);
		
		assertNotNull(persistedSector);
		
		assertEquals(1, persistedSector.getKey());
		assertEquals("Description1", persistedSector.getDescription());
		assertEquals("Customer", persistedSector.getCustomers().get(0).getType().getDescription());
		assertEquals("Employee", persistedSector.getEmployees().get(0).getType().getDescription());
		
		assertTrue(persistedSector.getLinks().toString().contains("</v1/sector?pageNumber=0&pageSize=10&sortBy=description&direction=asc>;rel=\"sectorVOList\""));
	}
	
	@Test
	void testFindById_WithResourceNotFoundException() {
		Long id = 10L;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testFindUsersByType() {
		
		String type = "Employee";
		
		Long id = 0L;
		
		UserSctr mockEntity = UserSctrMock.entity(id);
		UserSctr mockEntity2 = UserSctrMock.entity(id + 2);
		
		List<UserSctr> mockEntityList = List.of(mockEntity, mockEntity2);
		
		when(repository.findByType(type)).thenReturn(mockEntityList);
		
		List<UserSctr> users = service.findUsersByType(type);
		
		assertNotNull(users);
		
		UserSctr userZero = users.get(0);
		
		assertEquals(0, userZero.getKey());
		assertEquals("Username0", userZero.getUsername());
		assertEquals("Employee", userZero.getType().getDescription());
		assertEquals(true, userZero.getEnabled());
		
		UserSctr userOne = users.get(1);
		
		assertEquals(2, userOne.getKey());
		assertEquals("Username2", userOne.getUsername());
		assertEquals("Employee", userOne.getType().getDescription());
		assertEquals(true, userOne.getEnabled());
	}
	
	
	@Test
	void testFindSectorsByUserEmployee() {
		
		User mockEntity = UserMock.entity(); 
		List<Sector> mockEntityList = SectorMock.entityList();
		List<SimpleSectorVO> mockVoList = SectorMock.simpleVoList();
		
		when(repository.findSectorsByEmployee(anyLong())).thenReturn(mockEntityList);
		when(mapper.toSimpleVOList(anyList())).thenReturn(mockVoList);
		
		List<SimpleSectorVO> sectors = service.findSectorsByUser(mockEntity);
		
		assertNotNull(sectors);
		
		SimpleSectorVO sectorZero = sectors.get(0);
		
		assertEquals(0, sectorZero.getKey());
		assertEquals("Description0", sectorZero.getDescription());
		
		assertTrue(sectorZero.getLinks().toString().contains("</v1/sector/0>;rel=\"self\""));
		
		SimpleSectorVO sectorOne = sectors.get(1);
		
		assertEquals(1, sectorOne.getKey());
		assertEquals("Description1", sectorOne.getDescription());
		
		assertTrue(sectorOne.getLinks().toString().contains("</v1/sector/1>;rel=\"self\""));
	}
	
	@Test
	void testFindSectorsByUserCustomer() {
		
		User mockEntity = UserMock.entity(3L); 
		List<Sector> mockEntityList = SectorMock.entityList();
		List<SimpleSectorVO> mockVoList = SectorMock.simpleVoList();
		
		when(repository.findSectorsByCustomer(anyLong())).thenReturn(mockEntityList);
		when(mapper.toSimpleVOList(anyList())).thenReturn(mockVoList);
		
		List<SimpleSectorVO> sectors = service.findSectorsByUser(mockEntity);
		
		assertNotNull(sectors);
		
		SimpleSectorVO sectorZero = sectors.get(0);
		
		assertEquals(0, sectorZero.getKey());
		assertEquals("Description0", sectorZero.getDescription());
		
		assertTrue(sectorZero.getLinks().toString().contains("</v1/sector/0>;rel=\"self\""));
		
		SimpleSectorVO sectorOne = sectors.get(1);
		
		assertEquals(1, sectorOne.getKey());
		assertEquals("Description1", sectorOne.getDescription());
		
		assertTrue(sectorOne.getLinks().toString().contains("</v1/sector/1>;rel=\"self\""));
	}
	
	@Test
	void testCreate() {
		
		Sector mockEntity = SectorMock.entity();
		Sector persistedEntity = mockEntity;
		SectorVO mockVO = SectorMock.vo();
		
		when(mapper.toEntity(mockVO)).thenReturn(mockEntity);
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		SectorVO createdSector = service.create(mockVO);
		
		assertNotNull(createdSector);
		
		assertEquals(0, createdSector.getKey());
		assertEquals("Description0", createdSector.getDescription());
		assertEquals("Customer", createdSector.getCustomers().get(0).getType().getDescription());
		assertEquals("Employee", createdSector.getEmployees().get(0).getType().getDescription());
		
		assertTrue(createdSector.getLinks().toString().contains("</v1/sector?pageNumber=0&pageSize=10&sortBy=description&direction=asc>;rel=\"sectorVOList\""));
	}
	
	@Test
	void testCreate_WithRequiredObjectIsNullException() {
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.create(null);
		});
		
		String expectedMessage = "It is not possible to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateById() {
		Long id = 2L;
		
		Sector mockEntity = SectorMock.entity(id);
		Sector persistedEntity = mockEntity;
		SectorVO mockVO = SectorMock.vo(id);
		mockVO.setDescription(id + "Description");
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		SectorVO updatedSector = service.updateById(id, mockVO);
		
		assertNotNull(updatedSector);
		
		assertEquals(2, updatedSector.getKey());
		assertEquals("2Description", updatedSector.getDescription());
		assertEquals("Customer", updatedSector.getCustomers().get(0).getType().getDescription());
		assertEquals("Employee", updatedSector.getEmployees().get(0).getType().getDescription());
		
		assertTrue(updatedSector.getLinks().toString().contains("</v1/sector?pageNumber=0&pageSize=10&sortBy=description&direction=asc>;rel=\"sectorVOList\""));
	}
	
	@Test
	void testUpdateById_WithResourceNotFoundException() {
		Long id = 10L;
		SectorVO mockVO = SectorMock.vo();
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testUpdateById_WithRequiredObjectIsNullException() {
		Long id = 1L;
		SectorVO mockVO = null;
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "It is not possible to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testDeleteById() {
		Sector entity = SectorMock.entity(1L); 
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.deleteById(1L);
	}
	
	@Test
	void testDeleteById_WithResourceNotFoundException() {
		Long id = 10L;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}

}
