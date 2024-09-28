package br.com.hd.data.vo.chat.sector.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class SimpleSectorWrapperVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<SimpleSectorVO> sectors;
	private String userType;

	public SimpleSectorWrapperVO() {}
	
	public SimpleSectorWrapperVO(List<SimpleSectorVO> sectors, String userType) {
		this.sectors = sectors;
		this.userType = userType;
	}

	public List<SimpleSectorVO> getSectors() {
		return sectors;
	}

	public void setSectors(List<SimpleSectorVO> sectors) {
		this.sectors = sectors;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sectors, userType);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleSectorWrapperVO other = (SimpleSectorWrapperVO) obj;
		return Objects.equals(sectors, other.sectors) && Objects.equals(userType, other.userType);
	}

}
