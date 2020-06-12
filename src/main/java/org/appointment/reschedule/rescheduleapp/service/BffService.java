package org.appointment.reschedule.rescheduleapp.service;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.appointment.reschedule.rescheduleapp.dto.Facility;
import org.appointment.reschedule.rescheduleapp.dto.Member;

public interface BffService {

	Appointment findById(int id) ;

	Appointment save(Appointment appointment);
	
	Member findByMemId(int memberId);
	
	Facility findByFacilityId(int facilityId);
	
	Appointment findByFacilityIdAndMemberId(int faciltityId,int memberId);
	
}
