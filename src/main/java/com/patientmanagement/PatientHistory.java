package com.patientmanagement;

public class PatientHistory {
    private Node head;
    private Node tail;
    private Node current;

    private class Node {
        private PatientRecord patientRecord;
        private Node next;
        private Node previous;

        private Node(PatientRecord patientRecord) {
            this.patientRecord = patientRecord;
        }
    }

    public void addPatientRecord(PatientRecord patientRecord) {
        Node newNode = new Node(patientRecord);

        if (head == null) {
            head = newNode;
            tail = newNode;
            current = newNode;
        } else {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
        }
    }

    public PatientRecord getOldestRecord() {
        if (head == null) {
            return null;
        } else {
            return head.patientRecord;
        }
    }

    public PatientRecord getNewestRecord() {
        if (head == null) {
            return null;
        } else {
            return tail.patientRecord;
        }
    }

    public PatientRecord nextRecord() {
        if (current == null) {
            return null;
        } else if (current.next == null) {
            return null;
        } else {
            current = current.next;
            return current.patientRecord;
        }
    }

    public PatientRecord previousRecord() {
        if (current == null) {
            return null;
        } else if (current.previous == null) {
            return null;
        } else {
            current = current.previous;
            return current.patientRecord;
        }
    }
}
