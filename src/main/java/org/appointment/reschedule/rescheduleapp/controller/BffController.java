package org.appointment.reschedule.rescheduleapp.controller;

import java.util.List;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.appointment.reschedule.rescheduleapp.dto.Member;
import org.appointment.reschedule.rescheduleapp.exception.ResourceNotFoundException;
import org.appointment.reschedule.rescheduleapp.service.BffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BffController {

	private static final Logger log = LoggerFactory.getLogger(BffController.class);

	@Autowired
	BffService bffService;

	@RequestMapping(value = "/reschedule", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Appointment updateAppointmentSlot(@RequestBody Appointment reqPayLoad,
			@RequestHeader("X-correlationid") String correaltionId) {
		log.info("updateAppointmentSlot started{}", reqPayLoad.getId());
		Appointment appt = bffService.findById(reqPayLoad.getId());

		if (appt != null) {
			// Validate token of the request
			Member members = bffService.findByMemId(appt.getMemberId());

			if (members.getToken().equals(reqPayLoad.getToken())) {
				if (appt.isCancelled()) {
					throw new ResourceNotFoundException("Appointment is already cancelled", reqPayLoad.getMemberId());
				}
				appt.setAppointmentSlot(reqPayLoad.getAppointmentSlot());
			} else {
				throw new ResourceNotFoundException("token is not valid", appt.getMemberId());
			}

			// set correlation id from the request payload
			appt.setCorrealtionId(correaltionId);
		}
		// Update the timeslot for appointment.
		bffService.save(appt);
		return appt;
	}

	@RequestMapping(value = "/schedule/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public ResponseEntity<Appointment> getAppointmentSlot(@PathVariable(value = "id") int appointmentId) {
		Appointment appointment = null;
		try {
			appointment = bffService.findById(appointmentId);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Appointment Id not found", appointmentId);
		}

		return ResponseEntity.ok(appointment);
	}

	@RequestMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Appointment scheduleApp(@RequestBody Appointment reqPayLoad) {
		List<String> findAppointmentSlot = bffService.findAppointmentSlot();
		for (String slot : findAppointmentSlot) {
			if(slot.equals(reqPayLoad.getAppointmentSlot())){
				throw new ResourceNotFoundException(" Appointment slot is not available or same", reqPayLoad.getMemberId());				
			}			
			
		} 
		
		// Verify Member Id and Facility Id from the request
		Member members = null;
		if (reqPayLoad.getMemberId() != 0) {
			members = bffService.findByMemId(reqPayLoad.getMemberId());
		}
		if (reqPayLoad.getFacilityId() != 0) {
			bffService.findByFacilityId(reqPayLoad.getFacilityId());
		}
		
		if (!members.getToken().equals(reqPayLoad.getToken())) {
			throw new ResourceNotFoundException("token is not valid", members.getId());
		}
		Appointment app = new Appointment();
		app.setFacilityId(reqPayLoad.getFacilityId());
		app.setMemberId(reqPayLoad.getMemberId());
		app.setAppointmentSlot(reqPayLoad.getAppointmentSlot());
		try {
			bffService.save(app);
		} catch (DuplicateKeyException DuplicateKeyException) {
			throw new ResourceNotFoundException("DuplicateKey Found in Appointment", app.getId());
		}

		return bffService.findById(app.getId());
	}

	@RequestMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Appointment cancelAppointmentSlot(@RequestBody Appointment reqPayLoad,
			@RequestHeader("X-correlationid") String correaltionId) {
		log.info("cancelAppointmentSlot started{}", reqPayLoad.getId());
		Appointment appt = bffService.findById(reqPayLoad.getId());
		if (appt != null) {
			// Validate token of the request
			Member members = bffService.findByMemId(reqPayLoad.getMemberId());
			if (members.getToken().equals(reqPayLoad.getToken())) {
				if (!appt.isCancelled()) {
					appt.setCancelled(true);
				} else {
					throw new ResourceNotFoundException("Appointment is already cancelled", reqPayLoad.getMemberId());
				}
			} else {
				throw new ResourceNotFoundException("token is not valid", appt.getMemberId());
			}
			// set correlation id from the request payload
			appt.setCorrealtionId(correaltionId);
		}
		// Update the cancelled flag for appointment.
		bffService.save(appt);
		return appt;
	}

}
