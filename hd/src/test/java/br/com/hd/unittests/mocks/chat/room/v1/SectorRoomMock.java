package br.com.hd.unittests.mocks.chat.room.v1;

import br.com.hd.model.chat.room.v1.SectorRoom;

public class SectorRoomMock {
	
	public static SectorRoom entity() {
		return entity(0L);
	}
	
	public static SectorRoom entity(Long number) {
		SectorRoom vo = new SectorRoom();
		vo.setKey(number);
		vo.setDescription("Description" + number);
		
		return vo;
	}

}
