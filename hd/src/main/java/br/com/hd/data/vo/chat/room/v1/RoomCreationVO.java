package br.com.hd.data.vo.chat.room.v1;

import java.io.Serializable;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public class RoomCreationVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long customerKey;
	private Long employeeKey;
	
	@NotNull
	private Long sectorKey;
	
	public RoomCreationVO() {}

	public Long getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(Long customerKey) {
		this.customerKey = customerKey;
	}

	public Long getEmployeeKey() {
		return employeeKey;
	}

	public void setEmployeeKey(Long employeeKey) {
		this.employeeKey = employeeKey;
	}

	public Long getSectorKey() {
		return sectorKey;
	}

	public void setSectorKey(Long sectorKey) {
		this.sectorKey = sectorKey;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerKey, employeeKey, sectorKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomCreationVO other = (RoomCreationVO) obj;
		return Objects.equals(customerKey, other.customerKey) && Objects.equals(employeeKey, other.employeeKey)
				&& Objects.equals(sectorKey, other.sectorKey);
	}

}
