package br.com.hd.data.vo.chat.message.v1;

import java.io.Serializable;
import java.util.Objects;

import br.com.hd.model.chat.message.v1.MessageType;
import jakarta.validation.constraints.NotNull;

public class MessageCreationVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Roomkey can't be null!")
	private Long roomKey;
	
	@NotNull(message = "Type can't be null!")
	private MessageType type;
	
	@NotNull(message = "Content can't be null!")
	private String content;
	
	public MessageCreationVO() {}

	public Long getRoomKey() {
		return roomKey;
	}

	public void setRoomKey(Long roomKey) {
		this.roomKey = roomKey;
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

	@Override
	public int hashCode() {
		return Objects.hash(content, roomKey, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageCreationVO other = (MessageCreationVO) obj;
		return Objects.equals(content, other.content) && Objects.equals(roomKey, other.roomKey) && type == other.type;
	}

}
