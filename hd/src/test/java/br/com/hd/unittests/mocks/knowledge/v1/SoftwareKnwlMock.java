package br.com.hd.unittests.mocks.knowledge.v1;

import br.com.hd.model.knowledge.v1.SoftwareKnwl;

public class SoftwareKnwlMock {
	
	public static SoftwareKnwl entity(Long number) {
		SoftwareKnwl entity = new SoftwareKnwl();
		entity.setId(number % 2 == 0 ? 1L : 2L);
		entity.setDescription(number % 2 == 0 ? "Software A" : "Software B");
		
		return entity;
	}

}
