package br.com.hd.util.repository.v1;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class RepositoryUtil {
	
	public static String replaceToCount(String query, String alias) {
		
		return query.replace("SELECT " + alias, "SELECT COUNT(" + alias + ")");
	}

	public static String addPageable(String query, Pageable pageable, String alias) {
		
		Order order = pageable.getSort().get().toList().get(0);
		int size = pageable.getPageSize();
		long offset = pageable.getOffset();
		
		query += "ORDER BY " + alias + "." + order + " " + order.getDirection() + " ";
		query += "LIMIT " + size + " ";
		query += "OFFSET " + offset; 
		
		return query;
	}

}
