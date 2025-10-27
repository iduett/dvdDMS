package dms.ui;

import dms.model.DVD;
import dms.service.DVDCollection;
import dms.util.CSVImporter;
import dms.util.Validator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * Irene Duett, CEN 3024c, 10/26/2025
 * class: DVDGUIApp
 * purpose: GUI for interacting with the DVD Collection
 */
public class DVDGUIApp {

    private final DVDCollection collection = new DVDCollection();
    private final JFrame frame = new JFrame("DVD Management System");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DVDGUIApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 450); // slightly taller for CSV button
        frame.setLayout(new GridLayout(8, 1, 5, 5));

        JButton addBtn = new JButton("Add DVD");
        JButton listBtn = new JButton("List DVDs");
        JButton updateBtn = new JButton("Update DVD");
        JButton removeBtn = new JButton("Remove DVD");
        JButton avgBtn = new JButton("Compute Average Rating by Genre");
        JButton csvBtn = new JButton("Import DVDs from CSV File");
        JButton exitBtn = new JButton("Exit");

        frame.add(addBtn);
        frame.add(listBtn);
        frame.add(updateBtn);
        frame.add(removeBtn);
        frame.add(avgBtn);
        frame.add(csvBtn);
        frame.add(exitBtn);

        // Button actions
        addBtn.addActionListener(this::addDVD);
        listBtn.addActionListener(this::listDVDs);
        updateBtn.addActionListener(this::updateDVD);
        removeBtn.addActionListener(this::removeDVD);
        avgBtn.addActionListener(this::computeAverage);
        csvBtn.addActionListener(this::importCSV);
        exitBtn.addActionListener(e -> frame.dispose());

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addDVD(ActionEvent e) {
        try {
            String title = JOptionPane.showInputDialog(frame, "Enter Title:");
            String director = JOptionPane.showInputDialog(frame, "Enter Director:");
            int year = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter Release Year:"));
            if (!Validator.isValidYear(year)) throw new NumberFormatException("Invalid year.");
            String genre = JOptionPane.showInputDialog(frame, "Enter Genre:");
            double rating = Double.parseDouble(JOptionPane.showInputDialog(frame, "Enter Rating (-1 if unknown):"));
            if (!Validator.isValidRating(rating)) throw new NumberFormatException("Invalid rating.");

            DVD dvd = new DVD(0, title, director, year, genre, rating);
            collection.addDVD(dvd);
            JOptionPane.showMessageDialog(frame, "DVD added successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void listDVDs(ActionEvent e) {
        List<DVD> dvds = collection.listAll();
        if (dvds.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No DVDs in collection.");
        } else {
            StringBuilder sb = new StringBuilder();
            dvds.forEach(d -> sb.append(d).append("\n"));
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(450, 300));
            JOptionPane.showMessageDialog(frame, scrollPane, "DVD List", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateDVD(ActionEvent e) {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter DVD ID to update:"));
            DVD dvd = collection.findById(id).orElse(null);
            if (dvd == null) {
                JOptionPane.showMessageDialog(frame, "DVD not found.");
                return;
            }

            String title = JOptionPane.showInputDialog(frame, "New Title (" + dvd.getTitle() + "):");
            if (!title.isEmpty()) dvd.setTitle(title);

            String director = JOptionPane.showInputDialog(frame, "New Director (" + dvd.getDirector() + "):");
            if (!director.isEmpty()) dvd.setDirector(director);

            String yearStr = JOptionPane.showInputDialog(frame, "New Release Year (" + dvd.getReleaseYear() + "):");
            if (!yearStr.isEmpty()) {
                int year = Integer.parseInt(yearStr);
                if (!Validator.isValidYear(year)) throw new NumberFormatException("Invalid year.");
                dvd.setReleaseYear(year);
            }

            String genre = JOptionPane.showInputDialog(frame, "New Genre (" + dvd.getGenre() + "):");
            if (!genre.isEmpty()) dvd.setGenre(genre);

            String ratingStr = JOptionPane.showInputDialog(frame, "New Rating (" + dvd.getRating() + "):");
            if (!ratingStr.isEmpty()) {
                double rating = Double.parseDouble(ratingStr);
                if (!Validator.isValidRating(rating)) throw new NumberFormatException("Invalid rating.");
                dvd.setRating(rating);
            }

            JOptionPane.showMessageDialog(frame, "DVD updated successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    private void removeDVD(ActionEvent e) {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter DVD ID to remove:"));
            boolean removed = collection.removeDVDById(id);
            JOptionPane.showMessageDialog(frame, removed ? "DVD removed successfully!" : "DVD not found.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Error: Please enter a valid ID.");
        }
    }

    private void computeAverage(ActionEvent e) {
        String genre = JOptionPane.showInputDialog(frame, "Enter genre to compute average rating:");
        double avg = collection.computeAverageRatingByGenre(genre);
        JOptionPane.showMessageDialog(frame, "Average rating for genre '" + genre + "': " + avg);
    }

    /**
     * method: importCSV
     * parameters: ActionEvent
     * return: void
     * purpose: Allows the user to select a CSV file and import all DVD records
     */
    private void importCSV(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            int count = CSVImporter.importFromCSV(path, collection);
            JOptionPane.showMessageDialog(frame, count + " DVDs imported successfully!");
        }
    }
}
