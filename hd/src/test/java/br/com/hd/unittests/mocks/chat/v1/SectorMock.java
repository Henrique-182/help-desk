package br.com.hd.unittests.mocks.chat.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.hd.data.vo.chat.v1.SectorVO;
import br.com.hd.data.vo.chat.v1.SimpleSectorVO;
import br.com.hd.model.chat.v1.Sector;

public class SectorMock {
	
	public static Sector entity() {
		return entity(0L);
	}
	
	public static SectorVO vo() {
		return vo(0L);
	}
	
	public static SimpleSectorVO simpleVo() {
		return simpleVo(0L);
	}
	
	public static List<Sector> entityList() {
		List<Sector> list = new ArrayList<>();
		
		for (long i = 0; i < 14; i++) list.add(entity(i));
		
		return list;
	}
	
	public static List<SectorVO> voList() {
		List<SectorVO> list = new ArrayList<>();
		
		for (long i = 0; i < 14; i++) list.add(vo(i));
		
		return list;
	}
	
	public static List<SimpleSectorVO> simpleVoList() {
		List<SimpleSectorVO> list = new ArrayList<>();
		
		for (long i = 0; i < 14; i++) list.add(simpleVo(i));
		
		return list;
	}
	
	public static Sector entity(Long number) {
		Sector entity = new Sector();
		entity.setId(number);
		entity.setDescription("Description" + number);
		entity.setEmployees(null);
		entity.setCustomers(null);
		
		return entity;
	}
	
	public static SectorVO vo(Long number) {
		SectorVO vo = new SectorVO();
		vo.setKey(number);
		vo.setDescription("Description" + number);
		vo.setEmployees(null);
		vo.setCustomers(null);
		
		return vo;
	}
	
	public static SimpleSectorVO simpleVo(Long number) {
		SimpleSectorVO vo = new SimpleSectorVO();
		vo.setKey(number);
		vo.setDescription("Description" + number);
		
		return vo;
	}

}
