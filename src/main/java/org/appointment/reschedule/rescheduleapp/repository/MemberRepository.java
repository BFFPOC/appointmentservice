package org.appointment.reschedule.rescheduleapp.repository;

import org.appointment.reschedule.rescheduleapp.dto.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends  JpaRepository<Member, Integer>{

}
