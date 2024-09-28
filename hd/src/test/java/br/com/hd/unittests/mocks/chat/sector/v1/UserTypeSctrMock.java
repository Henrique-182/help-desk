package br.com.hd.unittests.mocks.chat.sector.v1;

import br.com.hd.model.chat.sector.v1.UserTypeSctr;

public class UserTypeSctrMock {
	
	public static UserTypeSctr entity(Long number) {
		UserTypeSctr entity = new UserTypeSctr();
		entity.setKey(number % 2 == 0 ? 2L : 3L);
		entity.setDescription(number % 2 == 0 ? "Employee" : "Customer");
		
		return entity;
	}

}
