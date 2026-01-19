# JavaFX Car Sharing App

## Anwendung ausführen

### Option 1: Mit Maven (Empfohlen)
```bash
mvn clean javafx:run
```

### Option 2: Mit IntelliJ IDEA

#### Schritt 1: VM-Optionen hinzufügen
1. **Run** → **Edit Configurations...**
2. Wählen Sie die **App**-Konfiguration (oder erstellen Sie eine neue Application-Konfiguration)
3. Setzen Sie **Main class** auf: `de.hnu.App`
4. Fügen Sie unter **VM options** folgendes ein:

```
--module-path "C:\Users\samue\.m2\repository\org\openjfx\javafx-controls\17.0.2;C:\Users\samue\.m2\repository\org\openjfx\javafx-graphics\17.0.2;C:\Users\samue\.m2\repository\org\openjfx\javafx-base\17.0.2" --add-modules javafx.controls,javafx.fxml,javafx.graphics
```

**ODER** verwenden Sie Maven-Variablen:
```
--module-path ${MAVEN_REPOSITORY}/org/openjfx/javafx-controls/17.0.2:${MAVEN_REPOSITORY}/org/openjfx/javafx-graphics/17.0.2:${MAVEN_REPOSITORY}/org/openjfx/javafx-base/17.0.2 --add-modules javafx.controls,javafx.fxml,javafx.graphics
```

#### Schritt 2: Projekt neu laden
1. **File** → **Invalidate Caches / Restart**
2. Oder: Rechtsklick auf `pom.xml` → **Maven** → **Reload Project**

## Problemlösung

### Fehler: "JavaFX-Runtime-Komponenten fehlen"
Dies bedeutet, dass JavaFX nicht im Modulpfad ist. Verwenden Sie **Option 1** (Maven) oder fügen Sie die VM-Optionen wie oben beschrieben hinzu.

### Dependencies neu laden
```bash
mvn clean install
```

## Projektstruktur
- `src/main/java/de/hnu/` - Hauptquellcode
- `src/test/java/de/hnu/` - Unit-Tests
- `pom.xml` - Maven-Konfiguration
