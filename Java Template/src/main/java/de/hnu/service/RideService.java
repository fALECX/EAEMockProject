package de.hnu.service;

import de.hnu.model.Ride;
import de.hnu.repository.RideRepository;

import java.util.List;
import java.util.Optional;

/**
 * Service für Fahrt-bezogene Geschäftslogik.
 * Kapselt die Zugriffe auf das RideRepository.
 *
 * Keine JavaFX-Abhängigkeiten - reine Business-Logik.
 */
public class RideService {

    private final RideRepository rideRepository;

    public RideService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    /**
     * Gibt alle verfügbaren Fahrten zurück.
     * Wird für die "Available Rides" Ansicht verwendet (Page 120).
     */
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    /**
     * Sucht Fahrten nach Kriterien.
     * Entspricht der "Find a Ride" Funktion (Page 115).
     *
     * @param from Startort (kann leer sein)
     * @param to Zielort (kann leer sein)
     * @return Liste der passenden Fahrten
     */
    public List<Ride> searchRides(String from, String to) {
        // ISO 9241-11 Effizienz: Leere Suche gibt alle Ergebnisse zurück
        // Dadurch kann der Nutzer auch ohne Eingabe browsen
        return rideRepository.searchRides(from, to);
    }

    /**
     * Findet eine Fahrt anhand der ID.
     * Wird für die Detail-Ansicht verwendet (Page 123).
     */
    public Optional<Ride> getRideById(long id) {
        return rideRepository.findById(id);
    }
}
