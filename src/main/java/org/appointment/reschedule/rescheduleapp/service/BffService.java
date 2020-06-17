package org.appointment.reschedule.rescheduleapp.service;

import java.util.List;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.appointment.reschedule.rescheduleapp.dto.Facility;
import org.appointment.reschedule.rescheduleapp.dto.Member;

public interface BffService {

	Appointment findById(int id);

	Appointment save(Appointment appointment);

	Member findByMemId(int memberId);

	Facility findByFacilityId(int facilityId);

	List<Appointment> findByMemberIdAndCancelledTrue(int memberId);

	public List<String> findAppointmentSlot();

	public List<Appointment> findByMemberIdAndFacilityId(int memberId, int facilityId);

}
