package br.com.hd.data.vo.auth.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import br.com.hd.model.auth.v1.Permission;
import br.com.hd.model.auth.v1.UserType;

public class UserVO extends RepresentationModel<UserVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long key;
	private String username;
	private String fullname;
	private UserType type;
	private Boolean accountNonExpired;
	private Boolean accountNonLocked;
	private Boolean credentialsNonExpired;
	private Boolean enabled;
	private List<Permission> permissions;
	
	public UserVO() {}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

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
	
	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public Boolean getAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public Boolean getAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(accountNonExpired, accountNonLocked, credentialsNonExpired, enabled,
				fullname, key, permissions, type, username);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserVO other = (UserVO) obj;
		return Objects.equals(accountNonExpired, other.accountNonExpired)
				&& Objects.equals(accountNonLocked, other.accountNonLocked)
				&& Objects.equals(credentialsNonExpired, other.credentialsNonExpired)
				&& Objects.equals(enabled, other.enabled) && Objects.equals(fullname, other.fullname)
				&& Objects.equals(key, other.key) && Objects.equals(permissions, other.permissions)
				&& type == other.type && Objects.equals(username, other.username);
	}

}
