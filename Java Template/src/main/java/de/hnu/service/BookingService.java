package de.hnu.service;

import de.hnu.model.Ride;
import de.hnu.model.User;
import de.hnu.repository.UserRepository;

/**
 * Service für Buchungen.
 * Simuliert das Buchen einer Fahrt.
 *
 * Entspricht der "Book this ride" Funktion (Page 123 → 131).
 */
public class BookingService {

    private final UserRepository userRepository;
    private Ride lastBookedRide; // Speichert die zuletzt gebuchte Fahrt für Bestätigung

    public BookingService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Bucht eine Fahrt für den aktuellen Benutzer.
     *
     * In diesem Prototyp wird die Buchung nur simuliert:
     * - Verfügbare Plätze werden reduziert
     * - Die gebuchte Fahrt wird gespeichert für die Bestätigung
     *
     * @param ride Die zu buchende Fahrt
     * @return true wenn Buchung erfolgreich, false wenn keine Plätze verfügbar
     */
    public boolean bookRide(Ride ride) {
        if (ride.getAvailableSeats() <= 0) {
            return false;
        }

        // Plätze reduzieren (Simulation)
        ride.setAvailableSeats(ride.getAvailableSeats() - 1);

        // Für Bestätigungsseite speichern
        this.lastBookedRide = ride;

        return true;
    }

    /**
     * Gibt die zuletzt gebuchte Fahrt zurück.
     * Wird für die Buchungsbestätigung verwendet (Page 131).
     */
    public Ride getLastBookedRide() {
        return lastBookedRide;
    }

    /**
     * Gibt den aktuellen Benutzer zurück.
     */
    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }
}
