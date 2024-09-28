package br.com.hd.integrationtests.mocks.auth.v1;

import br.com.hd.model.auth.v1.UserType;

public class UserTypeMock {
	
	public static UserType entity() {
		return entity(0L);
	}
	
	public static UserType entity(Long number) {
		UserType entity = new UserType();
		entity.setKey(number % 2 == 0 ? 2L : 3L);
		entity.setDescription(number % 2 == 0 ? "Employee" : "Customer");
		
		return entity;
	}

}
