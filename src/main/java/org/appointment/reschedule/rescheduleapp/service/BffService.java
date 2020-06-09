package org.appointment.reschedule.rescheduleapp.service;

import java.util.Optional;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.appointment.reschedule.rescheduleapp.exception.ResourceNotFoundException;

public interface BffService {

	Optional<Appointment> findById(int id) throws ResourceNotFoundException;

	Appointment save(Appointment appointment);
	
}
