package de.hnu.ui;

import de.hnu.controller.*;
import de.hnu.model.Ride;
import de.hnu.service.*;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Zentrale Navigation zwischen den Views.
 * Verwaltet den Scene-Wechsel und die View-Erstellung.
 *
 * ISO 9241-11 Effizienz: Zentrale Navigation ermöglicht konsistente
 * Übergänge zwischen Screens ohne redundanten Code.
 */
public class NavigationService {

    /**
     * Enum für alle verfügbaren Views im Prototyp.
     */
    public enum ViewType {
        FIND_RIDE,           // Page 115 - Hauptscreen
        FAVORITES,           // Page 118 - Favoriten
        AVAILABLE_RIDES,     // Page 120 - Suchergebnisse
        RIDE_DETAILS,        // Page 123 - Fahrt-Details
        BOOKING_CONFIRMATION // Page 131 - Buchungsbestätigung
    }

    private final Stage primaryStage;
    private final RideService rideService;
    private final FavoriteService favoriteService;
    private final BookingService bookingService;
    private final SessionService sessionService;

    // Speichert den letzten ViewType für Back-Navigation
    private ViewType previousView = ViewType.FIND_RIDE;

    // Breite und Höhe entsprechend Smartphone-Mockup
    private static final double SCENE_WIDTH = 380;
    private static final double SCENE_HEIGHT = 700;

    public NavigationService(Stage primaryStage,
                            RideService rideService,
                            FavoriteService favoriteService,
                            BookingService bookingService,
                            SessionService sessionService) {
        this.primaryStage = primaryStage;
        this.rideService = rideService;
        this.favoriteService = favoriteService;
        this.bookingService = bookingService;
        this.sessionService = sessionService;
    }

    /**
     * Navigiert zu einer View ohne zusätzliche Daten.
     */
    public void navigateTo(ViewType viewType) {
        navigateTo(viewType, null);
    }

    /**
     * Navigiert zu einer View mit optionalen Daten.
     *
     * @param viewType Der Ziel-View
     * @param data Optionale Daten (z.B. Ride für Details)
     */
    public void navigateTo(ViewType viewType, Object data) {
        Scene scene = createScene(viewType, data);
        primaryStage.setScene(scene);

        // ISO 9241-11 Effektivität: Titel zeigt aktuellen Kontext
        primaryStage.setTitle("Car Sharing - " + getViewTitle(viewType));
    }

    /**
     * Navigiert zurück zur vorherigen View.
     */
    public void navigateBack() {
        navigateTo(previousView);
    }

    /**
     * Setzt die vorherige View für Back-Navigation.
     */
    public void setPreviousView(ViewType viewType) {
        this.previousView = viewType;
    }

    /**
     * Erstellt die Scene für einen ViewType.
     */
    private Scene createScene(ViewType viewType, Object data) {
        switch (viewType) {
            case FIND_RIDE:
                previousView = ViewType.FIND_RIDE;
                FindRideController findRideController = new FindRideController(
                        rideService, this, sessionService);
                FindRideView findRideView = new FindRideView(findRideController);
                return new Scene(findRideView.getRoot(), SCENE_WIDTH, SCENE_HEIGHT);

            case FAVORITES:
                previousView = ViewType.FIND_RIDE;
                FavoriteRidesController favController = new FavoriteRidesController(
                        favoriteService, this, sessionService);
                FavoriteRidesView favView = new FavoriteRidesView(favController);
                return new Scene(favView.getRoot(), SCENE_WIDTH, SCENE_HEIGHT);

            case AVAILABLE_RIDES:
                previousView = ViewType.FIND_RIDE;
                AvailableRidesController availController = new AvailableRidesController(
                        rideService, this, sessionService);
                AvailableRidesView availView = new AvailableRidesView(availController);
                return new Scene(availView.getRoot(), SCENE_WIDTH, SCENE_HEIGHT);

            case RIDE_DETAILS:
                previousView = ViewType.AVAILABLE_RIDES;
                Ride ride = (Ride) data;
                RideDetailsController detailsController = new RideDetailsController(
                        bookingService, favoriteService, this, sessionService, ride);
                RideDetailsView detailsView = new RideDetailsView(detailsController);
                return new Scene(detailsView.getRoot(), SCENE_WIDTH, SCENE_HEIGHT);

            case BOOKING_CONFIRMATION:
                previousView = ViewType.AVAILABLE_RIDES;
                BookingConfirmationController confirmController = new BookingConfirmationController(
                        bookingService, this, sessionService);
                BookingConfirmationView confirmView = new BookingConfirmationView(confirmController);
                return new Scene(confirmView.getRoot(), SCENE_WIDTH, SCENE_HEIGHT);

            default:
                throw new IllegalArgumentException("Unbekannter ViewType: " + viewType);
        }
    }

    /**
     * Gibt den Titel für einen ViewType zurück.
     */
    private String getViewTitle(ViewType viewType) {
        switch (viewType) {
            case FIND_RIDE: return "Find a Ride";
            case FAVORITES: return "Favorite Rides";
            case AVAILABLE_RIDES: return "Available Rides";
            case RIDE_DETAILS: return "Ride Details";
            case BOOKING_CONFIRMATION: return "Booking Confirmation";
            default: return "Car Sharing";
        }
    }

    /**
     * Gibt die Session-Service zurück (für User-Initialen im Header).
     */
    public SessionService getSessionService() {
        return sessionService;
    }
}
