package br.com.hd.integrationtests.mocks.chat.sector.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.hd.integrationtests.data.vo.chat.sector.v1.SectorVO;
import br.com.hd.model.chat.sector.v1.Sector;

public class SectorMock {
	
	public static Sector entity() {
		return entity(0L);
	}
	
	public static SectorVO vo() {
		return vo(0L);
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
	
	public static Sector entity(Long number) {
		Sector entity = new Sector();
		entity.setId(number);
		entity.setDescription("Description" + number);
		entity.setEmployees(List.of(UserSctrMock.entity(2L)));
		entity.setCustomers(List.of(UserSctrMock.entity(3L)));
		
		return entity;
	}
	
	public static SectorVO vo(Long number) {
		SectorVO vo = new SectorVO();
		vo.setKey(number);
		vo.setDescription("Description" + number);
		vo.setEmployees(List.of(UserSctrMock.entity(2L)));
		vo.setCustomers(List.of(UserSctrMock.entity(3L)));
		
		return vo;
	}
	
}
