package br.com.hd.util.service.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.hd.exceptions.generic.v1.ResourceNotFoundException;
import br.com.hd.mappers.auth.v1.UserMapper;
import br.com.hd.mappers.chat.v1.SectorMapper;
import br.com.hd.model.auth.v1.User;
import br.com.hd.model.chat.message.v1.UserMssg;
import br.com.hd.model.chat.room.v1.RoomPriority;
import br.com.hd.model.chat.room.v1.SectorRoom;
import br.com.hd.model.chat.room.v1.UserRoom;
import br.com.hd.model.chat.sector.v1.Sector;
import br.com.hd.repositories.auth.v1.UserRepository;
import br.com.hd.repositories.chat.v1.RoomRepository;
import br.com.hd.repositories.chat.v1.SectorRepository;

@Service
public class ServiceUtil {
	
	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private SectorRepository sectorRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SectorMapper sectorMapper;
	
	public RoomPriority returnPriorityIfExists(String priorityDescription) {
		
		RoomPriority roomPriority = roomRepository.findPriorityByDescription(priorityDescription);
		
		if (roomPriority != null) return roomPriority;
		else throw new ResourceNotFoundException("No priority found for the description (" + priorityDescription + ") !");
	}
	
	public Boolean roomExists(Long roomKey) {
		
		Boolean roomExists = roomRepository.existsById(roomKey);
		
		if (roomExists) return true;
		else throw new ResourceNotFoundException("No room found for the id (" + roomKey + ") !");
	}
	
	public Boolean userPresentInRoom(Long userKey, Long roomKey) {
		
		Long count = roomRepository.countByUserKeyAndRoomKey(userKey, roomKey);
		
		if (count == 1) return true;
		else throw new ResourceNotFoundException("User (" + userKey + ") is not present in the room (" + roomKey + ") !");
	}
	
	public Boolean sectorExists(Long sectorKey) {
		
		Boolean sectorExists = sectorRepository.existsById(sectorKey);
		
		if (sectorExists) return true;
		else throw new ResourceNotFoundException("No sector found for the id (" + sectorKey + ") !");
	}
	
	public Boolean userExists(Long userKey) {
		
		Boolean userExists = userRepository.existsById(userKey);
		
		if (userExists) return true;
		else throw new ResourceNotFoundException("No user found for the id (" + userKey + ") !");
	}
	
	public Boolean employeeExists(Long employeeKey) {
		
		Boolean employeeExists = userRepository.existsById(employeeKey);
		
		if (employeeExists) return true;
		else throw new ResourceNotFoundException("No employee found for the id (" + employeeKey + ") !");
	}
	
	public Boolean customerExists(Long customerKey) {
		
		Boolean customerExists = userRepository.existsById(customerKey);
		
		if (customerExists) return true;
		else throw new ResourceNotFoundException("No customer found for the id (" + customerKey + ") !");
	}
	
	public UserRoom returnEmployeeIfExists(Long employeeKey) {
		
		User employee = userRepository.findById(employeeKey)
				.orElseThrow(() -> new ResourceNotFoundException("No employee found for the key (" + employeeKey + ") !"));
		
		return userMapper.toUserRoom(employee);
	}
	
	public UserRoom returnCustomerIfExists(Long customerKey) {
		
		User customer = userRepository.findById(customerKey)
				.orElseThrow(() -> new ResourceNotFoundException("No customer found for the key (" + customerKey + ") !"));
		
		return userMapper.toUserRoom(customer);
	}
	
	public SectorRoom returnSectorIfExists(Long sectorKey) {
		
		Sector sector = sectorRepository.findById(sectorKey)
				.orElseThrow(() -> new ResourceNotFoundException("No sector found for the key (" + sectorKey + ") !"));
		
		return sectorMapper.toSectorRoom(sector);
	}
	
	public UserMssg returnUserIfPresentInRoom(Long userKey, Long roomKey) {
		
		Long count = roomRepository.countByUserKeyAndRoomKey(userKey, roomKey);
		
		User user = new User();

		if (count == 1) user = userRepository.findById(userKey).orElseThrow();
		else throw new ResourceNotFoundException("User (" + userKey + ") is not present in the room (" + roomKey + ") !");
		
		return userMapper.toUserMssg(user);
	}
	
}
