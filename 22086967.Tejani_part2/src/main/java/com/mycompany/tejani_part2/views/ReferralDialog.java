package com.mycompany.tejani_part2.views;
import com.mycompany.tejani_part2.models.*;
import com.mycompany.tejani_part2.controllers.*;
import javax.swing.*;
import java.awt.*;

/**
 * Dialog for creating referrals - uses Singleton ReferralManager
 */
public class ReferralDialog extends JDialog {
    private HealthcareController controller;
    
    private JComboBox<String> patientBox;
    private JComboBox<String> referringClinicianBox;
    private JComboBox<String> receivingClinicianBox;
    private JComboBox<String> referringFacilityBox;
    private JComboBox<String> receivingFacilityBox;
    private JComboBox<String> urgencyBox;
    private JTextField reasonField;
    private JTextArea clinicalSummaryArea;
    private JTextArea investigationsArea;
    private JTextArea notesArea;
    
    public ReferralDialog(JFrame parent, HealthcareController controller) {
        super(parent, "Create New Referral", true);
        this.controller = controller;
        
        setSize(600, 700);
        setLocationRelativeTo(parent);
        
        initializeComponents();
    }
    
    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("Create New Patient Referral");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;
        
        // Patient Selection
        patientBox = new JComboBox<>();
        for (Patient p : controller.getAllPatients()) {
            patientBox.addItem(p.toString());
        }
        
        // Referring Clinician
        referringClinicianBox = new JComboBox<>();
        for (Clinician c : controller.getAllClinicians()) {
            referringClinicianBox.addItem(c.toString());
        }
        
        // Receiving Clinician (Specialists)
        receivingClinicianBox = new JComboBox<>();
        for (Clinician c : controller.getAllClinicians()) {
            // Filter for consultants/specialists
            if (c.getTitle().contains("Consultant") || c.getTitle().contains("Dr.")) {
                receivingClinicianBox.addItem(c.toString());
            }
        }
        
        // Referring Facility
        referringFacilityBox = new JComboBox<>();
        for (Facility f : controller.getAllFacilities()) {
            referringFacilityBox.addItem(f.toString());
        }
        
        // Receiving Facility
        receivingFacilityBox = new JComboBox<>();
        for (Facility f : controller.getAllFacilities()) {
            receivingFacilityBox.addItem(f.toString());
        }
        
        // Urgency
        urgencyBox = new JComboBox<>(new String[]{
            "Routine", "Urgent", "Non-urgent", "Emergency"
        });
        
        reasonField = new JTextField(30);
        
        clinicalSummaryArea = new JTextArea(4, 30);
        clinicalSummaryArea.setLineWrap(true);
        clinicalSummaryArea.setWrapStyleWord(true);
        JScrollPane summaryScroll = new JScrollPane(clinicalSummaryArea);
        
        investigationsArea = new JTextArea(3, 30);
        investigationsArea.setLineWrap(true);
        investigationsArea.setWrapStyleWord(true);
        JScrollPane investigationsScroll = new JScrollPane(investigationsArea);
        
        notesArea = new JTextArea(3, 30);
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        JScrollPane notesScroll = new JScrollPane(notesArea);
        
        // Add fields
        int row = 1;
        
        addField(mainPanel, gbc, "Patient:", patientBox, row++);
        addField(mainPanel, gbc, "Referring Clinician:", referringClinicianBox, row++);
        addField(mainPanel, gbc, "Referring From (Facility):", referringFacilityBox, row++);
        
        // Separator
        JSeparator separator = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        mainPanel.add(separator, gbc);
        gbc.gridwidth = 1;
        
        addField(mainPanel, gbc, "Referring To (Clinician):", receivingClinicianBox, row++);
        addField(mainPanel, gbc, "Referring To (Facility):", receivingFacilityBox, row++);
        
        // Another separator
        JSeparator separator2 = new JSeparator();
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        mainPanel.add(separator2, gbc);
        gbc.gridwidth = 1;
        
        addField(mainPanel, gbc, "Urgency Level:", urgencyBox, row++);
        addField(mainPanel, gbc, "Reason for Referral:", reasonField, row++);
        
        // Text areas
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        mainPanel.add(new JLabel("Clinical Summary:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        mainPanel.add(summaryScroll, gbc);
        row++;
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        mainPanel.add(new JLabel("Requested Investigations:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        mainPanel.add(investigationsScroll, gbc);
        row++;
        
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.3;
        mainPanel.add(new JLabel("Additional Notes:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        mainPanel.add(notesScroll, gbc);
        row++;
        
        // Info label
        JLabel infoLabel = new JLabel("<html><i>Note: Referral will be processed by the Singleton ReferralManager<br>" +
                                     "and saved to file with email notification.</i></html>");
        infoLabel.setForeground(Color.BLUE);
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        mainPanel.add(infoLabel, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton createBtn = new JButton("Create Referral");
        JButton cancelBtn = new JButton("Cancel");
        
        createBtn.addActionListener(e -> createReferral());
        cancelBtn.addActionListener(e -> dispose());
        
        buttonPanel.add(createBtn);
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
    
    private void createReferral() {
        // Validate required fields
        if (reasonField.getText().trim().isEmpty() || 
            clinicalSummaryArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Reason and Clinical Summary are required!", 
                "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Extract IDs from combo box selections
        String patientId = patientBox.getSelectedItem().toString().split(" - ")[0];
        String referringClinicianId = referringClinicianBox.getSelectedItem().toString().split(" - ")[0];
        String receivingClinicianId = receivingClinicianBox.getSelectedItem().toString().split(" - ")[0];
        String referringFacilityId = referringFacilityBox.getSelectedItem().toString().split(" - ")[0];
        String receivingFacilityId = receivingFacilityBox.getSelectedItem().toString().split(" - ")[0];
        
        // Create referral using controller (which uses Singleton ReferralManager)
        boolean success = controller.createReferral(
            patientId,
            referringClinicianId,
            receivingClinicianId,
            referringFacilityId,
            receivingFacilityId,
            (String) urgencyBox.getSelectedItem(),
            reasonField.getText().trim(),
            clinicalSummaryArea.getText().trim(),
            investigationsArea.getText().trim(),
            notesArea.getText().trim()
        );
        
        if (success) {
            JOptionPane.showMessageDialog(this, 
                "Referral created successfully!\n\n" +
                "- Referral document saved to file\n" +
                "- Email notification generated\n" +
                "- Audit trail updated\n\n" +
                "Check the output files for details.",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Error creating referral. Check console for details.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
