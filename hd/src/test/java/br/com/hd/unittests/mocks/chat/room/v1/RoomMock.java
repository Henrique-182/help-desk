package br.com.hd.unittests.mocks.chat.room.v1;

import java.util.Date;

import br.com.hd.data.vo.chat.room.v1.RoomVO;
import br.com.hd.model.chat.room.v1.Room;
import br.com.hd.model.chat.room.v1.RoomStatus;

public class RoomMock {
	
	public static Room entity() {
		return entity(0L);
	}
	
	public static RoomVO vo() {
		return vo(0L);
	}

	public static Room entity(Integer code) {
		return entity(code.longValue());
	}
	
	public static RoomVO vo(Integer code) {
		return vo(code.longValue());
	}
	
	public static Room entity(Long number) {
		Room entity = new Room();
		entity.setId(number);
		entity.setCode(number.intValue());
		entity.setStatus(
			number % 2 == 0 ? RoomStatus.Chatting
			: RoomStatus.Closed
		);
		entity.setCreateDatetime(new Date(number));
		entity.setCloseDatetime(new Date(number + number));
		entity.setEmployee(UserRoomMock.entity());
		entity.setCustomer(UserRoomMock.entity(1L));
		entity.setSector(SectorRoomMock.entity());
		entity.setMessages(null);
		
		return entity;
	}
	
	public static RoomVO vo(Long number) {
		RoomVO vo = new RoomVO();
		vo.setKey(number);
		vo.setCode(number.intValue());
		vo.setStatus(
			number % 2 == 0 ? RoomStatus.Chatting
			: RoomStatus.Closed
		);
		vo.setCreateDatetime(new Date(number));
		vo.setCloseDatetime(new Date(number + number));
		vo.setEmployee(UserRoomMock.entity());
		vo.setCustomer(UserRoomMock.entity(1L));
		vo.setSector(SectorRoomMock.entity());
		vo.setMessages(MessageRoomMock.entityList());
		
		return vo;
	}
	
}
