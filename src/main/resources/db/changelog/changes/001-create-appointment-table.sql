--liquibase formatted sql

--changeset system:001-create-appointment-table
CREATE TABLE appointment (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    start_date TIMESTAMP,
    duration INTERVAL
);
