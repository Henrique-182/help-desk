package br.com.hd.data.vo.chat.sector.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import br.com.hd.model.chat.sector.v1.UserSctr;

public class SectorVO extends RepresentationModel<SectorVO> implements Serializable {

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
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(customers, description, employees, key);
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
		SectorVO other = (SectorVO) obj;
		return Objects.equals(customers, other.customers) && Objects.equals(description, other.description)
				&& Objects.equals(employees, other.employees) && Objects.equals(key, other.key);
	}

}
