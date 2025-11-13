package dms.util;

/**
 * Irene Duett, CEN 3024c, 11/12/2025
 * class: Validator
 * purpose: Provides utility methods for validating DVD attributes such as release year and rating.
 *          Used throughout the application to ensure data consistency before database insertion or update.
 */
public class Validator {

    /**
     * method: isValidYear
     * parameters: int year
     * return: boolean
     * purpose: Checks if the given year is valid for a DVD release.
     *          Valid years are between 1895 (earliest known film) and the current year.
     *
     * @param year the release year to validate
     * @return true if the year is between 1895 and the current year, false otherwise
     */
    public static boolean isValidYear(int year) {
        int currentYear = java.time.Year.now().getValue();
        return year >= 1895 && year <= currentYear;
    }

    /**
     * method: isValidRating
     * parameters: double rating
     * return: boolean
     * purpose: Checks if the rating is valid.
     *          Accepts -1 to represent unknown rating or a number between 0.0 and 10.0.
     *
     * @param rating the rating to validate
     * @return true if rating is -1 or between 0.0 and 10.0, false otherwise
     */
    public static boolean isValidRating(double rating) {
        return rating == -1 || (rating >= 0.0 && rating <= 10.0);
    }
}
