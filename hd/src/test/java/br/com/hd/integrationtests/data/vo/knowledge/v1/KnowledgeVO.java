package br.com.hd.integrationtests.data.vo.knowledge.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import br.com.hd.model.knowledge.v1.SoftwareKnwl;
import br.com.hd.model.knowledge.v1.TagKnwl;

public class KnowledgeVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long key;
	private String title;
	private SoftwareKnwl software;
	private String content;
	private List<TagKnwl> tags;
	
	public KnowledgeVO() {}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public SoftwareKnwl getSoftware() {
		return software;
	}

	public void setSoftware(SoftwareKnwl software) {
		this.software = software;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<TagKnwl> getTags() {
		return tags;
	}

	public void setTags(List<TagKnwl> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, key, software, tags, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KnowledgeVO other = (KnowledgeVO) obj;
		return Objects.equals(content, other.content) && Objects.equals(key, other.key)
				&& Objects.equals(software, other.software) && Objects.equals(tags, other.tags)
				&& Objects.equals(title, other.title);
	}

}
