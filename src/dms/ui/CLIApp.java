package dms.ui;

import dms.model.DVD;
import dms.service.DVDCollection;
import dms.util.CSVImporter;
import dms.util.Validator;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Irene Duett, CEN 3024c, 10/9/2025
 * class: CLIApp
 * purpose: Provides a command-line interface for interacting with the DVD database.
 */
public class CLIApp {

    // Provide the path to your SQLite database here
    private final DVDCollection dvdCollection = new DVDCollection("jdbc:sqlite:C:/Users/irene/IdeaProjects/dvddms/db/dvds.db");
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        CLIApp app = new CLIApp();
        app.run();
    }

    private void run() {
        while (true) {
            System.out.println("\n--- DVD Management System ---");
            System.out.println("1. Add DVD");
            System.out.println("2. List DVDs");
            System.out.println("3. Update DVD");
            System.out.println("4. Remove DVD");
            System.out.println("5. Compute Average Rating by Genre");
            System.out.println("6. Import DVDs from CSV File");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": addDVD(); break;
                case "2": listDVDs(); break;
                case "3": updateDVD(); break;
                case "4": removeDVD(); break;
                case "5": computeAverage(); break;
                case "6": importCSV(); break;
                case "7": System.out.println("Exiting..."); return;
                default: System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addDVD() {
        try {
            System.out.print("Title: ");
            String title = scanner.nextLine();
            System.out.print("Director: ");
            String director = scanner.nextLine();
            System.out.print("Release Year: ");
            int year = Integer.parseInt(scanner.nextLine());
            if (!Validator.isValidYear(year)) throw new NumberFormatException("Invalid year.");
            System.out.print("Genre: ");
            String genre = scanner.nextLine();
            System.out.print("Rating (-1 if unknown): ");
            double rating = Double.parseDouble(scanner.nextLine());
            if (!Validator.isValidRating(rating)) throw new NumberFormatException("Invalid rating.");

            DVD dvd = new DVD(0, title, director, year, genre, rating);
            dvdCollection.addDVD(dvd);
            System.out.println("DVD added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listDVDs() {
        List<DVD> dvds = dvdCollection.listAll();
        if (dvds.isEmpty()) System.out.println("No DVDs in collection.");
        else dvds.forEach(System.out::println);
    }

    private void updateDVD() {
        try {
            System.out.print("Enter DVD ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Optional<DVD> dvdOpt = dvdCollection.findById(id);
            if (dvdOpt.isEmpty()) {
                System.out.println("DVD not found.");
                return;
            }

            DVD dvd = dvdOpt.get();
            System.out.print("New Title (" + dvd.getTitle() + "): ");
            String title = scanner.nextLine();
            if (!title.isEmpty()) dvd.setTitle(title);

            System.out.print("New Director (" + dvd.getDirector() + "): ");
            String director = scanner.nextLine();
            if (!director.isEmpty()) dvd.setDirector(director);

            System.out.print("New Release Year (" + dvd.getReleaseYear() + "): ");
            String yearStr = scanner.nextLine();
            if (!yearStr.isEmpty()) dvd.setReleaseYear(Integer.parseInt(yearStr));

            System.out.print("New Genre (" + dvd.getGenre() + "): ");
            String genre = scanner.nextLine();
            if (!genre.isEmpty()) dvd.setGenre(genre);

            System.out.print("New Rating (" + dvd.getRating() + "): ");
            String ratingStr = scanner.nextLine();
            if (!ratingStr.isEmpty()) dvd.setRating(Double.parseDouble(ratingStr));

            boolean success = dvdCollection.updateDVD(id, dvd);
            System.out.println(success ? "DVD updated successfully!" : "Update failed.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input.");
        }
    }

    private void removeDVD() {
        try {
            System.out.print("Enter DVD ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine());
            boolean removed = dvdCollection.removeDVDById(id);
            System.out.println(removed ? "DVD removed successfully!" : "DVD not found.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input.");
        }
    }

    private void computeAverage() {
        System.out.print("Enter genre to compute average rating: ");
        String genre = scanner.nextLine();
        double avg = dvdCollection.computeAverageRatingByGenre(genre);
        System.out.println("Average rating for genre '" + genre + "': " + avg);
    }

    private void importCSV() {
        try {
            System.out.print("Enter full CSV file path (example: C:\\Users\\YourName\\movies.csv): ");
            String filePath = scanner.nextLine();
            int count = CSVImporter.importFromCSV(filePath, dvdCollection);
            System.out.println(count + " DVDs imported successfully!");
        } catch (Exception e) {
            System.out.println("CSV import failed: " + e.getMessage());
        }
    }
}
