package org.appointment.reschedule.rescheduleapp.repository;

import java.util.List;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Integer> {

	List<Appointment> findByMemberId(int memberId);

	public static final String FIND_SLOTS = "SELECT APPT_SLOT FROM APPOINTMENT";

	@Query(value = FIND_SLOTS, nativeQuery = true)
	public List<String> findAppointmentSlot();

	public List<Appointment> findByMemberIdAndFacilityId(int memberId, int facilityId);

}
