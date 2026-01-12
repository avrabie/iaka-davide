--liquibase formatted sql

--changeset system:002-insert-initial-appointments
INSERT INTO appointment (title, start_date, duration) VALUES ('Doctor Appointment', '2021-01-02T10:15:25', '1 hour 30 minutes');
INSERT INTO appointment (title, start_date) VALUES ('Dentist Visit', '2021-01-02');
INSERT INTO appointment (title, start_date) VALUES ('Business Meeting', '2021-01-03');
INSERT INTO appointment (title) VALUES ('Gym Session');
INSERT INTO appointment (title) VALUES ('Mechanic Appointment');
INSERT INTO appointment (title) VALUES ('Lawer Appointment');
INSERT INTO appointment (title) VALUES ('Tax Adviser Consultation');
INSERT INTO appointment (title) VALUES ('G20 Davos Summit');
