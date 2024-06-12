package br.com.hd.services.auth.jwt.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.stereotype.Service;

import br.com.hd.data.vo.auth.v1.AccountCredentialsVO;
import br.com.hd.data.vo.auth.v1.UserVO;
import br.com.hd.exceptions.generic.v1.RequiredObjectIsNullException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.auth.v1.UserMapper;
import br.com.hd.model.auth.v1.Permission;
import br.com.hd.model.auth.v1.User;
import br.com.hd.repositories.auth.v1.UserCustomRepository;
import br.com.hd.repositories.auth.v1.UserRepository;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserCustomRepository customRepository;
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PagedResourcesAssembler<UserVO> assembler;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		
		if (user != null) return user;
		else throw new UsernameNotFoundException("Username (" + username + ") not found!");
	}
	
	@SuppressWarnings("unchecked")
	public PagedModel<EntityModel<UserVO>> findCustomPageable(Map<String, Object> params, Pageable pageable) {
		
		Map<String, Object> resultMap = customRepository.findCustomPageable(params, pageable);
		
		List<UserVO> voList =  mapper.toVOList((List<User>) resultMap.get("resultList"));
		
		return assembler.toModel(
				new PageImpl<>(
						voList, 
						pageable, 
						(long) resultMap.get("totalElements")
					)
				);
	}
	
	public UserVO findById(Integer id) {
		User persistedEntity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ")!"));
		
		return mapper.toVO(persistedEntity);
	}
	
	public UserVO create(AccountCredentialsVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		User user = new User();
		user.setUsername(data.getUsername());
		user.setFullname(data.getFullname());
		
		String password = createHash(data.getPassword());
		if (password.startsWith("{pbkdf2}")) password = password.substring("{pbkdf2}".length());
		user.setPassword(password);
		
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		
		List<Permission> permissions = new ArrayList<>();
		permissions.add(new Permission(3)); // COMMON_USER
		user.setPermissions(permissions);
		
		User createdUser = repository.save(user);
		
		return mapper.toVO(createdUser);
	}
	
	public UserVO updateById(Integer id, UserVO data) {
		if (data == null) throw new RequiredObjectIsNullException();
		
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		entity.setFullname(data.getFullname());
		entity.setAccountNonExpired(data.getAccountNonExpired());
		entity.setAccountNonLocked(data.getAccountNonLocked());
		entity.setCredentialsNonExpired(data.getCredentialsNonExpired());
		entity.setEnabled(data.getEnabled());
		entity.setPermissions(data.getPermissions());
		
		User updatedEntity = repository.save(entity);
		
		return mapper.toVO(updatedEntity);
	}
	
	public void deleteById(Integer id) {
		
		User entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for the id (" + id + ") !"));
		
		repository.delete(entity);
	}

	private static String createHash(String password) {
		Map<String, PasswordEncoder> encoders = new HashMap<>();

		encoders.put("pbkdf2", new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));

		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);

		passwordEncoder.setDefaultPasswordEncoderForMatches(new Pbkdf2PasswordEncoder("", 8, 185000, SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256));

		return passwordEncoder.encode(password);
	}

}
