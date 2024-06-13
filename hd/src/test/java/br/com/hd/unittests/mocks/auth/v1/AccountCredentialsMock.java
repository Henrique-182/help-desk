package br.com.hd.unittests.mocks.auth.v1;

import br.com.hd.data.vo.auth.v1.AccountCredentialsVO;

public class AccountCredentialsMock {

	public static AccountCredentialsVO vo() {
		return vo(0);
	}
	
	public static AccountCredentialsVO vo(Integer number) {
		AccountCredentialsVO a = new AccountCredentialsVO();
		a.setUsername("Username" + number);
		a.setPassword("Password" + number);
		
		return a;
	}
	
}
