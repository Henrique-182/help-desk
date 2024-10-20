package br.com.hd.data.vo.chat.dashboard.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class RoomEmployeeQuantityWrapperVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<RoomEmployeeQuantityVO> voList;
	
	public RoomEmployeeQuantityWrapperVO() {}
	
	public RoomEmployeeQuantityWrapperVO(List<RoomEmployeeQuantityVO> voList) {
		this.voList = voList;
	}

	public List<RoomEmployeeQuantityVO> getVoList() {
		return voList;
	}

	public void setVoList(List<RoomEmployeeQuantityVO> voList) {
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
		RoomEmployeeQuantityWrapperVO other = (RoomEmployeeQuantityWrapperVO) obj;
		return Objects.equals(voList, other.voList);
	}

}
