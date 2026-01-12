package com.execodex.app.repos;

import com.execodex.app.domain.Appointment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepo extends ReactiveCrudRepository<Appointment, Long> {
}
