package br.com.hd.integrationtests.data.vo.auth.v1;

import java.io.Serializable;
import java.util.Objects;

import br.com.hd.model.auth.v1.UserType;

public class CreateUserVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String fullname;
	private String password;
	private UserType type;
	
	public CreateUserVO() {}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fullname, password, type, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateUserVO other = (CreateUserVO) obj;
		return Objects.equals(fullname, other.fullname) && Objects.equals(password, other.password)
				&& type == other.type && Objects.equals(username, other.username);
	}
	
}
