package com.patientmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PatientRecord {
    private LocalDate visitDate;
    private String diagnosis;
    private String treatmentNotes;

    public PatientRecord(LocalDate visitDate, String diagnosis, String treatmentNotes) {
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.treatmentNotes = treatmentNotes;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatmentNotes() {
        return treatmentNotes;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return String.format("Visit Date: %s | Diagnosis: %s | Treatment Notes: %s", visitDate.format(formatter), diagnosis, treatmentNotes);
    }
}
