package br.com.hd.model.knowledge.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "KNOWLEDGE", name = "TB_KNOWLEDGE")
public class Knowledge implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;
	
	@Column(name = "TITLE", nullable = false, unique = true)
	private String title;
	
	@ManyToOne
	@JoinColumn(name = "FK_SOFTWARE")
	private SoftwareKnwl software;
	
	@Column(name = "CONTENT", nullable = false)
	private String content;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(	
		schema = "KNOWLEDGE",
		name = "TB_KNOWLEDGE_TAG",
		joinColumns = @JoinColumn(name = "FK_KNOWLEDGE"),
		inverseJoinColumns = @JoinColumn(name = "FK_TAG")
	)
	private List<TagKnwl> tags;

	public Knowledge() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		return Objects.hash(content, id, software, tags, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Knowledge other = (Knowledge) obj;
		return Objects.equals(content, other.content) && Objects.equals(id, other.id)
				&& Objects.equals(software, other.software) && Objects.equals(tags, other.tags)
				&& Objects.equals(title, other.title);
	}
	
}
