package de.hnu.controller;

import de.hnu.model.Ride;
import de.hnu.service.FavoriteService;
import de.hnu.service.SessionService;
import de.hnu.ui.NavigationService;
import de.hnu.ui.NavigationService.ViewType;

import java.util.List;

/**
 * Controller für die "Favorite Rides" View (Page 118).
 *
 * Verantwortlich für:
 * - Laden und Anzeigen der Favoriten
 * - Navigation zu Fahrt-Details
 */
public class FavoriteRidesController {

    private final FavoriteService favoriteService;
    private final NavigationService navigationService;
    private final SessionService sessionService;

    public FavoriteRidesController(FavoriteService favoriteService,
                                   NavigationService navigationService,
                                   SessionService sessionService) {
        this.favoriteService = favoriteService;
        this.navigationService = navigationService;
        this.sessionService = sessionService;
    }

    /**
     * Gibt alle Favoriten des aktuellen Benutzers zurück.
     */
    public List<Ride> getFavorites() {
        return favoriteService.getFavorites();
    }

    /**
     * Wird aufgerufen wenn eine Fahrt in der Liste angeklickt wird.
     * Navigiert zur Detail-Ansicht.
     *
     * ISO 9241-11 Effizienz: Direkte Navigation mit einem Klick.
     */
    public void onRideClicked(Ride ride) {
        navigationService.setPreviousView(ViewType.FAVORITES);
        navigationService.navigateTo(ViewType.RIDE_DETAILS, ride);
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
