package de.hnu.repository;

import de.hnu.model.Ride;
import de.hnu.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repository für Benutzer.
 * In-Memory-Speicher mit dem simulierten Benutzer Samuel Klefe.
 */
public class UserRepository {

    private List<User> users;

    public UserRepository() {
        this.users = new ArrayList<>();
        initializeDummyData();
    }

    /**
     * Initialisiert den simulierten Benutzer Samuel Klefe.
     */
    private void initializeDummyData() {
        // Aktueller Benutzer: Samuel Klefe
        User samuelKlefe = new User(1, "Samuel", "Klefe", 4.5);
        users.add(samuelKlefe);
    }

    /**
     * Gibt alle Benutzer zurück.
     */
    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    /**
     * Findet einen Benutzer anhand der ID.
     */
    public Optional<User> findById(long id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    /**
     * Gibt den aktuellen (eingeloggten) Benutzer zurück.
     * In diesem Prototyp ist das immer Samuel Klefe.
     */
    public User getCurrentUser() {
        return users.get(0); // Samuel Klefe
    }

    /**
     * Speichert einen Benutzer.
     */
    public User save(User user) {
        if (user.getId() == 0) {
            user.setId(getNextId());
            users.add(user);
        }
        return user;
    }

    private long getNextId() {
        return users.stream()
                .mapToLong(User::getId)
                .max()
                .orElse(0) + 1;
    }
}
