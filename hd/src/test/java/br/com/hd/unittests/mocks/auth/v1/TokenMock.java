package br.com.hd.unittests.mocks.auth.v1;

import java.util.Date;

import br.com.hd.data.vo.auth.v1.TokenVO;

public class TokenMock {

	public static TokenVO vo() {
		return vo(0);
	}
	
	public static TokenVO vo(Integer number) {
		TokenVO t = new TokenVO();
		t.setUsername("Username" + number);
		t.setAuthenticated(number % 2 == 0);
		t.setCreated(new Date(number));
		t.setExpiration(new Date(number));
		t.setAccessToken("Access Token" + number);
		t.setRefreshToken("Refresh Token" + number);
		
		return t;
	}
	
}
