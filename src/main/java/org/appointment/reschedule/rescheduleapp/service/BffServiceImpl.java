package org.appointment.reschedule.rescheduleapp.service;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.appointment.reschedule.rescheduleapp.dto.Member;
import org.appointment.reschedule.rescheduleapp.exception.ResourceNotFoundException;
import org.appointment.reschedule.rescheduleapp.repository.AppointmentRepository;
import org.appointment.reschedule.rescheduleapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BffServiceImpl implements BffService{
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Autowired
	MemberRepository membersRepository;
	

	@Override
	public Appointment findById(int id) {

		/*Optional<Appointment> appointment = appointmentRepository.findById(id);
		if (appointment == null) {
			throw new ResourceNotFoundException("Appointment Id is not Avalilable");
		}*/
		 return appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment Id not Found",id));

	}

	@Override
	public Appointment save(Appointment appointment) {
		// TODO Auto-generated method stub
		return appointmentRepository.save(appointment);
	}

	@Override
	public Member findByMemId(int memberId) {
		return membersRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Member Id not Found",memberId));
	}

}
