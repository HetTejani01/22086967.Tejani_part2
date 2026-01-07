package com.mycompany.tejani_part2.views;
import com.mycompany.tejani_part2.models.*;
import com.mycompany.tejani_part2.controllers.*;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Dialog for adding/editing appointments
 */
public class AppointmentDialog extends JDialog {
    private HealthcareController controller;
    private Appointment existingAppointment;
    private int editIndex;
    
    private JTextField idField;
    private JComboBox<String> patientBox;
    private JComboBox<String> clinicianBox;
    private JComboBox<String> facilityBox;
    private JTextField dateField;
    private JTextField timeField;
    private JSpinner durationSpinner;
    private JComboBox<String> typeBox;
    private JComboBox<String> statusBox;
    private JTextField reasonField;
    private JTextArea notesArea;
    private JTextField createdDateField;
    
    public AppointmentDialog(JFrame parent, HealthcareController controller, 
                            Appointment appointment, int index) {
        super(parent, appointment == null ? "Add Appointment" : "Edit Appointment", true);
        this.controller = controller;
        this.existingAppointment = appointment;
        this.editIndex = index;
        
        setSize(550, 600);
        setLocationRelativeTo(parent);
        
        initializeComponents();
        
        if (appointment != null) {
            populateFields(appointment);
        } else {
            idField.setText(controller.generateNextAppointmentId());
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            dateField.setText(currentDate);
            createdDateField.setText(currentDate);
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
        
        facilityBox = new JComboBox<>();
        for (Facility f : controller.getAllFacilities()) {
            facilityBox.addItem(f.toString());
        }
        
        dateField = new JTextField(20);
        timeField = new JTextField(20);
        durationSpinner = new JSpinner(new SpinnerNumberModel(15, 5, 120, 5));
        
        typeBox = new JComboBox<>(new String[]{
            "Routine Consultation", "Follow-up", "Urgent Consultation", 
            "Specialist Consultation", "Vaccination", "Health Check", "Emergency"
        });
        
        statusBox = new JComboBox<>(new String[]{
            "Scheduled", "Completed", "Cancelled", "No Show", "In Progress"
        });
        
        reasonField = new JTextField(20);
        notesArea = new JTextArea(3, 20);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        JScrollPane notesScroll = new JScrollPane(notesArea);
        
        createdDateField = new JTextField(20);
        createdDateField.setEditable(false);
        
        // Add components
        int row = 0;
        
        addField(mainPanel, gbc, "Appointment ID:", idField, row++);
        addField(mainPanel, gbc, "Patient:", patientBox, row++);
        addField(mainPanel, gbc, "Clinician:", clinicianBox, row++);
        addField(mainPanel, gbc, "Facility:", facilityBox, row++);
        addField(mainPanel, gbc, "Date (YYYY-MM-DD):", dateField, row++);
        addField(mainPanel, gbc, "Time (HH:MM):", timeField, row++);
        addField(mainPanel, gbc, "Duration (minutes):", durationSpinner, row++);
        addField(mainPanel, gbc, "Type:", typeBox, row++);
        addField(mainPanel, gbc, "Status:", statusBox, row++);
        addField(mainPanel, gbc, "Reason for Visit:", reasonField, row++);
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        mainPanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        mainPanel.add(notesScroll, gbc);
        row++;
        
        addField(mainPanel, gbc, "Created Date:", createdDateField, row++);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        
        saveBtn.addActionListener(e -> saveAppointment());
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
    
    private void populateFields(Appointment appointment) {
        idField.setText(appointment.getAppointmentId());
        
        // Find and select patient
        for (int i = 0; i < patientBox.getItemCount(); i++) {
            if (patientBox.getItemAt(i).startsWith(appointment.getPatientId())) {
                patientBox.setSelectedIndex(i);
                break;
            }
        }
        
        // Find and select clinician
        for (int i = 0; i < clinicianBox.getItemCount(); i++) {
            if (clinicianBox.getItemAt(i).startsWith(appointment.getClinicianId())) {
                clinicianBox.setSelectedIndex(i);
                break;
            }
        }
        
        // Find and select facility
        for (int i = 0; i < facilityBox.getItemCount(); i++) {
            if (facilityBox.getItemAt(i).startsWith(appointment.getFacilityId())) {
                facilityBox.setSelectedIndex(i);
                break;
            }
        }
        
        dateField.setText(appointment.getAppointmentDate());
        timeField.setText(appointment.getAppointmentTime());
        durationSpinner.setValue(appointment.getDurationMinutes());
        typeBox.setSelectedItem(appointment.getAppointmentType());
        statusBox.setSelectedItem(appointment.getStatus());
        reasonField.setText(appointment.getReasonForVisit());
        notesArea.setText(appointment.getNotes());
        createdDateField.setText(appointment.getCreatedDate());
    }
    
    private void saveAppointment() {
        // Validate required fields
        if (dateField.getText().trim().isEmpty() || timeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Date and Time are required!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Extract IDs from combo box selections
        String patientId = patientBox.getSelectedItem().toString().split(" - ")[0];
        String clinicianId = clinicianBox.getSelectedItem().toString().split(" - ")[0];
        String facilityId = facilityBox.getSelectedItem().toString().split(" - ")[0];
        
        // Create appointment object
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        Appointment appointment = new Appointment(
            idField.getText().trim(),
            patientId,
            clinicianId,
            facilityId,
            dateField.getText().trim(),
            timeField.getText().trim(),
            (Integer) durationSpinner.getValue(),
            (String) typeBox.getSelectedItem(),
            (String) statusBox.getSelectedItem(),
            reasonField.getText().trim(),
            notesArea.getText().trim(),
            createdDateField.getText().trim(),
            currentDate
        );
        
        // Add or update
        if (existingAppointment == null) {
            controller.addAppointment(appointment);
            JOptionPane.showMessageDialog(this, "Appointment added successfully!");
        } else {
            controller.updateAppointment(editIndex, appointment);
            JOptionPane.showMessageDialog(this, "Appointment updated successfully!");
        }
        
        dispose();
    }
}