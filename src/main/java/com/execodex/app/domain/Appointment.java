package com.execodex.app.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("appointment")
public class Appointment {
    @Id
    private Long id;
    private String title;
}
