package com.patientmanagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PatientHistoryTest {

    @Test
    public void testNextRecord() {
        Patient patient = new Patient("Patient Test", "Test");

        PatientRecord patientRecord1 = new PatientRecord(LocalDate.now(), "Test1", "Notes");
        PatientRecord patientRecord2 = new PatientRecord(LocalDate.now(), "Test2", "Notes");
        PatientRecord patientRecord3 = new PatientRecord(LocalDate.now(), "Test3", "Notes");

        patient.getPatientHistory().addPatientRecord(patientRecord1);
        patient.getPatientHistory().addPatientRecord(patientRecord2);
        patient.getPatientHistory().addPatientRecord(patientRecord3);

        Assertions.assertSame(patientRecord2, patient.getPatientHistory().nextRecord());
        Assertions.assertSame(patientRecord3, patient.getPatientHistory().nextRecord());
    }

    @Test
    public void testPreviousRecord() {
        Patient patient = new Patient("Patient Test", "Test");

        PatientRecord patientRecord1 = new PatientRecord(LocalDate.now(), "Test1", "Notes");
        PatientRecord patientRecord2 = new PatientRecord(LocalDate.now(), "Test2", "Notes");
        PatientRecord patientRecord3 = new PatientRecord(LocalDate.now(), "Test3", "Notes");

        patient.getPatientHistory().addPatientRecord(patientRecord1);
        patient.getPatientHistory().addPatientRecord(patientRecord2);
        patient.getPatientHistory().addPatientRecord(patientRecord3);

        patient.getPatientHistory().nextRecord();
        patient.getPatientHistory().nextRecord();

        Assertions.assertSame(patientRecord2, patient.getPatientHistory().previousRecord());
        Assertions.assertSame(patientRecord1, patient.getPatientHistory().previousRecord());
    }

    @Test
    public void testNavigationBoundaries() {
        Patient patient = new Patient("Patient Test", "Test");

        PatientRecord patientRecord1 = new PatientRecord(LocalDate.now(), "Test1", "Notes");
        PatientRecord patientRecord2 = new PatientRecord(LocalDate.now(), "Test2", "Notes");

        patient.getPatientHistory().addPatientRecord(patientRecord1);
        patient.getPatientHistory().addPatientRecord(patientRecord2);

        Assertions.assertNull(patient.getPatientHistory().previousRecord());

        patient.getPatientHistory().nextRecord();

        Assertions.assertNull(patient.getPatientHistory().nextRecord());
    }
}
