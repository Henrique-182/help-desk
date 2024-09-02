package br.com.hd.unittests.mocks.chat.message.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.hd.data.vo.chat.message.v1.MessageVO;
import br.com.hd.model.chat.message.v1.Message;
import br.com.hd.model.chat.message.v1.MessageType;

public class MessageMock {
	
	public static Message entity() {
		return entity(0L);
	}
	
	public static MessageVO vo() {
		return vo(0L);
	}
	
	public static List<Message> entityList() {
		List<Message> list = new ArrayList<>();
		
		for (long i = 0L; i < 14L; i++) list.add(entity(i));
		
		return list;
	}
	
	public static List<MessageVO> voList() {
		List<MessageVO> list = new ArrayList<>();
		
		for (long i = 0L; i < 14L; i++) list.add(vo(i));
		
		return list;
	}
	
	public static Message entity(Long number) {
		Message entity = new Message();
		entity.setId(number);
		entity.setType(MessageType.TEXT);
		entity.setContent("Content" + number);
		entity.setUser(UserMssgMock.entity(number % 2 == 0 ? 0L : 1L));
		entity.setCreateDatetime(new Date(number));
		entity.setUpdateDatetime(null);
		entity.setDeleteDatetime(null);
		
		return entity;
	}
	
	public static MessageVO vo(Long number) {
		MessageVO vo = new MessageVO();
		vo.setKey(number);
		vo.setType(MessageType.TEXT);
		vo.setContent("Content" + number);
		vo.setUser(UserMssgMock.entity(number % 2 == 0 ? 0L : 1L));
		vo.setCreateDatetime(new Date(number));
		vo.setUpdateDatetime(null);
		vo.setDeleteDatetime(null);
		
		return vo;
	}

}
