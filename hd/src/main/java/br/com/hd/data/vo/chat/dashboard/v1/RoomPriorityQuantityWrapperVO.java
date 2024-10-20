package br.com.hd.data.vo.chat.dashboard.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class RoomPriorityQuantityWrapperVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<RoomPriorityQuantityVO> voList;
	
	public RoomPriorityQuantityWrapperVO() {}

	public RoomPriorityQuantityWrapperVO(List<RoomPriorityQuantityVO> voList) {
		this.voList = voList;
	}

	public List<RoomPriorityQuantityVO> getVoList() {
		return voList;
	}

	public void setVoList(List<RoomPriorityQuantityVO> voList) {
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
		RoomPriorityQuantityWrapperVO other = (RoomPriorityQuantityWrapperVO) obj;
		return Objects.equals(voList, other.voList);
	}
	
}
