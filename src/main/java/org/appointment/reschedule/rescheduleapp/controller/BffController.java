package org.appointment.reschedule.rescheduleapp.controller;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.appointment.reschedule.rescheduleapp.dto.Member;
import org.appointment.reschedule.rescheduleapp.exception.ResourceNotFoundException;
import org.appointment.reschedule.rescheduleapp.service.BffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
		System.out.println("in rest service***************:" + reqPayLoad);

		Appointment appt = bffService.findById(reqPayLoad.getId());
		// :12345
		// Authorization:pogpoerjgpoom3or34p3vjo4jp
		if (appt != null) {
			// Validate token of the request
			Member members = bffService.findByMemId(appt.getMemberId());

			if (members.getToken().equals(reqPayLoad.getToken())) {
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
		// TODO: Implementation by corresponding team
		return bffService.findById(reqPayLoad.getId());
	}

	@RequestMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Appointment cancelAppointmentSlot(@RequestBody Appointment reqPayLoad) {
		log.info("cancelAppointmentSlot started{}", reqPayLoad.getId());
		System.out.println("cancel in rest service***************:" + reqPayLoad);
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
		}
		// Update the cancelled flag for appointment.
		bffService.save(appt);
		return appt;
	}

}
