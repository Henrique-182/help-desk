package br.com.hd.data.vo.knowledge.v1;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

public class TagVO extends RepresentationModel<TagVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long key;
	private String description;
	
	public TagVO() {}
	
	public Long getKey() {
		return key;
	}
	
	public void setKey(Long key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(description, key);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TagVO other = (TagVO) obj;
		return Objects.equals(description, other.description) && Objects.equals(key, other.key);
	}
	
}
