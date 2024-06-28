package br.com.hd.unittests.mocks.knowledge.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.hd.data.vo.knowledge.v1.KnowledgeVO;
import br.com.hd.model.knowledge.v1.Knowledge;

public class KnowledgeMock {
	
	public static Knowledge entity() {
		return entity(0L);
	}
	
	public static KnowledgeVO vo() {
		return vo(0L);
	}
	
	public static List<Knowledge> entityList() {
		List<Knowledge> list = new ArrayList<>();
		
		for (long i = 0; i < 13; i++) list.add(entity(i));
		
		return list;
	}
	
	public static List<KnowledgeVO> voList() {
		List<KnowledgeVO> list = new ArrayList<>();
		
		for (long i = 0; i < 13; i++) list.add(vo(i));
		
		return list;
	}
	
	public static Knowledge entity(Long number) {
		Knowledge entity = new Knowledge();
		entity.setId(number);
		entity.setTitle("Title" + number);
		entity.setContent("Content" + number);
		entity.setSoftware(SoftwareKnwlMock.entity(number));
		entity.setTags(TagKnwlMock.entityList());
		
		return entity;
	}
	
	public static KnowledgeVO vo(Long number) {
		KnowledgeVO vo = new KnowledgeVO();
		vo.setKey(number);
		vo.setTitle("Title" + number);
		vo.setContent("Content" + number);
		vo.setSoftware(SoftwareKnwlMock.entity(number));
		vo.setTags(TagKnwlMock.entityList());
		
		return vo;
	}

}
