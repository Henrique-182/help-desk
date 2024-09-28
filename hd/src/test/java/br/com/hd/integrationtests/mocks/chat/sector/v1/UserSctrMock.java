package br.com.hd.integrationtests.mocks.chat.sector.v1;

import br.com.hd.model.chat.sector.v1.UserSctr;

public class UserSctrMock {
	
	public static UserSctr entity() {
		return entity(0L);
	}
	
	public static UserSctr entity(Long number) {
		UserSctr entity = new UserSctr();
		entity.setKey(number);
		entity.setUsername("Username" + number);
		entity.setType(UserTypeSctrMock.entity(number));
		entity.setEnabled(true);
		
		return entity;
	}

}
