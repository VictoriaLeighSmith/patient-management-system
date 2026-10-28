package com.patientmanagement;

import java.util.ArrayList;

public class PatientManager {
    private final ArrayList<Patient> registeredPatients;

    public PatientManager() {
        this.registeredPatients = new ArrayList<>();
    }

    public void registerPatient(Patient patient) {
        this.registeredPatients.add(patient);
    }

    public Patient getPatientByID(int patientID) {
        for (Patient patient : this.registeredPatients) {
            if (patient.getPatientId() == patientID) {
                return patient;
            }
        }

        return null;
    }
}
