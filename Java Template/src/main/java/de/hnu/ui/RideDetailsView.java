package de.hnu.ui;

import de.hnu.controller.RideDetailsController;
import de.hnu.model.Ride;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.time.format.DateTimeFormatter;

/**
 * View für "Ride Details" (Page 123).
 *
 * Entspricht dem Balsamiq-Mockup:
 * - Header mit Back-Button, "Car Sharing"
 * - Fahrername in Großbuchstaben
 * - Rating-Sterne
 * - Details-Grid (Trip number, From, To, Distance, Date, Time, Available seats)
 * - "Book this ride" Button
 * - "Favor this ride" Button mit Herz
 * - Bottom Navigation Bar
 */
public class RideDetailsView {

    private final BorderPane root;
    private final RideDetailsController controller;
    private Label favoriteStatusLabel;

    public RideDetailsView(RideDetailsController controller) {
        this.controller = controller;
        this.root = new BorderPane();

        buildUI();
    }

    private void buildUI() {
        root.setStyle("-fx-background-color: white;");

        root.setTop(createHeader());
        root.setCenter(createContent());
        root.setBottom(createBottomNavigation());
    }

    private VBox createHeader() {
        VBox header = new VBox(5);
        header.setPadding(new Insets(15, 15, 10, 15));

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        // Back-Button
        Button backButton = new Button("←");
        backButton.setStyle("-fx-font-size: 18px; -fx-background-radius: 20; " +
                          "-fx-border-radius: 20; -fx-border-color: #ccc; " +
                          "-fx-background-color: white; -fx-min-width: 40; -fx-min-height: 40;");
        backButton.setOnAction(e -> controller.onBackClicked());

        Label titleLabel = new Label("Car Sharing");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 24));
        HBox.setMargin(titleLabel, new Insets(0, 0, 0, 10));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label userInitials = new Label(controller.getUserInitials());
        userInitials.setStyle("-fx-background-color: #333; -fx-text-fill: white; " +
                             "-fx-padding: 8 12; -fx-font-weight: bold;");

        topRow.getChildren().addAll(backButton, titleLabel, spacer, userInitials);

        header.getChildren().add(topRow);

        return header;
    }

    /**
     * Erstellt den Hauptinhalt mit Fahrt-Details.
     * ISO 9241-11 Effektivität: Alle relevanten Infos für Buchungsentscheidung.
     */
    private VBox createContent() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20, 25, 20, 25));
        content.setAlignment(Pos.TOP_CENTER);

        Ride ride = controller.getCurrentRide();

        // Fahrername (Großbuchstaben wie im Mockup)
        Label driverName = new Label(ride.getDriverName().toUpperCase());
        driverName.setFont(Font.font("System", FontWeight.BOLD, 22));

        // Rating-Sterne (groß)
        HBox ratingBox = createLargeRatingStars(ride.getDriverRating());

        // Spacer
        Region spacer1 = new Region();
        spacer1.setMinHeight(10);

        // Details-Grid
        // ISO 9241-11 Effizienz: Strukturierte Darstellung aller Infos
        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(20);
        detailsGrid.setVgap(10);
        detailsGrid.setPadding(new Insets(10, 0, 10, 0));

        // Trip number
        addDetailRow(detailsGrid, 0, "Trip number:", String.valueOf(ride.getId()));

        // From
        addDetailRow(detailsGrid, 1, "From:", ride.getOrigin());

        // To
        addDetailRow(detailsGrid, 2, "To:", ride.getDestination());

        // Distance
        addDetailRow(detailsGrid, 3, "Distance in km:", String.valueOf(ride.getDistanceKm()));

        // Date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        addDetailRow(detailsGrid, 4, "Date:", ride.getDate().format(dateFormatter));

        // Time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h a");
        String timeStr = ride.getTime().getHour() + " a.m.";
        if (ride.getTime().getHour() >= 12) {
            timeStr = (ride.getTime().getHour() == 12 ? 12 : ride.getTime().getHour() - 12) + " p.m.";
        }
        addDetailRow(detailsGrid, 5, "Time:", timeStr);

        // Available seats
        addDetailRow(detailsGrid, 6, "Available seats:", String.valueOf(ride.getAvailableSeats()));

        // Spacer
        Region spacer2 = new Region();
        spacer2.setMinHeight(15);

        // "Book this ride" Button
        // ISO 9241-11 Effektivität: Prominenter Button für Hauptaktion
        Button bookButton = new Button("Book this ride");
        bookButton.setStyle("-fx-background-color: #333; -fx-text-fill: white; " +
                           "-fx-font-size: 16px; -fx-font-weight: bold; " +
                           "-fx-padding: 12 50; -fx-background-radius: 8; " +
                           "-fx-font-style: italic;");
        bookButton.setOnAction(e -> {
            boolean success = controller.onBookRideClicked();
            if (!success) {
                showAlert("Buchung fehlgeschlagen", "Keine Plätze mehr verfügbar.");
            }
        });

        // Spacer
        Region spacer3 = new Region();
        spacer3.setMinHeight(10);

        // "Favor this ride" Button mit Herz
        HBox favorBox = new HBox(10);
        favorBox.setAlignment(Pos.CENTER);

        Label heartIcon = new Label("♡");
        heartIcon.setStyle("-fx-font-size: 20px;");

        Button favorButton = new Button("Favor this ride");
        favorButton.setStyle("-fx-background-color: #555; -fx-text-fill: white; " +
                            "-fx-font-size: 14px; -fx-padding: 10 30; " +
                            "-fx-background-radius: 8; -fx-font-style: italic;");

        // Status-Label für Feedback
        favoriteStatusLabel = new Label("");
        favoriteStatusLabel.setStyle("-fx-text-fill: #666; -fx-font-style: italic;");

        // Prüfen ob bereits Favorit
        if (controller.isFavorite()) {
            heartIcon.setText("❤");
            heartIcon.setStyle("-fx-font-size: 20px; -fx-text-fill: #cc0000;");
            favoriteStatusLabel.setText("Bereits in Favoriten");
        }

        favorButton.setOnAction(e -> {
            boolean added = controller.onFavoriteClicked();
            if (added) {
                heartIcon.setText("❤");
                heartIcon.setStyle("-fx-font-size: 20px; -fx-text-fill: #cc0000;");
                favoriteStatusLabel.setText("Zu Favoriten hinzugefügt!");
                // ISO 9241-11 Zufriedenheit: Sofortiges visuelles Feedback
            } else {
                favoriteStatusLabel.setText("Bereits in Favoriten");
            }
        });

        favorBox.getChildren().addAll(heartIcon, favorButton);

        content.getChildren().addAll(
                driverName,
                ratingBox,
                spacer1,
                detailsGrid,
                spacer2,
                bookButton,
                spacer3,
                favorBox,
                favoriteStatusLabel
        );

        return content;
    }

    /**
     * Fügt eine Zeile zum Details-Grid hinzu.
     */
    private void addDetailRow(GridPane grid, int row, String label, String value) {
        Label labelNode = new Label(label);
        labelNode.setFont(Font.font("System", FontWeight.BOLD, 14));
        labelNode.setAlignment(Pos.CENTER_RIGHT);

        Label valueNode = new Label(value);
        valueNode.setFont(Font.font("System", 14));

        grid.add(labelNode, 0, row);
        grid.add(valueNode, 1, row);
    }

    /**
     * Erstellt große Rating-Sterne für die Detail-Ansicht.
     */
    private HBox createLargeRatingStars(int rating) {
        HBox starsBox = new HBox(5);
        starsBox.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 5; i++) {
            Label star = new Label(i <= rating ? "★" : "☆");
            star.setStyle("-fx-text-fill: " + (i <= rating ? "#f5a623" : "#ccc") +
                         "; -fx-font-size: 28px;");
            starsBox.getChildren().add(star);
        }

        return starsBox;
    }

    /**
     * Zeigt einen Alert-Dialog an.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private HBox createBottomNavigation() {
        HBox navBar = new HBox();
        navBar.setAlignment(Pos.CENTER);
        navBar.setSpacing(60);
        navBar.setPadding(new Insets(15));
        navBar.setStyle("-fx-border-color: #ccc; -fx-border-width: 1 0 0 0;");

        Label homeIcon = new Label("🏠");
        homeIcon.setStyle("-fx-font-size: 24px;");

        Label carIcon = new Label("🚗");
        carIcon.setStyle("-fx-font-size: 24px;");

        Label profileIcon = new Label("👤");
        profileIcon.setStyle("-fx-font-size: 24px;");

        navBar.getChildren().addAll(homeIcon, carIcon, profileIcon);

        return navBar;
    }

    public BorderPane getRoot() {
        return root;
    }
}
