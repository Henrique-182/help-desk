package br.com.hd.data.vo.chat.dashboard.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class RoomStatusQuantityWrapperVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<RoomStatusQuantityVO> voList;
	
	public RoomStatusQuantityWrapperVO() {}

	public RoomStatusQuantityWrapperVO(List<RoomStatusQuantityVO> voList) {
		this.voList = voList;
	}

	public List<RoomStatusQuantityVO> getVoList() {
		return voList;
	}

	public void setVoList(List<RoomStatusQuantityVO> voList) {
		this.voList = voList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(voList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomStatusQuantityWrapperVO other = (RoomStatusQuantityWrapperVO) obj;
		return Objects.equals(voList, other.voList);
	}
	
}
