package br.com.hd.unittests.mocks.chat.room.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.hd.model.chat.message.v1.MessageType;
import br.com.hd.model.chat.room.v1.MessageRoom;

public class MessageRoomMock {
	
	public static List<MessageRoom> entityList() {
		List<MessageRoom> list = new ArrayList<>();
		
		for (long i = 0L; i < 14L; i++) list.add(entity(i));
		
		return list;
	}
	
	public static MessageRoom entity(Long number) {
		MessageRoom entity = new MessageRoom();
		entity.setKey(number);
		entity.setType(MessageType.TEXT);
		entity.setContent("Content" + number);
		entity.setUser(UserRoomMock.entity(number));
		entity.setCreateDatetime(new Date(number));
		entity.setUpdateDatetime(new Date(number));
		entity.setDeleteDatetime(new Date(number));
		
		return entity;
	}

}
