package com.execodex.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.Period;


@Table("appointment")
public record Appointment(
        @Id
        Long id,
        String title,
        LocalDateTime startDate,
        Duration duration
) {
}
