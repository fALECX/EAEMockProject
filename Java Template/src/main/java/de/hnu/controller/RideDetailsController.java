package de.hnu.controller;

import de.hnu.model.Ride;
import de.hnu.service.BookingService;
import de.hnu.service.FavoriteService;
import de.hnu.service.SessionService;
import de.hnu.ui.NavigationService;
import de.hnu.ui.NavigationService.ViewType;

/**
 * Controller für die "Ride Details" View (Page 123).
 *
 * Verantwortlich für:
 * - Anzeige der Fahrt-Details
 * - Buchung der Fahrt
 * - Hinzufügen zu Favoriten
 */
public class RideDetailsController {

    private final BookingService bookingService;
    private final FavoriteService favoriteService;
    private final NavigationService navigationService;
    private final SessionService sessionService;
    private final Ride currentRide;

    public RideDetailsController(BookingService bookingService,
                                 FavoriteService favoriteService,
                                 NavigationService navigationService,
                                 SessionService sessionService,
                                 Ride ride) {
        this.bookingService = bookingService;
        this.favoriteService = favoriteService;
        this.navigationService = navigationService;
        this.sessionService = sessionService;
        this.currentRide = ride;
    }

    /**
     * Gibt die aktuelle Fahrt zurück.
     */
    public Ride getCurrentRide() {
        return currentRide;
    }

    /**
     * Wird aufgerufen wenn "Book this ride" geklickt wird.
     * Bucht die Fahrt und navigiert zur Bestätigung.
     *
     * ISO 9241-11 Effektivität: Hauptaufgabe (Buchung) wird abgeschlossen.
     *
     * @return true wenn Buchung erfolgreich
     */
    public boolean onBookRideClicked() {
        boolean success = bookingService.bookRide(currentRide);
        if (success) {
            navigationService.navigateTo(ViewType.BOOKING_CONFIRMATION);
        }
        return success;
    }

    /**
     * Wird aufgerufen wenn "Favor this ride" geklickt wird.
     * Fügt die Fahrt zu den Favoriten hinzu.
     *
     * ISO 9241-11 Zufriedenheit: Feedback über Erfolg/Misserfolg.
     *
     * @return true wenn erfolgreich hinzugefügt, false wenn bereits Favorit
     */
    public boolean onFavoriteClicked() {
        return favoriteService.addFavorite(currentRide);
    }

    /**
     * Prüft ob die aktuelle Fahrt bereits ein Favorit ist.
     */
    public boolean isFavorite() {
        return favoriteService.isFavorite(currentRide);
    }

    /**
     * Navigiert zurück zur vorherigen View.
     */
    public void onBackClicked() {
        navigationService.navigateBack();
    }

    /**
     * Gibt die Benutzer-Initialen für den Header zurück.
     */
    public String getUserInitials() {
        return sessionService.getCurrentUserInitials();
    }
}
