package br.com.hd.controllers.knowledge.v1;

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

import br.com.hd.data.vo.knowledge.v1.TagVO;
import br.com.hd.services.knowledge.v1.TagService;
import br.com.hd.util.controller.v1.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tag", description = "Endpoints for Managing Tags")
@RestController
@RequestMapping(path = "/v1/tag")
public class TagController {

	@Autowired
	private TagService service;

	@Autowired
	private ControllerUtil util;

	@Operation(
		summary = "Finds All Tags", 
		description = "Finds All Tags", 
		tags = { "Tag" }, 
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					mediaType = "application/json", 
					array = @ArraySchema(
						schema = @Schema(
							implementation = TagVO.class
						)
					)
				)
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	@GetMapping
	public PagedModel<EntityModel<TagVO>> findCustomPageable(
		@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
		@RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
		@RequestParam(name = "sortBy", required = false, defaultValue = "description") String sortBy,
		@RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
		@RequestParam(name = "description", required = false, defaultValue = " ") String description
	) {

		Pageable pageable = util.createPageable(pageNumber, pageSize, sortBy, direction);

		return service.findCustomPageable(description, pageable);
	}

	@Operation(
		summary = "Finds a Tag", 
		description = "Finds a Tag By Id", 
		tags = { "Tag" }, 
		responses = {
			@ApiResponse(
				description = "Success", 
				responseCode = "200", 
				content = @Content(
					schema = @Schema(
						implementation = TagVO.class
					)
				)
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content) 
		}
	)
	@GetMapping(path = "/{id}")
	public TagVO findById(@PathVariable("id") Long id) {
		return service.findById(id);
	}

	@Operation(
		summary = "Creates a Tag", 
		description = "Creates a Tag", 
		tags = { "Tag" }, 
		responses = {
			@ApiResponse(
				description = "Created", 
				responseCode = "200", 
				content = @Content(
					schema = @Schema(
						implementation = TagVO.class
					)
				)
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	@PostMapping
	public TagVO create(@RequestBody TagVO data) {
		return service.create(data);
	}

	@Operation(
		summary = "Updates a Tag", 
		description = "Updates a Tag", 
		tags = { "Tag" }, 
		responses = {
			@ApiResponse(
				description = "Updated", 
				responseCode = "200", 
				content = @Content(
					schema = @Schema(
						implementation = TagVO.class
					)
				)
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content), 
		}
	)
	@PutMapping(path = "/{id}")
	public TagVO updateById(@PathVariable("id") Long id, @RequestBody TagVO data) {
		return service.updateById(id, data);
	}

	@Operation(
		summary = "Deletes a Tag",
		description = "Deletes a Tag",
		tags = {"Tag"},
		responses = {
			@ApiResponse(
				description = "Deleted", 
				responseCode = "204", 
				content = @Content(
					schema = @Schema(
						implementation = TagVO.class
					)
				)
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content)
		}
	)
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id) {
		service.deleteById(id);

		return ResponseEntity.noContent().build();
	}

}
