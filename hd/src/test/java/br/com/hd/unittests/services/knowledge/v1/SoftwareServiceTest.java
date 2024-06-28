package br.com.hd.unittests.services.knowledge.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.hd.data.vo.knowledge.v1.SoftwareVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.knowledge.v1.SoftwareMapper;
import br.com.hd.model.knowledge.v1.Software;
import br.com.hd.repositories.knowledge.v1.SoftwareRepository;
import br.com.hd.services.knowledge.v1.SoftwareService;
import br.com.hd.unittests.mocks.knowledge.v1.SoftwareMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class SoftwareServiceTest {
	
	@Autowired
	@InjectMocks
	private SoftwareService service;
	
	@Mock
	private SoftwareRepository repository;
	
	@Mock
	private SoftwareMapper mapper;
	
	@Test
	void testFindById() {
		
		Long id = 1L;
		
		Software mockEntity = SoftwareMock.entity(id);
		SoftwareVO mockVO = SoftwareMock.vo(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		SoftwareVO persistedSoftware = service.findById(id);
		
		assertNotNull(persistedSoftware);
		
		assertEquals(1, persistedSoftware.getKey());
		assertEquals("Name1", persistedSoftware.getDescription());
		assertTrue(persistedSoftware.getLinks().toString().contains("</v1/software?pageNumber=0&pageSize=10&sortBy=name&direction=asc>;rel=\"softwareVOList\""));
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
	void testCreate() {
		
		Software mockEntity = SoftwareMock.entity();
		Software persistedEntity = mockEntity;
		SoftwareVO mockVO = SoftwareMock.vo();
		
		when(mapper.toEntity(mockVO)).thenReturn(mockEntity);
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		SoftwareVO createdSoftware = service.create(mockVO);
		
		assertNotNull(createdSoftware);
		
		assertEquals(0, createdSoftware.getKey());
		assertEquals("Name0", createdSoftware.getDescription());
		assertTrue(createdSoftware.getLinks().toString().contains("</v1/software?pageNumber=0&pageSize=10&sortBy=name&direction=asc>;rel=\"softwareVOList\""));
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
		
		Long id = 1L;
		
		Software mockEntity = SoftwareMock.entity(id);
		Software persistedEntity = mockEntity;
		SoftwareVO mockVO = SoftwareMock.vo(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		SoftwareVO updatedSoftware = service.updateById(id, mockVO);
		
		assertNotNull(updatedSoftware);
		
		assertEquals(1, updatedSoftware.getKey());
		assertEquals("Name1", updatedSoftware.getDescription());
		assertTrue(updatedSoftware.getLinks().toString().contains("</v1/software?pageNumber=0&pageSize=10&sortBy=name&direction=asc>;rel=\"softwareVOList\""));
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Long id = null;
		SoftwareVO mockVO = SoftwareMock.vo();
		
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
		SoftwareVO mockVO = null;
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "It is not possible to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testDeleteById() {
		
		Long id = 1L;
		
		Software mockEntity = SoftwareMock.entity(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		
		service.deleteById(id);
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
