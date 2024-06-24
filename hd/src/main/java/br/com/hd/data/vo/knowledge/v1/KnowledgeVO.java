package br.com.hd.data.vo.knowledge.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import br.com.hd.model.knowledge.v1.SoftwareKnwl;
import br.com.hd.model.knowledge.v1.TagKnwl;

public class KnowledgeVO extends RepresentationModel<KnowledgeVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long key;
	private String title;
	private String description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(content, description, key, software, tags, title);
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
		KnowledgeVO other = (KnowledgeVO) obj;
		return Objects.equals(content, other.content) && Objects.equals(description, other.description)
				&& Objects.equals(key, other.key) && Objects.equals(software, other.software)
				&& Objects.equals(tags, other.tags) && Objects.equals(title, other.title);
	}

}
