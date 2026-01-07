package com.mycompany.tejani_part2.views;
import com.mycompany.tejani_part2.models.*;
import com.mycompany.tejani_part2.controllers.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Dialog for adding/editing patients
 */
public class PatientDialog extends JDialog {
    private HealthcareController controller;
    private Patient existingPatient;
    private int editIndex;
    
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dobField;
    private JTextField nhsField;
    private JComboBox<String> genderBox;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField postcodeField;
    private JTextField emergencyNameField;
    private JTextField emergencyPhoneField;
    private JTextField registrationDateField;
    private JTextField gpSurgeryField;
    
    public PatientDialog(JFrame parent, HealthcareController controller, 
                        Patient patient, int index) {
        super(parent, patient == null ? "Add Patient" : "Edit Patient", true);
        this.controller = controller;
        this.existingPatient = patient;
        this.editIndex = index;
        
        setSize(500, 600);
        setLocationRelativeTo(parent);
        
        initializeComponents();
        
        if (patient != null) {
            populateFields(patient);
        } else {
            idField.setText(controller.generateNextPatientId());
            registrationDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
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
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        dobField = new JTextField(20);
        nhsField = new JTextField(20);
        genderBox = new JComboBox<>(new String[]{"M", "F", "Other"});
        phoneField = new JTextField(20);
        emailField = new JTextField(20);
        addressField = new JTextField(20);
        postcodeField = new JTextField(20);
        emergencyNameField = new JTextField(20);
        emergencyPhoneField = new JTextField(20);
        registrationDateField = new JTextField(20);
        gpSurgeryField = new JTextField(20);
        
        // Add components
        int row = 0;
        
        addField(mainPanel, gbc, "Patient ID:", idField, row++);
        addField(mainPanel, gbc, "First Name:", firstNameField, row++);
        addField(mainPanel, gbc, "Last Name:", lastNameField, row++);
        addField(mainPanel, gbc, "Date of Birth (YYYY-MM-DD):", dobField, row++);
        addField(mainPanel, gbc, "NHS Number:", nhsField, row++);
        addField(mainPanel, gbc, "Gender:", genderBox, row++);
        addField(mainPanel, gbc, "Phone Number:", phoneField, row++);
        addField(mainPanel, gbc, "Email:", emailField, row++);
        addField(mainPanel, gbc, "Address:", addressField, row++);
        addField(mainPanel, gbc, "Postcode:", postcodeField, row++);
        addField(mainPanel, gbc, "Emergency Contact Name:", emergencyNameField, row++);
        addField(mainPanel, gbc, "Emergency Contact Phone:", emergencyPhoneField, row++);
        addField(mainPanel, gbc, "Registration Date:", registrationDateField, row++);
        addField(mainPanel, gbc, "GP Surgery ID:", gpSurgeryField, row++);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> savePatient());
        cancelBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(saveBtn);
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
    
    private void populateFields(Patient patient) {
        idField.setText(patient.getPatientId());
        firstNameField.setText(patient.getFirstName());
        lastNameField.setText(patient.getLastName());
        dobField.setText(patient.getDateOfBirth());
        nhsField.setText(patient.getNhsNumber());
        genderBox.setSelectedItem(patient.getGender());
        phoneField.setText(patient.getPhoneNumber());
        emailField.setText(patient.getEmail());
        addressField.setText(patient.getAddress());
        postcodeField.setText(patient.getPostcode());
        emergencyNameField.setText(patient.getEmergencyContactName());
        emergencyPhoneField.setText(patient.getEmergencyContactPhone());
        registrationDateField.setText(patient.getRegistrationDate());
        gpSurgeryField.setText(patient.getGpSurgeryId());
    }
    
    private void savePatient() {
        // Validate required fields
        if (firstNameField.getText().trim().isEmpty() || 
            lastNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "First Name and Last Name are required!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create patient object
        Patient patient = new Patient(
            idField.getText().trim(),
            firstNameField.getText().trim(),
            lastNameField.getText().trim(),
            dobField.getText().trim(),
            nhsField.getText().trim(),
            (String) genderBox.getSelectedItem(),
            phoneField.getText().trim(),
            emailField.getText().trim(),
            addressField.getText().trim(),
            postcodeField.getText().trim(),
            emergencyNameField.getText().trim(),
            emergencyPhoneField.getText().trim(),
            registrationDateField.getText().trim(),
            gpSurgeryField.getText().trim()
        );
        
        // Add or update
        if (existingPatient == null) {
            controller.addPatient(patient);
            JOptionPane.showMessageDialog(this, "Patient added successfully!");
        } else {
            controller.updatePatient(editIndex, patient);
            JOptionPane.showMessageDialog(this, "Patient updated successfully!");
        }
        
        dispose();
    }
}