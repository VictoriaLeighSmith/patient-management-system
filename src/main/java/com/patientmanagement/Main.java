package com.patientmanagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        WaitingRoom waitingRoom = new WaitingRoom();
        PatientManager patientManager = new PatientManager();

        // Automatically add 10 patient records to the system
        addPatientData(patientManager);

        boolean displayMenu = true;

        while (displayMenu) {
            System.out.println();
            System.out.println("*".repeat(51));
            System.out.println("*            PATIENT MANAGEMENT SYSTEM            *");
            System.out.println("*".repeat(51));
            System.out.println();
            System.out.println("1. Admit Patient");
            System.out.println("2. Admit Emergency Patient");
            System.out.println("3. Serve Next Patient");
            System.out.println("4. Display All Patients Waiting");
            System.out.println("5. Display Patient History");
            System.out.println("6. Add Patient Record");
            System.out.println("7. Exit");
            System.out.println();
            System.out.print("Please enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println();
                System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 7.");
                scanner.nextLine();
                continue;
            }

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    admitPatient(scanner, patientManager, waitingRoom);
                    break;
                case 2:
                    admitEmergencyPatient(scanner, patientManager, waitingRoom);
                    break;
                case 3:
                    serveNextPatient(waitingRoom);
                    break;
                case 4:
                    displayPatientsInQueue(waitingRoom);
                    break;
                case 5:
                    displayPatientRecords(scanner, patientManager);
                    break;
                case 6:
                    addPatientRecord(scanner, patientManager);
                    break;
                case 7:
                    System.out.println();
                    System.out.println("Exiting...");
                    displayMenu = false;
                    break;
                default:
                    System.out.println();
                    System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 7.");
                    System.out.println();
            }
        }

        scanner.close();
    }

    // Method to admit a non-emergency patient to the waiting room
    private static void admitPatient(Scanner scanner, PatientManager patientManager, WaitingRoom waitingRoom) {
        String patientName = "";
        String visitReason = "";

        while (patientName.isBlank()) {
            System.out.print("Enter patient name: ");
            patientName = scanner.nextLine().trim();

            if (patientName.isBlank()) {
                System.out.println();
                System.out.println("[ERROR] Patient name cannot be blank.");
                System.out.println();
            }
        }

        while (visitReason.isBlank()) {
            System.out.print("Enter reason for visit: ");
            visitReason = scanner.nextLine().trim();

            if (visitReason.isBlank()) {
                System.out.println();
                System.out.println("[ERROR] Reason for visit cannot be blank.");
                System.out.println();
            }
        }

        Patient patient = new Patient(patientName, visitReason);

        patientManager.registerPatient(patient);
        waitingRoom.addPatient(patient);

        System.out.println();
        System.out.println("[SUCCESS] Successfully admitted patient! Patient ID: " + patient.getPatientId());
        System.out.println();
    }

    // Method to admit an emergency patient to the waiting room
    private static void admitEmergencyPatient(Scanner scanner, PatientManager patientManager, WaitingRoom waitingRoom) {
        String patientName = "";
        String visitReason = "";
        int waitingRoomPosition = 0;
        boolean validPosition = false;

        while(patientName.isBlank()) {
            System.out.print("Enter emergency patient name: ");
            patientName = scanner.nextLine().trim();

            if (patientName.isBlank()) {
                System.out.println();
                System.out.println("[ERROR] Patient name cannot be blank.");
                System.out.println();
            }
        }

        while (visitReason.isBlank()) {
            System.out.print("Enter reason for visit: ");
            visitReason = scanner.nextLine().trim();

            if (visitReason.isBlank()) {
                System.out.println();
                System.out.println("[ERROR] Reason for visit cannot be blank.");
                System.out.println();
            }
        }

        while (!validPosition) {
            System.out.print("Enter emergency waiting room position: ");
            String waitingRoomPositionInput = scanner.nextLine().trim();

            try {
                waitingRoomPosition = Integer.parseInt(waitingRoomPositionInput);

                if (waitingRoomPosition > 0 && waitingRoomPosition <= waitingRoom.getPatientsWaiting().size() + 1) {
                    validPosition = true;
                } else {
                    System.out.println();
                    System.out.println("[ERROR] Invalid waiting room position. Please enter a number between 1 and " + (waitingRoom.getPatientsWaiting().size() + 1));
                    System.out.println();
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("[ERROR] Invalid waiting room position. Please enter a valid number.");
                System.out.println();
            }
        }

        Patient patient = new Patient(patientName, visitReason);

        patientManager.registerPatient(patient);
        waitingRoom.addEmergencyPatient(patient, waitingRoomPosition);

        System.out.println();
        System.out.println("[SUCCESS] Successfully admitted emergency patient at position " + waitingRoomPosition + "! ID: " + patient.getPatientId());
        System.out.println();
    }

    // Method to serve next patient in waiting room queue
    private static void serveNextPatient(WaitingRoom waitingRoom) {
        Patient patientServed = waitingRoom.serveNextPatient();

        if (patientServed == null) {
            System.out.println();
            System.out.println("[INFO] Waiting room is empty.");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("[SUCCESS] Patient served: " + patientServed);
            System.out.println();
        }
    }

    // Method to display all patients waiting in the waiting room queue
    private static void displayPatientsInQueue(WaitingRoom waitingRoom) {
        ArrayList<Patient> patientsWaiting = waitingRoom.getPatientsWaiting();

        if (patientsWaiting.isEmpty()) {
            System.out.println();
            System.out.println("[INFO] Waiting room is empty.");
            System.out.println();
        } else {
            System.out.println();
            System.out.println("* PATIENTS CURRENTLY WAITING TO BE SERVED *");
            System.out.println();

            for (Patient patient : patientsWaiting) {
                System.out.println(patient);
            }
        }
    }

    // Method to traverse through patient records
    private static void displayPatientRecords(Scanner scanner, PatientManager patientManager) {
        int patientID = 0;
        boolean validPatientID = false;
        Patient selectedPatient = null;

        while (!validPatientID) {
            System.out.print("Enter patient ID to view records: ");
            String patientIDInput = scanner.nextLine().trim();

            try {
                patientID = Integer.parseInt(patientIDInput);
                selectedPatient = patientManager.getPatientByID(patientID);

                if (selectedPatient == null) {
                    System.out.println();
                    System.out.println("[ERROR] Patient ID does not exist.");
                    System.out.println();
                } else {
                    validPatientID = true;
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("[ERROR] Invalid patient ID. Please enter a valid number.");
                System.out.println();
            }
        }

        boolean displayPatientRecordsMenu = true;

        while (displayPatientRecordsMenu) {
            System.out.println();
            System.out.println("* PATIENT HISTORY: " + selectedPatient.getFullName().toUpperCase() + " *");
            System.out.println();
            System.out.println("1. View Oldest Record");
            System.out.println("2. View Newest Record");
            System.out.println("3. Browse Patient Records");
            System.out.println("4. Return to Main Menu");
            System.out.println();
            System.out.print("Please choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println();
                System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 4.");
                scanner.nextLine();
                continue;
            }

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    PatientRecord oldestRecord = selectedPatient.getPatientHistory().getOldestRecord();

                    if (oldestRecord == null) {
                        System.out.println();
                        System.out.println("[INFO] No records to display.");
                        System.out.println();
                    } else {
                        System.out.println();
                        System.out.println(oldestRecord);
                        System.out.println();
                    }

                    break;
                case 2:
                    PatientRecord newestRecord = selectedPatient.getPatientHistory().getNewestRecord();

                    if (newestRecord == null) {
                        System.out.println();
                        System.out.println("[INFO] No records to display.");
                        System.out.println();
                    } else {
                        System.out.println();
                        System.out.println(newestRecord);
                        System.out.println();
                    }

                    break;
                case 3:
                    browsePatientRecords(scanner, selectedPatient.getPatientHistory());
                    break;
                case 4:
                    displayPatientRecordsMenu = false;
                    break;
                default:
                    System.out.println();
                    System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 4.");
                    System.out.println();
            }
        }
    }

    // Helper method to browse patient records
    private static void browsePatientRecords(Scanner scanner, PatientHistory patientHistory) {
        PatientRecord currentRecord = patientHistory.resetToOldestRecord();

        if (currentRecord == null) {
            System.out.println();
            System.out.println("[INFO] Patient history is empty.");
            System.out.println();

            return;
        }

        boolean browsePatientRecords = true;

        while (browsePatientRecords) {
            System.out.println();
            System.out.println(currentRecord);
            System.out.println();
            System.out.println("-".repeat(50));

            System.out.println("* PATIENT HISTORY *");
            System.out.println();
            System.out.println("1. Next Record");
            System.out.println("2. Previous Record");
            System.out.println("3. Back to Patient History Menu");
            System.out.println();
            System.out.print("Please choose an option: ");

            if (!scanner.hasNextInt()) {
                System.out.println();
                System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 3.");
                System.out.println();

                scanner.nextLine();
                continue;
            }

            int userChoice = scanner.nextInt();
            scanner.nextLine();

            switch (userChoice) {
                case 1:
                    PatientRecord nextRecord = patientHistory.nextRecord();

                    if (nextRecord != null) {
                        currentRecord = nextRecord;
                    } else {
                        System.out.println();
                        System.out.println("[INFO] You've reached the end of the patient's history.");
                        System.out.println();
                    }

                    break;
                case 2:
                    PatientRecord previousRecord = patientHistory.previousRecord();

                    if (previousRecord != null) {
                        currentRecord = previousRecord;
                    } else {
                        System.out.println();
                        System.out.println("[INFO] You've reached the beginning of the patient's history.");
                        System.out.println();
                    }

                    break;
                case 3:
                    browsePatientRecords = false;
                    break;
                default:
                    System.out.println();
                    System.out.println("[ERROR] Invalid choice. Please enter a number between 1 and 3.");
                    System.out.println();
            }
        }
    }

    // Method to add a patient record to the system
    private static void addPatientRecord(Scanner scanner, PatientManager patientManager) {
        int patientID = 0;
        boolean validPatientID = false;
        Patient selectedPatient = null;

        while (!validPatientID) {
            System.out.print("Enter patient ID to add a record: ");
            String patientIDInput = scanner.nextLine().trim();

            try {
                patientID = Integer.parseInt(patientIDInput);
                selectedPatient = patientManager.getPatientByID(patientID);

                if (selectedPatient == null) {
                    System.out.println();
                    System.out.println("[ERROR] Patient ID does not exist.");
                    System.out.println();
                } else {
                    validPatientID = true;
                }
            } catch (NumberFormatException e) {
                System.out.println();
                System.out.println("[ERROR] Invalid patient ID. Please enter a valid number.");
                System.out.println();
            }
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate visitDate = null;
        boolean validDate = false;
        String diagnosis = "";
        String treatmentNotes = "";

        while (!validDate) {
            System.out.print("Enter visit date (YYYY-MM-DD): ");
            String visitDateInput = scanner.nextLine().trim();

            try {
                visitDate = LocalDate.parse(visitDateInput, formatter);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println();
                System.out.println("[ERROR] Invalid date. Please enter a valid date in YYYY-MM-DD format.");
                System.out.println();
            }
        }

        while (diagnosis.isBlank()) {
            System.out.print("Enter patient diagnosis: ");
            diagnosis = scanner.nextLine().trim();

            if (diagnosis.isBlank()) {
                System.out.println();
                System.out.println("[ERROR] Diagnosis cannot be blank.");
                System.out.println();
            }
        }

        while (treatmentNotes.isBlank()) {
            System.out.print("Enter treatment notes: ");
            treatmentNotes = scanner.nextLine().trim();

            if (treatmentNotes.isBlank()) {
                System.out.println();
                System.out.println("[ERROR] Treatment notes cannot be blank.");
                System.out.println();
            }
        }

        PatientRecord patientRecord = new PatientRecord(visitDate, diagnosis, treatmentNotes);
        selectedPatient.getPatientHistory().addPatientRecord(patientRecord);

        System.out.println();
        System.out.println("[SUCCESS] Successfully added patient record!");
        System.out.println();
    }

    // Helper method to automatically add 10 records to the system
    public static void addPatientData(PatientManager patientManager) {
        Patient patient = new Patient("Test Patient", "Illness");
        patientManager.registerPatient(patient);

        for (int i = 1; i <= 10; i++) {
            PatientRecord patientRecord = new PatientRecord(LocalDate.of(2026, i, 1), "Diagnosis of " + i, "Treatment notes of " + i);
            patient.getPatientHistory().addPatientRecord(patientRecord);
        }
    }
}
