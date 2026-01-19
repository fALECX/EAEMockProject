package de.hnu.ui;

import de.hnu.controller.FavoriteRidesController;
import de.hnu.model.Ride;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.util.List;

/**
 * View für "Favorite Rides" (Page 118).
 *
 * Entspricht dem Balsamiq-Mockup:
 * - Header mit Back-Button, "Car Sharing", "Find a Ride"
 * - "Favorite rides:" Label
 * - Liste der Favoriten mit Herz-Icon, Name, Route, Distanz, Rating
 * - Bottom Navigation Bar
 */
public class FavoriteRidesView {

    private final BorderPane root;
    private final FavoriteRidesController controller;

    public FavoriteRidesView(FavoriteRidesController controller) {
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

    /**
     * Erstellt den Header mit Back-Button.
     * ISO 9241-11 Effizienz: Back-Button ermöglicht schnelle Rückkehr.
     */
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

        // Titel
        Label titleLabel = new Label("Car Sharing");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 24));
        HBox.setMargin(titleLabel, new Insets(0, 0, 0, 10));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // User-Initialen
        Label userInitials = new Label(controller.getUserInitials());
        userInitials.setStyle("-fx-background-color: #333; -fx-text-fill: white; " +
                             "-fx-padding: 8 12; -fx-font-weight: bold;");

        topRow.getChildren().addAll(backButton, titleLabel, spacer, userInitials);

        // Untertitel
        Label subtitleLabel = new Label("Find a Ride");
        subtitleLabel.setFont(Font.font("System", FontPosture.ITALIC, 16));
        subtitleLabel.setPadding(new Insets(0, 0, 0, 55));

        header.getChildren().addAll(topRow, subtitleLabel);

        return header;
    }

    /**
     * Erstellt den Hauptinhalt mit Favoriten-Liste.
     */
    private VBox createContent() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(10, 15, 10, 15));

        // "Favorite rides:" Label
        Label titleLabel = new Label("Favorite rides:");
        titleLabel.setFont(Font.font("System", FontPosture.ITALIC, 18));

        content.getChildren().add(titleLabel);

        // Favoriten laden und anzeigen
        List<Ride> favorites = controller.getFavorites();

        for (Ride ride : favorites) {
            VBox rideCard = createRideCard(ride);
            content.getChildren().add(rideCard);
        }

        // Falls keine Favoriten
        if (favorites.isEmpty()) {
            Label emptyLabel = new Label("Keine Favoriten vorhanden.");
            emptyLabel.setStyle("-fx-text-fill: #666; -fx-font-style: italic;");
            content.getChildren().add(emptyLabel);
        }

        return content;
    }

    /**
     * Erstellt eine Ride-Card für die Liste.
     * ISO 9241-11 Zufriedenheit: Übersichtliche Darstellung aller wichtigen Infos.
     */
    private VBox createRideCard(Ride ride) {
        VBox card = new VBox(3);
        card.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10; " +
                     "-fx-background-color: white;");
        card.setCursor(javafx.scene.Cursor.HAND);

        // Obere Zeile: Herz + Name + Rating
        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);

        // Herz-Icon (rot für Favorit)
        Label heartIcon = new Label("❤");
        heartIcon.setStyle("-fx-text-fill: #cc0000; -fx-font-size: 16px;");

        // Fahrername
        Label nameLabel = new Label(ride.getDriverName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Rating Stars
        HBox ratingBox = createRatingStars(ride.getDriverRating());

        topRow.getChildren().addAll(heartIcon, nameLabel, spacer, ratingBox);

        // Mittlere Zeile: Route
        Label routeLabel = new Label(ride.getRouteDisplay());
        routeLabel.setStyle("-fx-text-fill: #666;");

        // Untere Zeile: Distanz + Person-Icon
        HBox bottomRow = new HBox();
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        Label distanceLabel = new Label(ride.getDistanceKm() + " km");
        distanceLabel.setStyle("-fx-text-fill: #666;");

        Region bottomSpacer = new Region();
        HBox.setHgrow(bottomSpacer, Priority.ALWAYS);

        Label personIcon = new Label("🧍");
        personIcon.setStyle("-fx-font-size: 14px;");

        bottomRow.getChildren().addAll(distanceLabel, bottomSpacer, personIcon);

        card.getChildren().addAll(topRow, routeLabel, bottomRow);

        // Klick-Handler
        card.setOnMouseClicked(e -> controller.onRideClicked(ride));

        // Hover-Effekt für bessere Usability
        card.setOnMouseEntered(e -> card.setStyle(
                "-fx-border-color: #666; -fx-border-width: 1; -fx-padding: 10; " +
                "-fx-background-color: #f9f9f9;"));
        card.setOnMouseExited(e -> card.setStyle(
                "-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10; " +
                "-fx-background-color: white;"));

        return card;
    }

    /**
     * Erstellt die Rating-Sterne Anzeige.
     */
    private HBox createRatingStars(int rating) {
        HBox starsBox = new HBox(2);

        for (int i = 1; i <= 5; i++) {
            Label star = new Label(i <= rating ? "★" : "☆");
            star.setStyle("-fx-text-fill: " + (i <= rating ? "#f5a623" : "#ccc") +
                         "; -fx-font-size: 14px;");
            starsBox.getChildren().add(star);
        }

        return starsBox;
    }

    /**
     * Erstellt die Bottom Navigation Bar.
     */
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
