package br.com.hd.integrationtests.data.vo.chat.room.v1;

import java.io.Serializable;
import java.util.Objects;

import br.com.hd.model.chat.room.v1.RoomStatus;

public class RoomUpdateVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long employeeKey;
	private Long sectorKey;
	private RoomStatus status;
	private String reason;
	private String solution;
	private String priority;
	
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
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public int hashCode() {
		return Objects.hash(employeeKey, priority, reason, sectorKey, solution, status);
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
		return Objects.equals(employeeKey, other.employeeKey) && Objects.equals(priority, other.priority)
				&& Objects.equals(reason, other.reason) && Objects.equals(sectorKey, other.sectorKey)
				&& Objects.equals(solution, other.solution) && status == other.status;
	}

}
