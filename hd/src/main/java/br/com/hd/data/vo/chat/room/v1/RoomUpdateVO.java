package br.com.hd.data.vo.chat.room.v1;

import java.io.Serializable;
import java.util.Objects;

import br.com.hd.model.chat.room.v1.RoomStatus;
import jakarta.validation.constraints.NotNull;

public class RoomUpdateVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long employeeKey;
	
	private Long sectorKey;
	
	@NotNull
	private RoomStatus status;

	public RoomUpdateVO() {}

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

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeKey, sectorKey, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomUpdateVO other = (RoomUpdateVO) obj;
		return Objects.equals(employeeKey, other.employeeKey) && Objects.equals(sectorKey, other.sectorKey)
				&& status == other.status;
	}

}
