package br.com.hd.data.vo.chat.room.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class RoomWrapperVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<RoomVO> rooms;

	public RoomWrapperVO() {}
	
	public RoomWrapperVO(List<RoomVO> rooms) {
		this.rooms = rooms;
	}

	public List<RoomVO> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomVO> rooms) {
		this.rooms = rooms;
	}

	@Override
	public int hashCode() {
		return Objects.hash(rooms);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomWrapperVO other = (RoomWrapperVO) obj;
		return Objects.equals(rooms, other.rooms);
	}
	
}
