package br.com.hd.controllers.chat.v1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.hd.data.vo.chat.room.v1.RoomCreationVO;
import br.com.hd.data.vo.chat.room.v1.RoomUpdateVO;
import br.com.hd.data.vo.chat.room.v1.RoomVO;
import br.com.hd.data.vo.chat.room.v1.RoomWrapperVO;
import br.com.hd.model.auth.v1.User;
import br.com.hd.model.chat.room.v1.RoomStatus;
import br.com.hd.services.chat.v1.RoomService;
import br.com.hd.util.controller.v1.ControllerUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Room", description = "Endpoints for Managing Rooms")
@RestController
@RequestMapping(path = "/v1/room")
public class RoomController {
	
	@Autowired
	private RoomService service;
	
	@Autowired
	private ControllerUtil util;
	
	@Operation(
		summary = "Finds a Room",
		description = "Finds a Room by Code",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping(path = "/{code}")
	public RoomVO findByCode(@PathVariable("code") Integer code) {
		
		return service.findByCode(code);
	}
	
	@Operation(
		summary = "Finds a Room",
		description = "Finds a Room by Sector and Status",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomWrapperVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping(path = "/bySector/{sectorKey}")
	public RoomWrapperVO findBySectorAndStatus(
		@PathVariable("sectorKey") Long sectorKey,
		@RequestParam(name = "status1", required = true, defaultValue = "") RoomStatus status1,
		@RequestParam(name = "status2", required = false, defaultValue = "") RoomStatus status2,
		@RequestParam(name = "status3", required = false, defaultValue = "") RoomStatus status3,
		@RequestParam(name = "status4", required = false, defaultValue = "") RoomStatus status4,
		@RequestParam(name = "status5", required = false, defaultValue = "") RoomStatus status5
	) {
		
		List<RoomStatus> statusList = returnStatusList(status1, status2, status3, status4, status5);
		
		List<RoomVO> rooms = service.findBySectorAndStatus(sectorKey, statusList);
		
		return new RoomWrapperVO(rooms);
	}
	
	@Operation(
		summary = "Finds a Room",
		description = "Finds a Room by Sector, Employee and Status",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomWrapperVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping(path = "/bySectorAndEmployee/{sectorId}")
	public RoomWrapperVO findBySectorAndEmployeeAndStatus(
		@PathVariable("sectorId") Long sectorId,
		@RequestParam(name = "status1", required = true, defaultValue = "") RoomStatus status1,
		@RequestParam(name = "status2", required = false, defaultValue = "") RoomStatus status2,
		@RequestParam(name = "status3", required = false, defaultValue = "") RoomStatus status3,
		@RequestParam(name = "status4", required = false, defaultValue = "") RoomStatus status4,
		@RequestParam(name = "status5", required = false, defaultValue = "") RoomStatus status5
	) {
		
		List<RoomStatus> statusList = returnStatusList(status1, status2, status3, status4, status5);
		
		User currentUser = util.findUserByContext(SecurityContextHolder.getContext());
		
		List<RoomVO> rooms = service.findBySectorAndEmployeeAndStatus(sectorId, currentUser.getId(), statusList);
		
		return new RoomWrapperVO(rooms);
	}
	
	@Operation(
		summary = "Finds a Room",
		description = "Finds a Room by Sector, Customer and Status",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomWrapperVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@GetMapping(path = "/bySectorAndCustomerAndStatus/{sectorId}")
	public RoomWrapperVO findBySectorAndCustomerAndStatus(
		@PathVariable("sectorId") Long sectorId,
		@RequestParam(name = "status1", required = true, defaultValue = "") RoomStatus status1,
		@RequestParam(name = "status2", required = false, defaultValue = "") RoomStatus status2,
		@RequestParam(name = "status3", required = false, defaultValue = "") RoomStatus status3,
		@RequestParam(name = "status4", required = false, defaultValue = "") RoomStatus status4,
		@RequestParam(name = "status5", required = false, defaultValue = "") RoomStatus status5
	) {
		
		List<RoomStatus> statusList = returnStatusList(status1, status2, status3, status4, status5);
		
		User currentUser = util.findUserByContext(SecurityContextHolder.getContext());
		
		List<RoomVO> rooms = service.findBySectorAndCustomerAndStatus(sectorId, currentUser.getId(), statusList);
		
		return new RoomWrapperVO(rooms);
	}
	
	@Operation(
		summary = "Creates a Room",
		description = "Creates a Room by Customer",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping("/byCustomer")
	public RoomVO createByCustomer(@Valid @RequestBody RoomCreationVO data) {
		return service.createByCustomer(data);
	}
	
	@Operation(
		summary = "Creates a Room",
		description = "Creates a Room by Employee",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Created", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PostMapping("/byEmployee")
	public RoomVO createByEmployee(@Valid @RequestBody RoomCreationVO data) {

		User currentUser = util.findUserByContext(SecurityContextHolder.getContext());
		
		return service.createByEmployee(currentUser, data);
	}
	
	@Operation(
		summary = "Updates a Room",
		description = "Updates Room's Reason, Solution and Priority by Code",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PatchMapping("/reasonAndSolutionAndPriority/{code}")
	public RoomVO updateReasonAndSolutionAndPriority(@PathVariable("code") Integer code, @Valid @RequestBody RoomUpdateVO data) {
		
		return service.updateReasonAndSolutionAndPriority(code, data);
	}
	
	@Operation(
		summary = "Updates a Room",
		description = "Updates Room's Status by Code",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PatchMapping("/status/{code}")
	public RoomVO updateStatusByCode(@PathVariable("code") Integer code, @Valid @RequestBody RoomUpdateVO data) {
		
		return service.updateStatusByCode(code, data);
	}
	
	@Operation(
		summary = "Updates a Room",
		description = "Updates Room's Employee And Status by Code",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PatchMapping("/enterRoom/{code}")
	public RoomVO employeeEnterRoomByCode(@PathVariable("code") Integer code) {

		User currentUser = util.findUserByContext(SecurityContextHolder.getContext());
		
		return service.employeeEnterRoomByCode(code, currentUser);
	}
	
	@Operation(
		summary = "Updates a Room",
		description = "Updates Room's Employee, Sector And Status by Code",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = RoomVO.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@PatchMapping("/transferRoom/{code}")
	public RoomVO transferRoomByCode(@PathVariable("code") Integer code, @RequestBody RoomUpdateVO data) {
		
		return service.transferRoomByCode(code, data);
	}
	
	@Operation(
		summary = "Deletes a Room",
		description = "Deletes a Room",
		tags = {"Room"},
		responses = {
			@ApiResponse(description = "Deleted", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Forbidden", responseCode = "403", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Interval Server Error", responseCode = "500", content = @Content),
		}
	)
	@DeleteMapping("/{code}")
	public ResponseEntity<?> deleteByCode(@PathVariable("code") Integer code) {
		
		service.deleteByCode(code);
		
		return ResponseEntity.noContent().build();
	}
	
	private List<RoomStatus> returnStatusList(RoomStatus... statusArray) {
		
		List<RoomStatus> statusList = new ArrayList<>();
		
		for (int i = 0; i < statusArray.length; i++) {
			if (statusArray[i] != null) statusList.add(statusArray[i]);
		}
		
		return statusList;
	}

}
