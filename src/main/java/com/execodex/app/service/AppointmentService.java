package com.execodex.app.service;

import com.execodex.app.domain.Appointment;
import com.execodex.app.repos.AppointmentRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AppointmentService {

    private final AppointmentRepo appointmentRepo;

    public AppointmentService(AppointmentRepo appointmentRepo) {
        this.appointmentRepo = appointmentRepo;
    }

    public Mono<Appointment> saveAppointment(Appointment appointment){
        return appointmentRepo.save(appointment);
    }
    public Mono<Appointment> getAppointmentById(Long id){
        return appointmentRepo.findById(id);
    }

    public Flux<Appointment> getAllAppointments(){
        return appointmentRepo.findAll();
    }

}
