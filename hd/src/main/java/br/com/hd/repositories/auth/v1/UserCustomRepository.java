package br.com.hd.repositories.auth.v1;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.hd.model.auth.v1.User;
import br.com.hd.util.repository.v1.RepositoryUtil;
import jakarta.persistence.EntityManager;

@Repository
public class UserCustomRepository {
	
	private static final String ENTITY_NAME = "User";
	private static final String ALIAS = "USER";

	private EntityManager manager;
	
	public UserCustomRepository(EntityManager manager) {
		this.manager = manager;
	}

	public Map<String, Object> findCustomPageable(Map<String, Object> queryParams, Pageable pageable) {
		
		String name = (String) queryParams.get("name");
		String permission = (String) queryParams.get("permission");
		
		String query = "FROM User USER ";
		String condition = "WHERE ";
		
		if (!name.isBlank()) {
			query += condition + "(USER.username ILIKE '%" + name + "%' OR USER.fullname ILIKE '%" + name + "%') ";
			condition = "AND ";
		}
		
		if (!permission.isBlank()) {
			query += condition + "ELEMENT(USER.permissions).description ILIKE '%" + permission + "%' ";
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
					.createQuery(queryResultList, User.class)
					.setParameter("paramList", paramList)
					.getResultList(),
				"totalElements",
				manager
					.createQuery(queryTotalElements, Long.class)
					.getSingleResult()
			);
	}
	
}
