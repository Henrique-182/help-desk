package br.com.hd.controllers.chat.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hd.data.vo.chat.message.v1.MessageCreationVO;
import br.com.hd.data.vo.chat.message.v1.MessageUpdateVO;
import br.com.hd.data.vo.chat.message.v1.MessageVO;
import br.com.hd.model.auth.v1.User;
import br.com.hd.services.chat.v1.MessageService;
import br.com.hd.util.controller.v1.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/v1/message")
public class MessageController {
	
	@Autowired
	private MessageService service;
	
	@Autowired
	private ControllerUtil util;
	@Operation(
		summary = "Finds a Message",
		description = "Finds a Message by Id",
		tags = {"Message"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = MessageVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping(path = "/{id}")
	public MessageVO findById(@PathVariable("id") Long id) {
		
		return service.findById(id);
	}
	
	@Operation(
		summary = "Creates a Message",
		description = "Creates a Message",
		tags = {"Message"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = MessageVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping
	public MessageVO create(@Valid @RequestBody MessageCreationVO data) {
		
		User currentUser = util.findUserByContext(SecurityContextHolder.getContext());
		
		return service.create(currentUser, data);
	}
	
	@Operation(
		summary = "Updates a Message",
		description = "Updates Message's Content by Id",
		tags = {"Message"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = MessageVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PatchMapping(path = "/{id}")
	public MessageVO updateById(@PathVariable("id") Long id, @Valid @RequestBody MessageUpdateVO data) {
		
		User currentUser = util.findUserByContext(SecurityContextHolder.getContext());
		
		return service.updateById(id, currentUser, data);
	}
	
	@Operation(
		summary = "Soft Deletes a Message",
		description = "Soft Deletes a Message by Id",
		tags = {"Message"},
		responses = {
			@ApiResponse(description = "Deleted", responseCode = "200", content = @Content(schema = @Schema(implementation = MessageVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@DeleteMapping(path = "/{id}")
	public MessageVO softDeleteById(@PathVariable("id") Long id) {
		
		User currentUser = util.findUserByContext(SecurityContextHolder.getContext());
		
		return service.softDeleteById(id, currentUser);
	}

}
