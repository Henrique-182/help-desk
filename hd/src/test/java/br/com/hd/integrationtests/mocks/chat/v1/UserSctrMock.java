package br.com.hd.integrationtests.mocks.chat.v1;

import br.com.hd.model.auth.v1.UserType;
import br.com.hd.model.chat.v1.UserSctr;

public class UserSctrMock {
	
	public static UserSctr entity() {
		return entity(0L);
	}
	
	public static UserSctr entity(Long number) {
		UserSctr entity = new UserSctr();
		entity.setId(number);
		entity.setUsername("Username" + number);
		entity.setFullname("Fullname" + number);
		entity.setType(number % 2 == 0 ? UserType.Employee : UserType.Customer);
		entity.setEnabled(true);
		
		return entity;
	}

}
