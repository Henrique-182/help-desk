package br.com.hd.model.chat.sector.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import br.com.hd.model.auth.v1.Permission;
import br.com.hd.model.auth.v1.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = "AUTH", name = "TB_USER")
public class UserSctr implements Serializable {

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
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		schema = "AUTH",
		name = "TB_USER_PERMISSION",
		joinColumns = @JoinColumn(name = "FK_USER"),
		inverseJoinColumns = @JoinColumn(name = "FK_PERMISSION")
	)
	private List<Permission> permissions;

	public UserSctr() {}

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

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(enabled, fullname, id, permissions, type, username);
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
		return Objects.equals(enabled, other.enabled) && Objects.equals(fullname, other.fullname)
				&& Objects.equals(id, other.id) && Objects.equals(permissions, other.permissions) && type == other.type
				&& Objects.equals(username, other.username);
	}

}
