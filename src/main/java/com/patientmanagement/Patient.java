package com.patientmanagement;

public class Patient {
    private static int patientIdCounter = 0;

    private final int patientId;
    private String fullName;
    private String visitReason;

    public Patient(String fullName, String visitReason) {
        patientIdCounter++;

        this.patientId = patientIdCounter;
        this.fullName = fullName;
        this.visitReason = visitReason;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Reason for Visit: %s", patientId, fullName, visitReason);
    }
}
