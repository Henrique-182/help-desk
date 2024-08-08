package br.com.hd.integrationtests.data.vo.chat.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SectorEmbeddedVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("sectorVOList")
	private List<SectorVO> sectors;

	public SectorEmbeddedVO() {
	}

	public List<SectorVO> getSectors() {
		return sectors;
	}

	public void setSectors(List<SectorVO> sectors) {
		this.sectors = sectors;
	}

	@Override
	public int hashCode() {
		return Objects.hash(sectors);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SectorEmbeddedVO other = (SectorEmbeddedVO) obj;
		return Objects.equals(sectors, other.sectors);
	}
	
}
