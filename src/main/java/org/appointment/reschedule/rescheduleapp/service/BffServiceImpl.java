package org.appointment.reschedule.rescheduleapp.service;

import java.util.Optional;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.appointment.reschedule.rescheduleapp.exception.ResourceNotFoundException;
import org.appointment.reschedule.rescheduleapp.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BffServiceImpl implements BffService{
	
	@Autowired
	AppointmentRepository appointmentRepository;
	

	@Override
	public Optional<Appointment> findById(int id) throws ResourceNotFoundException {

		Optional<Appointment> appointment = appointmentRepository.findById(id);
		if (appointment == null) {
			throw new ResourceNotFoundException("Appointment Id is not Avalilable");
		}
		return appointment;
	}

	@Override
	public Appointment save(Appointment appointment) {
		// TODO Auto-generated method stub
		return null;
	}

}
