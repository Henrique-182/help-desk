package br.com.hd.unittests.mocks.knowledge.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.hd.model.knowledge.v1.TagKnwl;

public class TagKnwlMock {

	static List<TagKnwl> entityList() {
		List<TagKnwl> list = new ArrayList<>();
		
		for (long i = 0; i < 13; i++) list.add(entity(i));
		
		return list;
	}

	static TagKnwl entity(Long number) {
		TagKnwl entity = new TagKnwl();
		entity.setId(number);
		entity.setDescription("Name" + number);
		
		return entity;
	}
	
}
