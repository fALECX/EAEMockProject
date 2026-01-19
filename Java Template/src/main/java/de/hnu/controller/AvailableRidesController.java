package de.hnu.controller;

import de.hnu.model.Ride;
import de.hnu.service.RideService;
import de.hnu.service.SessionService;
import de.hnu.ui.NavigationService;
import de.hnu.ui.NavigationService.ViewType;

import java.util.List;

/**
 * Controller für die "Available Rides" View (Page 120).
 *
 * Verantwortlich für:
 * - Laden aller verfügbaren Fahrten
 * - Navigation zu Fahrt-Details
 * - Filter-Funktionalität (Platzhalter)
 */
public class AvailableRidesController {

    private final RideService rideService;
    private final NavigationService navigationService;
    private final SessionService sessionService;

    public AvailableRidesController(RideService rideService,
                                    NavigationService navigationService,
                                    SessionService sessionService) {
        this.rideService = rideService;
        this.navigationService = navigationService;
        this.sessionService = sessionService;
    }

    /**
     * Gibt alle verfügbaren Fahrten zurück.
     * In diesem Prototyp werden immer alle Fahrten angezeigt.
     */
    public List<Ride> getAvailableRides() {
        return rideService.getAllRides();
    }

    /**
     * Wird aufgerufen wenn eine Fahrt in der Liste angeklickt wird.
     * Navigiert zur Detail-Ansicht.
     *
     * ISO 9241-11 Effizienz: Direkte Navigation mit einem Klick.
     */
    public void onRideClicked(Ride ride) {
        navigationService.navigateTo(ViewType.RIDE_DETAILS, ride);
    }

    /**
     * Wird aufgerufen wenn der Filter-Button geklickt wird.
     * In diesem Prototyp nur ein Platzhalter.
     *
     * ISO 9241-11 Zufriedenheit: Feedback dass Feature nicht implementiert.
     */
    public void onFilterClicked() {
        // Platzhalter - Filter nicht implementiert in diesem Prototyp
        System.out.println("Filter-Funktion ist in diesem Prototyp nicht implementiert.");
    }

    /**
     * Navigiert zurück zur vorherigen View.
     */
    public void onBackClicked() {
        navigationService.navigateTo(ViewType.FIND_RIDE);
    }

    /**
     * Gibt die Benutzer-Initialen für den Header zurück.
     */
    public String getUserInitials() {
        return sessionService.getCurrentUserInitials();
    }
}
