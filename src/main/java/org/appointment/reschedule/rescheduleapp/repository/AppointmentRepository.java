package org.appointment.reschedule.rescheduleapp.repository;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends  JpaRepository<Appointment, Integer>{

}
