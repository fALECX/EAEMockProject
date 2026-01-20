# 📡 REST API Dokumentation - Car Sharing Backend

**Base URL:** `http://localhost:8080`

---

## 🎯 Endpoints Übersicht

| Method | Endpoint | Beschreibung | Request Body | Response |
|--------|----------|--------------|--------------|----------|
| GET | `/setup` | Testdaten erstellen | - | String (Success Message) |
| GET | `/rides` | Alle Fahrten abrufen | - | List\<Ride\> |
| GET | `/rides/{id}` | Einzelne Fahrt abrufen | - | Ride |
| POST | `/rides` | Neue Fahrt erstellen | Ride JSON | Ride |
| GET | `/users` | Alle Benutzer abrufen | - | List\<User\> |
| GET | `/users/{id}` | Einzelnen Benutzer abrufen | - | User |
| POST | `/users` | Neuen Benutzer erstellen | User JSON | User |
| GET | `/users/{id}/bookedRides` | Gebuchte Fahrten eines Users | - | List\<Ride\> |

---

## 📝 Detaillierte Endpoint-Dokumentation

### 1. Setup - Testdaten erstellen

**Request:**
```http
GET http://localhost:8080/setup
```

**Response:**
```
Setup completed! Created 3 users and 3 rides.
```

**Was wird erstellt:**
- 3 Benutzer (Samuel Klefe, Max Mustermann, Anna Schmidt)
- 3 Fahrten (Neu-Ulm→München, Stuttgart→Berlin, Ulm→Augsburg)
- Anna Schmidt hat 1 gebuchte Fahrt (bookedRides)

---

### 2. Alle Fahrten abrufen

**Request:**
```http
GET http://localhost:8080/rides
```

**Response:**
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
    "driverRating": 5,
    "driver": {
      "id": 1,
      "firstName": "Samuel",
      "lastName": "Klefe",
      "rating": 4.8
    }
  },
  ...
]
```

**Query Parameter (zukünftig):**
- `origin` - Filtern nach Startort
- `destination` - Filtern nach Zielort
- `date` - Filtern nach Datum

---

### 3. Einzelne Fahrt abrufen

**Request:**
```http
GET http://localhost:8080/rides/1
```

**Path Parameter:**
- `id` (long) - Ride ID

**Response:**
```json
{
  "id": 1,
  "driverName": "Samuel Klefe",
  "origin": "Neu-Ulm",
  "destination": "München",
  "distanceKm": 150,
  "date": "2026-01-25",
  "time": "14:30:00",
  "availableSeats": 3,
  "driverRating": 5,
  "driver": {
    "id": 1,
    "firstName": "Samuel",
    "lastName": "Klefe",
    "rating": 4.8
  }
}
```

**Status Codes:**
- `200 OK` - Fahrt gefunden
- `404 Not Found` - Fahrt existiert nicht (wird als `null` zurückgegeben)

---

### 4. Neue Fahrt erstellen

**Request:**
```http
POST http://localhost:8080/rides
Content-Type: application/json

{
  "driverName": "John Doe",
  "origin": "Ulm",
  "destination": "Stuttgart",
  "distanceKm": 95,
  "date": "2026-01-25",
  "time": "14:00:00",
  "availableSeats": 3,
  "driverRating": 5
}
```

**Request Body:**
```json
{
  "driverName": "string",       // Required
  "origin": "string",           // Required
  "destination": "string",      // Required
  "distanceKm": number,         // Required
  "date": "YYYY-MM-DD",         // Required (ISO 8601)
  "time": "HH:mm:ss",           // Required (ISO 8601)
  "availableSeats": number,     // Required (0-8)
  "driverRating": number        // Required (1-5)
}
```

**Response:**
```json
{
  "id": 4,  // Auto-generated
  "driverName": "John Doe",
  "origin": "Ulm",
  "destination": "Stuttgart",
  "distanceKm": 95,
  "date": "2026-01-25",
  "time": "14:00:00",
  "availableSeats": 3,
  "driverRating": 5,
  "driver": null  // Optional - kann später gesetzt werden
}
```

**Validation:**
- `driverRating`: 1-5
- `availableSeats`: 0-8 (typisch)
- `date`: Zukünftiges Datum empfohlen

---

### 5. Alle Benutzer abrufen

**Request:**
```http
GET http://localhost:8080/users
```

**Response:**
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

---

### 6. Einzelnen Benutzer abrufen

**Request:**
```http
GET http://localhost:8080/users/1
```

**Path Parameter:**
- `id` (long) - User ID

**Response:**
```json
{
  "id": 1,
  "firstName": "Samuel",
  "lastName": "Klefe",
  "rating": 4.8,
  "favorites": [],
  "bookedRides": []
}
```

---

### 7. Neuen Benutzer erstellen

**Request:**
```http
POST http://localhost:8080/users
Content-Type: application/json

{
  "firstName": "Jane",
  "lastName": "Doe",
  "rating": 4.5
}
```

**Request Body:**
```json
{
  "firstName": "string",  // Required
  "lastName": "string",   // Required
  "rating": number        // Required (1.0 - 5.0)
}
```

**Response:**
```json
{
  "id": 4,  // Auto-generated
  "firstName": "Jane",
  "lastName": "Doe",
  "rating": 4.5,
  "favorites": [],
  "bookedRides": []
}
```

---

### 8. Gebuchte Fahrten eines Benutzers

**Request:**
```http
GET http://localhost:8080/users/3/bookedRides
```

**Path Parameter:**
- `id` (long) - User ID

**Response:**
```json
[
  {
    "id": 3,
    "driverName": "Samuel Klefe",
    "origin": "Ulm",
    "destination": "Augsburg",
    "distanceKm": 80,
    "date": "2026-01-20",
    "time": "10:00:00",
    "availableSeats": 0,
    "driverRating": 5,
    "driver": {
      "id": 1,
      "firstName": "Samuel",
      "lastName": "Klefe",
      "rating": 4.8
    }
  }
]
```

**Use Case:**
- Zeigt alle Fahrten, die der Benutzer gebucht hat
- Wird für "Previous Rides" Feature verwendet
- Leere Liste, wenn keine Buchungen vorhanden

---

## 🧪 Testing mit verschiedenen Tools

### Browser (GET Requests)

Einfach URL in Adressleiste eingeben:
```
http://localhost:8080/rides
http://localhost:8080/users
http://localhost:8080/setup
```

### PowerShell (Windows)

**GET Request:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/rides" -Method GET
```

**POST Request:**
```powershell
$body = @{
    driverName = "Test Driver"
    origin = "Ulm"
    destination = "Stuttgart"
    distanceKm = 95
    date = "2026-01-25"
    time = "14:00:00"
    availableSeats = 3
    driverRating = 5
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/rides" `
  -Method POST `
  -ContentType "application/json" `
  -Body $body
```

### Postman

1. Neue Collection erstellen: "Car Sharing API"
2. Für jeden Endpoint ein Request erstellen
3. Speichern für wiederverwendbare Tests

**Beispiel - POST Ride:**
- Method: POST
- URL: `http://localhost:8080/rides`
- Headers: `Content-Type: application/json`
- Body → raw → JSON:
  ```json
  {
    "driverName": "Test",
    "origin": "A",
    "destination": "B",
    "distanceKm": 100,
    "date": "2026-01-25",
    "time": "14:00:00",
    "availableSeats": 3,
    "driverRating": 5
  }
  ```

### curl (Git Bash / Linux)

**GET:**
```bash
curl http://localhost:8080/rides
```

**POST:**
```bash
curl -X POST http://localhost:8080/rides \
  -H "Content-Type: application/json" \
  -d '{
    "driverName": "Test Driver",
    "origin": "Ulm",
    "destination": "Stuttgart",
    "distanceKm": 95,
    "date": "2026-01-25",
    "time": "14:00:00",
    "availableSeats": 3,
    "driverRating": 5
  }'
```

---

## 🔍 JSON Response Schema

### Ride Object

```typescript
{
  id: number,              // Auto-generated by DB
  driverName: string,      // Driver's full name
  origin: string,          // Start location
  destination: string,     // End location
  distanceKm: number,      // Distance in kilometers
  date: string,            // ISO 8601 date (YYYY-MM-DD)
  time: string,            // ISO 8601 time (HH:mm:ss)
  availableSeats: number,  // Number of free seats
  driverRating: number,    // 1-5 stars
  driver?: User            // Optional - ManyToOne relationship
}
```

### User Object

```typescript
{
  id: number,              // Auto-generated by DB
  firstName: string,       // User's first name
  lastName: string,        // User's last name
  rating: number,          // User rating (1.0 - 5.0)
  favorites: Ride[],       // List of favorite rides
  bookedRides: Ride[]      // List of booked rides (OneToMany)
}
```

---

## 🚨 Error Handling

Aktuell gibt die API bei Fehlern `null` zurück oder leere Listen.

**Zukünftige Verbesserungen:**
- HTTP Status Codes (404, 400, 500)
- Error Response mit Details:
  ```json
  {
    "error": "Ride not found",
    "code": 404,
    "timestamp": "2026-01-20T10:00:00"
  }
  ```

---

## 📊 Datenbank-Schema (Auto-Generated)

### RIDE Tabelle
- `ID` (BIGINT, PK, AUTO_INCREMENT)
- `DRIVERNAME` (VARCHAR 50)
- `ORIGIN` (VARCHAR 100)
- `DESTINATION` (VARCHAR 100)
- `DISTANCEKM` (INTEGER)
- `DATE` (DATE)
- `TIME` (TIME)
- `AVAILABLESEATS` (INTEGER)
- `DRIVERRATING` (INTEGER)
- `DRIVER_ID` (BIGINT, FK → APPUSER)

### APPUSER Tabelle
- `ID` (BIGINT, PK, AUTO_INCREMENT)
- `FIRSTNAME` (VARCHAR 50)
- `LASTNAME` (VARCHAR 50)
- `RATING` (DOUBLE)

### Join Tabellen (OneToMany)
- `APPUSER_RIDE` (favorites)
- `APPUSER_RIDE` (bookedRides)

---

**API Version:** 1.0  
**Last Updated:** 2026-01-20  
**Author:** GitHub Copilot
