package br.com.hd.services.audit.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hd.model.auth.v1.User;
import br.com.hd.repositories.auth.v1.UserRepository;

@Service
public class UserAuditService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User findUserByUsername(String username) {
		
		return userRepository.findByUsername(username);
	}
	
}
