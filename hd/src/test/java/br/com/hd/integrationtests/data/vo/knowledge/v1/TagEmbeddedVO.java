package br.com.hd.integrationtests.data.vo.knowledge.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagEmbeddedVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("tagVOList")
	private List<TagVO> tags;

	public TagEmbeddedVO() {}

	public List<TagVO> getTags() {
		return tags;
	}

	public void setTags(List<TagVO> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		return Objects.hash(tags);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagEmbeddedVO other = (TagEmbeddedVO) obj;
		return Objects.equals(tags, other.tags);
	}
	
}
