package br.com.hd.repositories.chat.v1;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.hd.model.chat.sector.v1.Sector;
import br.com.hd.util.repository.v1.RepositoryUtil;
import jakarta.persistence.EntityManager;

@Repository
public class SectorCustomRepository {
	
	private static final String ENTITY_NAME = "Sector";
	private static final String ALIAS = "SECT";
	
	private EntityManager manager;

	public SectorCustomRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public Map<String, Object> findCustomPageable(Map<String, Object> queryParams, Pageable pageable) {
		
		String description = (String) queryParams.get("description");
		String customerName = (String) queryParams.get("customerName");
		String employeeName = (String) queryParams.get("employeeName");
		
		String query = "FROM " + ENTITY_NAME + " " + ALIAS + " ";
		String condition = "WHERE ";
		
		if (RepositoryUtil.isParamValid(description)) {
			query += condition += ALIAS + ".description ILIKE '%" + description + "%' ";
			condition = "AND ";
		}
		
		if (RepositoryUtil.isParamValid(customerName)) {
			query += condition += "ELEMENT(" + ALIAS + ".customers).fullname ILIKE '%" + customerName + "%' ";
			condition = "AND ";
		}
		
		if (RepositoryUtil.isParamValid(employeeName)) {
			query += condition += "ELEMENT(" + ALIAS + ".employees).fullname ILIKE '%" + employeeName + "%' ";
			condition = "AND ";
		}
		
		String queryParamList = 
				RepositoryUtil.addSelectDistinct(ALIAS, pageable)
				+ query
				+ RepositoryUtil.addPageable(ALIAS, pageable);
		
		String queryResultList = RepositoryUtil.createQueryList(ENTITY_NAME, ALIAS, pageable);
		 
		String queryTotalElements = 
				RepositoryUtil.addCount(ALIAS)
				+ query;
		
		List<Object> paramList = manager.createQuery(queryParamList, Object.class).getResultList();
		
		return Map.of(
				"resultList", 
				manager
					.createQuery(queryResultList, Sector.class)
					.setParameter("paramList", paramList)
					.getResultList(),
				"totalElements", 
				manager
					.createQuery(queryTotalElements, Long.class)
					.getSingleResult()
			);
	 }

}
