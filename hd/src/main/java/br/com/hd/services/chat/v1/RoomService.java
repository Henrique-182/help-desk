package br.com.hd.services.chat.v1;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hd.controllers.chat.v1.RoomController;
import br.com.hd.data.vo.chat.room.v1.RoomCreationVO;
import br.com.hd.data.vo.chat.room.v1.RoomUpdateVO;
import br.com.hd.data.vo.chat.room.v1.RoomVO;
import br.com.hd.exceptions.generic.v1.InvalidArgumentsException;
import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.chat.v1.RoomMapper;
import br.com.hd.model.auth.v1.User;
import br.com.hd.model.chat.room.v1.Room;
import br.com.hd.model.chat.room.v1.RoomPriority;
import br.com.hd.model.chat.room.v1.RoomStatus;
import br.com.hd.model.chat.room.v1.SectorRoom;
import br.com.hd.model.chat.room.v1.UserRoom;
import br.com.hd.repositories.chat.v1.RoomRepository;
import br.com.hd.util.service.v1.ServiceUtil;
import jakarta.transaction.Transactional;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private ServiceUtil util;
	
	@Autowired
	private RoomMapper mapper;
	
	public RoomVO findByCode(Integer code) {
		
		Room persistedRoom = roomRepository.findByCode(code)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the code (" + code + ") !"));
		
		return addLinkSelfRel(mapper.toVO(persistedRoom));
	}
	
	@Transactional
	public List<RoomVO> findBySectorAndStatus(Long sectorKey, List<RoomStatus> statusList) {
		
		util.sectorExists(sectorKey);
		
		List<RoomVO> voList = mapper
				.toVOList(
					roomRepository.findBySectorKeyAndStatusIn(sectorKey, statusList)
				)
				.stream()
				.map(r -> addLinkSelfRel(r))
				.toList();
		
		return voList;
	}
	
	@Transactional
	public List<RoomVO> findBySectorAndEmployeeAndStatus(Long sectorId, Long employeeId, List<RoomStatus> statusList) {
		
		util.sectorExists(sectorId);
		
		util.employeeExists(employeeId);
		
		List<RoomVO> voList = mapper
				.toVOList(
					roomRepository.findBySectorKeyAndEmployeeKeyAndStatusIn(sectorId, employeeId, statusList)
				)
				.stream()
				.map(r -> addLinkSelfRel(r))
				.toList();

		return voList;
	}
	
	@Transactional
	public List<RoomVO> findBySectorAndCustomerAndStatus(Long sectorId, Long customerId, List<RoomStatus> statusList) {
		
		util.sectorExists(sectorId);
		
		util.customerExists(customerId);
		
		List<RoomVO> voList = mapper
				.toVOList(
					roomRepository.findBySectorKeyAndCustomerKeyAndStatusIn(sectorId, customerId, statusList)
				)
				.stream()
				.map(r -> addLinkSelfRel(r))
				.toList();

		return voList;
	}
	
	@Transactional
	public RoomVO createByCustomer(User currentUser, RoomCreationVO data) {
		
		Room room = new Room();
		room.setCustomer(
			util.returnCustomerIfExists(currentUser.getId())
		);
		room.setSector(
			util.returnSectorIfExists(data.getSectorKey())
		);
		room.setCode(returnMaxRoomCode() + 1);
		room.setStatus(RoomStatus.Open);
		room.setCreateDatetime(new Date());
		
		RoomPriority priority = util.returnPriorityIfExists("Normal");
		room.setPriority(priority);
		
		Room createdRoom = roomRepository.save(room);
		
		return addLinkSelfRel(mapper.toVO(createdRoom));
	}
	
	@Transactional
	public RoomVO createByEmployee(User currentUser, RoomCreationVO data) {
		
		if (currentUser.getId() == data.getCustomerKey()) throw new InvalidArgumentsException("It is not possible to create the room. Employee and Customer are equal !");
		
		Room room = new Room();
		
		room.setEmployee(
			util.returnEmployeeIfExists(currentUser.getId())
		);
		room.setCustomer(
			util.returnCustomerIfExists(data.getCustomerKey())
		);
		room.setSector(
			util.returnSectorIfExists(data.getSectorKey())
		);
		
		room.setCode(returnMaxRoomCode() + 1);
		room.setStatus(RoomStatus.Chatting);
		room.setCreateDatetime(new Date());
		
		RoomPriority priority = util.returnPriorityIfExists(data.getPriority());
		room.setPriority(priority);
		
		Room createdRoom = roomRepository.save(room);
		 
		return addLinkSelfRel(mapper.toVO(createdRoom));
	}
	
	@Transactional
	public RoomVO updateReasonAndSolutionAndPriority(Integer code, RoomUpdateVO data) {
		
		Room persistedRoom = roomRepository.findByCode(code)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the code (" + code + ") !"));
		
		RoomPriority priority = util.returnPriorityIfExists(data.getPriority());
		persistedRoom.setPriority(priority);
		persistedRoom.setReason(data.getReason());
		persistedRoom.setSolution(data.getSolution());		
		
		Room updatedRoom = roomRepository.save(persistedRoom);
		
		return addLinkSelfRel(mapper.toVO(updatedRoom));
	}
	
	@Transactional
	public RoomVO updateStatusByCode(Integer code, RoomUpdateVO data) {
		
		Room persistedRoom = roomRepository.findByCode(code)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the code (" + code + ") !"));
		
		persistedRoom.setStatus(data.getStatus());
		
		if (data.getStatus() == RoomStatus.Closed) {
			persistedRoom.setCloseDatetime(new Date());
			persistedRoom.setReason(data.getReason());
			persistedRoom.setSolution(data.getSolution());
		}
		
		Room updatedRoom = roomRepository.save(persistedRoom);
		
		return addLinkSelfRel(mapper.toVO(updatedRoom));
	}
	
	@Transactional
	public RoomVO employeeEnterRoomByCode(Integer code, User currentUser) {

		Room persistedRoom = roomRepository.findByCode(code)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the code (" + code + ") !"));

		persistedRoom.setEmployee(new UserRoom(currentUser.getId()));

		persistedRoom.setStatus(RoomStatus.Chatting);

		Room updatedRoom = roomRepository.save(persistedRoom);

		return addLinkSelfRel(mapper.toVO(updatedRoom));
	}
	
	@Transactional
	public RoomVO transferRoomByCode(Integer code, RoomUpdateVO data) {
		
		Room persistedRoom = roomRepository.findByCode(code)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the code (" + code + ") !"));
		
		if (util.employeeExists(data.getEmployeeKey())) {
			persistedRoom.setEmployee(new UserRoom(data.getEmployeeKey()));
		}
		
		if (util.sectorExists(data.getSectorKey())) {
			persistedRoom.setSector(new SectorRoom((data.getSectorKey())));
		}
		
		persistedRoom.setStatus(RoomStatus.Transferred);
		
		Room updatedRoom = roomRepository.save(persistedRoom);
		
		return addLinkSelfRel(mapper.toVO(updatedRoom));
	}
	
	@Transactional
	public void deleteByCode(Integer code) {
		
		Room persistedRoom = roomRepository.findByCode(code)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for the code (" + code + ") !"));
		
		roomRepository.delete(persistedRoom);
	}
	
	private RoomVO addLinkSelfRel(RoomVO vo) {
		return vo.add(linkTo(methodOn(RoomController.class).findByCode(vo.getCode())).withSelfRel());
	}
	
	private Integer returnMaxRoomCode() {
		
		return roomRepository.findMaxCode();
	}
	
}
