package dms.util;

import dms.model.DVD;
import dms.service.DVDCollection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Irene Duett, CEN 3024c, 11/12/2025
 *
 * The {@code CSVImporter} class provides functionality to import DVD data from
 * a CSV (Comma-Separated Values) file into the system’s {@link DVDCollection}.
 *
 * <p>Each line in the CSV file is expected to represent a single DVD record
 * with six attributes in the following order:
 * <b>ID, Title, Director, Release Year, Genre, Rating</b>.</p>
 *
 * <p>Invalid or improperly formatted lines are skipped automatically, and an
 * informational message is displayed in the console for transparency.</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 *     DVDCollection collection = new DVDCollection("db/dvds.db");
 *     int importedCount = CSVImporter.importFromCSV("data/dvds.csv", collection);
 *     System.out.println(importedCount + " DVDs successfully imported.");
 * </pre>
 */
public class CSVImporter {

    /**
     * Imports DVD data from a CSV file into the specified {@link DVDCollection}.
     * <p>
     * The method reads each line, splits it by commas, validates the data,
     * constructs a {@link DVD} object, and adds it to the collection.
     * Lines that are missing fields or contain invalid data are skipped.
     * </p>
     *
     * @param filePath   the full path to the CSV file to be imported
     * @param collection the {@link DVDCollection} where the DVDs will be stored
     * @return the number of DVDs successfully added to the collection
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
