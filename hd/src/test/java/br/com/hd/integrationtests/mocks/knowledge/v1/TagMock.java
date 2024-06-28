package br.com.hd.integrationtests.mocks.knowledge.v1;

import br.com.hd.integrationtests.data.vo.knowledge.v1.TagVO;

public class TagMock {
	
	public static TagVO vo() {
		return vo(0L);
	}
	
	public static TagVO vo(Long number) {
		TagVO vo = new TagVO();
		vo.setKey(number);
		vo.setDescription("Description" + number);
		
		return vo;
	}

}
