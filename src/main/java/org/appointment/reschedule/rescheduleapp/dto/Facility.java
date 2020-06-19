package org.appointment.reschedule.rescheduleapp.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FACILITY")
public class Facility {

	@Id
	@Column(name = "FAC_ID")
	private int id;
	@Column(name = "FAC_NAME")
	private String facName;
	@Column(name = "FAC_ADDRESS")
	private String facAddr;
	@Column(name = "FAC_CITY")
	private String facCity;

	

	public Facility() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFacName() {
		return facName;
	}

	public void setFacName(String facName) {
		this.facName = facName;
	}

	public String getFacAddr() {
		return facAddr;
	}

	public void setFacAddr(String facAddr) {
		this.facAddr = facAddr;
	}

	public String getFacCity() {
		return facCity;
	}

	public void setFacCity(String facCity) {
		this.facCity = facCity;
	}

	
}
