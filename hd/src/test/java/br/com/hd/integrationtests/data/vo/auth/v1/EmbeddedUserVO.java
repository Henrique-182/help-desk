package br.com.hd.integrationtests.data.vo.auth.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmbeddedUserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("userVOList")
	private List<UserVO> users;

	public EmbeddedUserVO() {}

	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
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
		EmbeddedUserVO other = (EmbeddedUserVO) obj;
		return Objects.equals(users, other.users);
	}

}
