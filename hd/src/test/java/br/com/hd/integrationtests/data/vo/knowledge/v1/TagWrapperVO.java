package br.com.hd.integrationtests.data.vo.knowledge.v1;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagWrapperVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("_embedded")
	private TagEmbeddedVO embedded;

	public TagWrapperVO() {}

	public TagEmbeddedVO getEmbedded() {
		return embedded;
	}

	public void setEmbedded(TagEmbeddedVO embedded) {
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
		TagWrapperVO other = (TagWrapperVO) obj;
		return Objects.equals(embedded, other.embedded);
	}
	
}
