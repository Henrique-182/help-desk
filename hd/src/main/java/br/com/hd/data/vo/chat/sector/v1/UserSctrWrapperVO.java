package br.com.hd.data.vo.chat.sector.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import br.com.hd.model.chat.sector.v1.UserSctr;

public class UserSctrWrapperVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<UserSctr> users;

	public UserSctrWrapperVO() {}
	
	public UserSctrWrapperVO(List<UserSctr> users) {
		this.users = users;
	}

	public List<UserSctr> getUsers() {
		return users;
	}

	public void setUsers(List<UserSctr> users) {
		this.users = users;
	}

	@Override
	public int hashCode() {
		return Objects.hash(users);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSctrWrapperVO other = (UserSctrWrapperVO) obj;
		return Objects.equals(users, other.users);
	}
	
}
