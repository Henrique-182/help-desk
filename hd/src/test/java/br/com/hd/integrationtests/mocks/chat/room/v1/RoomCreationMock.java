package br.com.hd.integrationtests.mocks.chat.room.v1;

import br.com.hd.integrationtests.data.vo.chat.room.v1.RoomCreationVO;

public class RoomCreationMock {
	
	public static RoomCreationVO vo() {
		RoomCreationVO vo = new RoomCreationVO();
		vo.setEmployeeKey(2L);
		vo.setCustomerKey(3L);
		vo.setSectorKey(2L);
		vo.setPriority("NORMAL");
				
		return vo;
	}

}
