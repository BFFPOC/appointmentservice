package org.appointment.reschedule.rescheduleapp.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER" )
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "MEM_ID")
	private int id;
	@Column(name = "MEM_NAME")
	private String memName;
	@Column(name = "MEM_ADDRESS")
	private String memAddr;
	@Column(name = "MEM_CITY")
	private String memCity;
	@Column(name = "TOKEN")
	private String token;

	public Member() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
