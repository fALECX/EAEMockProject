package de.hnu.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Model-Klasse für einen Benutzer.
 * JPA Entity mit Datenbankpersistierung.
 *
 * Der aktuelle Benutzer ist Samuel Klefe (simuliert).
 */
@Entity
@Table(name = "AppUser") // "User" ist oft ein reserviertes Keyword in SQL
public class User {

    @Id
    @GeneratedValue
    private long id;

    @Column(length=50)
    private String firstName;

    @Column(length=50)
    private String lastName;

    private double rating; // 1.0 - 5.0

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    private List<Ride> favorites;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.PERSIST)
    private List<Ride> bookedRides; // Gebuchte Fahrten für "Previous Rides"

    public User() {
        this.favorites = new ArrayList<>();
        this.bookedRides = new ArrayList<>();
    }

    public User(long id, String firstName, String lastName, double rating) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rating = rating;
        this.favorites = new ArrayList<>();
        this.bookedRides = new ArrayList<>();
    }

    // Getter und Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<Ride> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Ride> favorites) {
        this.favorites = favorites;
    }

    public List<Ride> getBookedRides() {
        return bookedRides;
    }

    public void setBookedRides(List<Ride> bookedRides) {
        this.bookedRides = bookedRides;
    }

    /**
     * Hilfsmethode für vollständigen Namen.
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Hilfsmethode für Initialen (z.B. "SK" für Samuel Klefe).
     */
    public String getInitials() {
        String firstInitial = firstName != null && !firstName.isEmpty()
            ? firstName.substring(0, 1).toUpperCase() : "";
        String lastInitial = lastName != null && !lastName.isEmpty()
            ? lastName.substring(0, 1).toUpperCase() : "";
        return firstInitial + lastInitial;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + getFullName() + '\'' +
                ", rating=" + rating +
                '}';
    }
}
