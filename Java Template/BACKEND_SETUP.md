# Car Sharing Backend Setup - Anleitung

## Implementierung abgeschlossen! ✅

Das Car Sharing Projekt wurde erfolgreich um Backend + Datenbank erweitert:

### Was wurde implementiert:

1. **pom.xml**: Erweitert mit Spring Boot Data JPA, EclipseLink, Derby, HikariCP
2. **JPA Entities**: `Ride.java` und `User.java` mit @Entity Annotationen
3. **AppConfig.java**: Spring Boot Configuration mit DataSource, EntityManagerFactory
4. **CarSharingService.java**: REST Controller mit Endpoints für Rides und Users
5. **application.properties**: Derby Datenbankverbindung konfiguriert
6. **App.java**: Spring Boot Integration parallel zu JavaFX

### Nächste Schritte:

#### 1. Maven Dependencies laden
In IntelliJ IDEA:
- Öffnen Sie die `pom.xml` Datei
- Rechtsklick auf das Projekt → **Maven** → **Reload Project**
- ODER klicken Sie auf das Maven-Icon rechts oben (wenn erscheint: "Load Maven Changes")

#### 2. Derby Datenbank starten
Sie haben 2 Optionen:

**Option A: Derby Embedded (Empfohlen für Entwicklung)**
- Ändern Sie in `application.properties`:
  ```
  app.datasource.jdbc-url=jdbc:derby:memory:carsharing;create=true
  ```
- Dies startet Derby automatisch im In-Memory Modus

**Option B: Derby Network Server (wie im Demo Code)**
- Derby Server manuell starten oder Docker verwenden
- Für Docker: Im Demo Code Ordner sollte ein `docker-compose.yml` existieren
- ODER Derby Network Server von Apache Derby herunterladen und starten

#### 3. Anwendung starten
Nach Maven Reload:
- Rechtsklick auf `App.java` → **Run 'App.main()'**
- ODER bestehende Run Configuration "App (JavaFX)" verwenden

Die Anwendung startet:
1. Spring Boot Backend auf http://localhost:8080
2. JavaFX Frontend Fenster

#### 4. REST API testen
Öffnen Sie http://localhost:8080/setup im Browser, um Testdaten zu erstellen.

Verfügbare Endpoints:
- GET  http://localhost:8080/setup (Testdaten erstellen)
- GET  http://localhost:8080/rides (Alle Fahrten)
- GET  http://localhost:8080/rides/{id} (Einzelne Fahrt)
- POST http://localhost:8080/rides (Neue Fahrt)
- GET  http://localhost:8080/users (Alle Benutzer)
- GET  http://localhost:8080/users/{id} (Einzelner Benutzer)
- GET  http://localhost:8080/users/{id}/bookedRides (Gebuchte Fahrten)
- POST http://localhost:8080/users (Neuer Benutzer)

### Architektur:
```
┌─────────────────┐
│  JavaFX UI      │ (Bestehend - Frontend)
└────────┬────────┘
         │
    ┌────┴────┐
    │ Services │ (In-Memory - kann beibehalten werden)
    └────┬────┘
         │
┌────────┴────────────┐
│ Spring Boot Backend │ (NEU - REST API)
│ - CarSharingService │
│ - AppConfig         │
└─────────┬───────────┘
          │
    ┌─────┴─────┐
    │ JPA/ORM   │ (EclipseLink)
    └─────┬─────┘
          │
    ┌─────┴─────┐
    │ Derby DB  │ (Persistierung)
    └───────────┘
```

### Modell-Beziehungen:
- **User** ←─ OneToMany ─→ **bookedRides** (List<Ride>)
- **User** ←─ OneToMany ─→ **favorites** (List<Ride>)
- **Ride** ─ ManyToOne ──→ **driver** (User)

### Troubleshooting:

**Fehler: "Cannot resolve symbol"**
→ Maven Dependencies neu laden (siehe Schritt 1)

**Fehler: "JavaFX Runtime components fehlen"**
→ Run Configuration prüfen: VM Options sollten JavaFX Module enthalten
→ ODER JavaFX Maven Plugin nutzen: `mvn javafx:run`

**Fehler: Derby Connection Failed**
→ Derby Server starten ODER auf Embedded-Modus wechseln (siehe Schritt 2)

**Port 8080 bereits belegt**
→ In `application.properties` Port ändern: `server.port=8081`

---
**Autor**: GitHub Copilot
**Datum**: 2026-01-20
