package com.mycompany.tejani_part2;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Minimal Main Application Entry Point
 * This version will compile even if other classes have issues
 */
public class App {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════╗");
        System.out.println("║   Healthcare Management System v1.0               ║");
        System.out.println("║   Student: 22086967.Tejani                         ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Application starting...");
        
        // Try to launch full GUI
        try {
            Class<?> guiClass = Class.forName("com.mycompany.tejani_part2.views.HealthcareGUI");
            Object gui = guiClass.getDeclaredConstructor().newInstance();
            System.out.println("✓ Full GUI launched successfully!");
        } catch (Exception e) {
            // If GUI fails, show basic window
            System.err.println("⚠ Could not load full GUI: " + e.getMessage());
            System.out.println("Showing test window instead...");
            
            JFrame frame = new JFrame("Healthcare System - Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            
            JLabel label = new JLabel(
                "<html><center>" +
                "<h1>Healthcare Management System</h1>" +
                "<p>Student: 22086967.Tejani</p>" +
                "<p><b>Status:</b> Application structure is ready</p>" +
                "<p>Please ensure all Java files have correct package declarations</p>" +
                "</center></html>",
                SwingConstants.CENTER
            );
            
            frame.add(label);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            System.out.println("✓ Test window displayed");
            System.out.println();
            System.out.println("Next steps:");
            System.out.println("1. Ensure all model classes have: package com.mycompany.tejani_part2.models;");
            System.out.println("2. Ensure all view classes have: package com.mycompany.tejani_part2.views;");
            System.out.println("3. Clean and Build project again");
        }
    }
}