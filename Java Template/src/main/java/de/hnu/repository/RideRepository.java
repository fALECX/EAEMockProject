package de.hnu.repository;

import de.hnu.model.Ride;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository für Fahrten (Rides).
 * In-Memory-Speicher mit vorgeladenen Dummy-Daten aus den Mockups.
 *
 * Daten entsprechen Page 120 (Available Rides Overview).
 */
public class RideRepository {

    private List<Ride> rides;

    public RideRepository() {
        this.rides = new ArrayList<>();
        initializeDummyData();
    }

    /**
     * Initialisiert die Dummy-Daten entsprechend der Balsamiq-Mockups.
     * Die Daten stammen aus Page 120 (Available Rides Overview).
     */
    private void initializeDummyData() {
        // Datum für alle Fahrten: 27.10.2025 (aus Mockup Page 123)
        LocalDate rideDate = LocalDate.of(2025, 10, 27);
        LocalTime rideTime = LocalTime.of(5, 0); // 5 a.m.

        // Ride 1: Max Mustermann - Ulm → Köln, 449 km, 4 Sterne
        rides.add(new Ride(1, "Max Mustermann", "Ulm", "Köln",
                449, rideDate, rideTime, 2, 4));

        // Ride 2: Thomas Schmidt - Ulm → Frankfurt, 287 km, 4 Sterne
        rides.add(new Ride(2, "Thomas Schmidt", "Ulm", "Frankfurt",
                287, rideDate, LocalTime.of(6, 30), 3, 4));

        // Ride 3: John Smith - Munich → Köln, 573 km, 4 Sterne
        rides.add(new Ride(3, "John Smith", "Munich", "Köln",
                573, rideDate, LocalTime.of(7, 0), 1, 4));

        // Ride 4: Erika Mustermann - Ulm → Frankfurt, 287 km, 3 Sterne
        rides.add(new Ride(4, "Erika Mustermann", "Ulm", "Frankfurt",
                287, rideDate, LocalTime.of(8, 0), 2, 3));

        // Ride 5: Hannes Müller - Ulm → Frankfurt, 287 km, 1 Stern
        rides.add(new Ride(5, "Hannes Müller", "Ulm", "Frankfurt",
                287, rideDate, LocalTime.of(9, 0), 4, 1));

        // Ride 6: Anna Johnson - Augsburg → Frankfurt, 360 km, 5 Sterne
        rides.add(new Ride(6, "Anna Johnson", "Augsburg", "Frankfurt",
                360, rideDate, LocalTime.of(10, 0), 2, 5));
    }

    /**
     * Gibt alle verfügbaren Fahrten zurück.
     */
    public List<Ride> findAll() {
        return new ArrayList<>(rides);
    }

    /**
     * Findet eine Fahrt anhand der ID.
     */
    public Optional<Ride> findById(long id) {
        return rides.stream()
                .filter(ride -> ride.getId() == id)
                .findFirst();
    }

    /**
     * Sucht Fahrten nach Start- und Zielort.
     * Leere Suchparameter werden ignoriert (flexible Suche).
     */
    public List<Ride> searchRides(String from, String to) {
        return rides.stream()
                .filter(ride -> {
                    boolean matchesFrom = from == null || from.isEmpty()
                            || ride.getOrigin().toLowerCase().contains(from.toLowerCase());
                    boolean matchesTo = to == null || to.isEmpty()
                            || ride.getDestination().toLowerCase().contains(to.toLowerCase());
                    return matchesFrom && matchesTo;
                })
                .toList();
    }

    /**
     * Speichert eine neue Fahrt.
     */
    public Ride save(Ride ride) {
        if (ride.getId() == 0) {
            ride.setId(getNextId());
        }
        rides.add(ride);
        return ride;
    }

    private long getNextId() {
        return rides.stream()
                .mapToLong(Ride::getId)
                .max()
                .orElse(0) + 1;
    }
}
