package com.mycompany.tejani_part2.utilities;

import java.io.*;
import java.util.*;

/**
 * Utility class for reading CSV files
 */
public class CSVReader {
    
    /**
     * Read CSV file and return as list of string arrays
     */
    public static List<String[]> readCSV(String filename, boolean skipHeader) {
        List<String[]> data = new ArrayList<>();
        BufferedReader br = null;
        
        try {
            br = new BufferedReader(new FileReader(filename));
            String line;
            boolean firstLine = true;
            
            while ((line = br.readLine()) != null) {
                if (firstLine && skipHeader) {
                    firstLine = false;
                    continue;
                }
                
                String[] values = parseCSVLine(line);
                data.add(values);
                firstLine = false;
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (IOException e) {
            System.err.println("Error reading file: " + filename);
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return data;
    }
    
    /**
     * Parse a CSV line handling quoted values and commas within quotes
     */
    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(sb.toString().trim());
                sb = new StringBuilder();
            } else {
                sb.append(c);
            }
        }
        
        result.add(sb.toString().trim());
        
        return result.toArray(new String[0]);
    }
    
    /**
     * Safe method to get value from array with default
     */
    public static String getValue(String[] array, int index, String defaultValue) {
        if (array != null && index >= 0 && index < array.length) {
            String value = array[index];
            return (value != null && !value.isEmpty()) ? value : defaultValue;
        }
        return defaultValue;
    }
    
    /**
     * Safe method to parse integer with default
     */
    public static int getIntValue(String[] array, int index, int defaultValue) {
        String value = getValue(array, index, "");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}