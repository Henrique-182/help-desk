package br.com.hd.data.vo.chat.room.v1;

import java.io.Serializable;
import java.util.Objects;

import jakarta.validation.constraints.NotNull;

public class RoomCreationVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private Long customerKey;
	
	@NotNull
	private Long sectorKey;
	
	private String priority;
	
	public RoomCreationVO() {}

	public Long getCustomerKey() {
		return customerKey;
	}

	public void setCustomerKey(Long customerKey) {
		this.customerKey = customerKey;
	}

	public Long getSectorKey() {
		return sectorKey;
	}

	public void setSectorKey(Long sectorKey) {
		this.sectorKey = sectorKey;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerKey, priority, sectorKey);
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
		return Objects.equals(customerKey, other.customerKey) && Objects.equals(priority, other.priority)
				&& Objects.equals(sectorKey, other.sectorKey);
	}

}
