package br.com.hd.util.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import br.com.hd.model.auth.v1.User;
import br.com.hd.services.audit.v1.UserAuditService;

@Service
public class ControllerUtil {
	
	@Autowired
	private UserAuditService service;

	public Pageable createPageable(Integer pageNumber, Integer pageSize, String sortBy, String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		return PageRequest.of(pageNumber, pageSize, Sort.by(sortDirection, sortBy));
	}
	
	public User findUserByContent(SecurityContext context) {
		
		return service.findUserByUsername(context.getAuthentication().getName());
	}
	
}
