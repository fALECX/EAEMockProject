# 🚀 Quick Start Guide - Backend Testen

## ✅ Status: Implementation abgeschlossen!

Die Backend-Integration ist fertig. Folgen Sie diesen Schritten:

---

## Schritt 1: Maven Reload (KRITISCH!)

**IntelliJ IDEA:**

1. Öffnen Sie die Datei `pom.xml` im Editor
2. Sie sollten oben rechts ein Banner sehen: "Maven projects need to be imported"
3. Klicken Sie auf **"Import Changes"** oder **"Enable Auto-Import"**

**Alternative Methode:**
1. Rechtsklick auf das Projekt "Java Template" im Project Explorer
2. Wählen Sie **Maven → Reload Project**

**Warten Sie ~30-60 Sekunden**, bis alle Dependencies heruntergeladen sind.

✅ **Erfolgreich, wenn:**
- Im "External Libraries" Ordner erscheinen: Spring Boot, EclipseLink, Derby, HikariCP
- Keine roten Fehler mehr in den Java-Dateien

---

## Schritt 2: Anwendung starten

### Option A: Über IntelliJ Run Configuration (Empfohlen)

1. Öffnen Sie `src/main/java/de/hnu/App.java`
2. Rechtsklick auf die Datei → **Run 'App.main()'**
3. ODER klicken Sie auf den grünen Play-Button neben der `main()` Methode

### Option B: Über Maven (Falls JavaFX Probleme auftreten)

Terminal in IntelliJ öffnen und ausführen:
```powershell
mvn clean javafx:run
```

---

## Schritt 3: Erfolgsprüfung

### Was Sie sehen sollten:

**1. Console Output:**
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v3.0.0)

Spring Boot Backend gestartet auf http://localhost:8080
Car Sharing App gestartet.
Eingeloggter Benutzer: Samuel Klefe
```

**2. JavaFX Fenster:**
- Ihre Car Sharing UI öffnet sich wie gewohnt

**3. Backend läuft:**
- Spring Boot Server auf Port 8080

---

## Schritt 4: Backend API testen

### 4.1 Testdaten erstellen

Öffnen Sie im Browser:
```
http://localhost:8080/setup
```

**Erwartete Antwort:**
```
Setup completed! Created 3 users and 3 rides.
```

### 4.2 Alle Fahrten anzeigen

```
http://localhost:8080/rides
```

**Erwartete Antwort (JSON):**
```json
[
  {
    "id": 1,
    "driverName": "Samuel Klefe",
    "origin": "Neu-Ulm",
    "destination": "München",
    "distanceKm": 150,
    "date": "2026-01-25",
    "time": "14:30:00",
    "availableSeats": 3,
    "driverRating": 5
  },
  {
    "id": 2,
    "driverName": "Max Mustermann",
    "origin": "Stuttgart",
    "destination": "Berlin",
    "distanceKm": 630,
    "date": "2026-01-26",
    "time": "09:00:00",
    "availableSeats": 2,
    "driverRating": 4
  }
]
```

### 4.3 Alle Benutzer anzeigen

```
http://localhost:8080/users
```

**Erwartete Antwort (JSON):**
```json
[
  {
    "id": 1,
    "firstName": "Samuel",
    "lastName": "Klefe",
    "rating": 4.8,
    "favorites": [],
    "bookedRides": []
  },
  {
    "id": 2,
    "firstName": "Max",
    "lastName": "Mustermann",
    "rating": 4.5,
    "favorites": [],
    "bookedRides": []
  },
  {
    "id": 3,
    "firstName": "Anna",
    "lastName": "Schmidt",
    "rating": 4.9,
    "favorites": [],
    "bookedRides": [
      {
        "id": 3,
        "driverName": "Samuel Klefe",
        "origin": "Ulm",
        "destination": "Augsburg",
        ...
      }
    ]
  }
]
```

### 4.4 Gebuchte Fahrten eines Benutzers

```
http://localhost:8080/users/3/bookedRides
```

**Erwartete Antwort:**
Eine Liste mit der gebuchten Fahrt von Anna Schmidt.

---

## Schritt 5: Postman/Curl Testing (Optional)

### Neue Fahrt erstellen (POST)

**Postman:**
- Method: POST
- URL: http://localhost:8080/rides
- Headers: Content-Type: application/json
- Body (raw JSON):
```json
{
  "driverName": "Test Driver",
  "origin": "Ulm",
  "destination": "Stuttgart",
  "distanceKm": 95,
  "date": "2026-01-25",
  "time": "14:00:00",
  "availableSeats": 3,
  "driverRating": 5
}
```

**PowerShell (curl):**
```powershell
curl -X POST http://localhost:8080/rides `
  -H "Content-Type: application/json" `
  -d '{\"driverName\":\"Test Driver\",\"origin\":\"Ulm\",\"destination\":\"Stuttgart\",\"distanceKm\":95,\"date\":\"2026-01-25\",\"time\":\"14:00:00\",\"availableSeats\":3,\"driverRating\":5}'
```

---

## 🔧 Troubleshooting

### ❌ Fehler: "Cannot resolve symbol 'EntityManager'"

**Problem:** Maven Dependencies wurden nicht geladen

**Lösung:**
1. Schritt 1 wiederholen (Maven Reload)
2. Falls das nicht hilft: IntelliJ neustarten
3. Dann erneut Maven Reload

### ❌ Fehler: "Port 8080 already in use"

**Problem:** Ein anderer Prozess nutzt Port 8080

**Lösung 1 - Port ändern:**
Öffnen Sie `src/main/resources/application.properties`:
```properties
server.port=8081
```

**Lösung 2 - Prozess beenden:**
```powershell
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F
```

### ❌ Fehler: "JavaFX Runtime components fehlen"

**Problem:** JavaFX VM Options fehlen

**Lösung:**
Nutzen Sie Maven statt direkter Ausführung:
```powershell
mvn clean javafx:run
```

### ❌ Fehler beim Start: "Failed to configure EntityManagerFactory"

**Problem:** Derby oder EclipseLink Konfiguration

**Lösung:**
Prüfen Sie `application.properties`:
```properties
app.datasource.jdbc-url=jdbc:derby:memory:carsharing;create=true
app.datasource.driver-class-name=org.apache.derby.jdbc.EmbeddedDriver
```

### ❌ JSON Fehler: "No serializer found for class java.time.LocalDate"

**Problem:** Jackson JSR310 Module fehlt

**Lösung:** 
Bereits in pom.xml enthalten - Maven Reload durchführen.

---

## 📊 Datenbank-Inspektion (Advanced)

### Derby Datenbank anzeigen

Da wir In-Memory Derby nutzen (`jdbc:derby:memory:carsharing`), existiert die Datenbank nur während die App läuft.

**Option 1: SQL über Code ausführen**
Fügen Sie in `CarSharingService.java` einen Debug-Endpoint hinzu:
```java
@GetMapping("/debug/tables")
public String showTables() {
    Query q = em.createNativeQuery("SELECT TABLENAME FROM SYS.SYSTABLES WHERE TABLETYPE='T'");
    return q.getResultList().toString();
}
```

**Option 2: Persistente Datenbank (für Entwicklung)**
Ändern Sie `application.properties`:
```properties
# Statt memory:
app.datasource.jdbc-url=jdbc:derby:directory:carsharing_db;create=true
```
Dann können Sie die DB mit Derby Tools öffnen.

---

## ✅ Checkliste: Alles funktioniert?

- [ ] Maven Dependencies geladen (keine roten Fehler in IntelliJ)
- [ ] App startet ohne Fehler
- [ ] JavaFX Fenster öffnet sich
- [ ] Console zeigt "Spring Boot Backend gestartet"
- [ ] http://localhost:8080/setup gibt "Setup completed" zurück
- [ ] http://localhost:8080/rides zeigt JSON-Array mit Fahrten
- [ ] http://localhost:8080/users zeigt JSON-Array mit Benutzern

**Wenn alle Punkte ✅ sind: GLÜCKWUNSCH! Backend läuft perfekt!**

---

## 🎯 Nächste Entwicklungsschritte (Optional)

### Frontend mit Backend verbinden

Aktuell nutzt das JavaFX Frontend noch die In-Memory Repositories.  
Um das Backend zu nutzen:

1. **HTTP Client integrieren:**
   - Java 11+: `java.net.http.HttpClient`
   - Oder Spring's `RestTemplate`

2. **Services anpassen:**
   ```java
   // Statt: rideRepository.findAll()
   // Neu: HTTP GET http://localhost:8080/rides
   ```

3. **Async Loading:**
   - Daten asynchron vom Backend laden
   - JavaFX UI-Thread beachten (`Platform.runLater()`)

### Weitere REST Endpoints

- PUT `/rides/{id}` - Fahrt aktualisieren
- DELETE `/rides/{id}` - Fahrt löschen
- POST `/users/{userId}/favorites/{rideId}` - Favorit hinzufügen
- POST `/users/{userId}/book/{rideId}` - Fahrt buchen

---

**Viel Erfolg beim Testen! 🚀**

Bei Fragen oder Problemen: Alle Dateien sind dokumentiert und folgen dem Demo Code Pattern.
