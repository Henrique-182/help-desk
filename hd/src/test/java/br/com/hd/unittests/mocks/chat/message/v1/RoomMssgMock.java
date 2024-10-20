package br.com.hd.unittests.mocks.chat.message.v1;

import br.com.hd.model.chat.message.v1.RoomMssg;

public class RoomMssgMock {

	public static RoomMssg entity() {
		return entity(0L);
	}
	
	public static RoomMssg entity(Long number) {
		RoomMssg entity = new RoomMssg();
		entity.setKey(number);
		entity.setCode(number.intValue());
		
		return entity;
	}
	
}
