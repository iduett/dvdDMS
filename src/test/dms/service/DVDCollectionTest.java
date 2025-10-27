package dms.service;

/**
 * Irene Duett, CEN 3024c, 10/16/2025
 * class: DVDCollectionTest
 * purpose: testing adding, removing, updating, listing, and computing average of DVD records.
 */

import dms.model.DVD;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class DVDCollectionTest {

    private DVDCollection collection;

    @BeforeEach
    void setup() {
        collection = new DVDCollection();
        collection.addDVD(new DVD(0, "The Matrix", "Wachowski", 1999, "Sci-Fi", 8.7));
        collection.addDVD(new DVD(0, "Inception", "Nolan", 2010, "Sci-Fi", 9.0));
        collection.addDVD(new DVD(0, "Titanic", "Cameron", 1997, "Drama", 7.8));
    }

    @Test
    void testAddDVD() {
        DVD newDvd = new DVD(0, "Avatar", "Cameron", 2009, "Sci-Fi", 8.2);
        collection.addDVD(newDvd);
        assertTrue(collection.listAll().stream().anyMatch(d -> d.getTitle().equals("Avatar")));
    }

    @Test
    void testRemoveDVD() {
        boolean removed = collection.removeDVDById(1);
        assertTrue(removed);
        Optional<DVD> dvd = collection.findById(1);
        assertTrue(dvd.isEmpty());
    }

    @Test
    void testUpdateDVD() {
        DVD updated = new DVD(2, "Inception", "Nolan", 2010, "Sci-Fi", 9.5);
        boolean success = collection.updateDVD(2, updated);
        assertTrue(success);

        Optional<DVD> dvd = collection.findById(2);
        assertTrue(dvd.isPresent());
        assertEquals(9.5, dvd.get().getRating());
    }

    @Test
    void testListAll() {
        List<DVD> allDvds = collection.listAll();
        assertEquals(3, allDvds.size());
    }

    @Test
    void testAverageRatingByGenre() {
        double avg = collection.computeAverageRatingByGenre("Sci-Fi");
        assertEquals((8.7 + 9.0) / 2, avg);
    }
}
