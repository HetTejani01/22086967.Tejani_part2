package com.mycompany.tejani_part2.models;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Singleton class for managing referrals in the Healthcare Management System
 * This ensures single instance creation to prevent resource conflicts and maintain data consistency
 */
public class ReferralManager {
    // Singleton instance
    private static ReferralManager instance;
    
    // Referral queue for managing pending referrals
    private Queue<Referral> referralQueue;
    
    // List to store all referrals
    private List<Referral> allReferrals;
    
    // Audit trail for referral operations
    private List<String> auditTrail;
    
    // Email log for simulated email communications
    private List<String> emailLog;
    
    // Private constructor to prevent instantiation
    private ReferralManager() {
        this.referralQueue = new LinkedList<>();
        this.allReferrals = new ArrayList<>();
        this.auditTrail = new ArrayList<>();
        this.emailLog = new ArrayList<>();
        
        // Add initialization to audit trail
        addToAuditTrail("ReferralManager initialized");
    }
    
    /**
     * Get the singleton instance of ReferralManager
     * Thread-safe implementation
     */
    public static synchronized ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }
    
    /**
     * Add a new referral to the system
     */
    public void addReferral(Referral referral) {
        allReferrals.add(referral);
        
        // Add to queue if status is "New" or "Pending"
        if ("New".equalsIgnoreCase(referral.getStatus()) || 
            "Pending".equalsIgnoreCase(referral.getStatus())) {
            referralQueue.offer(referral);
        }
        
        addToAuditTrail("Referral added: " + referral.getReferralId());
    }
    
    /**
     * Process the next referral in the queue
     */
    public Referral processNextReferral() {
        Referral referral = referralQueue.poll();
        if (referral != null) {
            addToAuditTrail("Referral processed: " + referral.getReferralId());
        }
        return referral;
    }
    
    /**
     * Generate referral document and save to file
     */
    public boolean generateReferralDocument(Referral referral, Patient patient, 
                                           Clinician referringClinician, 
                                           Clinician receivingClinician,
                                           Facility referringFacility,
                                           Facility receivingFacility) {
        try {
            String fileName = "referral_" + referral.getReferralId() + ".txt";
            FileWriter writer = new FileWriter(fileName, true);
            PrintWriter pw = new PrintWriter(writer);
            
            // Generate referral content
            pw.println("═══════════════════════════════════════════════════════");
            pw.println("         NHS REFERRAL LETTER");
            pw.println("═══════════════════════════════════════════════════════");
            pw.println();
            pw.println("Referral ID: " + referral.getReferralId());
            pw.println("Date: " + referral.getReferralDate());
            pw.println("Urgency: " + referral.getUrgencyLevel());
            pw.println();
            
            // Referring clinician details
            pw.println("FROM:");
            pw.println(referringClinician.getFullName());
            pw.println(referringClinician.getSpeciality());
            pw.println(referringFacility.getFacilityName());
            pw.println(referringFacility.getAddress());
            pw.println("Email: " + referringClinician.getEmail());
            pw.println("Phone: " + referringClinician.getPhoneNumber());
            pw.println();
            
            // Receiving clinician details
            pw.println("TO:");
            pw.println(receivingClinician.getFullName());
            pw.println(receivingClinician.getSpeciality());
            pw.println(receivingFacility.getFacilityName());
            pw.println(receivingFacility.getAddress());
            pw.println("Email: " + receivingClinician.getEmail());
            pw.println();
            
            // Patient details
            pw.println("PATIENT DETAILS:");
            pw.println("Name: " + patient.getFullName());
            pw.println("NHS Number: " + patient.getNhsNumber());
            pw.println("Date of Birth: " + patient.getDateOfBirth());
            pw.println("Gender: " + patient.getGender());
            pw.println("Contact: " + patient.getPhoneNumber());
            pw.println("Email: " + patient.getEmail());
            pw.println("Address: " + patient.getAddress() + ", " + patient.getPostcode());
            pw.println();
            
            // Referral details
            pw.println("REASON FOR REFERRAL:");
            pw.println(referral.getReferralReason());
            pw.println();
            
            pw.println("CLINICAL SUMMARY:");
            pw.println(referral.getClinicalSummary());
            pw.println();
            
            pw.println("REQUESTED INVESTIGATIONS:");
            pw.println(referral.getRequestedInvestigations());
            pw.println();
            
            if (referral.getNotes() != null && !referral.getNotes().isEmpty()) {
                pw.println("ADDITIONAL NOTES:");
                pw.println(referral.getNotes());
                pw.println();
            }
            
            pw.println("═══════════════════════════════════════════════════════");
            pw.println("This is a computer-generated referral letter.");
            pw.println("Generated on: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date()));
            pw.println("═══════════════════════════════════════════════════════");
            pw.println();
            
            pw.close();
            
            addToAuditTrail("Referral document generated: " + fileName);
            return true;
            
        } catch (IOException e) {
            addToAuditTrail("Error generating referral document: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Generate email notification for referral (simulated)
     */
    public void generateReferralEmail(Referral referral, Patient patient, 
                                     Clinician referringClinician, 
                                     Clinician receivingClinician) {
        StringBuilder email = new StringBuilder();
        email.append("═════════════════════════════════════════════\n");
        email.append("EMAIL NOTIFICATION - NEW REFERRAL\n");
        email.append("═════════════════════════════════════════════\n");
        email.append("To: ").append(receivingClinician.getEmail()).append("\n");
        email.append("From: ").append(referringClinician.getEmail()).append("\n");
        email.append("Subject: New Referral - ").append(patient.getFullName())
             .append(" (").append(referral.getUrgencyLevel()).append(")\n\n");
        email.append("Dear ").append(receivingClinician.getFullName()).append(",\n\n");
        email.append("A new referral has been made for:\n");
        email.append("Patient: ").append(patient.getFullName()).append("\n");
        email.append("NHS Number: ").append(patient.getNhsNumber()).append("\n");
        email.append("Reason: ").append(referral.getReferralReason()).append("\n");
        email.append("Urgency: ").append(referral.getUrgencyLevel()).append("\n\n");
        email.append("Please review the full referral details at your earliest convenience.\n\n");
        email.append("Regards,\n");
        email.append(referringClinician.getFullName()).append("\n");
        email.append("═════════════════════════════════════════════\n\n");
        
        emailLog.add(email.toString());
        addToAuditTrail("Email notification generated for referral: " + referral.getReferralId());
    }
    
    /**
     * Save all email communications to file
     */
    public void saveEmailLog() {
        try {
            FileWriter writer = new FileWriter("referral_emails.txt", false);
            PrintWriter pw = new PrintWriter(writer);
            
            for (String email : emailLog) {
                pw.println(email);
            }
            
            pw.close();
            addToAuditTrail("Email log saved to file");
        } catch (IOException e) {
            addToAuditTrail("Error saving email log: " + e.getMessage());
        }
    }
    
    /**
     * Update referral status
     */
    public void updateReferralStatus(String referralId, String newStatus) {
        for (Referral referral : allReferrals) {
            if (referral.getReferralId().equals(referralId)) {
                referral.setStatus(newStatus);
                addToAuditTrail("Referral " + referralId + " status updated to: " + newStatus);
                break;
            }
        }
    }
    
    /**
     * Get all referrals
     */
    public List<Referral> getAllReferrals() {
        return new ArrayList<>(allReferrals);
    }
    
    /**
     * Get pending referrals count
     */
    public int getPendingReferralsCount() {
        return referralQueue.size();
    }
    
    /**
     * Add entry to audit trail
     */
    private void addToAuditTrail(String entry) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        auditTrail.add(timestamp + " - " + entry);
    }
    
    /**
     * Get audit trail
     */
    public List<String> getAuditTrail() {
        return new ArrayList<>(auditTrail);
    }
    
    /**
     * Save audit trail to file
     */
    public void saveAuditTrail() {
        try {
            FileWriter writer = new FileWriter("referral_audit_trail.txt", false);
            PrintWriter pw = new PrintWriter(writer);
            
            pw.println("═══════════════════════════════════════════════════════");
            pw.println("        REFERRAL SYSTEM AUDIT TRAIL");
            pw.println("═══════════════════════════════════════════════════════");
            pw.println();
            
            for (String entry : auditTrail) {
                pw.println(entry);
            }
            
            pw.close();
        } catch (IOException e) {
            System.err.println("Error saving audit trail: " + e.getMessage());
        }
    }
    
    /**
     * Clear all data (for testing purposes)
     */
    public void clearAll() {
        referralQueue.clear();
        allReferrals.clear();
        emailLog.clear();
        addToAuditTrail("All referral data cleared");
    }
}