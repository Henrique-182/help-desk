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

import com.github.dockerjava.zerodep.shaded.org.apache.hc.core5.http.ParseException;

import br.com.hd.data.vo.knowledge.v1.KnowledgeVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.knowledge.v1.KnowledgeMapper;
import br.com.hd.model.knowledge.v1.Knowledge;
import br.com.hd.repositories.knowledge.v1.KnowledgeRepository;
import br.com.hd.services.knowledge.v1.KnowledgeService;
import br.com.hd.unittests.mocks.knowledge.v1.KnowledgeMock;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class KnowledgeServiceTest {

	@Autowired
	@InjectMocks
	private KnowledgeService service;
	
	@Mock
	private KnowledgeRepository repository;
	
	@Mock
	private KnowledgeMapper mapper;
	
	@Test
	void testFindById() {
		
		Long id = 1L;
		
		Knowledge mockEntity = KnowledgeMock.entity(id);
		KnowledgeVO mockVO = KnowledgeMock.vo(id);
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(mapper.toVO(mockEntity)).thenReturn(mockVO);
		
		KnowledgeVO persistedKnowledge = service.findById(id);
		
		assertNotNull(persistedKnowledge);
		
		assertEquals(1L, persistedKnowledge.getKey());
		assertEquals("Title1", persistedKnowledge.getTitle());
		assertEquals("Content1", persistedKnowledge.getContent());
		
		assertEquals("Software B", persistedKnowledge.getSoftware().getDescription());
		
		assertEquals("Name0", persistedKnowledge.getTags().get(0).getDescription());
		assertEquals("Name1", persistedKnowledge.getTags().get(1).getDescription());
		
		assertTrue(persistedKnowledge.getLinks().toString().contains("</v1/knowledge?pageNumber=0&pageSize=10&sortBy=title&direction=asc>;rel=\"knowledgeVOList\""));
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
	void testCreate() throws ParseException {
		
		Knowledge mockEntity = KnowledgeMock.entity();
		Knowledge persistedEntity = mockEntity;
		KnowledgeVO mockVO = KnowledgeMock.vo();
		
		when(mapper.toEntity(mockVO)).thenReturn(mockEntity);
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		KnowledgeVO createdKnowledge = service.create(mockVO);
		
		assertNotNull(createdKnowledge);
		
		assertEquals(0L, createdKnowledge.getKey());
		assertEquals("Title0", createdKnowledge.getTitle());
		assertEquals("Content0", createdKnowledge.getContent());
		
		assertEquals("Software A", createdKnowledge.getSoftware().getDescription());
		
		assertEquals("Name0", createdKnowledge.getTags().get(0).getDescription());
		assertEquals("Name1", createdKnowledge.getTags().get(1).getDescription());
		
		assertTrue(createdKnowledge.getLinks().toString().contains("</v1/knowledge?pageNumber=0&pageSize=10&sortBy=title&direction=asc>;rel=\"knowledgeVOList\""));
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
		
		Knowledge mockEntity = KnowledgeMock.entity(id);
		Knowledge persistedEntity = mockEntity;
		KnowledgeVO mockVO = KnowledgeMock.vo(id);
		mockVO.setTitle(id + "Title");
		mockVO.setContent(id + "Content");
		
		when(repository.findById(id)).thenReturn(Optional.of(mockEntity));
		when(repository.save(mockEntity)).thenReturn(persistedEntity);
		when(mapper.toVO(persistedEntity)).thenReturn(mockVO);
		
		KnowledgeVO updatedKnowledge = service.updateById(id, mockVO);
		
		assertNotNull(updatedKnowledge);
		
		assertEquals(2, updatedKnowledge.getKey());
		assertEquals("2Title", updatedKnowledge.getTitle());
		assertEquals("2Content", updatedKnowledge.getContent());
		
		assertEquals("Software A", updatedKnowledge.getSoftware().getDescription());
		
		assertEquals("Name0", updatedKnowledge.getTags().get(0).getDescription());
		assertEquals("Name1", updatedKnowledge.getTags().get(1).getDescription());
		
		assertTrue(updatedKnowledge.getLinks().toString().contains("</v1/knowledge?pageNumber=0&pageSize=10&sortBy=title&direction=asc>;rel=\"knowledgeVOList\""));
	}
	
	@Test
	void testUpdateByIdWithResourceNotFoundException() {
		Long id = null;
		KnowledgeVO mockVO = KnowledgeMock.vo();
		
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
		KnowledgeVO mockVO = null;
		
		Exception exception = assertThrows(RequiredObjectIsNullException.class, () -> {
			service.updateById(id, mockVO);
		});
		
		String expectedMessage = "It is not possible to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertEquals(expectedMessage, actualMessage);
	}
	
	@Test
	void testDeleteById() {
		Knowledge entity = KnowledgeMock.entity(1L); 
		
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
