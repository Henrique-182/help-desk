package br.com.hd.model.chat.room.v1;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(schema = "CHAT", name = "TB_ROOM")
public class Room implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;
	
	@Column(name = "CODE", nullable = false, unique = true)
	private Integer code;
	
	@ManyToOne
	@JoinColumn(name = "FK_USER_CUSTOMER")
	private UserRoom customer;
	
	@ManyToOne
	@JoinColumn(name = "FK_USER_EMPLOYEE")
	private UserRoom employee;
	
	@ManyToOne
	@JoinColumn(name = "FK_SECTOR")
	private SectorRoom sector;
	
	@OneToMany
	@JoinColumn
	private List<MessageRoom> messages;

	public Room() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public UserRoom getCustomer() {
		return customer;
	}

	public void setCustomer(UserRoom customer) {
		this.customer = customer;
	}

	public UserRoom getEmployee() {
		return employee;
	}

	public void setEmployee(UserRoom employee) {
		this.employee = employee;
	}

	public SectorRoom getSector() {
		return sector;
	}

	public void setSector(SectorRoom sector) {
		this.sector = sector;
	}
	
	public List<MessageRoom> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageRoom> messages) {
		this.messages = messages;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, customer, employee, id, messages, sector);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Room other = (Room) obj;
		return Objects.equals(code, other.code) && Objects.equals(customer, other.customer)
				&& Objects.equals(employee, other.employee) && Objects.equals(id, other.id)
				&& Objects.equals(messages, other.messages) && Objects.equals(sector, other.sector);
	}

}
