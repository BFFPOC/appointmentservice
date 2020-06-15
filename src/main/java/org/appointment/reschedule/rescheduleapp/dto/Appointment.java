package org.appointment.reschedule.rescheduleapp.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "APPOINTMENT")
public class Appointment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence-generator")
	@SequenceGenerator(name = "sequence-generator", sequenceName = "appt_sequence")
	@Column(name = "APPT_ID")
	private int id;
	@Column(name = "APPT_SLOT")
	private String appointmentSlot;
	@Column(name = "MEMBER_ID")
	private int memberId;
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}

	@Column(name = "FACILITY_ID")
	private int facilityId;
	@Column(name = "CANCELLED")
	@Type(type = "numeric_boolean")
	private boolean cancelled;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "appointment")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Member member;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "appointment")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Facility facility;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String token;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Transient
	private String correaltionId;

	public Appointment() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
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
		return "Appointment{" + ", memberid='" + memberId + '\'' + ", appintmentslot=" + appointmentSlot + '}';
	}

}
