package br.com.hd.model.chat.room.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import br.com.hd.model.chat.message.v1.MessageType;
import br.com.hd.model.chat.message.v1.UserMssg;
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
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "FK_USER")
	private UserMssg user;
	
	@Column(name = "CONTENT_TYPE", nullable = false)
	private MessageType type;
	
	@Column(name = "CONTENT", nullable = false)
	private String content;
	
	@Column(name = "SENT_DATETIME", nullable = false)
	private Date sentDatetime;
	
	public MessageRoom() {}
	
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
	
	public Date getSentDatetime() {
		return sentDatetime;
	}
	
	public void setSentDatetime(Date sentDatetime) {
		this.sentDatetime = sentDatetime;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(content, id, sentDatetime, type, user);
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
		return Objects.equals(content, other.content) && Objects.equals(id, other.id)
				&& Objects.equals(sentDatetime, other.sentDatetime) && type == other.type
				&& Objects.equals(user, other.user);
	}

}
