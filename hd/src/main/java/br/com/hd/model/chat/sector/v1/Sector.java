package br.com.hd.model.chat.sector.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = "CHAT", name = "TB_SECTOR")
public class Sector implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;
	
	@Column(name = "DESCRIPTION", nullable = false, unique = true)
	private String description;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		schema = "CHAT",
		name = "TB_SECTOR_USER_EMPLOYEE",
		joinColumns = @JoinColumn(name = "FK_SECTOR"),
		inverseJoinColumns = @JoinColumn(name = "FK_USER")
	)
	private List<UserSctr> employees;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		schema = "CHAT",
		name = "TB_SECTOR_USER_CUSTOMER",
		joinColumns = @JoinColumn(name = "FK_SECTOR"),
		inverseJoinColumns = @JoinColumn(name = "FK_USER")
	)
	private List<UserSctr> customers;

	public Sector() {}

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
	
	public List<UserSctr> getEmployees() {
		return employees;
	}

	public void setEmployees(List<UserSctr> employees) {
		this.employees = employees;
	}

	public List<UserSctr> getCustomers() {
		return customers;
	}

	public void setCustomers(List<UserSctr> customers) {
		this.customers = customers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customers, description, employees, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sector other = (Sector) obj;
		return Objects.equals(customers, other.customers) && Objects.equals(description, other.description)
				&& Objects.equals(employees, other.employees) && Objects.equals(id, other.id);
	}

}
