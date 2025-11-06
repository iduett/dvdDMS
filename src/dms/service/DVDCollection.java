package dms.service;

import dms.model.DVD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * class: Irene Duett, CEN 3024c, 10/9/2025
 * DVDCollection
 * purpose: Stores and manages DVDs using a SQLite database, providing CRUD operations and calculations.
 */
public class DVDCollection {

    private final String dbUrl;

    /**
     * constructor: DVDCollection
     * parameters: String dbUrl
     * purpose: Initializes the collection with a SQLite database connection URL
     */
    public DVDCollection(String dbUrl) {
        this.dbUrl = dbUrl;
        createTableIfNotExists();
    }

    /**
     * method: createTableIfNotExists
     * parameters: none
     * return: void
     * purpose: Ensures the dvd table exists in the database
     */
    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS dvd (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "director TEXT," +
                "release_year INTEGER," +
                "genre TEXT," +
                "rating REAL" +
                ")";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    /**
     * method: addDVD
     * parameters: DVD dvd
     * return: void
     * purpose: Adds a DVD to the database
     */
    public void addDVD(DVD dvd) {
        String sql = "INSERT INTO dvd (title, director, release_year, genre, rating) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dvd.getTitle());
            stmt.setString(2, dvd.getDirector());
            stmt.setInt(3, dvd.getReleaseYear());
            stmt.setString(4, dvd.getGenre());
            stmt.setDouble(5, dvd.getRating());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding DVD: " + e.getMessage());
        }
    }

    /**
     * method: listAll
     * parameters: none
     * return: List<DVD>
     * purpose: Returns a list of all DVDs from the database
     */
    public List<DVD> listAll() {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT * FROM dvd";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                DVD dvd = new DVD(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getInt("release_year"),
                        rs.getString("genre"),
                        rs.getDouble("rating")
                );
                dvds.add(dvd);
            }
        } catch (SQLException e) {
            System.out.println("Error listing DVDs: " + e.getMessage());
        }
        return dvds;
    }

    /**
     * method: findById
     * parameters: int id
     * return: Optional<DVD>
     * purpose: Finds a DVD by its unique ID from the database
     */
    public Optional<DVD> findById(int id) {
        String sql = "SELECT * FROM dvd WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DVD dvd = new DVD(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("director"),
                        rs.getInt("release_year"),
                        rs.getString("genre"),
                        rs.getDouble("rating")
                );
                return Optional.of(dvd);
            }
        } catch (SQLException e) {
            System.out.println("Error finding DVD: " + e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * method: removeDVDById
     * parameters: int id
     * return: boolean
     * purpose: Removes a DVD with the given ID from the database
     */
    public boolean removeDVDById(int id) {
        String sql = "DELETE FROM dvd WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error removing DVD: " + e.getMessage());
            return false;
        }
    }

    /**
     * method: computeAverageRatingByGenre
     * parameters: String genre
     * return: double
     * purpose: Computes the average rating of all DVDs in a specified genre
     */
    public double computeAverageRatingByGenre(String genre) {
        String sql = "SELECT AVG(rating) AS avg_rating FROM dvd WHERE genre = ? AND rating >= 0";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, genre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("avg_rating");
        } catch (SQLException e) {
            System.out.println("Error computing average: " + e.getMessage());
        }
        return 0.0;
    }

    /**
     * method: updateDVD
     * parameters: int id, DVD updatedDVD
     * return: boolean
     * purpose: Updates an existing DVD in the database
     */
    public boolean updateDVD(int id, DVD updatedDVD) {
        String sql = "UPDATE dvd SET title=?, director=?, release_year=?, genre=?, rating=? WHERE id=?";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, updatedDVD.getTitle());
            stmt.setString(2, updatedDVD.getDirector());
            stmt.setInt(3, updatedDVD.getReleaseYear());
            stmt.setString(4, updatedDVD.getGenre());
            stmt.setDouble(5, updatedDVD.getRating());
            stmt.setInt(6, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating DVD: " + e.getMessage());
            return false;
        }
    }
}
