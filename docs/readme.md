# Swingdating
![Project Banner](/docs/ProductPic.png?raw=true)

Swingdating ist ein benutzerfreundliches Dating-Portal, entwickelt mit Java-Swing im Rahmen eines Schulprojekts zur Anwendungsentwicklung. Die Anwendung dient dazu, anhand von Benutzereingaben den „perfekten“ Partner aus einer SQL Schuldatenbank zu ermitteln.

---

## 📖 Inhaltsverzeichnis
- [Projektbeschreibung](#-projektbeschreibung)
- [Aufbau und Struktur](#️-aufbau-und-struktur)
- [Funktionen](#-funktionen)
- [Herausforderungen](#-herausforderungen)
- [Mögliche Erweiterungen](#-mögliche-erweiterungen)

---

## 📝 Projektbeschreibung

Im Projekt „Dating-Portal“ bestand die Aufgabe darin, ein Programm zu entwickeln, das anhand bestimmter Benutzereingaben aus der Schuldatenbank einer fiktiven Schule passende Partner vorschlägt. Ziel war es, eine interaktive Benutzeroberfläche sowie einen effizienten Algorithmus zur Partnersuche zu implementieren.

Hierfür wurde eine entsprechende Datenbank bereitgestellt, die als Grundlage diente. Zur Kommunikation zwischen der Datenbank und Java wurde eine vorgefertigte SQL-Klasse genutzt. Das UI wurde mit dem Java-Swing-Framework erstellt.

---

## 🛠️ Aufbau und Struktur
### Ordnerstruktur
Die Ordnerstruktur des Projekts ist größtenteils selbsterklärend:
- **App.java**: Die Einstiegspunkt-Datei nutzt die JPanel-Pages aus dem `Pages`-Ordner.
- **Components**: Enthält wiederverwendbare Komponenten wie ein angepasstes InputField, Buttons oder Dropdown-Menüs.
- **System**: Beinhaltet alles, was nicht direkt Teil der visuellen UI ist, z. B. die SQLite-Datenbankmanagement-Klasse oder andere Datenstrukturklassen und Enums.
- **Assets**: Beinhaltet Bilder und Schriftarten, die in der App genutzt werden.

### UI-Aufbau
Das App-Layout besteht primär aus drei Hauptseiten, die mithilfe eines CardLayouts gewechselt werden können. Diese Seiten werden bei Bedarf dynamisch erstellt und entfernt, da ein dauerhaftes Laden nicht effizient möglich wäre.

#### App Entry
Das JFrame besteht aus einem `rootPanel`, das eine angepasste Titlebar sowie das `mainPanel` enthält. Letzteres wechselt zwischen den verschiedenen Seiten.

##### Login Page
Die Login-Seite, die standardmäßig als erstes geladen wird, leitet den Benutzer entweder direkt zur Homepage weiter oder – falls der eingegebene Benutzername nicht existiert – zur Registrierungsseite.

##### Register Page
Die Registrierungsseite ist ein CardPanel, das durch verschiedene Subtiles navigiert. Jeder Subtile dient der Erfassung spezifischer Benutzerdaten. 

Die `RST_Layout.java`-Datei fungiert als Grundseite und wird von allen anderen `RST_[NAME].java`-Seiten erweitert. Sie definiert eine `valid()`-Methode, die die eingegebenen Daten überprüft und erst dann den Wechsel zur nächsten Seite erlaubt. Bei fehlerhaften Eingaben wird dies visuell angezeigt.

Nach erfolgreicher Eingabe wird der Benutzer erstellt, zur Datenbank hinzugefügt und automatisch zu seinem Home-Profil weitergeleitet.

##### Home Page
Die Home-Seite ist ein SplitPanel, bei dem links die Filteroptionen und rechts die dazu passenden Nutzer angezeigt werden. Nutzerkarten werden in der Klasse `ProfileCardFHP.java` definiert. Diese zeigen zunächst grundlegende Informationen, bieten aber durch einen „Details“-Button zusätzliche Einblicke in einem separaten Popup-Fenster.

Beide Teile der SplitView sind in ScrollPanels eingebettet, um eine dynamische Anzeige zu ermöglichen.

### Komponenten
#### Titlebar
Die Titlebar ist ein angepasstes JPanel mit On-Drag-Methoden, um das JFrame zu bewegen. Außerdem bietet sie Buttons zum Minimieren, Maximieren und Schließen des Fensters. Der Titel ist über eine öffentliche Methode in der App-Klasse anpassbar.

#### InputField
Das InputField ist ein JPanel mit einem nach innen versetzten Eingabefeld, um eine angepasste Border zu realisieren. Es unterstützt folgende Funktionen:
- Anpassbare Methoden für Schreib-, Fokus- und Submit-Ereignisse.
- Rote Umrandung bei fehlerhaften Eingaben.
- Eingabe nur von Zahlen (andere Zeichen werden automatisch entfernt).
- Option für Passwortfelder mit Sternchenanzeige.

Der aktuelle Wert kann mit `getValue()` und `setValue()` abgerufen oder gesetzt werden.

#### Dropdown Menu
Das Dropdown-Menü ist optisch angepasst und akzeptiert als Datenquellen nur Enums oder Objekte, die das `APU_Enum`-Interface implementieren. Dieses definiert eine `getName()`-Methode, um Daten einheitlich zu behandeln.

### Klassen
#### AppDesign
Die `AppDesign`-Klasse wird einmalig definiert und zentral verwendet. Sie speichert Farben, Schriftarten, Größen und Pfade. Ein Wechsel zwischen Light- und Dark-Mode ist möglich, jedoch derzeit nicht im laufenden Betrieb, sondern nur beim Initialisieren.

#### AppUser 
Die `AppUser`-Klasse repräsentiert die Datenbankeinträge der Tabelle `appusers`. Sie bietet Methoden zum Abrufen von Benutzern anhand von Benutzernamen oder UUID sowie zum Erstellen eines „Null-Benutzers“. Änderungen können mit `savetoDB()` in der Datenbank gespeichert werden, sofern die Daten gültig sind.

#### CredentialHash
Die `CredentialHash` Klasse ist für die Erstellung und Überprüfung von Benutzerpasswörtern verantwortlich. Sie verwendet den SHA256-Algorithmus der JDK zusammen mit einem zufällig generierten Salt, um Passwörter sicher zu speichern.

---

## ✨ Funktionen
- **Registrierung**: Es kann ein neuer Nutzer erstellt werden.
- **Anmeldung**: Registrierte Nutzer können sich anmelden und basierend auf ihren angegebenen Informationen nach Partnern suchern. Die bereits Projekttechnisch vorgegebenen Schlüerdaten der Datenbank wurde der Benutzername aus Kombination des `vorname` + `.` + `nachname` erstellt. Dabei entspricht das password dem username.
- **Dating**: Der nutzer kann verschiedene Filterkrieterien aktivieren/verändern um eine entsprechend zutreffende List an Datinnutzern zu bekommen.

---

## 🚧 Herausforderungen

Während der Entwicklung des Projekts traten verschiedene Herausforderungen auf:
- **GUI-Design**: Gestaltung einer benutzerfreundlichen Oberfläche mit Java Swing. Die möglichkeit das UI selber nach belieben anzupassen ist in swing schwierig und ohne weiters nicht leicht umzusetzen.
- **Datenvalidierung**: Sicherstellen, dass Benutzereingaben vollständig und korrekt sind, um typesafty in der Datenbank zu gewähren.

---

## 🌟 Mögliche Erweiterungen
- Speichern der Sortierungspräferenzen von Nutzern.
- Hinzufügen einer Löschfunktion für Benutzer.