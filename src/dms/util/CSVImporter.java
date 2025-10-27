package dms.util;

import dms.model.DVD;
import dms.service.DVDCollection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Irene Duett, CEN 3024c, 10/25/2025
 * class: CSVImporter
 * purpose: Reads data from a CSV file and loads it into the DVDCollection
 */
public class CSVImporter {

    /**
     * method: importFromCSV
     * parameters: String filePath, DVDCollection collection
     * return: int (number of DVDs successfully added)
     * purpose: Reads a CSV file and adds valid DVD records to the system
     */
    public static int importFromCSV(String filePath, DVDCollection collection) {
        int count = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length != 6) {
                    System.out.println("Invalid line skipped: " + line);
                    continue;
                }

                try {
                    int id = Integer.parseInt(values[0].trim());
                    String title = values[1].trim();
                    String director = values[2].trim();
                    int year = Integer.parseInt(values[3].trim());
                    String genre = values[4].trim();
                    double rating = Double.parseDouble(values[5].trim());

                    DVD dvd = new DVD(id, title, director, year, genre, rating);
                    collection.addDVD(dvd);
                    count++;

                } catch (Exception e) {
                    System.out.println("Invalid data, skipped line: " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading file. Make sure the file path is correct.");
        }

        return count;
    }
}
