package br.com.hd.integrationtests.data.vo.chat.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import br.com.hd.model.chat.sector.v1.UserSctr;

public class SectorVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long key;
	private String description;
	private List<UserSctr> employees;
	private List<UserSctr> customers;
	
	public SectorVO() {}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
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
		return Objects.hash(customers, description, employees, key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SectorVO other = (SectorVO) obj;
		return Objects.equals(customers, other.customers) && Objects.equals(description, other.description)
				&& Objects.equals(employees, other.employees) && Objects.equals(key, other.key);
	}
	
}
