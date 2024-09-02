package br.com.hd.controllers.chat.v1;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.hd.data.vo.chat.sector.v1.SectorVO;
import br.com.hd.data.vo.chat.sector.v1.SimpleSectorVO;
import br.com.hd.data.vo.chat.sector.v1.SimpleSectorWrapperVO;
import br.com.hd.model.auth.v1.User;
import br.com.hd.services.chat.v1.SectorService;
import br.com.hd.util.controller.v1.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Sector", description = "Endpoints for Managing Sectors")
@RestController
@RequestMapping(path = "/v1/sector")
public class SectorController {
	
	@Autowired
	private SectorService service;
	
	@Autowired
	private ControllerUtil util;
	
	@Operation(
		summary = "Finds All Sectors",
		description = "Finds All Sectors",
		tags = {"Sector"},
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = SectorVO.class)))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)	
	@GetMapping
	public PagedModel<EntityModel<SectorVO>> findCustomPageable(
		@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
		@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
		@RequestParam(name = "sortBy", required = false, defaultValue = "description") String sortBy,
		@RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
		@RequestParam(name = "description", required = false, defaultValue = " ") String description,
		@RequestParam(name = "customerName", required = false, defaultValue = " ") String customerName,
		@RequestParam(name = "employeeName", required = false, defaultValue = " ") String employeeName
	) {
		
		Pageable pageable = util.createPageable(pageNumber, pageSize, sortBy, direction);
		
		Map<String, Object> params = new HashMap<>();
		params.put("description", description);
		params.put("customerName", customerName);
		params.put("employeeName", employeeName);
		
		return service.findCustomPageable(params, pageable);
	}
	
	@Operation(
		summary = "Finds a Sector",
		description = "Finds a Sector by Id",
		tags = {"Sector"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = SectorVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)	
	@GetMapping(path = "/{id}")
	public SectorVO findById(@PathVariable("id") Long id) {
		
		return service.findById(id);
	}
	
	@Operation(
		summary = "Finds Sectors",
		description = "Finds Sectors by User",
		tags = {"Sector"},
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json",
					array = @ArraySchema(schema = @Schema(implementation = SimpleSectorVO.class)))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)	
	@GetMapping(path = "/byUser")
	public SimpleSectorWrapperVO findSectorsByUsername() {
		
		User currentUser = util.findUserByContext(SecurityContextHolder.getContext());
		
		return new SimpleSectorWrapperVO(service.findSectorsByUser(currentUser));
	}
	
	@Operation(
		summary = "Creates a Sector",
		description = "Creates a Sector",
		tags = {"Sector"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = SectorVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping
	public SectorVO create(@RequestBody SectorVO data) {
		return service.create(data);
	}
	
	@Operation(
		summary = "Updates a Sector",
		description = "Updates a Sector",
		tags = {"Sector"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = SectorVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PutMapping(path = "/{id}")
	public SectorVO updateById(@PathVariable("id") Long id, @RequestBody SectorVO data) {
		return service.updateById(id, data);
	}
	
	@Operation(
		summary = "Deletes a Sector",
		description = "Deletes a Sector",
		tags = {"Sector"},
		responses = {
			@ApiResponse(description = "Deleted", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		service.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}

}
