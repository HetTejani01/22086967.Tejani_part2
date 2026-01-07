package com.mycompany.tejani_part2.utilities;

import com.mycompany.tejani_part2.models.*;
import java.io.*;
import java.util.*;

/**
 * Data Manager class - handles all data operations (Model in MVC)
 */
public class DataManager {
    private List<Patient> patients;
    private List<Clinician> clinicians;
    private List<Facility> facilities;
    private List<Appointment> appointments;
    private List<Prescription> prescriptions;
    private ReferralManager referralManager;
    
    public DataManager() {
        this.patients = new ArrayList<>();
        this.clinicians = new ArrayList<>();
        this.facilities = new ArrayList<>();
        this.appointments = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.referralManager = ReferralManager.getInstance();
    }
    
    // ==================== LOADING DATA ====================
    
    public boolean loadPatients(String filename) {
        try {
            List<String[]> data = CSVReader.readCSV(filename, true);
            patients.clear();
            
            int loadedCount = 0;
            int skippedCount = 0;
            
            for (String[] row : data) {
                // Skip empty rows
                if (row.length == 0 || (row.length == 1 && row[0].trim().isEmpty())) {
                    skippedCount++;
                    continue;
                }
                
                if (row.length >= 14) {
                    Patient patient = new Patient(
                        CSVReader.getValue(row, 0, ""),
                        CSVReader.getValue(row, 1, ""),
                        CSVReader.getValue(row, 2, ""),
                        CSVReader.getValue(row, 3, ""),
                        CSVReader.getValue(row, 4, ""),
                        CSVReader.getValue(row, 5, ""),
                        CSVReader.getValue(row, 6, ""),
                        CSVReader.getValue(row, 7, ""),
                        CSVReader.getValue(row, 8, ""),
                        CSVReader.getValue(row, 9, ""),
                        CSVReader.getValue(row, 10, ""),
                        CSVReader.getValue(row, 11, ""),
                        CSVReader.getValue(row, 12, ""),
                        CSVReader.getValue(row, 13, "")
                    );
                    patients.add(patient);
                    loadedCount++;
                } else {
                    System.err.println("Skipping row with insufficient columns: " + row.length);
                    skippedCount++;
                }
            }
            System.out.println("Loaded " + loadedCount + " patients (skipped " + skippedCount + " rows)");
            return true;
        } catch (Exception e) {
            System.err.println("Error loading patients: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean loadClinicians(String filename) {
        try {
            List<String[]> data = CSVReader.readCSV(filename, true);
            clinicians.clear();
            
            for (String[] row : data) {
                if (row.length >= 12) {
                    Clinician clinician = new Clinician(
                        CSVReader.getValue(row, 0, ""),
                        CSVReader.getValue(row, 1, ""),
                        CSVReader.getValue(row, 2, ""),
                        CSVReader.getValue(row, 3, ""),
                        CSVReader.getValue(row, 4, ""),
                        CSVReader.getValue(row, 5, ""),
                        CSVReader.getValue(row, 6, ""),
                        CSVReader.getValue(row, 7, ""),
                        CSVReader.getValue(row, 8, ""),
                        CSVReader.getValue(row, 9, ""),
                        CSVReader.getValue(row, 10, ""),
                        CSVReader.getValue(row, 11, "")
                    );
                    clinicians.add(clinician);
                }
            }
            System.out.println("Loaded " + clinicians.size() + " clinicians");
            return true;
        } catch (Exception e) {
            System.err.println("Error loading clinicians: " + e.getMessage());
            return false;
        }
    }
    
    public boolean loadFacilities(String filename) {
        try {
            List<String[]> data = CSVReader.readCSV(filename, true);
            facilities.clear();
            
            int loadedCount = 0;
            int skippedCount = 0;
            
            for (String[] row : data) {
                // Skip empty rows
                if (row.length == 0 || (row.length == 1 && row[0].trim().isEmpty())) {
                    skippedCount++;
                    continue;
                }
                
                if (row.length >= 11) {
                    try {
                        Facility facility = new Facility(
                            CSVReader.getValue(row, 0, ""),
                            CSVReader.getValue(row, 1, ""),
                            CSVReader.getValue(row, 2, ""),
                            CSVReader.getValue(row, 3, ""),
                            CSVReader.getValue(row, 4, ""),
                            CSVReader.getValue(row, 5, ""),
                            CSVReader.getValue(row, 6, ""),
                            CSVReader.getValue(row, 7, ""),
                            CSVReader.getValue(row, 8, ""),
                            CSVReader.getIntValue(row, 9, 0),
                            CSVReader.getValue(row, 10, "")
                        );
                        facilities.add(facility);
                        loadedCount++;
                    } catch (Exception e) {
                        System.err.println("Error parsing facility row: " + e.getMessage());
                        skippedCount++;
                    }
                } else {
                    System.err.println("Skipping facility row with insufficient columns: " + row.length);
                    for (int i = 0; i < row.length; i++) {
                        System.err.println("  Column " + i + ": " + row[i]);
                    }
                    skippedCount++;
                }
            }
            System.out.println("Loaded " + loadedCount + " facilities (skipped " + skippedCount + " rows)");
            return true;
        } catch (Exception e) {
            System.err.println("Error loading facilities: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean loadAppointments(String filename) {
        try {
            List<String[]> data = CSVReader.readCSV(filename, true);
            appointments.clear();
            
            for (String[] row : data) {
                if (row.length >= 13) {
                    Appointment appointment = new Appointment(
                        CSVReader.getValue(row, 0, ""),
                        CSVReader.getValue(row, 1, ""),
                        CSVReader.getValue(row, 2, ""),
                        CSVReader.getValue(row, 3, ""),
                        CSVReader.getValue(row, 4, ""),
                        CSVReader.getValue(row, 5, ""),
                        CSVReader.getIntValue(row, 6, 15),
                        CSVReader.getValue(row, 7, ""),
                        CSVReader.getValue(row, 8, ""),
                        CSVReader.getValue(row, 9, ""),
                        CSVReader.getValue(row, 10, ""),
                        CSVReader.getValue(row, 11, ""),
                        CSVReader.getValue(row, 12, "")
                    );
                    appointments.add(appointment);
                }
            }
            System.out.println("Loaded " + appointments.size() + " appointments");
            return true;
        } catch (Exception e) {
            System.err.println("Error loading appointments: " + e.getMessage());
            return false;
        }
    }
    
    public boolean loadPrescriptions(String filename) {
        try {
            List<String[]> data = CSVReader.readCSV(filename, true);
            prescriptions.clear();
            
            for (String[] row : data) {
                if (row.length >= 15) {
                    Prescription prescription = new Prescription(
                        CSVReader.getValue(row, 0, ""),
                        CSVReader.getValue(row, 1, ""),
                        CSVReader.getValue(row, 2, ""),
                        CSVReader.getValue(row, 3, ""),
                        CSVReader.getValue(row, 4, ""),
                        CSVReader.getValue(row, 5, ""),
                        CSVReader.getValue(row, 6, ""),
                        CSVReader.getValue(row, 7, ""),
                        CSVReader.getIntValue(row, 8, 7),
                        CSVReader.getValue(row, 9, ""),
                        CSVReader.getValue(row, 10, ""),
                        CSVReader.getValue(row, 11, ""),
                        CSVReader.getValue(row, 12, ""),
                        CSVReader.getValue(row, 13, ""),
                        CSVReader.getValue(row, 14, "")
                    );
                    prescriptions.add(prescription);
                }
            }
            System.out.println("Loaded " + prescriptions.size() + " prescriptions");
            return true;
        } catch (Exception e) {
            System.err.println("Error loading prescriptions: " + e.getMessage());
            return false;
        }
    }
    
    public boolean loadReferrals(String filename) {
        try {
            List<String[]> data = CSVReader.readCSV(filename, true);
            
            for (String[] row : data) {
                if (row.length >= 16) {
                    Referral referral = new Referral(
                        CSVReader.getValue(row, 0, ""),
                        CSVReader.getValue(row, 1, ""),
                        CSVReader.getValue(row, 2, ""),
                        CSVReader.getValue(row, 3, ""),
                        CSVReader.getValue(row, 4, ""),
                        CSVReader.getValue(row, 5, ""),
                        CSVReader.getValue(row, 6, ""),
                        CSVReader.getValue(row, 7, ""),
                        CSVReader.getValue(row, 8, ""),
                        CSVReader.getValue(row, 9, ""),
                        CSVReader.getValue(row, 10, ""),
                        CSVReader.getValue(row, 11, ""),
                        CSVReader.getValue(row, 12, ""),
                        CSVReader.getValue(row, 13, ""),
                        CSVReader.getValue(row, 14, ""),
                        CSVReader.getValue(row, 15, "")
                    );
                    referralManager.addReferral(referral);
                }
            }
            System.out.println("Loaded " + referralManager.getAllReferrals().size() + " referrals");
            return true;
        } catch (Exception e) {
            System.err.println("Error loading referrals: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== CRUD OPERATIONS ====================
    
    public void addPatient(Patient patient) { patients.add(patient); }
    public void updatePatient(int index, Patient patient) {
        if (index >= 0 && index < patients.size()) patients.set(index, patient);
    }
    public void deletePatient(int index) {
        if (index >= 0 && index < patients.size()) patients.remove(index);
    }
    public List<Patient> getPatients() { return patients; }
    public Patient getPatientById(String id) {
        for (Patient p : patients) {
            if (p.getPatientId().equals(id)) return p;
        }
        return null;
    }
    
    public void addClinician(Clinician clinician) { clinicians.add(clinician); }
    public void updateClinician(int index, Clinician clinician) {
        if (index >= 0 && index < clinicians.size()) clinicians.set(index, clinician);
    }
    public void deleteClinician(int index) {
        if (index >= 0 && index < clinicians.size()) clinicians.remove(index);
    }
    public List<Clinician> getClinicians() { return clinicians; }
    public Clinician getClinicianById(String id) {
        for (Clinician c : clinicians) {
            if (c.getClinicianId().equals(id)) return c;
        }
        return null;
    }
    
    public void addAppointment(Appointment appointment) { appointments.add(appointment); }
    public void updateAppointment(int index, Appointment appointment) {
        if (index >= 0 && index < appointments.size()) appointments.set(index, appointment);
    }
    public void deleteAppointment(int index) {
        if (index >= 0 && index < appointments.size()) appointments.remove(index);
    }
    public List<Appointment> getAppointments() { return appointments; }
    
    public void addPrescription(Prescription prescription) { prescriptions.add(prescription); }
    public void updatePrescription(int index, Prescription prescription) {
        if (index >= 0 && index < prescriptions.size()) prescriptions.set(index, prescription);
    }
    public void deletePrescription(int index) {
        if (index >= 0 && index < prescriptions.size()) prescriptions.remove(index);
    }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    
    public List<Facility> getFacilities() { return facilities; }
    public Facility getFacilityById(String id) {
        for (Facility f : facilities) {
            if (f.getFacilityId().equals(id)) return f;
        }
        return null;
    }
    
    public ReferralManager getReferralManager() { return referralManager; }
    
    // ==================== SAVE OPERATIONS ====================
    
    public boolean savePrescriptions(String filename) {
        try {
            FileWriter writer = new FileWriter(filename, false);
            PrintWriter pw = new PrintWriter(writer);
            
            pw.println("prescription_id,patient_id,clinician_id,appointment_id,prescription_date,medication_name,dosage,frequency,duration_days,quantity,instructions,pharmacy_name,status,issue_date,collection_date");
            
            for (Prescription p : prescriptions) {
                pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%d,%s,%s,%s,%s,%s,%s%n",
                    p.getPrescriptionId(), p.getPatientId(), p.getClinicianId(),
                    p.getAppointmentId(), p.getPrescriptionDate(), p.getMedicationName(),
                    p.getDosage(), p.getFrequency(), p.getDurationDays(), p.getQuantity(),
                    p.getInstructions(), p.getPharmacyName(), p.getStatus(),
                    p.getIssueDate(), p.getCollectionDate()
                );
            }
            
            pw.close();
            System.out.println("Prescriptions saved to " + filename);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving prescriptions: " + e.getMessage());
            return false;
        }
    }
    
    public String generateNextId(String prefix, int currentCount) {
        return String.format("%s%03d", prefix, currentCount + 1);
    }
}