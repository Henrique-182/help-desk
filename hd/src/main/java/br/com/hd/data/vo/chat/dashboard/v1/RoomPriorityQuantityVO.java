package br.com.hd.data.vo.chat.dashboard.v1;

import java.util.Objects;

public class RoomPriorityQuantityVO {

	private String priority;
	private Long quantity;
	
	public RoomPriorityQuantityVO() {}

	public RoomPriorityQuantityVO(String priority, Long quantity) {
		this.priority = priority;
		this.quantity = quantity;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(priority, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomPriorityQuantityVO other = (RoomPriorityQuantityVO) obj;
		return Objects.equals(priority, other.priority) && Objects.equals(quantity, other.quantity);
	}
	
}
