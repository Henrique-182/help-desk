package br.com.hd.unittests.mocks.auth.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.hd.data.vo.auth.v1.UserVO;
import br.com.hd.model.auth.v1.User;

public class UserMock {
	
	public static User entity() {
		return entity(0);
	}
	
	public static UserVO vo() {
		return vo(0);
	}
	
	public static List<User> entityList() {
		List<User> users = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) users.add(entity(i));
		
		return users;
	}
	
	public static List<UserVO> voList() {
		List<UserVO> users = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) users.add(vo(i));
		
		return users;
	}
	
	public static User entity(Integer number) {
		User u = new User();
		u.setId(number);
		u.setUsername("Username" + number);
		u.setFullname("Fullname" + number);
		u.setPassword("Password" + number);
		u.setAccountNonExpired(number % 2 == 0);
		u.setAccountNonLocked(number % 2 == 0);
		u.setCredentialsNonExpired(number % 2 == 0);
		u.setEnabled(number % 2 == 0);
		u.setPermissions(PermissionMock.entityList());
		
		return u;
	}
	
	public static UserVO vo(Integer number) {
		UserVO u = new UserVO();
		u.setKey(number);
		u.setUsername("Username" + number);
		u.setFullname("Fullname" + number);
		u.setAccountNonExpired(number % 2 == 0);
		u.setAccountNonLocked(number % 2 == 0);
		u.setCredentialsNonExpired(number % 2 == 0);
		u.setEnabled(number % 2 == 0);
		u.setPermissions(PermissionMock.entityList());
		
		return u;
	}

}
