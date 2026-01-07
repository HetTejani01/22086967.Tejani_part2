package com.mycompany.tejani_part2.views;
import com.mycompany.tejani_part2.models.*;
import com.mycompany.tejani_part2.controllers.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Dialog for adding/editing clinicians
 */
public class ClinicianDialog extends JDialog {
    private HealthcareController controller;
    private Clinician existingClinician;
    private int editIndex;
    
    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JComboBox<String> titleBox;
    private JTextField specialityField;
    private JTextField gmcField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField workplaceIdField;
    private JComboBox<String> workplaceTypeBox;
    private JComboBox<String> employmentStatusBox;
    private JTextField startDateField;
    
    public ClinicianDialog(JFrame parent, HealthcareController controller, 
                          Clinician clinician, int index) {
        super(parent, clinician == null ? "Add Clinician" : "Edit Clinician", true);
        this.controller = controller;
        this.existingClinician = clinician;
        this.editIndex = index;
        
        setSize(500, 550);
        setLocationRelativeTo(parent);
        
        initializeComponents();
        
        if (clinician != null) {
            populateFields(clinician);
        } else {
            idField.setText(controller.generateNextClinicianId());
            startDateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
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
        titleBox = new JComboBox<>(new String[]{"Dr.", "Sister", "Nurse", "Consultant"});
        specialityField = new JTextField(20);
        gmcField = new JTextField(20);
        phoneField = new JTextField(20);
        emailField = new JTextField(20);
        workplaceIdField = new JTextField(20);
        workplaceTypeBox = new JComboBox<>(new String[]{"GP Surgery", "Hospital"});
        employmentStatusBox = new JComboBox<>(new String[]{"Full-time", "Part-time"});
        startDateField = new JTextField(20);
        
        // Add components
        int row = 0;
        
        addField(mainPanel, gbc, "Clinician ID:", idField, row++);
        addField(mainPanel, gbc, "Title:", titleBox, row++);
        addField(mainPanel, gbc, "First Name:", firstNameField, row++);
        addField(mainPanel, gbc, "Last Name:", lastNameField, row++);
        addField(mainPanel, gbc, "Speciality:", specialityField, row++);
        addField(mainPanel, gbc, "GMC Number:", gmcField, row++);
        addField(mainPanel, gbc, "Phone Number:", phoneField, row++);
        addField(mainPanel, gbc, "Email:", emailField, row++);
        addField(mainPanel, gbc, "Workplace ID:", workplaceIdField, row++);
        addField(mainPanel, gbc, "Workplace Type:", workplaceTypeBox, row++);
        addField(mainPanel, gbc, "Employment Status:", employmentStatusBox, row++);
        addField(mainPanel, gbc, "Start Date:", startDateField, row++);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> saveClinician());
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
    
    private void populateFields(Clinician clinician) {
        idField.setText(clinician.getClinicianId());
        firstNameField.setText(clinician.getFirstName());
        lastNameField.setText(clinician.getLastName());
        titleBox.setSelectedItem(clinician.getTitle());
        specialityField.setText(clinician.getSpeciality());
        gmcField.setText(clinician.getGmcNumber());
        phoneField.setText(clinician.getPhoneNumber());
        emailField.setText(clinician.getEmail());
        workplaceIdField.setText(clinician.getWorkplaceId());
        workplaceTypeBox.setSelectedItem(clinician.getWorkplaceType());
        employmentStatusBox.setSelectedItem(clinician.getEmploymentStatus());
        startDateField.setText(clinician.getStartDate());
    }
    
    private void saveClinician() {
        // Validate required fields
        if (firstNameField.getText().trim().isEmpty() || 
            lastNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "First Name and Last Name are required!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create clinician object
        Clinician clinician = new Clinician(
            idField.getText().trim(),
            firstNameField.getText().trim(),
            lastNameField.getText().trim(),
            (String) titleBox.getSelectedItem(),
            specialityField.getText().trim(),
            gmcField.getText().trim(),
            phoneField.getText().trim(),
            emailField.getText().trim(),
            workplaceIdField.getText().trim(),
            (String) workplaceTypeBox.getSelectedItem(),
            (String) employmentStatusBox.getSelectedItem(),
            startDateField.getText().trim()
        );
        
        // Add or update
        if (existingClinician == null) {
            controller.addClinician(clinician);
            JOptionPane.showMessageDialog(this, "Clinician added successfully!");
        } else {
            controller.updateClinician(editIndex, clinician);
            JOptionPane.showMessageDialog(this, "Clinician updated successfully!");
        }
        
        dispose();
    }
}