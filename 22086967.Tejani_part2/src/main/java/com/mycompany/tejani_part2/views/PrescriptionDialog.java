package com.mycompany.tejani_part2.views;
import com.mycompany.tejani_part2.models.*;
import com.mycompany.tejani_part2.controllers.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Dialog for adding/editing prescriptions
 */
public class PrescriptionDialog extends JDialog {
    private HealthcareController controller;
    private Prescription existingPrescription;
    private int editIndex;
    
    private JTextField idField;
    private JComboBox<String> patientBox;
    private JComboBox<String> clinicianBox;
    private JTextField appointmentIdField;
    private JTextField dateField;
    private JTextField medicationField;
    private JTextField dosageField;
    private JTextField frequencyField;
    private JSpinner durationSpinner;
    private JTextField quantityField;
    private JTextArea instructionsArea;
    private JTextField pharmacyField;
    private JComboBox<String> statusBox;
    private JTextField issueDateField;
    private JTextField collectionDateField;
    
    public PrescriptionDialog(JFrame parent, HealthcareController controller, 
                             Prescription prescription, int index) {
        super(parent, prescription == null ? "Add Prescription" : "Edit Prescription", true);
        this.controller = controller;
        this.existingPrescription = prescription;
        this.editIndex = index;
        
        setSize(550, 650);
        setLocationRelativeTo(parent);
        
        initializeComponents();
        
        if (prescription != null) {
            populateFields(prescription);
        } else {
            idField.setText(controller.generateNextPrescriptionId());
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            dateField.setText(currentDate);
            issueDateField.setText(currentDate);
        }
    }
    
    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Initialize fields
        idField = new JTextField(20);
        idField.setEditable(false);
        
        // Populate combo boxes
        patientBox = new JComboBox<>();
        for (Patient p : controller.getAllPatients()) {
            patientBox.addItem(p.toString());
        }
        
        clinicianBox = new JComboBox<>();
        for (Clinician c : controller.getAllClinicians()) {
            clinicianBox.addItem(c.toString());
        }
        
        appointmentIdField = new JTextField(20);
        dateField = new JTextField(20);
        medicationField = new JTextField(20);
        dosageField = new JTextField(20);
        frequencyField = new JTextField(20);
        durationSpinner = new JSpinner(new SpinnerNumberModel(7, 1, 90, 1));
        quantityField = new JTextField(20);
        
        instructionsArea = new JTextArea(3, 20);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        JScrollPane instructionsScroll = new JScrollPane(instructionsArea);
        
        pharmacyField = new JTextField(20);
        
        statusBox = new JComboBox<>(new String[]{
            "Issued", "Collected", "Pending", "Cancelled"
        });
        
        issueDateField = new JTextField(20);
        collectionDateField = new JTextField(20);
        
        // Add components
        int row = 0;
        
        addField(mainPanel, gbc, "Prescription ID:", idField, row++);
        addField(mainPanel, gbc, "Patient:", patientBox, row++);
        addField(mainPanel, gbc, "Clinician:", clinicianBox, row++);
        addField(mainPanel, gbc, "Appointment ID (optional):", appointmentIdField, row++);
        addField(mainPanel, gbc, "Prescription Date:", dateField, row++);
        addField(mainPanel, gbc, "Medication Name:", medicationField, row++);
        addField(mainPanel, gbc, "Dosage:", dosageField, row++);
        addField(mainPanel, gbc, "Frequency:", frequencyField, row++);
        addField(mainPanel, gbc, "Duration (days):", durationSpinner, row++);
        addField(mainPanel, gbc, "Quantity:", quantityField, row++);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        mainPanel.add(new JLabel("Instructions:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        mainPanel.add(instructionsScroll, gbc);
        row++;
        
        addField(mainPanel, gbc, "Pharmacy Name:", pharmacyField, row++);
        addField(mainPanel, gbc, "Status:", statusBox, row++);
        addField(mainPanel, gbc, "Issue Date:", issueDateField, row++);
        addField(mainPanel, gbc, "Collection Date (optional):", collectionDateField, row++);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveBtn = new JButton("Save");
        JButton saveToFileBtn = new JButton("Save & Export to File");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> savePrescription(false));
        saveToFileBtn.addActionListener(e -> savePrescription(true));
        cancelBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(saveToFileBtn);
        buttonPanel.add(cancelBtn);
        
        // Layout
        setLayout(new BorderLayout());
        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addField(JPanel panel, GridBagConstraints gbc, String label, 
                         JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        panel.add(new JLabel(label), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(field, gbc);
    }
    
    private void populateFields(Prescription prescription) {
        idField.setText(prescription.getPrescriptionId());
        
        // Find and select patient
        for (int i = 0; i < patientBox.getItemCount(); i++) {
            if (patientBox.getItemAt(i).startsWith(prescription.getPatientId())) {
                patientBox.setSelectedIndex(i);
                break;
            }
        }
        
        // Find and select clinician
        for (int i = 0; i < clinicianBox.getItemCount(); i++) {
            if (clinicianBox.getItemAt(i).startsWith(prescription.getClinicianId())) {
                clinicianBox.setSelectedIndex(i);
                break;
            }
        }
        
        appointmentIdField.setText(prescription.getAppointmentId());
        dateField.setText(prescription.getPrescriptionDate());
        medicationField.setText(prescription.getMedicationName());
        dosageField.setText(prescription.getDosage());
        frequencyField.setText(prescription.getFrequency());
        durationSpinner.setValue(prescription.getDurationDays());
        quantityField.setText(prescription.getQuantity());
        instructionsArea.setText(prescription.getInstructions());
        pharmacyField.setText(prescription.getPharmacyName());
        statusBox.setSelectedItem(prescription.getStatus());
        issueDateField.setText(prescription.getIssueDate());
        collectionDateField.setText(prescription.getCollectionDate());
    }
    
    private void savePrescription(boolean exportToFile) {
        // Validate required fields
        if (medicationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Medication Name is required!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Extract IDs from combo box selections
        String patientId = patientBox.getSelectedItem().toString().split(" - ")[0];
        String clinicianId = clinicianBox.getSelectedItem().toString().split(" - ")[0];
        
        // Create prescription object
        Prescription prescription = new Prescription(
            idField.getText().trim(),
            patientId,
            clinicianId,
            appointmentIdField.getText().trim(),
            dateField.getText().trim(),
            medicationField.getText().trim(),
            dosageField.getText().trim(),
            frequencyField.getText().trim(),
            (Integer) durationSpinner.getValue(),
            quantityField.getText().trim(),
            instructionsArea.getText().trim(),
            pharmacyField.getText().trim(),
            (String) statusBox.getSelectedItem(),
            issueDateField.getText().trim(),
            collectionDateField.getText().trim()
        );
        
        // Add or update
        if (existingPrescription == null) {
            controller.addPrescription(prescription);
            JOptionPane.showMessageDialog(this, "Prescription added successfully!");
        } else {
            controller.updatePrescription(editIndex, prescription);
            JOptionPane.showMessageDialog(this, "Prescription updated successfully!");
        }
        
        // Export to file if requested
        if (exportToFile) {
            if (controller.savePrescriptions("prescriptions.csv")) {
                JOptionPane.showMessageDialog(this, 
                    "Prescription saved to prescriptions.csv!");
            }
        }
        
        dispose();
    }
}