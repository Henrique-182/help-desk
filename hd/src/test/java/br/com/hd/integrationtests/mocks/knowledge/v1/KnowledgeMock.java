package br.com.hd.integrationtests.mocks.knowledge.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.hd.integrationtests.data.vo.knowledge.v1.KnowledgeVO;

public class KnowledgeMock {
	
	public static KnowledgeVO vo() {
		return vo(0L);
	}
	
	public static List<KnowledgeVO> voList() {
		List<KnowledgeVO> list = new ArrayList<>();
		
		for (long i = 0; i < 13; i++) list.add(vo(i));
		
		return list;
	}
	
	public static KnowledgeVO vo(Long number) {
		KnowledgeVO vo = new KnowledgeVO();
		vo.setKey(number);
		vo.setTitle("Title" + number);
		vo.setContent("Content" + number);
		vo.setSoftware(SoftwareMock.entity(number));
		
		return vo;
	}

}
