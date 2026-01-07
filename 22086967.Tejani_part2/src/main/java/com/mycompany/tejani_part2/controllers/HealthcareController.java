package com.mycompany.tejani_part2.controllers;
import com.mycompany.tejani_part2.models.*;
import com.mycompany.tejani_part2.utilities.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Controller class - handles business logic and coordinates between Model and View (MVC)
 */
public class HealthcareController {
    private DataManager dataManager;
    private ReferralManager referralManager;
    
    public HealthcareController() {
        this.dataManager = new DataManager();
        this.referralManager = ReferralManager.getInstance();
    }
    
    // ==================== DATA LOADING ====================
    
    public boolean loadAllData() {
        boolean success = true;
        success &= dataManager.loadPatients("patients.csv");
        success &= dataManager.loadClinicians("clinicians.csv");
        success &= dataManager.loadFacilities("facilities.csv");
        success &= dataManager.loadAppointments("appointments.csv");
        success &= dataManager.loadPrescriptions("prescriptions.csv");
        success &= dataManager.loadReferrals("referrals.csv");
        return success;
    }
    
    public boolean loadPatients(String filename) {
        return dataManager.loadPatients(filename);
    }
    
    public boolean loadClinicians(String filename) {
        return dataManager.loadClinicians(filename);
    }
    
    public boolean loadFacilities(String filename) {
        return dataManager.loadFacilities(filename);
    }
    
    public boolean loadAppointments(String filename) {
        return dataManager.loadAppointments(filename);
    }
    
    public boolean loadPrescriptions(String filename) {
        return dataManager.loadPrescriptions(filename);
    }
    
    public boolean loadReferrals(String filename) {
        return dataManager.loadReferrals(filename);
    }
    
    // ==================== PATIENT OPERATIONS ====================
    
    public List<Patient> getAllPatients() {
        return dataManager.getPatients();
    }
    
    public void addPatient(Patient patient) {
        dataManager.addPatient(patient);
    }
    
    public void updatePatient(int index, Patient patient) {
        dataManager.updatePatient(index, patient);
    }
    
    public void deletePatient(int index) {
        dataManager.deletePatient(index);
    }
    
    public String generateNextPatientId() {
        return dataManager.generateNextId("P", dataManager.getPatients().size());
    }
    
    // ==================== CLINICIAN OPERATIONS ====================
    
    public List<Clinician> getAllClinicians() {
        return dataManager.getClinicians();
    }
    
    public void addClinician(Clinician clinician) {
        dataManager.addClinician(clinician);
    }
    
    public void updateClinician(int index, Clinician clinician) {
        dataManager.updateClinician(index, clinician);
    }
    
    public void deleteClinician(int index) {
        dataManager.deleteClinician(index);
    }
    
    public String generateNextClinicianId() {
        return dataManager.generateNextId("C", dataManager.getClinicians().size());
    }
    
    // ==================== APPOINTMENT OPERATIONS ====================
    
    public List<Appointment> getAllAppointments() {
        return dataManager.getAppointments();
    }
    
    public void addAppointment(Appointment appointment) {
        dataManager.addAppointment(appointment);
    }
    
    public void updateAppointment(int index, Appointment appointment) {
        dataManager.updateAppointment(index, appointment);
    }
    
    public void deleteAppointment(int index) {
        dataManager.deleteAppointment(index);
    }
    
    public String generateNextAppointmentId() {
        return dataManager.generateNextId("A", dataManager.getAppointments().size());
    }
    
    // ==================== PRESCRIPTION OPERATIONS ====================
    
    public List<Prescription> getAllPrescriptions() {
        return dataManager.getPrescriptions();
    }
    
    public void addPrescription(Prescription prescription) {
        dataManager.addPrescription(prescription);
    }
    
    public void updatePrescription(int index, Prescription prescription) {
        dataManager.updatePrescription(index, prescription);
    }
    
    public void deletePrescription(int index) {
        dataManager.deletePrescription(index);
    }
    
    public String generateNextPrescriptionId() {
        return dataManager.generateNextId("RX", dataManager.getPrescriptions().size());
    }
    
    public boolean savePrescriptions(String filename) {
        return dataManager.savePrescriptions(filename);
    }
    
    // ==================== REFERRAL OPERATIONS ====================
    
    public List<Referral> getAllReferrals() {
        return referralManager.getAllReferrals();
    }
    
    public String generateNextReferralId() {
        return dataManager.generateNextId("R", referralManager.getAllReferrals().size());
    }
    
    /**
     * Create a new referral with complete validation and document generation
     */
    public boolean createReferral(String patientId, String referringClinicianId,
                                  String referredToClinicianId, String referringFacilityId,
                                  String referredToFacilityId, String urgencyLevel,
                                  String referralReason, String clinicalSummary,
                                  String requestedInvestigations, String notes) {
        
        // Get entities
        Patient patient = dataManager.getPatientById(patientId);
        Clinician referringClinician = dataManager.getClinicianById(referringClinicianId);
        Clinician receivingClinician = dataManager.getClinicianById(referredToClinicianId);
        Facility referringFacility = dataManager.getFacilityById(referringFacilityId);
        Facility receivingFacility = dataManager.getFacilityById(referredToFacilityId);
        
        if (patient == null || referringClinician == null || receivingClinician == null ||
            referringFacility == null || receivingFacility == null) {
            System.err.println("Invalid referral data - missing entities");
            return false;
        }
        
        // Create referral
        String referralId = generateNextReferralId();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        
        Referral referral = new Referral(
            referralId,
            patientId,
            referringClinicianId,
            referredToClinicianId,
            referringFacilityId,
            referredToFacilityId,
            currentDate,
            urgencyLevel,
            referralReason,
            clinicalSummary,
            requestedInvestigations,
            "New",
            "",
            notes,
            currentDate,
            currentDate
        );
        
        // Add to system
        referralManager.addReferral(referral);
        
        // Generate documents
        boolean docGenerated = referralManager.generateReferralDocument(
            referral, patient, referringClinician, receivingClinician,
            referringFacility, receivingFacility
        );
        
        // Generate email notification
        referralManager.generateReferralEmail(
            referral, patient, referringClinician, receivingClinician
        );
        
        // Save email log
        referralManager.saveEmailLog();
        
        // Save audit trail
        referralManager.saveAuditTrail();
        
        return docGenerated;
    }
    
    public int getPendingReferralsCount() {
        return referralManager.getPendingReferralsCount();
    }
    
    // ==================== FACILITY OPERATIONS ====================
    
    public List<Facility> getAllFacilities() {
        return dataManager.getFacilities();
    }
    
    // ==================== HELPER METHODS ====================
    
    public DataManager getDataManager() {
        return dataManager;
    }
}