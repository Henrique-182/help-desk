package br.com.hd.integrationtests.repositories.knowledge.v1;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.hd.model.knowledge.v1.Knowledge;
import br.com.hd.repositories.knowledge.v1.KnowledgeCustomRepository;
import jakarta.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class KnowledgeRepositoryTest extends AbstractIntegrationTest {
	
	@Autowired
	private EntityManager manager;
	
	private KnowledgeCustomRepository customRepository;
	
	@SuppressWarnings("unchecked")
	@Test
	@Order(1)
	void testFindCustomPageable() {
		
		customRepository = new KnowledgeCustomRepository(manager);
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("title", "title");
		queryParams.put("content", "content");
		queryParams.put("softwareDescription", "software");
		queryParams.put("tagDescription", "tag");
		
		Pageable pageable = PageRequest.of(0, 3, Sort.by(Direction.DESC, "id"));
		
		Map<String, Object> resultMap = customRepository.findCustomPageable(queryParams, pageable);
		
		assertNotNull(resultMap);
		
		List<Knowledge> resultList = (List<Knowledge>) resultMap.get("resultList");
		
		assertNotNull(resultList);
		
		Knowledge knowledgeZero = resultList.get(0);
		
		assertNotNull(knowledgeZero);
		
		assertEquals(6, knowledgeZero.getId());
		assertEquals("Title Knowledge CC", knowledgeZero.getTitle());
		assertEquals("Content Knowledge CC", knowledgeZero.getContent());
		assertEquals("Software C", knowledgeZero.getSoftware().getDescription());
		assertEquals("Tag A", knowledgeZero.getTags().get(0).getDescription());
		
		Knowledge knowledgeOne = resultList.get(1);
		
		assertNotNull(knowledgeOne);
		
		assertEquals(5, knowledgeOne.getId());
		assertEquals("Title Knowledge C", knowledgeOne.getTitle());
		assertEquals("Content Knowledge C", knowledgeOne.getContent());
		assertEquals("Software C", knowledgeOne.getSoftware().getDescription());
		assertEquals("Tag D", knowledgeOne.getTags().get(0).getDescription());
		
		Long totalElements = (Long) resultMap.get("totalElements");
		
		assertEquals(5, totalElements);
	}

}
