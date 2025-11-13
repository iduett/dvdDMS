package dms.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Irene Duett, CEN 3024c, 11/12/2025
 * <p>
 * Class: {@code DVDTest}
 * </p>
 *
 * <p>
 * Purpose: This JUnit test class verifies the correct creation and initialization
 * of {@link DVD} objects. It ensures that the constructor of the {@code DVD} class
 * properly assigns and retrieves the expected attribute values such as ID, title,
 * and rating.
 * </p>
 *
 * <p>
 * Relationship: This class depends on the {@link DVD} model class to perform its tests.
 * It uses JUnit 5 testing framework annotations and assertion methods.
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 *     // Run this test automatically through IntelliJ or Maven:
 *     // Right-click the class and select "Run DVDTest"
 * </pre>
 */
class DVDTest {

    /**
     * Tests that a {@link DVD} object is created correctly with all its attributes
     * properly assigned and retrievable through its getter methods.
     *
     * <p>This method verifies:</p>
     * <ul>
     *     <li>The ID matches the expected value.</li>
     *     <li>The title matches the expected value.</li>
     *     <li>The rating matches the expected value.</li>
     * </ul>
     *
     * @throws AssertionError if any of the expected values do not match
     *                        the actual values returned by the DVD getters.
     */
    @Test
    void testDVDCreation() {
        DVD dvd = new DVD(1, "The Matrix", "Wachowski", 1999, "Sci-Fi", 8.7);
        assertEquals(1, dvd.getId());
        assertEquals("The Matrix", dvd.getTitle());
        assertEquals(8.7, dvd.getRating());
    }
}
