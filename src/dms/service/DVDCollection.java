package dms.service;

import dms.model.DVD;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * class: Irene Duett, CEN 3024c, 10/9/2025
 * DVDCollection
 * purpose: Stores and manages the list of DVD objects, providing CRUD operations and calculations.
 */
public class DVDCollection {

    private final List<DVD> dvds = new ArrayList<>();
    private int nextId = 1;

    /**
     * method: addDVD
     * parameters: DVD dvd
     * return: void
     * purpose: Adds a DVD to the collection and assigns a unique ID
     */
    public void addDVD(DVD dvd) {
        dvd = new DVD(nextId++, dvd.getTitle(), dvd.getDirector(), dvd.getReleaseYear(), dvd.getGenre(), dvd.getRating());
        dvds.add(dvd);
    }

    /**
     * method: listAll
     * parameters: none
     * return: List<DVD>
     * purpose: Returns a list of all DVDs in the collection
     */
    public List<DVD> listAll() {
        return new ArrayList<>(dvds);
    }

    /**
     * method: findById
     * parameters: int id
     * return: Optional<DVD>
     * purpose: Finds a DVD by its unique ID
     */
    public Optional<DVD> findById(int id) {
        return dvds.stream().filter(d -> d.getId() == id).findFirst();
    }

    /**
     * method: removeDVDById
     * parameters: int id
     * return: boolean
     * purpose: Removes a DVD with the given ID, returns true if successful
     */
    public boolean removeDVDById(int id) {
        return dvds.removeIf(d -> d.getId() == id);
    }

    /**
     * method: computeAverageRatingByGenre
     * parameters: String genre
     * return: double
     * purpose: Computes the average rating of all DVDs in a specified genre
     */
    public double computeAverageRatingByGenre(String genre) {
        return dvds.stream()
                .filter(d -> d.getGenre().equalsIgnoreCase(genre) && d.getRating() >= 0)
                .mapToDouble(DVD::getRating)
                .average()
                .orElse(0.0);
    }

    /**
     * method: updateDVD
     * parameters: int id, DVD updatedDVD
     * return: boolean
     * purpose: Updates the fields of an existing DVD by ID. Returns true if updated successfully
     */
    public boolean updateDVD(int id, DVD updatedDVD) {
        Optional<DVD> existing = findById(id);
        if (existing.isPresent()) {
            DVD dvd = existing.get();
            dvd.setTitle(updatedDVD.getTitle());
            dvd.setDirector(updatedDVD.getDirector());
            dvd.setReleaseYear(updatedDVD.getReleaseYear());
            dvd.setGenre(updatedDVD.getGenre());
            dvd.setRating(updatedDVD.getRating());
            return true;
        }
        return false;
    }
}
