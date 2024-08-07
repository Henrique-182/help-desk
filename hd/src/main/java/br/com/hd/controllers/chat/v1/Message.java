package br.com.hd.controllers.chat.v1;

import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	private String from;
	private String message;
	
	
	public Message() {
	}
	public Message(String from, String message) {
		this.from = from;
		this.message = message;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public int hashCode() {
		return Objects.hash(from, message);
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
		return Objects.equals(from, other.from) && Objects.equals(message, other.message);
	}
	
	
}
