package dms.service;

import dms.model.DVD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Irene Duett, CEN 3024c, 11/12/2025
 *
 * The {@code DVDCollection} class provides full CRUD (Create, Read, Update, Delete)
 * functionality for managing {@link DVD} records stored in a SQLite database.
 *
 * <p>This class acts as the service layer between the user interface (CLI or GUI)
 * and the database, handling all SQL operations. It also provides a method to compute
 * the average rating for DVDs filtered by genre.</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 *     DVDCollection collection = new DVDCollection("C:/path/to/dvds.db");
 *     collection.addDVD(new DVD(0, "Inception", "Christopher Nolan", 2010, "Sci-Fi", 9.0));
 *     List&lt;DVD&gt; allDvds = collection.listAll();
 * </pre>
 */
public class DVDCollection {

    private final String dbUrl;

    /**
     * Constructs a new {@code DVDCollection} object linked to the specified SQLite database.
     * Automatically ensures that the {@code dvd} table exists by invoking
     * {@link #createTableIfNotExists()}.
     *
     * @param dbUrl the full database connection URL (e.g., "jdbc:sqlite:C:/path/to/dvds.db")
     */
    public DVDCollection(String dbUrl) {
        this.dbUrl = dbUrl;
        createTableIfNotExists();
    }

    /**
     * Ensures that the {@code dvd} table exists in the connected SQLite database.
     * If the table does not exist, it is created automatically.
     *
     * @throws SQLException if there is an error executing the SQL statement
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
     * Adds a new {@link DVD} record to the database.
     *
     * @param dvd the {@link DVD} object to add
     * @throws SQLException if a database access error occurs during insertion
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
     * Retrieves all {@link DVD} records from the database.
     *
     * @return a list containing all DVDs currently stored in the database
     * @throws SQLException if a database access error occurs while retrieving data
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
     * Finds a {@link DVD} in the database by its unique ID.
     *
     * @param id the unique identifier of the DVD to search for
     * @return an {@link Optional} containing the found {@link DVD}, or empty if not found
     * @throws SQLException if a database access error occurs while retrieving data
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
     * Removes a {@link DVD} record from the database using its unique ID.
     *
     * @param id the ID of the DVD to remove
     * @return {@code true} if the DVD was successfully deleted; {@code false} otherwise
     * @throws SQLException if a database access error occurs during deletion
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
     * Computes the average rating of all {@link DVD} records in a specified genre.
     * Only DVDs with non-negative ratings are included in the calculation.
     *
     * @param genre the genre to filter DVDs by
     * @return the average rating for the given genre, or 0.0 if none are found
     * @throws SQLException if a database access error occurs during computation
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
     * Updates an existing {@link DVD} record in the database.
     *
     * @param id          the unique ID of the DVD to update
     * @param updatedDVD  a {@link DVD} object containing the new data to apply
     * @return {@code true} if the update was successful; {@code false} otherwise
     * @throws SQLException if a database access error occurs during update
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
