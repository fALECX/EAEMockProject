package de.hnu.ui;

import de.hnu.controller.BookingConfirmationController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * View für "Booking Confirmation" (Page 131).
 *
 * Entspricht dem Balsamiq-Mockup:
 * - Header mit Back-Button, "Car Sharing"
 * - Bestätigungs-Card mit "THANK YOU!" Überschrift
 * - Bestätigungstext
 * - "Go back to the overview" Button
 * - Bottom Navigation Bar
 */
public class BookingConfirmationView {

    private final BorderPane root;
    private final BookingConfirmationController controller;

    public BookingConfirmationView(BookingConfirmationController controller) {
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
     * Erstellt den Hauptinhalt mit Bestätigungs-Card.
     * ISO 9241-11 Zufriedenheit: Klare Erfolgsrückmeldung nach Buchung.
     */
    private VBox createContent() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 20, 20, 20));
        content.setAlignment(Pos.TOP_CENTER);

        // Bestätigungs-Card (wie im Mockup mit grauem Header)
        VBox confirmationCard = new VBox();
        confirmationCard.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; " +
                                 "-fx-background-color: white;");
        confirmationCard.setMaxWidth(320);

        // Card-Header mit "THANK YOU!"
        VBox cardHeader = new VBox();
        cardHeader.setStyle("-fx-background-color: #e0e0e0; -fx-padding: 15;");

        Label thankYouLabel = new Label("THANK YOU!");
        thankYouLabel.setFont(Font.font("System", FontWeight.BOLD, 22));
        cardHeader.getChildren().add(thankYouLabel);

        // Card-Body mit Text
        VBox cardBody = new VBox(15);
        cardBody.setPadding(new Insets(15));

        // Bestätigungstext (exakt wie im Mockup)
        Label line1 = new Label("You have successfully booked this trip!");
        line1.setWrapText(true);

        Label line2 = new Label("We will inform the driver about it.");
        line2.setWrapText(true);

        Label line3 = new Label("Have a pleasant journey!");
        line3.setWrapText(true);

        // Spacer
        Region spacer = new Region();
        spacer.setMinHeight(10);

        // "Go back to the overview" Button
        // ISO 9241-11 Effizienz: Klarer Weg zurück zum Ausgangspunkt
        Button backToOverviewButton = new Button("Go back to the overview");
        backToOverviewButton.setStyle("-fx-background-color: #333; -fx-text-fill: white; " +
                                     "-fx-font-size: 13px; -fx-padding: 10 20; " +
                                     "-fx-background-radius: 5; -fx-font-style: italic;");
        backToOverviewButton.setOnAction(e -> controller.onBackToOverviewClicked());

        cardBody.getChildren().addAll(line1, line2, line3, spacer, backToOverviewButton);

        confirmationCard.getChildren().addAll(cardHeader, cardBody);

        content.getChildren().add(confirmationCard);

        return content;
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
