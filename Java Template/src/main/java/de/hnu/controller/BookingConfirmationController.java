package de.hnu.controller;

import de.hnu.model.Ride;
import de.hnu.service.BookingService;
import de.hnu.service.SessionService;
import de.hnu.ui.NavigationService;
import de.hnu.ui.NavigationService.ViewType;

/**
 * Controller für die "Booking Confirmation" View (Page 131).
 *
 * Verantwortlich für:
 * - Anzeige der Buchungsbestätigung
 * - Navigation zurück zur Übersicht
 */
public class BookingConfirmationController {

    private final BookingService bookingService;
    private final NavigationService navigationService;
    private final SessionService sessionService;

    public BookingConfirmationController(BookingService bookingService,
                                         NavigationService navigationService,
                                         SessionService sessionService) {
        this.bookingService = bookingService;
        this.navigationService = navigationService;
        this.sessionService = sessionService;
    }

    /**
     * Gibt die zuletzt gebuchte Fahrt zurück.
     */
    public Ride getBookedRide() {
        return bookingService.getLastBookedRide();
    }

    /**
     * Wird aufgerufen wenn "Go back to the overview" geklickt wird.
     * Navigiert zurück zum Hauptscreen.
     *
     * ISO 9241-11 Effizienz: Klarer Weg zurück zum Start.
     */
    public void onBackToOverviewClicked() {
        navigationService.navigateTo(ViewType.FIND_RIDE);
    }

    /**
     * Navigiert zurück (Back-Button im Header).
     */
    public void onBackClicked() {
        navigationService.navigateTo(ViewType.AVAILABLE_RIDES);
    }

    /**
     * Gibt die Benutzer-Initialen für den Header zurück.
     */
    public String getUserInitials() {
        return sessionService.getCurrentUserInitials();
    }
}
