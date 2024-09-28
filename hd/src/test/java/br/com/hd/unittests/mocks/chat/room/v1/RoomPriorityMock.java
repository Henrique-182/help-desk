package br.com.hd.unittests.mocks.chat.room.v1;

import br.com.hd.model.chat.room.v1.RoomPriority;

public class RoomPriorityMock {
	
	public RoomPriority entity() {
		return entity(0L);
	}
	
	public RoomPriority entity(Long number) {
		RoomPriority entity = new RoomPriority();
		entity.setKey(number);
		entity.setDescription("Description" + number);
		
		return entity;
	}

}
