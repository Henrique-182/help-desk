package br.com.hd.integrationtests.repositories.knowledge.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import br.com.hd.model.knowledge.v1.Software;
import br.com.hd.repositories.knowledge.v1.SoftwareRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext
public class SoftwareRepositoryTest extends AbstractIntegrationTest {

	@Autowired
	private SoftwareRepository repository;
	
	@Test
	@Order(1)
	void testFindPageableByDescription() {
		
		String description = "software";
		Pageable pageable = PageRequest.of(0, 2, Sort.by(Direction.DESC, "description"));
		
		Page<Software> pageResult = repository.findPageableByDescriptionContainingIgnoreCase(description, pageable);
		
		assertEquals(2, pageResult.getSize());
		assertEquals(3, pageResult.getTotalElements());
		assertEquals(2, pageResult.getTotalPages());
		assertEquals(0, pageResult.getNumber());
		
		List<Software> resultList = pageResult.getContent();
		
		Software softwareZero = resultList.get(0);
		
		assertNotNull(softwareZero);
		
		assertEquals(3, softwareZero.getId());
		assertEquals("Software C", softwareZero.getDescription());
		
		Software softwareOne = resultList.get(1);
		
		assertNotNull(softwareOne);
		
		assertEquals(2, softwareOne.getId());
		assertEquals("Software B", softwareOne.getDescription());
		
	}
	
}
