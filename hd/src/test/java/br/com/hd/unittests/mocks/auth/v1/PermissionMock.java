package br.com.hd.unittests.mocks.auth.v1;

import java.util.ArrayList;
import java.util.List;

import br.com.hd.model.auth.v1.Permission;

public class PermissionMock {
	
	public static Permission entity() {
		return entity(0);
	}
	
	public static List<Permission> entityList() {
		List<Permission> permissions = new ArrayList<>();
		
		for (int i = 0; i < 13; i++) permissions.add(entity(i));
		
		return permissions;
	}
	
	public static Permission entity(Integer number) {
		Permission p = new Permission();
		p.setId(number);
		p.setDescription("Description" + number);
		
		return p;
	}

}
