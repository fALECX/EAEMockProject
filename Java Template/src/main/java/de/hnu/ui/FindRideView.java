package de.hnu.ui;

import de.hnu.controller.FindRideController;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * View für "Find a Ride" (Page 115 - Mainscreen).
 *
 * Entspricht dem Balsamiq-Mockup:
 * - Header mit Back-Button, Titel "Car Sharing", Untertitel "Find a Ride"
 * - Eingabefelder: From, To, Date, Time
 * - "Find a Ride" Button
 * - Link zu Favoriten
 * - Bottom Navigation Bar (Platzhalter)
 */
public class FindRideView {

    private final BorderPane root;
    private final FindRideController controller;

    // Eingabefelder
    private TextField fromField;
    private TextField toField;
    private DatePicker datePicker;
    private TextField timeField;

    public FindRideView(FindRideController controller) {
        this.controller = controller;
        this.root = new BorderPane();

        buildUI();
    }

    /**
     * Baut die komplette UI entsprechend dem Mockup.
     */
    private void buildUI() {
        root.setStyle("-fx-background-color: white;");

        // Header
        root.setTop(createHeader());

        // Hauptinhalt
        root.setCenter(createContent());

        // Bottom Navigation
        root.setBottom(createBottomNavigation());
    }

    /**
     * Erstellt den Header mit Back-Button und Titel.
     * ISO 9241-11 Effektivität: Klare Orientierung durch konsistenten Header.
     */
    private VBox createHeader() {
        VBox header = new VBox(5);
        header.setPadding(new Insets(15, 15, 10, 15));

        // Obere Zeile: Back-Button und User-Initialen
        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        // Back-Button (auf Hauptscreen nicht aktiv, aber sichtbar für Konsistenz)
        Button backButton = new Button("←");
        backButton.setStyle("-fx-font-size: 18px; -fx-background-radius: 20; " +
                          "-fx-border-radius: 20; -fx-border-color: #ccc; " +
                          "-fx-background-color: white; -fx-min-width: 40; -fx-min-height: 40;");
        backButton.setDisable(true); // Auf Hauptscreen deaktiviert

        // Titel "Car Sharing"
        Label titleLabel = new Label("Car Sharing");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, FontPosture.ITALIC, 24));
        HBox.setMargin(titleLabel, new Insets(0, 0, 0, 10));

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // User-Initialen
        Label userInitials = new Label(controller.getUserInitials());
        userInitials.setStyle("-fx-background-color: #333; -fx-text-fill: white; " +
                             "-fx-padding: 8 12; -fx-font-weight: bold;");

        topRow.getChildren().addAll(backButton, titleLabel, spacer, userInitials);

        // Untertitel "Find a Ride"
        Label subtitleLabel = new Label("Find a Ride");
        subtitleLabel.setFont(Font.font("System", FontPosture.ITALIC, 16));
        subtitleLabel.setPadding(new Insets(0, 0, 0, 55));

        header.getChildren().addAll(topRow, subtitleLabel);

        return header;
    }

    /**
     * Erstellt den Hauptinhalt mit Suchformular.
     * ISO 9241-11 Effizienz: Minimale Eingaben für Kernfunktion.
     */
    private VBox createContent() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.setAlignment(Pos.TOP_CENTER);

        // From-Feld
        HBox fromBox = createInputFieldWithClear("From");

        // To-Feld
        HBox toBox = createInputFieldWithClear("To");

        // Date und Time in einer Zeile
        HBox dateTimeRow = new HBox(10);
        dateTimeRow.setAlignment(Pos.CENTER);

        // Date-Picker
        VBox dateBox = new VBox(5);
        datePicker = new DatePicker();
        datePicker.setPromptText("Date");
        datePicker.setPrefWidth(140);
        Button clearDateBtn = createClearButton(() -> datePicker.setValue(null));
        HBox dateWithClear = new HBox(5, datePicker, clearDateBtn);
        dateBox.getChildren().add(dateWithClear);

        // Time-Feld
        VBox timeBox = new VBox(5);
        timeField = new TextField();
        timeField.setPromptText("Time");
        timeField.setPrefWidth(140);
        Button clearTimeBtn = createClearButton(() -> timeField.clear());
        HBox timeWithClear = new HBox(5, timeField, clearTimeBtn);
        timeBox.getChildren().add(timeWithClear);

        dateTimeRow.getChildren().addAll(dateBox, timeBox);

        // Spacer
        Region spacer = new Region();
        spacer.setMinHeight(30);

        // Find a Ride Button
        // ISO 9241-11 Effektivität: Prominenter Button für Hauptaktion
        Button findRideButton = new Button("Find a Ride");
        findRideButton.setStyle("-fx-background-color: #333; -fx-text-fill: white; " +
                               "-fx-font-size: 16px; -fx-font-weight: bold; " +
                               "-fx-padding: 12 40; -fx-background-radius: 8; " +
                               "-fx-font-style: italic;");
        findRideButton.setOnAction(e -> controller.onFindRideClicked(
                fromField.getText(), toField.getText()));

        // Großer Spacer
        Region bigSpacer = new Region();
        VBox.setVgrow(bigSpacer, Priority.ALWAYS);

        // Link zu Favoriten
        // ISO 9241-11 Effizienz: Schnellzugriff auf häufig genutzte Funktion
        Hyperlink favoritesLink = new Hyperlink("Click here to view recently viewed or favorite rides");
        favoritesLink.setStyle("-fx-text-fill: #0066cc; -fx-font-size: 12px;");
        favoritesLink.setOnAction(e -> controller.onFavoritesLinkClicked());

        content.getChildren().addAll(
                fromBox,
                toBox,
                dateTimeRow,
                spacer,
                findRideButton,
                bigSpacer,
                favoritesLink
        );

        return content;
    }

    /**
     * Erstellt ein Eingabefeld mit Clear-Button.
     */
    private HBox createInputFieldWithClear(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setPrefWidth(250);
        field.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 8;");

        Button clearBtn = createClearButton(() -> field.clear());

        // Referenz speichern
        if (placeholder.equals("From")) {
            fromField = field;
        } else if (placeholder.equals("To")) {
            toField = field;
        }

        HBox container = new HBox(5, field, clearBtn);
        container.setAlignment(Pos.CENTER);
        return container;
    }

    /**
     * Erstellt einen roten Clear-Button (⊗).
     */
    private Button createClearButton(Runnable action) {
        Button btn = new Button("⊗");
        btn.setStyle("-fx-text-fill: #cc0000; -fx-background-color: transparent; " +
                    "-fx-font-size: 16px; -fx-cursor: hand;");
        btn.setOnAction(e -> action.run());
        return btn;
    }

    /**
     * Erstellt die Bottom Navigation Bar.
     * Platzhalter - nicht funktional in diesem Prototyp.
     */
    private HBox createBottomNavigation() {
        HBox navBar = new HBox();
        navBar.setAlignment(Pos.CENTER);
        navBar.setSpacing(60);
        navBar.setPadding(new Insets(15));
        navBar.setStyle("-fx-border-color: #ccc; -fx-border-width: 1 0 0 0;");

        // Home Icon
        Label homeIcon = new Label("🏠");
        homeIcon.setStyle("-fx-font-size: 24px;");

        // Car Icon
        Label carIcon = new Label("🚗");
        carIcon.setStyle("-fx-font-size: 24px;");

        // Profile Icon
        Label profileIcon = new Label("👤");
        profileIcon.setStyle("-fx-font-size: 24px;");

        navBar.getChildren().addAll(homeIcon, carIcon, profileIcon);

        return navBar;
    }

    /**
     * Gibt das Root-Element für die Scene zurück.
     */
    public BorderPane getRoot() {
        return root;
    }
}
