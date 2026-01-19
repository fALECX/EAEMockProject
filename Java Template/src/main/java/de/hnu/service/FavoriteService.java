package de.hnu.service;

import de.hnu.model.Ride;
import de.hnu.model.User;
import de.hnu.repository.RideRepository;
import de.hnu.repository.UserRepository;

import java.util.List;

/**
 * Service für Favoriten-Verwaltung.
 * Ermöglicht das Hinzufügen und Entfernen von Fahrten aus den Favoriten.
 *
 * Entspricht der "Favorite Rides" Funktion (Page 118).
 */
public class FavoriteService {

    private final UserRepository userRepository;
    private final RideRepository rideRepository;

    public FavoriteService(UserRepository userRepository, RideRepository rideRepository) {
        this.userRepository = userRepository;
        this.rideRepository = rideRepository;
        initializeDefaultFavorites();
    }

    /**
     * Initialisiert die Standard-Favoriten für Samuel Klefe.
     * Entsprechend Mockup Page 118: Thomas Schmidt und Erika Mustermann.
     */
    private void initializeDefaultFavorites() {
        User currentUser = userRepository.getCurrentUser();
        List<Ride> allRides = rideRepository.findAll();

        // Favoriten aus Mockup: Thomas Schmidt (ID 2) und Erika Mustermann (ID 4)
        allRides.stream()
                .filter(r -> r.getDriverName().equals("Thomas Schmidt")
                          || r.getDriverName().equals("Erika Mustermann"))
                .forEach(ride -> currentUser.getFavorites().add(ride));
    }

    /**
     * Gibt die Favoriten des aktuellen Benutzers zurück.
     */
    public List<Ride> getFavorites() {
        User currentUser = userRepository.getCurrentUser();
        return currentUser.getFavorites();
    }

    /**
     * Fügt eine Fahrt zu den Favoriten hinzu.
     *
     * @return true wenn erfolgreich hinzugefügt, false wenn bereits vorhanden
     */
    public boolean addFavorite(Ride ride) {
        User currentUser = userRepository.getCurrentUser();

        // Prüfen ob bereits in Favoriten
        boolean alreadyFavorite = currentUser.getFavorites().stream()
                .anyMatch(r -> r.getId() == ride.getId());

        if (!alreadyFavorite) {
            currentUser.getFavorites().add(ride);
            return true;
        }
        return false;
    }

    /**
     * Entfernt eine Fahrt aus den Favoriten.
     *
     * @return true wenn erfolgreich entfernt, false wenn nicht vorhanden
     */
    public boolean removeFavorite(Ride ride) {
        User currentUser = userRepository.getCurrentUser();
        return currentUser.getFavorites().removeIf(r -> r.getId() == ride.getId());
    }

    /**
     * Prüft ob eine Fahrt in den Favoriten ist.
     */
    public boolean isFavorite(Ride ride) {
        User currentUser = userRepository.getCurrentUser();
        return currentUser.getFavorites().stream()
                .anyMatch(r -> r.getId() == ride.getId());
    }
}
