package br.com.hd.util.repository.v1;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class RepositoryUtil {
	
	public static boolean isParamValid(String param) {
		
		return !(param == null || param.isBlank() || param.isEmpty());
	}
	
	public static String createQueryList(String entityName, String alias, Pageable pageable) {
		
		Order order = pageable.getSort().get().toList().get(0);
		
		return "SELECT " + alias 
			+ " FROM " + entityName + " " + alias 
			+ " WHERE " + alias + "." + order.getProperty() +  " IN :paramList "
			+ " ORDER BY " + alias + "." + order.getProperty() + " " + order.getDirection();
	}
	
	public static String addCount(String alias) {
		
		return "SELECT COUNT(DISTINCT " + alias + ") ";
	}
	
	public static String addSelectDistinct(String alias, Pageable pageable) {
		
		String sortBy = pageable.getSort().get().toList().get(0).getProperty();
		
		return "SELECT DISTINCT " + alias + "." + sortBy + " ";
	}

	public static String addPageable(String alias, Pageable pageable) {
		
		String query = "";
		
		Order order = pageable.getSort().get().toList().get(0);
		
		query += "ORDER BY " + alias + "." + order.getProperty() + " " + order.getDirection() + " ";
		query += "LIMIT " + pageable.getPageSize() + " ";
		query += "OFFSET " + pageable.getOffset(); 
		
		return query;
	}

}
