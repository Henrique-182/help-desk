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

import br.com.hd.data.vo.chat.v1.SectorVO;
import br.com.hd.data.vo.chat.v1.SimpleSectorVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.chat.v1.SectorMapper;
import br.com.hd.model.auth.v1.User;
import br.com.hd.model.auth.v1.UserType;
import br.com.hd.model.chat.sector.v1.Sector;
import br.com.hd.repositories.chat.v1.SectorRepository;
import br.com.hd.services.chat.v1.SectorService;
import br.com.hd.unittests.mocks.auth.v1.UserMock;
import br.com.hd.unittests.mocks.chat.v1.SectorMock;

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
		assertEquals(UserType.Customer, persistedSector.getCustomers().get(0).getType());
		assertEquals(UserType.Employee, persistedSector.getEmployees().get(0).getType());
		
		assertTrue(persistedSector.getLinks().toString().contains("</v1/sector?pageNumber=0&pageSize=10&sortBy=description&direction=asc>;rel=\"sectorVOList\""));
	}
	
	@Test
	void testFindByIdWithResourceNotFoundException() {
		Long id = 10L;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
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
		assertEquals(UserType.Customer, createdSector.getCustomers().get(0).getType());
		assertEquals(UserType.Employee, createdSector.getEmployees().get(0).getType());
		
		assertTrue(createdSector.getLinks().toString().contains("</v1/sector?pageNumber=0&pageSize=10&sortBy=description&direction=asc>;rel=\"sectorVOList\""));
	}
	
	@Test
	void testCreateWithRequiredObjectIsNullException() {
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
		assertEquals(UserType.Customer, updatedSector.getCustomers().get(0).getType());
		assertEquals(UserType.Employee, updatedSector.getEmployees().get(0).getType());
		
		assertTrue(updatedSector.getLinks().toString().contains("</v1/sector?pageNumber=0&pageSize=10&sortBy=description&direction=asc>;rel=\"sectorVOList\""));
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
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
	void testUpdateByIdWithRequiredObjectIsNullException() {
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
	void testDeleteByIdWithResourceNotFoundException() {
		Long id = 10L;
		
		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			service.deleteById(id);
		});
		
		String expectedMessage = "No records found for the id (" + id + ") !";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}

}
