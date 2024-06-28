package br.com.hd.integrationtests.data.vo.knowledge.v1;

import java.io.Serializable;
import java.util.Objects;

public class SoftwareVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long key;
	private String description;

	public SoftwareVO() {}

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
		return Objects.hash(description, key);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SoftwareVO other = (SoftwareVO) obj;
		return Objects.equals(description, other.description) && Objects.equals(key, other.key);
	}
	
}
