package de.hnu.controller;

import de.hnu.model.Ride;
import de.hnu.service.RideService;
import de.hnu.service.SessionService;
import de.hnu.ui.NavigationService;
import de.hnu.ui.NavigationService.ViewType;

import java.util.List;

/**
 * Controller für die "Find a Ride" View (Page 115).
 *
 * Verantwortlich für:
 * - Verarbeitung der Sucheingaben
 * - Navigation zu Suchergebnissen und Favoriten
 */
public class FindRideController {

    private final RideService rideService;
    private final NavigationService navigationService;
    private final SessionService sessionService;

    public FindRideController(RideService rideService,
                             NavigationService navigationService,
                             SessionService sessionService) {
        this.rideService = rideService;
        this.navigationService = navigationService;
        this.sessionService = sessionService;
    }

    /**
     * Führt die Suche nach Fahrten durch.
     * Wird aufgerufen wenn der "Find a Ride" Button geklickt wird.
     *
     * ISO 9241-11 Effektivität: Nutzer kann Hauptaufgabe (Fahrt finden) ausführen.
     *
     * @param from Startort
     * @param to Zielort
     */
    public void onFindRideClicked(String from, String to) {
        // Suche durchführen und zu Ergebnissen navigieren
        // Die Suchergebnisse werden in AvailableRidesController geladen
        navigationService.navigateTo(ViewType.AVAILABLE_RIDES);
    }

    /**
     * Navigiert zur Favoriten-Ansicht.
     * Wird aufgerufen wenn der "Click here to view..." Link geklickt wird.
     */
    public void onFavoritesLinkClicked() {
        navigationService.navigateTo(ViewType.FAVORITES);
    }

    /**
     * Gibt die Benutzer-Initialen für den Header zurück.
     */
    public String getUserInitials() {
        return sessionService.getCurrentUserInitials();
    }
}
