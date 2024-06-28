package br.com.hd.unittests.mocks.knowledge.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.hd.data.vo.knowledge.v1.SoftwareVO;
import br.com.hd.model.knowledge.v1.Software;

public class SoftwareMock {
	
	public static Software entity() {
		return entity(0L);
	}
	
	public static SoftwareVO vo() {
		return vo(0L);
	}
	
	public static List<Software> entityList() {
		List<Software> list = new ArrayList<>();
		
		for (long i = 0; i < 13; i++) list.add(entity(i));
		
		return list;
	}
	
	public static List<SoftwareVO> voList() {
		List<SoftwareVO> list = new ArrayList<>();
		
		for (long i = 0; i < 13; i++) list.add(vo(i));
		
		return list;
	}
	
	public static Software entity(Long number) {
		Software entity = new Software();
		entity.setId(number);
		entity.setDescription("Name" + number);
		
		return entity;
	}
	
	public static SoftwareVO vo(Long number) {
		SoftwareVO vo = new SoftwareVO();
		vo.setKey(number);
		vo.setDescription("Name" + number);
		
		return vo;
	}

}
