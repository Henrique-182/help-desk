package br.com.hd.unittests.mocks.chat.message.v1;

import br.com.hd.model.chat.message.v1.UserMssg;

public class UserMssgMock {
	
	public static UserMssg entity() {
		return entity(0L);
	}
	
	public static UserMssg entity(Long number) {
		UserMssg entity = new UserMssg();
		entity.setKey(number);
		entity.setUsername("Username" + number);
		entity.setType(UserTypeMssgMock.entity(number));
		
		return entity;
	}

}
