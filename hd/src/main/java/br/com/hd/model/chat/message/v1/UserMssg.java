package br.com.hd.model.chat.message.v1;

import java.io.Serializable;
import java.util.Objects;

import br.com.hd.model.auth.v1.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "AUTH", name = "TB_USER")
public class UserMssg implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;
	
	@Column(name = "USERNAME", nullable = false, unique = true)
	private String username;
	
	@Column(name = "FULLNAME", nullable = false)
	private String fullname;
	
	@Column(name = "USER_TYPE", nullable = false)
	private UserType type;
	
	@Column(name = "ENABLED", nullable = false)
	private Boolean enabled;

	public UserMssg() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		return Objects.hash(enabled, fullname, id, type, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserMssg other = (UserMssg) obj;
		return Objects.equals(enabled, other.enabled) && Objects.equals(fullname, other.fullname)
				&& Objects.equals(id, other.id) && type == other.type && Objects.equals(username, other.username);
	}
	
}
