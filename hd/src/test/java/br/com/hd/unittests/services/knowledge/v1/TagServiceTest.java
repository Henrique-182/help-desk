package br.com.hd.unittests.services.knowledge.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import br.com.hd.data.vo.knowledge.v1.TagVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.knowledge.v1.TagMapper;
import br.com.hd.model.knowledge.v1.Tag;
import br.com.hd.repositories.knowledge.v1.TagRepository;
import br.com.hd.services.knowledge.v1.TagService;
import br.com.hd.unittests.mocks.knowledge.v1.TagMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class TagServiceTest {
	
	@Autowired
	@InjectMocks
	private TagService service;
	
	@Mock
	private TagRepository repository;
	
	@Mock
	private TagMapper mapper;
	
	@Test
	void testFindById() {
		
		Long id = 1L;
		
		Tag mockEntity = TagMock.entity(id);
		TagVO mockVO = TagMock.vo(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		TagVO persistedTag = service.findById(id);
		
		assertNotNull(persistedTag);
		
		assertNotNull(persistedTag);
		assertEquals(1, persistedTag.getKey());
		assertEquals("Name1", persistedTag.getDescription());
		
		//assertTrue(persistedTag.getLinks().toString().contains("</v1/tag?page=0&size=10&sortBy=name&direction=asc>;rel=\"tagVOList\""));
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
		
		Tag mockEntity = TagMock.entity();
		Tag persistedEntity = mockEntity;
		TagVO mockVO = TagMock.vo();
		
		when(mapper.toEntity(mockVO)).thenReturn(mockEntity);
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		TagVO createdTag = service.create(mockVO);
		
		assertNotNull(createdTag);
		
		assertNotNull(createdTag);
		assertEquals(0, createdTag.getKey());
		assertEquals("Name0", createdTag.getDescription());
		
		//assertTrue(createdTag.getLinks().toString().contains("</v1/tag?page=0&size=10&sortBy=name&direction=asc>;rel=\"tagVOList\""));
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
		
		Tag mockEntity = TagMock.entity(id);
		Tag persistedEntity = mockEntity;
		TagVO mockVO = TagMock.vo(id);
		mockVO.setDescription(id + "Name");
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		TagVO updatedTag = service.updateById(id, mockVO);
		
		assertNotNull(updatedTag);
		
		assertNotNull(updatedTag);
		assertEquals(2, updatedTag.getKey());
		assertEquals("2Name", updatedTag.getDescription());
		
		//assertTrue(updatedTag.getLinks().toString().contains("</v1/tag?page=0&size=10&sortBy=name&direction=asc>;rel=\"tagVOList\""));
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Long id = 10L;
		TagVO mockVO = TagMock.vo();
		
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
		TagVO mockVO = null;
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "It is not possible to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testDeleteById() {
		Tag entity = TagMock.entity(1L); 
		
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
