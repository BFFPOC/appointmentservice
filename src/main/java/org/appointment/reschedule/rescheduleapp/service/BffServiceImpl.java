package org.appointment.reschedule.rescheduleapp.service;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.appointment.reschedule.rescheduleapp.dto.Facility;
import org.appointment.reschedule.rescheduleapp.dto.Member;
import org.appointment.reschedule.rescheduleapp.exception.ResourceNotFoundException;
import org.appointment.reschedule.rescheduleapp.repository.AppointmentRepository;
import org.appointment.reschedule.rescheduleapp.repository.FacilityRepository;
import org.appointment.reschedule.rescheduleapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BffServiceImpl implements BffService{
	
	@Autowired
	AppointmentRepository appointmentRepository;
	
	@Autowired
	MemberRepository membersRepository;
	
	@Autowired
	FacilityRepository facilitiesRepository;
	

	@Override
	public Appointment findById(int id) {

		return appointmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Appointment Id not Found", id));

	}

	@Override
	public Appointment save(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	@Override
	public Member findByMemId(int memberId) {
		return membersRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Member Id not Found",memberId));
	}

	@Override
	public Facility findByFacilityId(int facilityId) {
		return facilitiesRepository.findById(facilityId).orElseThrow(() -> new ResourceNotFoundException("Facility Id not Found",facilityId));
	}

	@Override
	public Appointment findByFacilityIdAndMemberId(int faciltityId, int memberId) {
		return appointmentRepository.findByFacilityIdAndMemberId(faciltityId, memberId);
	}

}
