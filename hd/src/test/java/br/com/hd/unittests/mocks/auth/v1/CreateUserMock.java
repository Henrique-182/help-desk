package br.com.hd.unittests.mocks.auth.v1;

import br.com.hd.data.vo.auth.v1.CreateUserVO;

public class CreateUserMock {
	
	public static CreateUserVO vo() {
		return vo(0L);
	}
	
	public static CreateUserVO vo(Long number) {
		CreateUserVO vo = new CreateUserVO();
		vo.setUsername("Username" + number);
		vo.setFullname("Fullname" + number);
		vo.setPassword("Password" + number);
		vo.setType(UserTypeMock.entity(number));
		
		return vo;
	}

}
