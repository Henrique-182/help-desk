package br.com.hd.unittests.mocks.chat.message.v1;

import br.com.hd.data.vo.chat.message.v1.MessageUpdateVO;

public class MessageUpdateVOMock {
	
	public static MessageUpdateVO vo() {
		return vo(0L);
	}

	public static MessageUpdateVO vo(Long number) {
		MessageUpdateVO vo = new MessageUpdateVO();
		vo.setContent("Content" + number);
		
		return vo;
	}
	
}
