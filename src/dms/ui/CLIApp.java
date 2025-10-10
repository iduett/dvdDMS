package dms.ui;

import dms.model.DVD;
import dms.service.DVDCollection;
import dms.util.Validator;
import java.util.List;
import java.util.Scanner;

/**
 * Irene Duett, CEN 3024c, 10/9/2025
 * class: CLIApp
 * purpose: Provides a simple command-line interface for interacting with the DVD Collection.
 */
public class CLIApp {

    private final DVDCollection dvdCollection = new DVDCollection();
    private final Scanner scanner = new Scanner(System.in);

    /**
     * method: main
     * parameters: String[] args
     * return: void
     * purpose: Entry point of the DVD Management System CLI
     */
    public static void main(String[] args) {
        CLIApp app = new CLIApp();
        app.run();
    }

    /**
     * method: run
     * parameters: none
     * return: void
     * purpose: Displays the main menu and handles user input in a loop
     */
    private void run() {
        while (true) {
            System.out.println("\n--- DVD Management System ---");
            System.out.println("1. Add DVD");
            System.out.println("2. List DVDs");
            System.out.println("3. Update DVD");
            System.out.println("4. Remove DVD");
            System.out.println("5. Compute Average Rating by Genre");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1": addDVD(); break;
                case "2": listDVDs(); break;
                case "3": updateDVD(); break;
                case "4": removeDVD(); break;
                case "5": computeAverage(); break;
                case "6": System.out.println("Exiting..."); return;
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
            System.out.println("DVD added successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage() + " Please enter valid input.");
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
            DVD dvd = dvdCollection.findById(id).orElse(null);
            if (dvd == null) {
                System.out.println("DVD not found.");
                return;
            }
            System.out.print("New Title (" + dvd.getTitle() + "): ");
            String title = scanner.nextLine();
            if (!title.isEmpty()) dvd.setTitle(title);
            System.out.print("New Director (" + dvd.getDirector() + "): ");
            String director = scanner.nextLine();
            if (!director.isEmpty()) dvd.setDirector(director);
            System.out.print("New Release Year (" + dvd.getReleaseYear() + "): ");
            String yearStr = scanner.nextLine();
            if (!yearStr.isEmpty()) {
                int year = Integer.parseInt(yearStr);
                if (!Validator.isValidYear(year)) throw new NumberFormatException("Invalid year.");
                dvd.setReleaseYear(year);
            }
            System.out.print("New Genre (" + dvd.getGenre() + "): ");
            String genre = scanner.nextLine();
            if (!genre.isEmpty()) dvd.setGenre(genre);
            System.out.print("New Rating (" + dvd.getRating() + "): ");
            String ratingStr = scanner.nextLine();
            if (!ratingStr.isEmpty()) {
                double rating = Double.parseDouble(ratingStr);
                if (!Validator.isValidRating(rating)) throw new NumberFormatException("Invalid rating.");
                dvd.setRating(rating);
            }
            System.out.println("DVD updated successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void removeDVD() {
        try {
            System.out.print("Enter DVD ID to remove: ");
            int id = Integer.parseInt(scanner.nextLine());
            boolean removed = dvdCollection.removeDVDById(id);
            System.out.println(removed ? "DVD removed successfully." : "DVD not found.");
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid ID.");
        }
    }

    private void computeAverage() {
        System.out.print("Enter genre to compute average rating: ");
        String genre = scanner.nextLine();
        double avg = dvdCollection.computeAverageRatingByGenre(genre);
        System.out.println("Average rating for genre '" + genre + "': " + avg);
    }
}
