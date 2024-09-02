package br.com.hd.model.chat.room.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import br.com.hd.model.chat.message.v1.MessageType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(schema = "CHAT", name = "TB_MESSAGE")
public class MessageRoom implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Long key;
	
	@ManyToOne
	@JoinColumn(name = "FK_USER")
	private UserRoom user;
	
	@Column(name = "CONTENT_TYPE", nullable = false)
	private MessageType type;
	
	@Column(name = "CONTENT", nullable = false)
	private String content;
	
	@Column(name = "CREATE_DATETIME", nullable = false)
	private Date createDatetime;
	
	@Column(name = "UPDATE_DATETIME", nullable = false)
	private Date updateDatetime;
	
	@Column(name = "DELETE_DATETIME", nullable = false)
	private Date deleteDatetime;
	
	@ManyToOne
    @JoinColumn(name = "FK_ROOM", nullable = false)
    private Room room;
	
	public MessageRoom() {}
	
	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public UserRoom getUser() {
		return user;
	}
	
	public void setUser(UserRoom user) {
		this.user = user;
	}
	
	public MessageType getType() {
		return type;
	}
	
	public void setType(MessageType type) {
		this.type = type;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Date getUpdateDatetime() {
		return updateDatetime;
	}

	public void setUpdateDatetime(Date updateDatetime) {
		this.updateDatetime = updateDatetime;
	}

	public Date getDeleteDatetime() {
		return deleteDatetime;
	}

	public void setDeleteDatetime(Date deleteDatetime) {
		this.deleteDatetime = deleteDatetime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content, createDatetime, deleteDatetime, key, room, type, updateDatetime, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageRoom other = (MessageRoom) obj;
		return Objects.equals(content, other.content) && Objects.equals(createDatetime, other.createDatetime)
				&& Objects.equals(deleteDatetime, other.deleteDatetime) && Objects.equals(key, other.key)
				&& Objects.equals(room, other.room) && type == other.type
				&& Objects.equals(updateDatetime, other.updateDatetime) && Objects.equals(user, other.user);
	}

}
