package br.com.hd.unittests.mocks.chat.room.v1;

import br.com.hd.integrationtests.mocks.auth.v1.UserTypeMock;
import br.com.hd.model.chat.room.v1.UserRoom;

public class UserRoomMock {
	
	public static UserRoom entity() {
		return entity(0L);
	}
	
	public static UserRoom entity(Long number) {
		UserRoom entity = new UserRoom();
		entity.setKey(number);
		entity.setUsername("Username" + number);
		entity.setType(UserTypeMock.entity(number));
		
		return entity;
	}

}
