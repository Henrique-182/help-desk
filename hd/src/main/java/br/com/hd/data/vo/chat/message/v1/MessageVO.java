package br.com.hd.data.vo.chat.message.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import br.com.hd.model.chat.message.v1.MessageType;
import br.com.hd.model.chat.message.v1.RoomMssg;
import br.com.hd.model.chat.message.v1.UserMssg;

public class MessageVO extends RepresentationModel<MessageVO> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long key;
	private UserMssg user;
	private RoomMssg room;
	private MessageType type;
	private String content;
	private Date createDatetime;
	private Date updateDatetime;
	private Date deleteDatetime;
	
	public MessageVO() {}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
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
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ Objects.hash(content, createDatetime, deleteDatetime, key, room, type, updateDatetime, user);
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
		MessageVO other = (MessageVO) obj;
		return Objects.equals(content, other.content) && Objects.equals(createDatetime, other.createDatetime)
				&& Objects.equals(deleteDatetime, other.deleteDatetime) && Objects.equals(key, other.key)
				&& Objects.equals(room, other.room) && type == other.type
				&& Objects.equals(updateDatetime, other.updateDatetime) && Objects.equals(user, other.user);
	}

}
