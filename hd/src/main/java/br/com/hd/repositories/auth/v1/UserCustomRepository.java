package br.com.hd.repositories.auth.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.hd.model.auth.v1.User;
import br.com.hd.util.repository.v1.RepositoryUtil;
import jakarta.persistence.EntityManager;

@Repository
public class UserCustomRepository {

	@Autowired
	private EntityManager manager;
	
	public Map<String, Object> findCustomPageable(Map<String, Object> queryParams, Pageable pageable) {
		
		String name = (String) queryParams.get("name");
		String permission = (String) queryParams.get("permission");
		
		String queryString = "SELECT USER FROM User USER ";
		String condition = "WHERE ";
		
		if (!name.isBlank()) {
			queryString += condition + "(USER.username ILIKE '%" + name + "%' OR USER.fullname ILIKE '%" + name + "%') ";
			condition = "AND ";
		}
		
		if (!permission.isBlank()) {
			queryString += condition + "ELEMENT(USER.permissions).description ILIKE '%" + permission + "%' ";
			condition = "AND ";
		}
		
		String queryStringPageable = RepositoryUtil.addPageable(queryString, pageable, "USER");
		String queryStringCount = RepositoryUtil.replaceToCount(queryString, "USER");
		
		var queryPageable = manager.createQuery(queryStringPageable, User.class);
		var queryCount = manager.createQuery(queryStringCount, Long.class);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultList", queryPageable.getResultList());
		resultMap.put("totalElements", queryCount.getSingleResult());
		
		return resultMap;
	}
	
}
