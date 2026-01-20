# 📋 Diagramm-Spezifikationen für KI-Generierung

## Projekt: Car Sharing Application (JavaFX + Spring Boot Backend)

Diese Datei enthält alle Informationen, um die 3 geforderten UML-Diagramme zu erstellen.

---

## 1. FUNCTION ARCHITECTURE DIAGRAM

### Anforderung:
Erstelle ein Function Architecture Diagram (Schichtenarchitektur) für eine Car Sharing Anwendung.

### System-Komponenten:

**Layer 1: Presentation Layer (JavaFX UI)**
- FindRideView (Hauptbildschirm - Fahrtsuche)
- AvailableRidesView (Suchergebnisse)
- RideDetailsView (Fahrt-Details)
- FavoriteRidesView (Favoriten-Liste)
- BookingConfirmationView (Buchungsbestätigung)
- NavigationService (View-Routing zwischen Screens)

**Layer 2: Controller Layer**
- FindRideController (Event-Handling für Fahrtsuche)
- AvailableRidesController (Event-Handling für Ergebnisliste)
- RideDetailsController (Event-Handling für Details und Buchung)
- FavoriteRidesController (Event-Handling für Favoriten)

**Layer 3: Business Logic Layer**
Services:
- RideService (Fahrtsuche, CRUD für Fahrten)
- FavoriteService (Favoriten hinzufügen/entfernen)
- BookingService (Fahrt buchen)
- SessionService (Aktueller Benutzer)

Repositories (In-Memory):
- RideRepository (Speichert Fahrten)
- UserRepository (Speichert Benutzer)

**Layer 4: Backend Layer (Spring Boot)**
- CarSharingService (@RestController - REST API Endpoints)
- AppConfig (@Configuration - Spring Boot Konfiguration)

**Layer 5: Persistence Layer**
- JPA/EclipseLink (ORM Framework)
- Entity Manager (Datenbankzugriff)
- JPA Entities (User, Ride mit @Entity Annotationen)

**Layer 6: Data Layer**
- Derby Database (In-Memory Embedded)
- Tabellen: APPUSER, RIDE, APPUSER_RIDE (Join Tables für favorites und bookedRides)

### Datenfluss:
1. User interagiert mit UI View
2. View ruft Controller auf
3. Controller nutzt Services
4. Services nutzen Repositories ODER REST API
5. REST API nutzt EntityManager
6. EntityManager speichert in Derby Database

### Parallele Architektur:
- JavaFX Frontend läuft im selben JVM Prozess wie Spring Boot Backend
- Frontend kann In-Memory Repositories ODER REST API nutzen
- Backend stellt REST Endpoints bereit (Port 8080)

---

## 2. CONCEPTUAL UML CLASS DIAGRAM

### Anforderung:
Erstelle ein konzeptuelles UML-Klassendiagramm (Domänenmodell) mit den Haupt-Geschäftsobjekten.

### Klassen (vereinfacht, nur wichtige Attribute):

**User (Benutzer)**
- Attribute:
  - firstName: String
  - lastName: String
  - rating: double (1.0 - 5.0)
  - favorites: List<Ride>
  - bookedRides: List<Ride>
- Methoden:
  - getFullName(): String
  - getInitials(): String

**Ride (Fahrt)**
- Attribute:
  - driverName: String
  - origin: String (Startort)
  - destination: String (Zielort)
  - distanceKm: int
  - date: LocalDate
  - time: LocalTime
  - availableSeats: int (verfügbare Plätze)
  - driverRating: int (1-5 Sterne)
  - driver: User (Beziehung zum Fahrer)
- Methoden:
  - getRouteDisplay(): String (z.B. "Ulm - München")

**RideService**
- Methoden:
  - searchRides(from: String, to: String): List<Ride>
  - getAllRides(): List<Ride>
  - getRideById(id: long): Ride

**FavoriteService**
- Methoden:
  - addFavorite(ride: Ride, user: User): void
  - removeFavorite(ride: Ride, user: User): void
  - getFavorites(user: User): List<Ride>
  - isFavorite(ride: Ride, user: User): boolean

**BookingService**
- Methoden:
  - bookRide(ride: Ride, user: User): void
  - cancelBooking(ride: Ride, user: User): void

### Beziehungen:

1. **User → Ride (favorites)**: 
   - Typ: OneToMany
   - Ein User kann viele Fahrten als Favoriten haben

2. **User → Ride (bookedRides)**:
   - Typ: OneToMany
   - Ein User kann viele Fahrten buchen

3. **Ride → User (driver)**:
   - Typ: ManyToOne
   - Jede Fahrt hat einen Fahrer (User)

4. **Services nutzen (use) User und Ride**:
   - RideService verwaltet Rides
   - FavoriteService nutzt User und Ride
   - BookingService nutzt User und Ride

### Use Cases:
1. Fahrt suchen (searchRides)
2. Fahrt-Details ansehen
3. Fahrt buchen
4. Favoriten verwalten (hinzufügen/entfernen)
5. Gebuchte Fahrten ansehen

### Aktueller Nutzer:
- Samuel Klefe (fest eingeloggter Benutzer im Prototyp)

---

## 3. TECHNICAL UML CLASS DIAGRAM

### Anforderung:
Erstelle ein vollständiges technisches UML-Klassendiagramm mit ALLEN Klassen, Attributen, Methoden und Beziehungen.

### MODEL LAYER (JPA Entities)

**User**
```
<<Entity>>
- id: long (@Id @GeneratedValue)
- firstName: String (@Column length=50)
- lastName: String (@Column length=50)
- rating: double
- favorites: List<Ride> (@OneToMany, FetchType.LAZY, CascadeType.PERSIST)
- bookedRides: List<Ride> (@OneToMany, FetchType.LAZY, CascadeType.PERSIST)

+ User()
+ User(id: long, firstName: String, lastName: String, rating: double)
+ getId(): long
+ setId(id: long): void
+ getFirstName(): String
+ setFirstName(firstName: String): void
+ getLastName(): String
+ setLastName(lastName: String): void
+ getRating(): double
+ setRating(rating: double): void
+ getFavorites(): List<Ride>
+ setFavorites(favorites: List<Ride>): void
+ getBookedRides(): List<Ride>
+ setBookedRides(bookedRides: List<Ride>): void
+ getFullName(): String
+ getInitials(): String
+ toString(): String
```

**Ride**
```
<<Entity>>
- id: long (@Id @GeneratedValue)
- driverName: String (@Column length=50)
- origin: String (@Column length=100)
- destination: String (@Column length=100)
- distanceKm: int
- date: LocalDate
- time: LocalTime
- availableSeats: int
- driverRating: int
- driver: User (@ManyToOne)

+ Ride()
+ Ride(id: long, driverName: String, origin: String, destination: String, 
       distanceKm: int, date: LocalDate, time: LocalTime, 
       availableSeats: int, driverRating: int)
+ getId(): long
+ setId(id: long): void
+ getDriverName(): String
+ setDriverName(driverName: String): void
+ getOrigin(): String
+ setOrigin(origin: String): void
+ getDestination(): String
+ setDestination(destination: String): void
+ getDistanceKm(): int
+ setDistanceKm(distanceKm: int): void
+ getDate(): LocalDate
+ setDate(date: LocalDate): void
+ getTime(): LocalTime
+ setTime(time: LocalTime): void
+ getAvailableSeats(): int
+ setAvailableSeats(availableSeats: int): void
+ getDriverRating(): int
+ setDriverRating(driverRating: int): void
+ getDriver(): User
+ setDriver(driver: User): void
+ getRouteDisplay(): String
+ toString(): String
```

### REPOSITORY LAYER

**UserRepository**
```
- users: List<User>

+ UserRepository()
- initializeDummyData(): void
+ findAll(): List<User>
+ findById(id: long): Optional<User>
+ getCurrentUser(): User
+ save(user: User): User
- getNextId(): long
```

**RideRepository**
```
- rides: List<Ride>

+ RideRepository()
- initializeDummyData(): void
+ findAll(): List<Ride>
+ findById(id: long): Optional<Ride>
+ searchRides(from: String, to: String): List<Ride>
+ save(ride: Ride): Ride
- getNextId(): long
```

### SERVICE LAYER

**SessionService**
```
- userRepository: UserRepository

+ SessionService(userRepository: UserRepository)
+ getCurrentUser(): User
+ getUserInitials(): String
```

**RideService**
```
- rideRepository: RideRepository

+ RideService(rideRepository: RideRepository)
+ searchRides(from: String, to: String): List<Ride>
+ getAllRides(): List<Ride>
+ getRideById(id: long): Ride
```

**FavoriteService**
```
- userRepository: UserRepository
- rideRepository: RideRepository

+ FavoriteService(userRepository: UserRepository, rideRepository: RideRepository)
+ addFavorite(rideId: long): boolean
+ removeFavorite(rideId: long): boolean
+ getFavorites(): List<Ride>
+ isFavorite(rideId: long): boolean
```

**BookingService**
```
- userRepository: UserRepository

+ BookingService(userRepository: UserRepository)
+ bookRide(ride: Ride): void
```

### CONTROLLER LAYER

**FindRideController**
```
- rideService: RideService
- navigationService: NavigationService

+ FindRideController(rideService: RideService, navigationService: NavigationService)
+ onSearchClicked(from: String, to: String): void
+ getUserInitials(): String
```

**AvailableRidesController**
```
- rideService: RideService
- favoriteService: FavoriteService
- navigationService: NavigationService
- searchResults: List<Ride>

+ AvailableRidesController(rideService: RideService, favoriteService: FavoriteService, 
                           navigationService: NavigationService, searchResults: List<Ride>)
+ getSearchResults(): List<Ride>
+ onRideClicked(ride: Ride): void
+ onFavoriteClicked(rideId: long): void
+ isFavorite(rideId: long): boolean
+ onBackClicked(): void
+ getUserInitials(): String
```

**RideDetailsController**
```
- selectedRide: Ride
- favoriteService: FavoriteService
- bookingService: BookingService
- navigationService: NavigationService

+ RideDetailsController(selectedRide: Ride, favoriteService: FavoriteService,
                        bookingService: BookingService, navigationService: NavigationService)
+ getRide(): Ride
+ isFavorite(): boolean
+ onFavoriteClicked(): void
+ onBookTripClicked(): void
+ onBackClicked(): void
+ getUserInitials(): String
```

**FavoriteRidesController**
```
- favoriteService: FavoriteService
- navigationService: NavigationService

+ FavoriteRidesController(favoriteService: FavoriteService, navigationService: NavigationService)
+ getFavorites(): List<Ride>
+ onRideClicked(ride: Ride): void
+ onBackClicked(): void
+ getUserInitials(): String
```

### UI LAYER (Views)

**NavigationService**
```
<<enum>> ViewType {FIND_RIDE, FAVORITES, AVAILABLE_RIDES, RIDE_DETAILS, BOOKING_CONFIRMATION}

- primaryStage: Stage
- rideService: RideService
- favoriteService: FavoriteService
- bookingService: BookingService
- sessionService: SessionService
- previousView: ViewType

+ NavigationService(primaryStage: Stage, rideService: RideService, favoriteService: FavoriteService,
                    bookingService: BookingService, sessionService: SessionService)
+ navigateTo(viewType: ViewType): void
+ navigateTo(viewType: ViewType, data: Object): void
+ back(): void
+ setPreviousView(viewType: ViewType): void
- createScene(viewType: ViewType, data: Object): Scene
- getViewTitle(viewType: ViewType): String
```

**FindRideView**
```
- root: BorderPane
- controller: FindRideController

+ FindRideView(controller: FindRideController)
- buildUI(): void
- createHeader(): VBox
- createContent(): VBox
- createBottomNavigation(): HBox
+ getRoot(): BorderPane
```

**AvailableRidesView**
```
- root: BorderPane
- controller: AvailableRidesController

+ AvailableRidesView(controller: AvailableRidesController)
- buildUI(): void
- createHeader(): VBox
- createContent(): VBox
- createRideCard(ride: Ride): VBox
- createRatingStars(rating: int): HBox
- createBottomNavigation(): HBox
+ getRoot(): BorderPane
```

**RideDetailsView**
```
- root: BorderPane
- controller: RideDetailsController

+ RideDetailsView(controller: RideDetailsController)
- buildUI(): void
- createHeader(): VBox
- createContent(): VBox
- createRatingStars(rating: int): HBox
- createBookButton(): Button
- createBottomNavigation(): HBox
+ getRoot(): BorderPane
```

**FavoriteRidesView**
```
- root: BorderPane
- controller: FavoriteRidesController

+ FavoriteRidesView(controller: FavoriteRidesController)
- buildUI(): void
- createHeader(): VBox
- createContent(): VBox
- createRideCard(ride: Ride): VBox
- createRatingStars(rating: int): HBox
- createBottomNavigation(): HBox
+ getRoot(): BorderPane
```

**BookingConfirmationView**
```
- root: BorderPane
- navigationService: NavigationService
- sessionService: SessionService

+ BookingConfirmationView(navigationService: NavigationService, sessionService: SessionService)
- buildUI(): void
- createHeader(): VBox
- createContent(): VBox
- createBottomNavigation(): HBox
+ getRoot(): BorderPane
```

### BACKEND LAYER

**CarSharingService**
```
<<@RestController>>
<<@Transactional>>
- em: EntityManager (@PersistenceContext)

+ setup(): String (@GetMapping("/setup"))
+ getRide(id: long): Ride (@GetMapping("/rides/{id}"))
+ getAllRides(): List<Ride> (@GetMapping("/rides"))
+ createRide(ride: Ride): Ride (@PostMapping("/rides"))
+ getUser(id: long): User (@GetMapping("/users/{id}"))
+ getAllUsers(): List<User> (@GetMapping("/users"))
+ createUser(user: User): User (@PostMapping("/users"))
+ getBookedRides(id: long): List<Ride> (@GetMapping("/users/{id}/bookedRides"))
```

**AppConfig**
```
<<@Configuration>>
<<@EnableJpaRepositories>>
<<@EnableTransactionManagement>>

+ dataSourceProperties(): DataSourceProperties (@Bean, @ConfigurationProperties("app.datasource"))
+ dataSource(): DataSource (@Bean, @ConfigurationProperties("app.datasource"))
+ entityManagerFactory(): LocalContainerEntityManagerFactoryBean (@Bean, @ConfigurationProperties("app.jpa"))
+ transactionManager(entityManagerFactory: LocalContainerEntityManagerFactoryBean): PlatformTransactionManager (@Bean)
```

**App**
```
<<@SpringBootApplication>>
extends Application

- springContext: ConfigurableApplicationContext (static)

+ init(): void (override)
+ start(primaryStage: Stage): void (override)
+ stop(): void (override)
+ main(args: String[]): void (static)
```

### BEZIEHUNGEN (Alle):

**Model Layer:**
1. User "1" --→ "*" Ride : favorites (@OneToMany)
2. User "1" --→ "*" Ride : bookedRides (@OneToMany)
3. Ride "*" --→ "1" User : driver (@ManyToOne)

**Repository Layer:**
4. UserRepository --→ User : manages
5. RideRepository --→ Ride : manages

**Service Layer:**
6. SessionService --→ UserRepository : uses
7. RideService --→ RideRepository : uses
8. FavoriteService --→ UserRepository : uses
9. FavoriteService --→ RideRepository : uses
10. BookingService --→ UserRepository : uses

**Controller Layer:**
11. FindRideController --→ RideService : uses
12. FindRideController --→ NavigationService : uses
13. AvailableRidesController --→ RideService : uses
14. AvailableRidesController --→ FavoriteService : uses
15. AvailableRidesController --→ NavigationService : uses
16. RideDetailsController --→ FavoriteService : uses
17. RideDetailsController --→ BookingService : uses
18. RideDetailsController --→ NavigationService : uses
19. FavoriteRidesController --→ FavoriteService : uses
20. FavoriteRidesController --→ NavigationService : uses

**UI Layer:**
21. FindRideView --→ FindRideController : uses
22. AvailableRidesView --→ AvailableRidesController : uses
23. RideDetailsView --→ RideDetailsController : uses
24. FavoriteRidesView --→ FavoriteRidesController : uses
25. BookingConfirmationView --→ NavigationService : uses
26. BookingConfirmationView --→ SessionService : uses

**Navigation:**
27. NavigationService --→ RideService : uses
28. NavigationService --→ FavoriteService : uses
29. NavigationService --→ BookingService : uses
30. NavigationService --→ SessionService : uses

**Backend:**
31. CarSharingService --→ User : persists
32. CarSharingService --→ Ride : persists
33. AppConfig ..> CarSharingService : configures
34. App --→ AppConfig : uses
35. App --→ NavigationService : creates

---

## ENTITY RELATIONSHIP DIAGRAM (Datenbank)

### Tabellen:

**APPUSER**
- ID: BIGINT (Primary Key, Auto-Generated)
- FIRSTNAME: VARCHAR(50)
- LASTNAME: VARCHAR(50)
- RATING: DOUBLE

**RIDE**
- ID: BIGINT (Primary Key, Auto-Generated)
- DRIVERNAME: VARCHAR(50)
- ORIGIN: VARCHAR(100)
- DESTINATION: VARCHAR(100)
- DISTANCEKM: INTEGER
- DATE: DATE
- TIME: TIME
- AVAILABLESEATS: INTEGER
- DRIVERRATING: INTEGER
- DRIVER_ID: BIGINT (Foreign Key → APPUSER.ID)

**APPUSER_RIDE** (Join Table für favorites)
- APPUSER_ID: BIGINT (Foreign Key → APPUSER.ID)
- FAVORITES_ID: BIGINT (Foreign Key → RIDE.ID)

**APPUSER_RIDE** (Join Table für bookedRides)
- APPUSER_ID: BIGINT (Foreign Key → APPUSER.ID)
- BOOKEDRIDES_ID: BIGINT (Foreign Key → RIDE.ID)

### Beziehungen:
1. APPUSER ||--o{ RIDE : "driver" (Ein User kann viele Rides als Driver haben)
2. APPUSER ||--o{ APPUSER_RIDE (favorites) : "has" (Ein User kann viele Favoriten haben)
3. RIDE ||--o{ APPUSER_RIDE (favorites) : "in" (Eine Ride kann in vielen Favoriten sein)
4. APPUSER ||--o{ APPUSER_RIDE (bookedRides) : "has" (Ein User kann viele Buchungen haben)
5. RIDE ||--o{ APPUSER_RIDE (bookedRides) : "in" (Eine Ride kann mehrfach gebucht sein)

---

## TECHNOLOGIE-STACK

**Frontend:**
- JavaFX 17.0.2
- Java 17
- Maven

**Backend:**
- Spring Boot 3.0.0
- Spring Data JPA
- EclipseLink 4.0.0 (JPA Provider)
- HikariCP 5.0.1 (Connection Pooling)

**Datenbank:**
- Apache Derby 10.15.2.0 (Embedded, In-Memory)

**Build:**
- Maven 3.x
- JavaFX Maven Plugin

---

## DEPLOYMENT

**Deployment-Typ:** Single JAR (Embedded)
- Alles läuft in einem JVM Prozess
- JavaFX UI + Spring Boot Backend parallel
- Derby Datenbank embedded (In-Memory)

**Ports:**
- Spring Boot Backend: 8080 (HTTP REST API)
- JavaFX UI: Kein Port (lokales GUI)
- Derby: Kein Port (Embedded Mode)

**Laufzeit:**
- JDK 17+ erforderlich
- Start über: `java -jar carsharing-1.0.jar` ODER `mvn javafx:run`

---

## DESIGN PATTERNS

**Verwendete Patterns:**
1. **MVC (Model-View-Controller):** UI Layer
2. **Repository Pattern:** RideRepository, UserRepository
3. **Service Layer Pattern:** RideService, FavoriteService, etc.
4. **Singleton:** NavigationService, Services
5. **Dependency Injection:** Spring Boot @Autowired, Constructor Injection
6. **DAO Pattern:** JPA EntityManager
7. **Factory Pattern:** NavigationService.createScene()

---

## ANWEISUNGEN FÜR KI

Erstelle basierend auf diesen Informationen:

1. **Function Architecture Diagram:**
   - Zeige alle 6 Layer (Presentation → Data)
   - Zeige Datenfluss zwischen Layern
   - Markiere parallele Ausführung (JavaFX + Spring Boot)
   - Nutze Farben für Layer-Unterscheidung

2. **Conceptual UML Class Diagram:**
   - Nur Hauptklassen: User, Ride, Services
   - Nur wichtige Attribute (keine technischen Details)
   - Alle Beziehungen zeigen (OneToMany, ManyToOne)
   - Use Cases als Notizen hinzufügen

3. **Technical UML Class Diagram:**
   - ALLE 24 Klassen mit ALLEN Attributen und Methoden
   - Alle 35 Beziehungen zeigen
   - Annotations als Stereotypes (<<Entity>>, <<RestController>>)
   - Visibility Markers (+, -, #)
   - Gruppiere nach Layern (Packages)

**Output-Format:** UML 2.5 Standard, professionell, lesbar

---

**Erstellt:** 2026-01-20  
**Projekt:** Car Sharing Application  
**Autor:** GitHub Copilot
