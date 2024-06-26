package br.com.hd.integrationtests.data.vo.auth.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WrapperUserVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("_embedded")
	private EmbeddedUserVO embedded;
	
	public WrapperUserVO() {}

	public EmbeddedUserVO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(EmbeddedUserVO embedded) {
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
		WrapperUserVO other = (WrapperUserVO) obj;
		return Objects.equals(embedded, other.embedded);
	}

}
