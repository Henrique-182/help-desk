package br.com.hd.unittests.mocks.chat.message.v1;

import br.com.hd.model.chat.message.v1.UserTypeMssg;

public class UserTypeMssgMock {

	public static UserTypeMssg entity() {
		return entity(0L);
	}
	
	public static UserTypeMssg entity(Long number) {
		UserTypeMssg entity = new UserTypeMssg();
		entity.setKey(number % 2 == 0 ? 2L : 3L);
		entity.setDescription(number % 2 == 0 ? "Employee" : "Customer");
		
		return entity;
	}
	
}
