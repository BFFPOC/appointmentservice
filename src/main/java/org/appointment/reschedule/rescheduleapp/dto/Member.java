package org.appointment.reschedule.rescheduleapp.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER")
public class Member {

	@Id
	@Column(name = "MEM_ID")
	private int memberId;
	@Column(name = "MEM_NAME")
	private String memName;
	@Column(name = "MEM_ADDRESS")
	private String memAddr;
	@Column(name = "MEM_CITY")
	private String memCity;
	@Column(name = "TOKEN")
	private String token;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "MEM_ID", nullable = false)
	private Appointment appointment;

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Member() {

	}

	public int getId() {
		return memberId;
	}

	public void setId(int id) {
		this.memberId = id;
	}

	public String getMemName() {
		return memName;
	}

	public void setMemName(String memName) {
		this.memName = memName;
	}

	public String getMemAddr() {
		return memAddr;
	}

	public void setMemAddr(String memAddr) {
		this.memAddr = memAddr;
	}

	public String getMemCity() {
		return memCity;
	}

	public void setMemCity(String memCity) {
		this.memCity = memCity;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
