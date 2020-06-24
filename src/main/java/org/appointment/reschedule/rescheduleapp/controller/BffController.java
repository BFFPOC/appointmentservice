package org.appointment.reschedule.rescheduleapp.controller;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.appointment.reschedule.rescheduleapp.dto.JwtToken;
import org.appointment.reschedule.rescheduleapp.dto.Member;
import org.appointment.reschedule.rescheduleapp.exception.ResourceNotFoundException;
import org.appointment.reschedule.rescheduleapp.repository.AppointmentRepository;
import org.appointment.reschedule.rescheduleapp.service.BffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class BffController {

	private static final Logger log = LoggerFactory.getLogger(BffController.class);

	@Autowired
	BffService bffService;

	@Autowired
	AppointmentRepository appointmentRepository;

	@RequestMapping(value = "/reschedule", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Appointment updateAppointmentSlot(@RequestBody Appointment reqPayLoad,
			@RequestHeader("X-correlationid") String correaltionId) {
		log.info("updateAppointmentSlot started{}", reqPayLoad.getId());
		Appointment appt = bffService.findById(reqPayLoad.getId());

		if (appt != null) {
			// Validate token of the request
			Member members = bffService.findByMemId(appt.getMemberId());

			if (members.getToken().equals(validateToken(reqPayLoad.getToken()))) {
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

	@RequestMapping(value = "/getAppointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
	public List<Appointment> getAppointments(@PathVariable(value = "id") int memberId) {
		log.info("getAppointments started{}", memberId);
		List<Appointment> appointment = null;
		try {
			appointment = bffService.findByMemberId(memberId);
			if (appointment == null || appointment.isEmpty()) {
				log.info("getAppointments list size", appointment.size());
				throw new ResourceNotFoundException("Appointments are not available for the given member Id", memberId);
			}
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Member Id not found", memberId);
		}
		log.info("getAppointments ended{}", memberId);
		return appointment;
	}

	@RequestMapping(value = "/schedule", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	public Appointment scheduleApp(@RequestBody Appointment reqPayLoad,
			@RequestHeader("X-correlationid") String correaltionId) {
		List<String> findAppointmentSlot = bffService.findAppointmentSlot();
		for (String slot : findAppointmentSlot) {
			if (slot.equals(reqPayLoad.getAppointmentSlot())) {
				throw new ResourceNotFoundException(" Appointment slot is not available or same",
						reqPayLoad.getMemberId());
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

		if (!members.getToken().equals(validateToken(reqPayLoad.getToken()))) {
			throw new ResourceNotFoundException("token is not valid", members.getId());
		}

		List<Appointment> findByMemberIdAndFacilityId = bffService.findByMemberIdAndFacilityId(reqPayLoad.getMemberId(),
				reqPayLoad.getFacilityId());

		Appointment appointmentData = null;
		if (findByMemberIdAndFacilityId.size() == 0) {
			Appointment app = new Appointment();
			app.setFacilityId(reqPayLoad.getFacilityId());
			app.setMemberId(reqPayLoad.getMemberId());
			app.setAppointmentSlot(reqPayLoad.getAppointmentSlot());
			app.setCorrealtionId(correaltionId);
			try {
				appointmentData = bffService.save(app);
			} catch (DuplicateKeyException DuplicateKeyException) {
				throw new ResourceNotFoundException("DuplicateKey Found in Appointment", app.getId());
			}

		} else {
			for (Appointment appointment : findByMemberIdAndFacilityId) {
				if (appointment.getFacilityId() == reqPayLoad.getFacilityId()
						&& appointment.getMemberId() == reqPayLoad.getMemberId() && !appointment.isCancelled()) {
					appointment.setAppointmentSlot(reqPayLoad.getAppointmentSlot());
					appointment.setCorrealtionId(correaltionId);
					appointmentData = bffService.save(appointment);
				} else if (appointment.getFacilityId() == reqPayLoad.getFacilityId()
						&& appointment.getMemberId() == reqPayLoad.getMemberId() && appointment.isCancelled()) {
					Appointment app = new Appointment();
					app.setFacilityId(reqPayLoad.getFacilityId());
					app.setMemberId(reqPayLoad.getMemberId());
					app.setAppointmentSlot(reqPayLoad.getAppointmentSlot());
					app.setCorrealtionId(correaltionId);
					try {
						appointmentData = bffService.save(app);
					} catch (DuplicateKeyException DuplicateKeyException) {
						throw new ResourceNotFoundException("DuplicateKey Found in Appointment", app.getId());
					}

				}

			}
		}

		return bffService.findById(appointmentData.getId());
	}

	@RequestMapping(value = "/cancel", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
	public Appointment cancelAppointmentSlot(@RequestBody Appointment reqPayLoad,
			@RequestHeader("X-correlationid") String correaltionId) {
		log.info("cancelAppointmentSlot started{}", reqPayLoad.getId());
		Appointment appt = bffService.findById(reqPayLoad.getId());
		if (appt != null) {
			// Validate token of the request
			Member members = bffService.findByMemId(reqPayLoad.getMemberId());
			if (members.getToken().equals(validateToken(reqPayLoad.getToken()))) {
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

	public String validateToken(String token) {
		log.info("input token{}::", token);
		String[] split_string = token.split("\\.");
		String base64EncodedHeader = split_string[0];
		String base64EncodedBody = split_string[1];
		log.info("~~~~~~~~~ JWT Header ~~~~~~~");
		Base64 base64Url = new Base64(true);
		String header = new String(base64Url.decode(base64EncodedHeader));
		log.info("JWT Header : " + header);
		log.info("~~~~~~~~~ JWT Body ~~~~~~~");
		String body = new String(base64Url.decode(base64EncodedBody));
		log.info("JWT Body {}: ", body);
		ObjectMapper mapper = new ObjectMapper();
		JwtToken readValue = null;
		try {
			readValue = mapper.readValue(body, JwtToken.class);
		} catch (JsonProcessingException e) {
			log.error("json mapping  exception:{}", e.getMessage());
		}
		log.info(" valid token after decoding:::{} ", readValue.getToken());
		return readValue.getToken();
	}

}
