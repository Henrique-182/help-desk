package br.com.hd.unittests.mocks.chat.message.v1;

import br.com.hd.data.vo.chat.message.v1.MessageCreationVO;
import br.com.hd.model.chat.message.v1.MessageType;

public class MessageCreationVOMock {
	
	public static MessageCreationVO vo() {
		return vo(0L);
	}
	
	public static MessageCreationVO vo(Long number) {
		MessageCreationVO vo = new MessageCreationVO();
		vo.setRoomKey(number);
		vo.setType(MessageType.TEXT);
		vo.setContent("Content" + number);
		
		return vo;
	}

}
