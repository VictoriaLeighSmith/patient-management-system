package com.patientmanagement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class WaitingRoomTest {

    @Test
    public void testPatientsServedFIFO() {
        WaitingRoom waitingRoom = new WaitingRoom();

        Patient patient1 = new Patient("Victoria Smith", "Test");
        Patient patient2 = new Patient("John Smith", "Test");
        Patient patient3 = new Patient("Joe Smith", "Test");

        waitingRoom.addPatient(patient1);
        waitingRoom.addPatient(patient2);
        waitingRoom.addPatient(patient3);

        Assertions.assertSame(patient1, waitingRoom.serveNextPatient());
        Assertions.assertSame(patient2, waitingRoom.serveNextPatient());
        Assertions.assertSame(patient3, waitingRoom.serveNextPatient());
    }

    @Test
    public void testEmergencyPatientPosition() {
        WaitingRoom waitingRoom = new WaitingRoom();

        Patient patient1 = new Patient("Victoria Smith", "Test");
        Patient patient2 = new Patient("John Smith", "Test");
        Patient patient3 = new Patient("Joe Smith", "Test");
        Patient emergencyPatient = new Patient("Emergency Patient", "Test");

        waitingRoom.addPatient(patient1);
        waitingRoom.addPatient(patient2);
        waitingRoom.addPatient(patient3);

        waitingRoom.addEmergencyPatient(emergencyPatient, 2);

        ArrayList<Patient> patientsWaiting = waitingRoom.getPatientsWaiting();

        Assertions.assertSame(patient1, patientsWaiting.get(0));
        Assertions.assertSame(emergencyPatient, patientsWaiting.get(1));
        Assertions.assertSame(patient2, patientsWaiting.get(2));
        Assertions.assertSame(patient3, patientsWaiting.get(3));
    }
}
