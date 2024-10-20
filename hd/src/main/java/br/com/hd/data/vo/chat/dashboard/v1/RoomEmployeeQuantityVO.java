package br.com.hd.data.vo.chat.dashboard.v1;

import java.util.Objects;

public class RoomEmployeeQuantityVO {
	
	String username;
	Long quantity;
	
	public RoomEmployeeQuantityVO() {}
	
	public RoomEmployeeQuantityVO(String username, Long quantity) {
		this.username = username;
		this.quantity = quantity;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(quantity, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomEmployeeQuantityVO other = (RoomEmployeeQuantityVO) obj;
		return Objects.equals(quantity, other.quantity) && Objects.equals(username, other.username);
	}
	
}
