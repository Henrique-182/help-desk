package br.com.hd.repositories.knowledge.v1;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.hd.model.knowledge.v1.Knowledge;
import br.com.hd.util.repository.v1.RepositoryUtil;
import jakarta.persistence.EntityManager;

@Repository
public class KnowledgeCustomRepository {
	
	private static final String ENTITY_NAME = "Knowledge";
	private static final String ALIAS = "KNOW";

	private EntityManager manager;

	public KnowledgeCustomRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public Map<String, Object> findCustomPageable(Map<String, Object> queryParams, Pageable pageable) {
		
		String title = (String) queryParams.get("title");
		String content = (String) queryParams.get("content");
		String softwareDescription = (String) queryParams.get("softwareDescription");
		String tagDescription = (String) queryParams.get("tagDescription");
		
		String query = "FROM Knowledge KNOW ";
		String condition = "WHERE ";
		
		if (RepositoryUtil.isParamValid(title)) {
			query += condition += "KNOW.title ILIKE '%" + title + "%' ";
			condition = "AND ";
		}
		
		if (RepositoryUtil.isParamValid(content)) {
			query += condition += "KNOW.content ILIKE '%" + content + "%' ";
			condition = "AND ";
		}
		
		if (RepositoryUtil.isParamValid(softwareDescription)) {
			query += condition += "KNOW.software.description ILIKE '%" + softwareDescription + "%' ";
			condition = "AND ";
		}
		
		if (RepositoryUtil.isParamValid(tagDescription)) {
			query += condition += "ELEMENT(KNOW.tags).description ILIKE '%" + tagDescription + "%' ";
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
					.createQuery(queryResultList, Knowledge.class)
					.setParameter("paramList", paramList)
					.getResultList(),
				"totalElements", 
				manager
					.createQuery(queryTotalElements, Long.class)
					.getSingleResult()
			);
	}
}
