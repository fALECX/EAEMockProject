package de.hnu.service;

import de.hnu.model.User;
import de.hnu.repository.UserRepository;

/**
 * Service für die aktuelle Benutzersitzung.
 * Verwaltet den eingeloggten Benutzer (Samuel Klefe).
 *
 * In diesem Prototyp ist der Benutzer hardcoded - keine echte Authentifizierung.
 */
public class SessionService {

    private final UserRepository userRepository;

    public SessionService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Gibt den aktuell eingeloggten Benutzer zurück.
     * In diesem Prototyp immer Samuel Klefe.
     */
    public User getCurrentUser() {
        return userRepository.getCurrentUser();
    }

    /**
     * Gibt die Initialen des aktuellen Benutzers zurück.
     * Wird im Header angezeigt (z.B. "SK" für Samuel Klefe).
     *
     * Im Mockup wird "AS" angezeigt (Aylin Sisman),
     * aber wir verwenden "SK" für Samuel Klefe.
     */
    public String getCurrentUserInitials() {
        return getCurrentUser().getInitials();
    }

    /**
     * Prüft ob ein Benutzer eingeloggt ist.
     * In diesem Prototyp immer true.
     */
    public boolean isLoggedIn() {
        return true;
    }
}
