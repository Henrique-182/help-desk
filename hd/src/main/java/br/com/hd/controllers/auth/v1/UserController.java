package br.com.hd.controllers.auth.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.hd.data.vo.auth.v1.AccountCredentialsVO;
import br.com.hd.data.vo.auth.v1.UserVO;
import br.com.hd.services.auth.jwt.v1.UserService;
import br.com.hd.util.controller.v1.ControllerUtil;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v1/user")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private ControllerUtil util;

	@GetMapping
	public PagedModel<EntityModel<UserVO>> findCustomPageable(
		@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
		@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
		@RequestParam(name = "sortBy", required = false, defaultValue = "username") String sortBy,
		@RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
		@RequestParam(name = "name", required = false, defaultValue = " ") String name,
		@RequestParam(name = "permission", required = false, defaultValue = " ") String permission	
	) {
		Pageable pageable = util.createPageable(pageNumber, pageSize, sortBy, direction);
		
		Map<String, Object> params = new HashMap<>();
		params.put("name", name);
		params.put("permission", permission);
		
		return service.findCustomPageable(params, pageable);
	}
	
	@GetMapping(path = "/{id}")
	public UserVO findById(@PathVariable("id") Integer id) {
		
		return service.findById(id);
	}
	
	@PostMapping
	public UserVO create(@Valid @RequestBody AccountCredentialsVO data) {
		
		return service.create(data);
	}
	
	@PutMapping(path = "/{id}")
	public UserVO updateById(@PathVariable("id") Integer id, @RequestBody UserVO data) {
		
		return service.updateById(id, data);
	}
	
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Integer id) {
		
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
}
