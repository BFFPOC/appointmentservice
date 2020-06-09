package org.appointment.reschedule.rescheduleapp.controller;

import java.util.Optional;

import org.appointment.reschedule.rescheduleapp.dto.Appointment;
import org.appointment.reschedule.rescheduleapp.exception.ResourceNotFoundException;
import org.appointment.reschedule.rescheduleapp.service.BffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BffController {
	
	
	private static final Logger log = LoggerFactory.getLogger(BffController.class);

	
	@Autowired
	BffService bffService;
	
	@PutMapping("/reschedule/{id}")
	public ResponseEntity<Optional<Appointment>> updateAppointmentSlot(@PathVariable(value = "id") int appointmentId) throws ResourceNotFoundException{
		log.info("updateAppointmentSlot started{}", appointmentId);
	
		Optional<Appointment> appointment;
		
		try {
			appointment	 = bffService.findById(appointmentId);
		}catch(ResourceNotFoundException e) {
			throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Foo Not Found", e);
		}
		
		//TODO : update the timeslot in database.
		// As write operation is failing still needs to work on this.
	    // final Appointment updatedEmployee = bffService.save(user);
	     return new ResponseEntity<>(appointment, HttpStatus.OK);
	}
	
	 @GetMapping("/schedule/{id}")
	public ResponseEntity<Optional<Appointment>> scheduleApp(@PathVariable(value = "id") int appointmentId) {
		// TODO: Implementation by corresponding team
		Optional<Appointment> appointment = null;
		try {
			appointment = bffService.findById(appointmentId);
		} catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Appointment Id not found");
		}
		
		return ResponseEntity.ok(appointment);
	}
	 
	    @PostMapping("/cancel/{id}")
		public ResponseEntity<Optional<Appointment>> scheduleAppt(@PathVariable(value = "id") int appointmentId) {
			// TODO: Implementation by corresponding team
			Optional<Appointment> appointment = null;
			try {
				appointment = bffService.findById(appointmentId);
			} catch (ResourceNotFoundException e) {
				throw new ResourceNotFoundException("Appointment Id not found");
			}
			
			return ResponseEntity.ok(appointment);
		}


}
