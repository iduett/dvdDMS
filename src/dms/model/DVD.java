package dms.model;

/**
 * Irene Duett, CEN 3024c, 10/9/2025
 * class: DVD
 * purpose: Represents a DVD record with six attributes.
 */
public class DVD {

    private int id;
    private String title;
    private String director;
    private int releaseYear;
    private String genre;
    private double rating;

    /**
     * method: DVD (constructor)
     * parameters: int id, String title, String director, int releaseYear, String genre, double rating
     * return: none
     * purpose: Creates a new DVD object with the provided values
     */
    public DVD(int id, String title, String director, int releaseYear, String genre, double rating) {
        this.id = id;
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.rating = rating;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }

    @Override
    public String toString() {
        return id + ": " + title + " (" + releaseYear + ") - " + director + " - " + genre + " - Rating: " + rating;
    }
}
