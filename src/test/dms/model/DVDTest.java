package dms.model;
/**
 * Irene Duett, CEN 3024c, 10/16/2025
 * class: DVDTest
 * purpose: testing adding of DVD record with six attributes.
 */
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DVDTest {

    @Test
    void testDVDCreation() {
        DVD dvd = new DVD(1, "The Matrix", "Wachowski", 1999, "Sci-Fi", 8.7);
        assertEquals(1, dvd.getId());
        assertEquals("The Matrix", dvd.getTitle());
        assertEquals(8.7, dvd.getRating());
    }
}
