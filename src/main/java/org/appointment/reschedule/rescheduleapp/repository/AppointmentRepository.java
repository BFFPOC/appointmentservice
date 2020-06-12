package org.appointment.reschedule.rescheduleapp.repository;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends  CrudRepository<Appointment, Integer>{
	
	Appointment findByFacilityIdAndMemberId(int facilityId,int memberId);

}
