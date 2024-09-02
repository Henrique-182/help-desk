package br.com.hd.integrationtests.data.vo.chat.sector.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SectorWrapperVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("_embedded")
	private SectorEmbeddedVO embedded;

	public SectorWrapperVO() {}

	public SectorEmbeddedVO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(SectorEmbeddedVO embedded) {
		this.embedded = embedded;
	}

	@Override
	public int hashCode() {
		return Objects.hash(embedded);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SectorWrapperVO other = (SectorWrapperVO) obj;
		return Objects.equals(embedded, other.embedded);
	}
	
}
