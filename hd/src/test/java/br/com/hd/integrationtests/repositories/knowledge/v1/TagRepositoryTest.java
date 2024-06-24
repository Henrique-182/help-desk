package br.com.hd.integrationtests.repositories.knowledge.v1;

import static org.junit.Assert.assertNotNull;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.hd.integrationtests.testcontainers.v1.AbstractIntegrationTest;
import br.com.hd.model.knowledge.v1.Tag;
import br.com.hd.repositories.knowledge.v1.TagRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class TagRepositoryTest extends AbstractIntegrationTest {
	
	@Autowired
	private TagRepository repository;
	
	@Test
	@Order(1)
	void testFindPageableByDescription() {
		
		String description = "tag";
		Pageable pageable = PageRequest.of(0, 3, Sort.by(Direction.DESC, "description"));
		
		Page<Tag> pageResult = repository.findPageableByDescriptionContainingIgnoreCase(description, pageable);
		
		assertEquals(3, pageResult.getSize());
		assertEquals(6, pageResult.getTotalElements());
		assertEquals(2, pageResult.getTotalPages());
		assertEquals(0, pageResult.getNumber());
		
		List<Tag> resultList = pageResult.getContent();
		
		Tag tagZero = resultList.get(0);
		
		assertNotNull(tagZero);
		
		assertEquals(6, tagZero.getId());
		assertEquals("Tag F", tagZero.getDescription());
		
		Tag tagOne = resultList.get(1);
		
		assertNotNull(tagOne);
		
		assertEquals(5, tagOne.getId());
		assertEquals("Tag E", tagOne.getDescription());
	}

}
