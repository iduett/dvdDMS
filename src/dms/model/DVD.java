package dms.model;

/**
 * Irene Duett, CEN 3024c, 11/12/2025
 *
 * The {@code DVD} class represents a single DVD record in the system.
 * Each DVD includes an ID, title, director, release year, genre, and rating.
 *
 * <p>This class serves as a data model and is used by other layers such as
 * the service layer ({@link dms.service.DVDCollection}) and data access layer
 * to store, retrieve, and manipulate DVD information.</p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 *     DVD dvd = new DVD(1, "The Matrix", "Wachowski", 1999, "Sci-Fi", 8.7);
 *     System.out.println(dvd.getTitle()); // Output: The Matrix
 * </pre>
 */
public class DVD {

    private int id;
    private String title;
    private String director;
    private int releaseYear;
    private String genre;
    private double rating;

    /**
     * Constructs a {@code DVD} object with the specified attributes.
     *
     * @param id          the unique identifier for the DVD
     * @param title       the title of the DVD
     * @param director    the name of the DVD's director
     * @param releaseYear the year the DVD was released
     * @param genre       the genre of the DVD (e.g., Drama, Family, Sci-Fi)
     * @param rating      the average rating of the DVD
     */
    public DVD(int id, String title, String director, int releaseYear, String genre, double rating) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = rating;
    }

    /**
     * Returns the unique identifier for the DVD.
     *
     * @return the DVD ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the title of the DVD.
     *
     * @return the DVD title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Updates the title of the DVD.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the name of the DVD’s director.
     *
     * @return the director’s name
     */
    public String getDirector() {
        return director;
    }

    /**
     * Updates the director of the DVD.
     *
     * @param director the new director’s name
     */
    public void setDirector(String director) {
        this.director = director;
    }

    /**
     * Returns the release year of the DVD.
     *
     * @return the year the DVD was released
     */
    public int getReleaseYear() {
        return releaseYear;
    }

    /**
     * Updates the release year of the DVD.
     *
     * @param releaseYear the new release year
     */
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * Returns the genre of the DVD.
     *
     * @return the DVD genre
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Updates the genre of the DVD.
     *
     * @param genre the new genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Returns the rating of the DVD.
     *
     * @return the DVD rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Updates the rating of the DVD.
     *
     * @param rating the new rating value
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Returns a string representation of the DVD object.
     *
     * @return a formatted string containing all DVD details
     */
    @Override
    public String toString() {
        return id + ": " + title + " (" + releaseYear + ") - " + director + " - " + genre + " - Rating: " + rating;
    }
}
