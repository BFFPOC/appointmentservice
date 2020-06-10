package org.appointment.reschedule.rescheduleapp.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "APPOINTMENT")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "APPT_ID")
	private Integer id;
	@Column(name = "APPT_SLOT")
	private String appointmentSlot;
	@Column(name = "MEMBER_ID")
	private int memberId;
	@Column(name = "FACILITY_ID")
	private int facilityId;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String token;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String correaltionId;

	public Appointment() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppointmentSlot() {
		return appointmentSlot;
	}

	public void setAppointmentSlot(String appointmentSlot) {
		this.appointmentSlot = appointmentSlot;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	public String getCorrealtionId() {
		return correaltionId;
	}

	public void setCorrealtionId(String correaltionId) {
		this.correaltionId = correaltionId;
	}

	@Override
	public String toString() {
		return "Appintment{" + ", memberid='" + memberId + '\'' + ", appintmentslot=" + appointmentSlot + '}';
	}

}
