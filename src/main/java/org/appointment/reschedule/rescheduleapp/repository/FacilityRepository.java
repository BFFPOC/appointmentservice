package org.appointment.reschedule.rescheduleapp.repository;

import org.appointment.reschedule.rescheduleapp.dto.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends  JpaRepository<Facility, Integer>{

}
