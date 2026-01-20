package de.hnu.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Model-Klasse für eine Fahrt (Ride).
 * JPA Entity mit Datenbankpersistierung.
 *
 * Entspricht den Daten aus dem Balsamiq-Mockup (Page 120, 123).
 */
@Entity
public class Ride {

    @Id
    @GeneratedValue
    private long id;

    @Column(length=50)
    private String driverName;

    @Column(length=100)
    private String origin;

    @Column(length=100)
    private String destination;

    private int distanceKm;
    private LocalDate date;
    private LocalTime time;
    private int availableSeats;
    private int driverRating; // 1-5 Sterne

    @ManyToOne
    private User driver; // Beziehung zum Fahrer

    public Ride() {
    }

    public Ride(long id, String driverName, String origin, String destination,
                int distanceKm, LocalDate date, LocalTime time,
                int availableSeats, int driverRating) {
        this.id = id;
        this.driverName = driverName;
        this.origin = origin;
        this.destination = destination;
        this.distanceKm = distanceKm;
        this.date = date;
        this.time = time;
        this.availableSeats = availableSeats;
        this.driverRating = driverRating;
    }

    // Getter und Setter

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(int distanceKm) {
        this.distanceKm = distanceKm;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public int getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(int driverRating) {
        this.driverRating = driverRating;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    /**
     * Hilfsmethode für Anzeige der Route.
     */
    public String getRouteDisplay() {
        return origin + " - " + destination;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", driverName='" + driverName + '\'' +
                ", route='" + getRouteDisplay() + '\'' +
                ", distanceKm=" + distanceKm +
                '}';
    }
}
