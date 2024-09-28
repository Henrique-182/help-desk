package br.com.hd.integrationtests.data.vo.chat.room.v1;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import br.com.hd.model.chat.room.v1.MessageRoom;
import br.com.hd.model.chat.room.v1.RoomPriority;
import br.com.hd.model.chat.room.v1.RoomStatus;
import br.com.hd.model.chat.room.v1.SectorRoom;
import br.com.hd.model.chat.room.v1.UserRoom;

public class RoomVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long key;
	private Integer code;
	private RoomStatus status;
	private String reason;
	private String solution;
	private Date createDatetime;
	private Date closeDatetime;
	private RoomPriority priority;
	private UserRoom customer;
	private UserRoom employee;
	private SectorRoom sector;
	private List<MessageRoom> messages;
	
	public RoomVO() {}

	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public RoomStatus getStatus() {
		return status;
	}

	public void setStatus(RoomStatus status) {
		this.status = status;
	}
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Date getCreateDatetime() {
		return createDatetime;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public Date getCloseDatetime() {
		return closeDatetime;
	}

	public void setCloseDatetime(Date closeDatetime) {
		this.closeDatetime = closeDatetime;
	}
	
	public RoomPriority getPriority() {
		return priority;
	}

	public void setPriority(RoomPriority priority) {
		this.priority = priority;
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
		return Objects.hash(closeDatetime, code, createDatetime, customer, employee, key, messages, priority, reason,
				sector, solution, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomVO other = (RoomVO) obj;
		return Objects.equals(closeDatetime, other.closeDatetime) && Objects.equals(code, other.code)
				&& Objects.equals(createDatetime, other.createDatetime) && Objects.equals(customer, other.customer)
				&& Objects.equals(employee, other.employee) && Objects.equals(key, other.key)
				&& Objects.equals(messages, other.messages) && Objects.equals(priority, other.priority)
				&& Objects.equals(reason, other.reason) && Objects.equals(sector, other.sector)
				&& Objects.equals(solution, other.solution) && status == other.status;
	}

}
