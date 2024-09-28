package br.com.hd.model.chat.sector.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import br.com.hd.model.auth.v1.Permission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "AUTH", name = "TB_USER")
public class UserSctr implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Long key;
	
	@Column(name = "USERNAME", nullable = false, unique = true)
	private String username;
	
	@ManyToOne
	@JoinColumn(name = "FK_USER_TYPE")
	private UserTypeSctr type;
	
	@Column(name = "ENABLED", nullable = false)
	private Boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		schema = "AUTH",
		name = "TB_USER_PERMISSION",
		joinColumns = @JoinColumn(name = "FK_USER"),
		inverseJoinColumns = @JoinColumn(name = "FK_PERMISSION")
	)
	private List<Permission> permissions;

	public UserSctr() {}

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
	
	public UserTypeSctr getType() {
		return type;
	}

	public void setType(UserTypeSctr type) {
		this.type = type;
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
		return Objects.hash(enabled, key, permissions, type, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSctr other = (UserSctr) obj;
		return Objects.equals(enabled, other.enabled) && Objects.equals(key, other.key)
				&& Objects.equals(permissions, other.permissions) && Objects.equals(type, other.type)
				&& Objects.equals(username, other.username);
	}

}
