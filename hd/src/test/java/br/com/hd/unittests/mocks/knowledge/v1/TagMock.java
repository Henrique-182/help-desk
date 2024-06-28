package br.com.hd.unittests.mocks.knowledge.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.hd.data.vo.knowledge.v1.TagVO;
import br.com.hd.model.knowledge.v1.Tag;

public class TagMock {
	
	public static Tag entity() {
		return entity(0L);
	}
	
	public static TagVO vo() {
		return vo(0L);
	}
	
	public static List<Tag> entityList() {
		List<Tag> list = new ArrayList<>();
		
		for (long i = 0; i < 13; i++) list.add(entity(i));
		
		return list;
	}
	
	public static List<TagVO> voList() {
		List<TagVO> list = new ArrayList<>();
		
		for (long i = 0; i < 13; i++) list.add(vo(i));
		
		return list;
	}
	
	public static Tag entity(Long number) {
		Tag topic = new Tag();
		topic.setId(number);
		topic.setDescription("Name" + number);
		
		return topic;
	}
	
	public static TagVO vo(Long number) {
		TagVO vo = new TagVO();
		vo.setKey(number);
		vo.setDescription("Name" + number);
		
		return vo;
	}

}
