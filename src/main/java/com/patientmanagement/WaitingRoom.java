package com.patientmanagement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {
    private final Queue<Patient> patientQueue;

    public WaitingRoom() {
        patientQueue = new LinkedList<>();
    }

    public void addPatient(Patient patient) {
        patientQueue.offer(patient);
    }

    public Patient serveNextPatient() {
       return patientQueue.poll();
    }

    // Maybe change the return type here to List?
    public ArrayList<Patient> getPatientsWaiting() {
        return new ArrayList<Patient>(patientQueue);
    }

    public void addEmergencyPatient(Patient patient, int position) {
        if (position < 1 || position > patientQueue.size() + 1) {
            throw new IndexOutOfBoundsException("Position must be between 1 and " + (patientQueue.size() + 1));
        }

        Queue<Patient> tempQueue = new LinkedList<>();

        for (int i = 0; i < position - 1; i++) {
            tempQueue.offer(patientQueue.poll());
        }

        tempQueue.offer(patient);

        while (!patientQueue.isEmpty()) {
            tempQueue.offer(patientQueue.poll());
        }

        while (!tempQueue.isEmpty()) {
            patientQueue.offer(tempQueue.poll());
        }
    }

    public boolean isEmpty() {
        return patientQueue.isEmpty();
    }
}
