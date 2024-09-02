package br.com.hd.integrationtests.data.vo.chat.message.v1;

import java.io.Serializable;
import java.util.Objects;

public class MessageUpdateVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String content;

	public MessageUpdateVO() {}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		return Objects.hash(content);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageUpdateVO other = (MessageUpdateVO) obj;
		return Objects.equals(content, other.content);
	}
	
}
