package de.hnu;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.hnu.model.Ride;
import de.hnu.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * REST Controller für Car-Sharing Backend.
 * Analog zum UniversityService aus dem Demo Code.
 */
@RestController
@Transactional
public class CarSharingService {

    @PersistenceContext
    private EntityManager em;

    /**
     * Setup Endpoint zum Initialisieren von Testdaten.
     * GET http://localhost:8080/setup
     */
    @GetMapping("/setup")
    public String setup() {
        // Testbenutzer erstellen
        User driver1 = new User(0, "Samuel", "Klefe", 4.8);
        User driver2 = new User(0, "Max", "Mustermann", 4.5);
        User passenger = new User(0, "Anna", "Schmidt", 4.9);

        // Testfahrten erstellen
        Ride ride1 = new Ride(0, "Samuel Klefe", "Neu-Ulm", "München",
                              150, LocalDate.of(2026, 1, 25), LocalTime.of(14, 30),
                              3, 5);
        ride1.setDriver(driver1);

        Ride ride2 = new Ride(0, "Max Mustermann", "Stuttgart", "Berlin",
                              630, LocalDate.of(2026, 1, 26), LocalTime.of(9, 0),
                              2, 4);
        ride2.setDriver(driver2);

        // Gebuchte Fahrt für Passenger
        Ride bookedRide = new Ride(0, "Samuel Klefe", "Ulm", "Augsburg",
                                    80, LocalDate.of(2026, 1, 20), LocalTime.of(10, 0),
                                    0, 5);
        bookedRide.setDriver(driver1);
        passenger.getBookedRides().add(bookedRide);

        // Persistieren
        em.persist(driver1);
        em.persist(driver2);
        em.persist(passenger);
        em.persist(ride1);
        em.persist(ride2);

        return "Setup completed! Created 3 users and 3 rides.";
    }

    /**
     * Einzelne Fahrt abrufen.
     * GET http://localhost:8080/rides/{id}
     */
    @GetMapping("/rides/{id}")
    public Ride getRide(@PathVariable("id") long id) {
        return em.find(Ride.class, id);
    }

    /**
     * Alle Fahrten abrufen.
     * GET http://localhost:8080/rides
     */
    @GetMapping("/rides")
    public List<Ride> getAllRides() {
        Query q = em.createQuery("SELECT r FROM Ride r");
        return q.getResultList();
    }

    /**
     * Neue Fahrt erstellen.
     * POST http://localhost:8080/rides
     */
    @PostMapping("/rides")
    public Ride createRide(@RequestBody Ride ride) {
        em.persist(ride);
        return ride;
    }

    /**
     * Einzelnen Benutzer abrufen.
     * GET http://localhost:8080/users/{id}
     */
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") long id) {
        return em.find(User.class, id);
    }

    /**
     * Alle Benutzer abrufen.
     * GET http://localhost:8080/users
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {
        Query q = em.createQuery("SELECT u FROM User u");
        return q.getResultList();
    }

    /**
     * Neuen Benutzer erstellen.
     * POST http://localhost:8080/users
     */
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        em.persist(user);
        return user;
    }

    /**
     * Gebuchte Fahrten eines Benutzers abrufen.
     * GET http://localhost:8080/users/{id}/bookedRides
     */
    @GetMapping("/users/{id}/bookedRides")
    public List<Ride> getBookedRides(@PathVariable("id") long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            return user.getBookedRides();
        }
        return List.of();
    }

}
