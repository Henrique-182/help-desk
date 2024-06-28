package br.com.hd.model.knowledge.v1;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(schema = "KNOWLEDGE", name = "TB_SOFTWARE")
public class SoftwareKnwl implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;
	
	@Column(name = "DESCRIPTION", nullable = false, unique = true)
	private String description;

	public SoftwareKnwl() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SoftwareKnwl other = (SoftwareKnwl) obj;
		return Objects.equals(description, other.description) && Objects.equals(id, other.id);
	}
	
}
