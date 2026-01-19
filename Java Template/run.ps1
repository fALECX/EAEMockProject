# Run JavaFX App mit korrekten JavaFX-Modulen

$JAVAFX_PATH = "C:\Users\samue\.m2\repository\org\openjfx"
$CONTROLS = "$JAVAFX_PATH\javafx-controls\17.0.2\javafx-controls-17.0.2.jar;$JAVAFX_PATH\javafx-controls\17.0.2\javafx-controls-17.0.2-win.jar"
$GRAPHICS = "$JAVAFX_PATH\javafx-graphics\17.0.2\javafx-graphics-17.0.2.jar;$JAVAFX_PATH\javafx-graphics\17.0.2\javafx-graphics-17.0.2-win.jar"
$BASE = "$JAVAFX_PATH\javafx-base\17.0.2\javafx-base-17.0.2.jar;$JAVAFX_PATH\javafx-base\17.0.2\javafx-base-17.0.2-win.jar"
$FXML = "$JAVAFX_PATH\javafx-fxml\17.0.2\javafx-fxml-17.0.2.jar;$JAVAFX_PATH\javafx-fxml\17.0.2\javafx-fxml-17.0.2-win.jar"

$MODULE_PATH = "$CONTROLS;$GRAPHICS;$BASE;$FXML"

Write-Host "Starte JavaFX Anwendung..."
Write-Host "Module Path: $MODULE_PATH"

& "C:\Users\samue\.jdks\openjdk-22\bin\java.exe" `
    --module-path "$MODULE_PATH" `
    --add-modules javafx.controls,javafx.fxml,javafx.graphics `
    -cp "target\classes;C:\Users\samue\.m2\repository\org\springframework\boot\spring-boot-starter-web\3.0.0\*" `
    de.hnu.App
