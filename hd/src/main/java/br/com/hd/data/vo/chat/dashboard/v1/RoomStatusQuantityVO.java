package br.com.hd.data.vo.chat.dashboard.v1;

import java.io.Serializable;
import java.util.Objects;

import br.com.hd.model.chat.room.v1.RoomStatus;

public class RoomStatusQuantityVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private RoomStatus status;
	private Long quantity;
	
	public RoomStatusQuantityVO() {}
	
	public RoomStatusQuantityVO(RoomStatus status, Long quantity) {
		this.status = status;
		this.quantity = quantity;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(quantity, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomStatusQuantityVO other = (RoomStatusQuantityVO) obj;
		return Objects.equals(quantity, other.quantity) && status == other.status;
	}
	
}
