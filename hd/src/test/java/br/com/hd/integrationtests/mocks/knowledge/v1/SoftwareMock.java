package br.com.hd.integrationtests.mocks.knowledge.v1;

import br.com.hd.integrationtests.data.vo.knowledge.v1.SoftwareVO;
import br.com.hd.model.knowledge.v1.SoftwareKnwl;

public class SoftwareMock {
	
	public static SoftwareKnwl entity() {
		return entity(0L);
	}
	
	public static SoftwareVO vo() {
		return vo(0L);
	}
	
	public static SoftwareKnwl entity(Long number) {
		SoftwareKnwl entity = new SoftwareKnwl();
		entity.setId(number % 2 == 0 ? 1L : 2L);
		entity.setDescription(number % 2 == 0 ? "Software A" : "Software B");
		
		return entity;
	}
	
	public static SoftwareVO vo(Long number) {
		SoftwareVO entity = new SoftwareVO();
		entity.setKey(number);
		entity.setDescription("Description" + number);
		
		return entity;
	}

}
