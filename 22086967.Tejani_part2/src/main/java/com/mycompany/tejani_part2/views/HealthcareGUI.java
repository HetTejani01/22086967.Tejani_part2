package com.mycompany.tejani_part2.views;

import com.mycompany.tejani_part2.controllers.*;
import com.mycompany.tejani_part2.models.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Main GUI View - Healthcare Management System (View in MVC)
 */
public class HealthcareGUI extends JFrame {
    private HealthcareController controller;
    private JTabbedPane tabbedPane;
    
    private DefaultTableModel patientTableModel;
    private DefaultTableModel clinicianTableModel;
    private DefaultTableModel facilityTableModel;
    private DefaultTableModel appointmentTableModel;
    private DefaultTableModel prescriptionTableModel;
    private DefaultTableModel referralTableModel;
    
    private JTable patientTable;
    private JTable clinicianTable;
    private JTable facilityTable;
    private JTable appointmentTable;
    private JTable prescriptionTable;
    private JTable referralTable;
    
    public HealthcareGUI() {
        controller = new HealthcareController();
        
        setTitle("Healthcare Management System - 22086967.Tejani");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initializeComponents();
        createMenuBar();
        
        setVisible(true);
    }
    
    private void initializeComponents() {
        tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Patients", createPatientPanel());
        tabbedPane.addTab("Clinicians", createClinicianPanel());
        tabbedPane.addTab("Facilities", createFacilityPanel());
        tabbedPane.addTab("Appointments", createAppointmentPanel());
        tabbedPane.addTab("Prescriptions", createPrescriptionPanel());
        tabbedPane.addTab("Referrals", createReferralPanel());
        
        add(tabbedPane);
    }
    
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu fileMenu = new JMenu("File");
        JMenuItem loadDataItem = new JMenuItem("Load All Data");
        JMenuItem exitItem = new JMenuItem("Exit");
        
        loadDataItem.addActionListener(e -> loadAllData());
        exitItem.addActionListener(e -> System.exit(0));
        
        fileMenu.add(loadDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        JMenu dataMenu = new JMenu("Data");
        JMenuItem loadPatientsItem = new JMenuItem("Load Patients");
        JMenuItem loadCliniciansItem = new JMenuItem("Load Clinicians");
        JMenuItem loadFacilitiesItem = new JMenuItem("Load Facilities");
        JMenuItem loadAppointmentsItem = new JMenuItem("Load Appointments");
        JMenuItem loadPrescriptionsItem = new JMenuItem("Load Prescriptions");
        JMenuItem loadReferralsItem = new JMenuItem("Load Referrals");
        
        loadPatientsItem.addActionListener(e -> loadDataFile("patients.csv", "Patients"));
        loadCliniciansItem.addActionListener(e -> loadDataFile("clinicians.csv", "Clinicians"));
        loadFacilitiesItem.addActionListener(e -> loadDataFile("facilities.csv", "Facilities"));
        loadAppointmentsItem.addActionListener(e -> loadDataFile("appointments.csv", "Appointments"));
        loadPrescriptionsItem.addActionListener(e -> loadDataFile("prescriptions.csv", "Prescriptions"));
        loadReferralsItem.addActionListener(e -> loadDataFile("referrals.csv", "Referrals"));
        
        dataMenu.add(loadPatientsItem);
        dataMenu.add(loadCliniciansItem);
        dataMenu.add(loadFacilitiesItem);
        dataMenu.add(loadAppointmentsItem);
        dataMenu.add(loadPrescriptionsItem);
        dataMenu.add(loadReferralsItem);
        
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(dataMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    // ==================== PATIENT PANEL ====================
    
    private JPanel createPatientPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "First Name", "Last Name", "DOB", "NHS Number", 
                           "Gender", "Phone", "Email", "Address"};
        patientTableModel = new DefaultTableModel(columns, 0);
        patientTable = new JTable(patientTableModel);
        JScrollPane scrollPane = new JScrollPane(patientTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Patient");
        JButton editBtn = new JButton("Edit Patient");
        JButton deleteBtn = new JButton("Delete Patient");
        JButton refreshBtn = new JButton("Refresh");
        
        addBtn.addActionListener(e -> showAddPatientDialog());
        editBtn.addActionListener(e -> showEditPatientDialog());
        deleteBtn.addActionListener(e -> deleteSelectedPatient());
        refreshBtn.addActionListener(e -> refreshPatientTable());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshPatientTable() {
        patientTableModel.setRowCount(0);
        for (Patient p : controller.getAllPatients()) {
            patientTableModel.addRow(new Object[] {
                p.getPatientId(),
                p.getFirstName(),
                p.getLastName(),
                p.getDateOfBirth(),
                p.getNhsNumber(),
                p.getGender(),
                p.getPhoneNumber(),
                p.getEmail(),
                p.getAddress()
            });
        }
    }
    
    // ==================== CLINICIAN PANEL ====================
    
    private JPanel createClinicianPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Title", "First Name", "Last Name", "Speciality", 
                           "GMC Number", "Phone", "Email", "Workplace ID", "Workplace Type", 
                           "Employment Status", "Start Date"};
        clinicianTableModel = new DefaultTableModel(columns, 0);
        clinicianTable = new JTable(clinicianTableModel);
        JScrollPane scrollPane = new JScrollPane(clinicianTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Clinician");
        JButton editBtn = new JButton("Edit Clinician");
        JButton deleteBtn = new JButton("Delete Clinician");
        JButton refreshBtn = new JButton("Refresh");
        
        addBtn.addActionListener(e -> showAddClinicianDialog());
        editBtn.addActionListener(e -> showEditClinicianDialog());
        deleteBtn.addActionListener(e -> deleteSelectedClinician());
        refreshBtn.addActionListener(e -> refreshClinicianTable());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshClinicianTable() {
        clinicianTableModel.setRowCount(0);
        for (Clinician c : controller.getAllClinicians()) {
            clinicianTableModel.addRow(new Object[] {
                c.getClinicianId(),
                c.getTitle(),
                c.getFirstName(),
                c.getLastName(),
                c.getSpeciality(),
                c.getGmcNumber(),
                c.getPhoneNumber(),
                c.getEmail(),
                c.getWorkplaceId(),
                c.getWorkplaceType(),
                c.getEmploymentStatus(),
                c.getStartDate()
            });
        }
    }
    
    // ==================== FACILITY PANEL ====================
    
    private JPanel createFacilityPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Name", "Type", "Address", "Postcode", 
                           "Phone", "Email", "Opening Hours", "Manager", "Capacity", "Specialities"};
        facilityTableModel = new DefaultTableModel(columns, 0);
        facilityTable = new JTable(facilityTableModel);
        JScrollPane scrollPane = new JScrollPane(facilityTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton refreshBtn = new JButton("Refresh");
        JButton viewBtn = new JButton("View Details");
        
        refreshBtn.addActionListener(e -> refreshFacilityTable());
        viewBtn.addActionListener(e -> showFacilityDetails());
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(viewBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshFacilityTable() {
        facilityTableModel.setRowCount(0);
        for (Facility f : controller.getAllFacilities()) {
            facilityTableModel.addRow(new Object[] {
                f.getFacilityId(),
                f.getFacilityName(),
                f.getFacilityType(),
                f.getAddress(),
                f.getPostcode(),
                f.getPhoneNumber(),
                f.getEmail(),
                f.getOpeningHours(),
                f.getManagerName(),
                f.getCapacity(),
                f.getSpecialitiesOffered()
            });
        }
    }
    
    // ==================== APPOINTMENT PANEL ====================
    
    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Patient ID", "Clinician ID", "Facility ID", 
                           "Date", "Time", "Duration", "Type", "Status", "Reason", 
                           "Notes", "Created Date", "Last Modified"};
        appointmentTableModel = new DefaultTableModel(columns, 0);
        appointmentTable = new JTable(appointmentTableModel);
        JScrollPane scrollPane = new JScrollPane(appointmentTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Appointment");
        JButton editBtn = new JButton("Edit Appointment");
        JButton deleteBtn = new JButton("Delete Appointment");
        JButton refreshBtn = new JButton("Refresh");
        
        addBtn.addActionListener(e -> showAddAppointmentDialog());
        editBtn.addActionListener(e -> showEditAppointmentDialog());
        deleteBtn.addActionListener(e -> deleteSelectedAppointment());
        refreshBtn.addActionListener(e -> refreshAppointmentTable());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshAppointmentTable() {
        appointmentTableModel.setRowCount(0);
        for (Appointment a : controller.getAllAppointments()) {
            appointmentTableModel.addRow(new Object[] {
                a.getAppointmentId(),
                a.getPatientId(),
                a.getClinicianId(),
                a.getFacilityId(),
                a.getAppointmentDate(),
                a.getAppointmentTime(),
                a.getDurationMinutes(),
                a.getAppointmentType(),
                a.getStatus(),
                a.getReasonForVisit(),
                a.getNotes(),
                a.getCreatedDate(),
                a.getLastModified()
            });
        }
    }
    
    // ==================== PRESCRIPTION PANEL ====================
    
    private JPanel createPrescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Patient ID", "Clinician ID", "Date", "Medication", 
                           "Dosage", "Frequency", "Duration", "Pharmacy", "Status"};
        prescriptionTableModel = new DefaultTableModel(columns, 0);
        prescriptionTable = new JTable(prescriptionTableModel);
        JScrollPane scrollPane = new JScrollPane(prescriptionTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Add Prescription");
        JButton editBtn = new JButton("Edit Prescription");
        JButton deleteBtn = new JButton("Delete Prescription");
        JButton refreshBtn = new JButton("Refresh");
        JButton saveBtn = new JButton("Save to File");
        
        addBtn.addActionListener(e -> showAddPrescriptionDialog());
        editBtn.addActionListener(e -> showEditPrescriptionDialog());
        deleteBtn.addActionListener(e -> deleteSelectedPrescription());
        refreshBtn.addActionListener(e -> refreshPrescriptionTable());
        saveBtn.addActionListener(e -> savePrescriptions());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(editBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(saveBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshPrescriptionTable() {
        prescriptionTableModel.setRowCount(0);
        for (Prescription p : controller.getAllPrescriptions()) {
            prescriptionTableModel.addRow(new Object[] {
                p.getPrescriptionId(),
                p.getPatientId(),
                p.getClinicianId(),
                p.getPrescriptionDate(),
                p.getMedicationName(),
                p.getDosage(),
                p.getFrequency(),
                p.getDurationDays(),
                p.getPharmacyName(),
                p.getStatus()
            });
        }
    }
    
    // ==================== REFERRAL PANEL ====================
    
    private JPanel createReferralPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columns = {"ID", "Patient ID", "From Clinician", "To Clinician", 
                           "Date", "Urgency", "Reason", "Status"};
        referralTableModel = new DefaultTableModel(columns, 0);
        referralTable = new JTable(referralTableModel);
        JScrollPane scrollPane = new JScrollPane(referralTable);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addBtn = new JButton("Create Referral");
        JButton refreshBtn = new JButton("Refresh");
        JButton viewBtn = new JButton("View Details");
        
        addBtn.addActionListener(e -> showCreateReferralDialog());
        refreshBtn.addActionListener(e -> refreshReferralTable());
        viewBtn.addActionListener(e -> showReferralDetails());
        
        buttonPanel.add(addBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(refreshBtn);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void refreshReferralTable() {
        referralTableModel.setRowCount(0);
        for (Referral r : controller.getAllReferrals()) {
            referralTableModel.addRow(new Object[] {
                r.getReferralId(),
                r.getPatientId(),
                r.getReferringClinicianId(),
                r.getReferredToClinicianId(),
                r.getReferralDate(),
                r.getUrgencyLevel(),
                r.getReferralReason(),
                r.getStatus()
            });
        }
    }
    
    // ==================== DIALOG METHODS ====================
    
    private void showAddPatientDialog() {
        PatientDialog dialog = new PatientDialog(this, controller, null, -1);
        dialog.setVisible(true);
        refreshPatientTable();
    }
    
    private void showEditPatientDialog() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to edit");
            return;
        }
        Patient patient = controller.getAllPatients().get(selectedRow);
        PatientDialog dialog = new PatientDialog(this, controller, patient, selectedRow);
        dialog.setVisible(true);
        refreshPatientTable();
    }
    
    private void deleteSelectedPatient() {
        int selectedRow = patientTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a patient to delete");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this patient?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deletePatient(selectedRow);
            refreshPatientTable();
        }
    }
    
    private void showAddClinicianDialog() {
        ClinicianDialog dialog = new ClinicianDialog(this, controller, null, -1);
        dialog.setVisible(true);
        refreshClinicianTable();
    }
    
    private void showEditClinicianDialog() {
        int selectedRow = clinicianTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a clinician to edit");
            return;
        }
        Clinician clinician = controller.getAllClinicians().get(selectedRow);
        ClinicianDialog dialog = new ClinicianDialog(this, controller, clinician, selectedRow);
        dialog.setVisible(true);
        refreshClinicianTable();
    }
    
    private void deleteSelectedClinician() {
        int selectedRow = clinicianTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a clinician to delete");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this clinician?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteClinician(selectedRow);
            refreshClinicianTable();
        }
    }
    
    private void showAddAppointmentDialog() {
        AppointmentDialog dialog = new AppointmentDialog(this, controller, null, -1);
        dialog.setVisible(true);
        refreshAppointmentTable();
    }
    
    private void showEditAppointmentDialog() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to edit");
            return;
        }
        Appointment appointment = controller.getAllAppointments().get(selectedRow);
        AppointmentDialog dialog = new AppointmentDialog(this, controller, appointment, selectedRow);
        dialog.setVisible(true);
        refreshAppointmentTable();
    }
    
    private void deleteSelectedAppointment() {
        int selectedRow = appointmentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an appointment to delete");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this appointment?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deleteAppointment(selectedRow);
            refreshAppointmentTable();
        }
    }
    
    private void showAddPrescriptionDialog() {
        PrescriptionDialog dialog = new PrescriptionDialog(this, controller, null, -1);
        dialog.setVisible(true);
        refreshPrescriptionTable();
    }
    
    private void showEditPrescriptionDialog() {
        int selectedRow = prescriptionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a prescription to edit");
            return;
        }
        Prescription prescription = controller.getAllPrescriptions().get(selectedRow);
        PrescriptionDialog dialog = new PrescriptionDialog(this, controller, prescription, selectedRow);
        dialog.setVisible(true);
        refreshPrescriptionTable();
    }
    
    private void deleteSelectedPrescription() {
        int selectedRow = prescriptionTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a prescription to delete");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete this prescription?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.deletePrescription(selectedRow);
            refreshPrescriptionTable();
        }
    }
    
    private void showCreateReferralDialog() {
        ReferralDialog dialog = new ReferralDialog(this, controller);
        dialog.setVisible(true);
        refreshReferralTable();
    }
    
    private void showReferralDetails() {
        int selectedRow = referralTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a referral to view");
            return;
        }
        Referral referral = controller.getAllReferrals().get(selectedRow);
        JOptionPane.showMessageDialog(this, 
            "Referral ID: " + referral.getReferralId() + "\n" +
            "Patient: " + referral.getPatientId() + "\n" +
            "Reason: " + referral.getReferralReason() + "\n" +
            "Urgency: " + referral.getUrgencyLevel() + "\n" +
            "Status: " + referral.getStatus() + "\n" +
            "Clinical Summary: " + referral.getClinicalSummary(),
            "Referral Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void savePrescriptions() {
        if (controller.savePrescriptions("prescriptions.csv")) {
            JOptionPane.showMessageDialog(this, "Prescriptions saved successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Error saving prescriptions", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showFacilityDetails() {
        int selectedRow = facilityTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a facility to view");
            return;
        }
        Facility facility = controller.getAllFacilities().get(selectedRow);
        JOptionPane.showMessageDialog(this, 
            "Facility ID: " + facility.getFacilityId() + "\n" +
            "Name: " + facility.getFacilityName() + "\n" +
            "Type: " + facility.getFacilityType() + "\n" +
            "Address: " + facility.getAddress() + ", " + facility.getPostcode() + "\n" +
            "Phone: " + facility.getPhoneNumber() + "\n" +
            "Email: " + facility.getEmail() + "\n" +
            "Opening Hours: " + facility.getOpeningHours() + "\n" +
            "Manager: " + facility.getManagerName() + "\n" +
            "Capacity: " + facility.getCapacity() + "\n" +
            "Specialities: " + facility.getSpecialitiesOffered(),
            "Facility Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ==================== HELPER METHODS ====================
    
    private void loadAllData() {
        System.out.println("=== Starting to load data ===");
        if (controller.loadAllData()) {
            refreshPatientTable();
            refreshClinicianTable();
            refreshFacilityTable();
            refreshAppointmentTable();
            refreshPrescriptionTable();
            refreshReferralTable();
            
            JOptionPane.showMessageDialog(this, 
                "✓ Data loaded successfully!\n\n" +
                "Patients: " + controller.getAllPatients().size() + "\n" +
                "Clinicians: " + controller.getAllClinicians().size() + "\n" +
                "Facilities: " + controller.getAllFacilities().size() + "\n" +
                "Appointments: " + controller.getAllAppointments().size() + "\n" +
                "Prescriptions: " + controller.getAllPrescriptions().size() + "\n" +
                "Referrals: " + controller.getAllReferrals().size());
        } else {
            JOptionPane.showMessageDialog(this, 
                "Some data files could not be loaded.\nCheck console for details.",
                "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void loadDataFile(String filename, String dataType) {
        boolean success = false;
        switch (dataType) {
            case "Patients":
                success = controller.loadPatients(filename);
                refreshPatientTable();
                break;
            case "Clinicians":
                success = controller.loadClinicians(filename);
                refreshClinicianTable();
                break;
            case "Facilities":
                success = controller.loadFacilities(filename);
                refreshFacilityTable();
                break;
            case "Appointments":
                success = controller.loadAppointments(filename);
                refreshAppointmentTable();
                break;
            case "Prescriptions":
                success = controller.loadPrescriptions(filename);
                refreshPrescriptionTable();
                break;
            case "Referrals":
                success = controller.loadReferrals(filename);
                refreshReferralTable();
                break;
        }
        
        if (success) {
            JOptionPane.showMessageDialog(this, dataType + " loaded successfully!");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error loading " + dataType + ". Check if file exists.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "Healthcare Management System\n" +
            "Version 1.0\n\n" +
            "Student: 22086967.Tejani\n\n" +
            "Architecture: MVC Pattern\n" +
            "Design Pattern: Singleton (ReferralManager)\n\n" +
            "A comprehensive system for managing patients,\n" +
            "clinicians, appointments, prescriptions, and referrals.",
            "About", JOptionPane.INFORMATION_MESSAGE);
    }
}