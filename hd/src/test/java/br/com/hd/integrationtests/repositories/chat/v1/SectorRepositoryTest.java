package br.com.hd.integrationtests.repositories.chat.v1;

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
import br.com.hd.model.chat.v1.Sector;
import br.com.hd.repositories.chat.v1.SectorCustomRepository;
import br.com.hd.repositories.chat.v1.SectorRepository;
import jakarta.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class SectorRepositoryTest extends AbstractIntegrationTest {
	
	@Autowired
	private EntityManager manager;
	
	@Autowired
	private SectorRepository repository;
	
	private SectorCustomRepository customRepository;
	
	@Test
	@Order(1)
	void testFindSectorsByCustomer() {
		
		Long userId = 3L;
		
		List<Sector> entityList = repository.findSectorsByCustomer(userId);
		
		assertNotNull(entityList);
		
		Sector sectorZero = entityList.get(0);
		
		assertEquals(1, sectorZero.getId());
		assertEquals("Sector A", sectorZero.getDescription());
		
		Sector sectorOne = entityList.get(1);
		
		assertEquals(2, sectorOne.getId());
		assertEquals("Sector B", sectorOne.getDescription());
	}
	
	@Test
	@Order(2)
	void testFindSectorsByEmployee() {
		
		Long userId = 1L;
		
		List<Sector> entityList = repository.findSectorsByEmployee(userId);
		
		assertNotNull(entityList);
		
		Sector sectorZero = entityList.get(0);
		
		assertEquals(1, sectorZero.getId());
		assertEquals("Sector A", sectorZero.getDescription());
		
		Sector sectorOne = entityList.get(1);
		
		assertEquals(2, sectorOne.getId());
		assertEquals("Sector B", sectorOne.getDescription());
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	@Order(3)
	void testFindCustomPageable() {
		
		customRepository = new SectorCustomRepository(manager);
		
		Map<String, Object> queryParams = new HashMap<>();
		queryParams.put("description", "sector");
		queryParams.put("customerName", " ");
		queryParams.put("employeeName", " ");
		
		Pageable pageable = PageRequest.of(0, 3, Sort.by(Direction.DESC, "id"));
		
		Map<String, Object> resultMap = customRepository.findCustomPageable(queryParams, pageable);
		
		assertNotNull(resultMap);
		
		List<Sector> resultList = (List<Sector>) resultMap.get("resultList");
		
		assertNotNull(resultList);
		
		Sector sectorZero = resultList.get(0);
		
		assertNotNull(sectorZero);
		
		assertEquals(3, sectorZero.getId());
		assertEquals("Sector C", sectorZero.getDescription());
		assertEquals("COMMON_USER", sectorZero.getCustomers().get(0).getUsername());
		assertEquals("ADM", sectorZero.getEmployees().get(0).getUsername());
		
		Sector sectorOne = resultList.get(1);
		
		assertNotNull(sectorOne);
		
		assertEquals(2, sectorOne.getId());
		assertEquals("Sector B", sectorOne.getDescription());
		assertEquals("COMMON_USER", sectorOne.getCustomers().get(0).getUsername());
		assertEquals("ADM", sectorOne.getEmployees().get(0).getUsername());
		
		Long totalElements = (Long) resultMap.get("totalElements");
		
		assertEquals(3, totalElements);
	}

}
