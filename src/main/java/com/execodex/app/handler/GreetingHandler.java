package com.execodex.app.handler;

import com.execodex.app.domain.Appointment;
import com.execodex.app.service.AppointmentService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {
    private final AppointmentService appointmentService;

    public GreetingHandler(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    public Mono<ServerResponse> handleHello(ServerRequest request) {
        return ServerResponse.ok().bodyValue("Hello from APP");
    }

    public Mono<ServerResponse> handleAppointment(ServerRequest serverRequest) {
        //
        Flux<Appointment> allAppointments = appointmentService.getAllAppointments();
        return ServerResponse.ok().body(allAppointments, Appointment.class);
    }
}
