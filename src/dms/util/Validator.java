package dms.util;

/**
 * Irene Duett, CEN 3024c, 10/9/2025
 * class: Validator
 * purpose: Provides simple validation methods for DVD attributes.
 */
public class Validator {

    /**
     * method: isValidYear
     * parameters: int year
     * return: boolean
     * purpose: Checks if the year is between 1895 and the current year.
     */
    public static boolean isValidYear(int year) {
        int currentYear = java.time.Year.now().getValue();
        return year >= 1895 && year <= currentYear;
    }

    /**
     * method: isValidRating
     * parameters: double rating
     * return: boolean
     * purpose: Checks if rating is -1 (unknown) or between 0.0 and 10.0.
     */
    public static boolean isValidRating(double rating) {
        return rating == -1 || (rating >= 0.0 && rating <= 10.0);
    }
}
