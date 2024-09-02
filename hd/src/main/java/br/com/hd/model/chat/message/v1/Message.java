package br.com.hd.model.chat.message.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "FK_USER")
	private UserMssg user;
	
	@ManyToOne
	@JoinColumn(name = "FK_ROOM")
	private RoomMssg room;
	
	@Column(name = "CONTENT_TYPE", nullable = false)
	private MessageType type;
	
	@Column(name = "CONTENT", nullable = false)
	private String content;
	
	@Column(name = "CREATE_DATETIME", nullable = false)
	private Date createDatetime;
	
	@Column(name = "UPDATE_DATETIME", nullable = true)
	private Date updateDatetime;
	
	@Column(name = "DELETE_DATETIME", nullable = true)
	private Date deleteDatetime;
	
	public Message() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserMssg getUser() {
		return user;
	}

	public void setUser(UserMssg user) {
		this.user = user;
	}
	
	public RoomMssg getRoom() {
		return room;
	}

	public void setRoom(RoomMssg room) {
		this.room = room;
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
		return Objects.hash(content, createDatetime, deleteDatetime, id, room, type, updateDatetime, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		return Objects.equals(content, other.content) && Objects.equals(createDatetime, other.createDatetime)
				&& Objects.equals(deleteDatetime, other.deleteDatetime) && Objects.equals(id, other.id)
				&& Objects.equals(room, other.room) && type == other.type
				&& Objects.equals(updateDatetime, other.updateDatetime) && Objects.equals(user, other.user);
	}

}
