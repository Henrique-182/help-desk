package br.com.hd.model.chat.room.v1;

import java.io.Serializable;
import java.util.Objects;

import br.com.hd.model.auth.v1.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "AUTH", name = "TB_USER")
public class UserRoom implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Long key;
	
	@Column(name = "USERNAME", nullable = false, unique = true)
	private String username;
	
	@Column(name = "USER_TYPE", nullable = false)
	private UserType type;
	
	public UserRoom() {}
	
	public UserRoom(Long key) {
		this.key = key;
	}

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

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, type, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserRoom other = (UserRoom) obj;
		return Objects.equals(key, other.key) && type == other.type && Objects.equals(username, other.username);
	}

}
