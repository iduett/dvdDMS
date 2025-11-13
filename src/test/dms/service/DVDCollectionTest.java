package dms.service;

import dms.model.DVD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Irene Duett, CEN 3024c, 11/12/2025
 *
 * The {@code DVDCollectionTest} class contains unit tests for verifying the
 * functionality of the {@link DVDCollection} class. It tests key operations such
 * as adding, removing, updating, listing, and computing the average rating of
 * DVD records. Each test ensures that the corresponding methods in
 * {@code DVDCollection} behave as expected.
 *
 * <p>This class uses the JUnit 5 testing framework to validate functionality.</p>
 *
 * <p><b>Dependencies:</b></p>
 * <ul>
 *   <li>{@link dms.model.DVD} – for representing DVD records.</li>
 *   <li>{@link dms.service.DVDCollection} – for managing DVD data.</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 *     DVDCollectionTest test = new DVDCollectionTest();
 *     test.setup();
 *     test.testAddDVD();
 * </pre>
 */
class DVDCollectionTest {

    private DVDCollection collection;

    /**
     * Initializes the test environment by creating a new {@link DVDCollection}
     * instance and populating it with sample data.
     */
    @BeforeEach
    void setup() {
        collection = new DVDCollection();
        collection.addDVD(new DVD(0, "The Matrix", "Wachowski", 1999, "Sci-Fi", 8.7));
        collection.addDVD(new DVD(0, "Inception", "Nolan", 2010, "Sci-Fi", 9.0));
        collection.addDVD(new DVD(0, "Titanic", "Cameron", 1997, "Drama", 7.8));
    }

    /**
     * Tests the {@link DVDCollection#addDVD(DVD)} method to ensure that a new
     * DVD can be successfully added to the collection.
     */
    @Test
    void testAddDVD() {
        DVD newDvd = new DVD(0, "Avatar", "Cameron", 2009, "Sci-Fi", 8.2);
        collection.addDVD(newDvd);
        assertTrue(collection.listAll().stream().anyMatch(d -> d.getTitle().equals("Avatar")));
    }

    /**
     * Tests the {@link DVDCollection#removeDVDById(int)} method to verify that
     * DVDs can be correctly removed by their ID.
     */
    @Test
    void testRemoveDVD() {
        boolean removed = collection.removeDVDById(1);
        assertTrue(removed);
        Optional<DVD> dvd = collection.findById(1);
        assertTrue(dvd.isEmpty());
    }

    /**
     * Tests the {@link DVDCollection#updateDVD(int, DVD)} method to confirm that
     * existing DVD records can be updated successfully.
     */
    @Test
    void testUpdateDVD() {
        DVD updated = new DVD(2, "Inception", "Nolan", 2010, "Sci-Fi", 9.5);
        boolean success = collection.updateDVD(2, updated);
        assertTrue(success);

        Optional<DVD> dvd = collection.findById(2);
        assertTrue(dvd.isPresent());
        assertEquals(9.5, dvd.get().getRating());
    }

    /**
     * Tests the {@link DVDCollection#listAll()} method to ensure that all DVDs
     * in the collection are listed correctly.
     */
    @Test
    void testListAll() {
        List<DVD> allDvds = collection.listAll();
        assertEquals(3, allDvds.size());
    }

    /**
     * Tests the {@link DVDCollection#computeAverageRatingByGenre(String)} method
     * to verify that the average rating is correctly calculated for DVDs of a
     * given genre.
     */
    @Test
    void testAverageRatingByGenre() {
        double avg = collection.computeAverageRatingByGenre("Sci-Fi");
        assertEquals((8.7 + 9.0) / 2, avg);
    }
}
