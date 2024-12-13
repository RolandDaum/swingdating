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
### Ordner- und Dateistruktur
Die Ordnerstruktur des Projekts ist größtenteils selbsterklärend:
- **App.java**: Die Einstiegspunkt-Datei nutzt die JPanel-Pages aus dem `Pages`-Ordner, welche sie ein einem CardLayout hin und her wechselt.
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
Das InputField ist ein JPanel mit einem nach innen versetzten Eingabefeld, um eine angepasste Border zu bekommen. Es unterstützt folgende Funktionen:
- Anpassbare Methoden für Schreib-, Fokus- und Submit-Ereignisse.
- Rote Umrandung bei fehlerhaften Eingaben.
- Eingabe nur von Zahlen (andere Zeichen werden automatisch entfernt).
- Option für Passwortfelder mit Sternchenanzeige.

Der aktuelle Wert kann mit `getValue()` und `setValue(String)` abgerufen oder gesetzt werden.
##### Event methoden
- `onType(Consumer<String>)`
- `onActive(Runnable)`
- `onInactive(Runnable)`
- `onSubmit(Consumer<String>)`

Die Eventmethoden könnten mit gesetzt werden. Die gesetzten Methoden werden dann bei entsprechender Eventauslösung ausgeführt.

#### Dropdown Menu
Das Dropdown-Menü ist optisch angepasst und akzeptiert als Datenquellen nur Enums oder Objekte, die das `APU_Enum` - Interface implementieren. Dieses definiert eine `getName()` - Methode, welche genutzt wird, um den Anzeigewerde des implementierten Objektes zu bekommen (Die Werte, die im Nachhinein ausgewählt werden können).

### Klassen
#### AppDesign
Die `AppDesign`-Klasse wird einmalig definiert und zentral verwendet. Sie speichert Farben, Schriftarten, Größen und Pfade. Ein Wechsel zwischen Light- und Dark-Mode ist möglich, jedoch derzeit nicht im laufenden Betrieb, sondern nur beim Initialisieren. Warum der Hotswapable Designmodus nicht funktioniet ist und bleibt für mich noch ein Rätsel. Um das möglich zu machen, müssen alle UI Elemente auf das gleiche AppDesign Objekt zurückgreifen, was eigentlich schon der fall ist. Stand jetzt ist es nur noch eine Frage, wie man alle Elemente auf effizienter Art repainten kann, wobei diese dann idealerweise die veränderten Farbwerte des globalen AppDesign Objektes annehmen.

#### AppUser 
Die `AppUser` - Klasse repräsentiert die Datenbankeinträge der Tabelle `appusers`. Sie bietet Methoden zum Abrufen von Benutzern anhand von Benutzernamen oder UUID aus der Datenbank. Änderungen können mit `savetoDB()` in der Datenbank gespeichert werden, sofern die Daten gültig sind.

#### CredentialHash
Die `CredentialHash` Klasse ist für die Erstellung und Überprüfung von Benutzerpasswörtern verantwortlich. Sie verwendet den SHA256-Algorithmus des JDKs zusammen mit einem zufällig generierten Salt, um Passwörter sicher zu speichern.

---

### Dating Algorithmus
Der Hauptdating Algorithmus lässt sich als Methode Namens `getMatches()` in der Page Klasse `PageHome` auffinden.
Dabei wird ein SQL Abfrage String zusammengestellt, welcher je nach ausgwählten Filter Parameter immer wieder eine zusätzliche `WHERE ... AND ... AND ...` Bedingung hinzufügt.
Die erst gefilterte SQL Abfrage gibt nur die zutreffenden UUIDs aus, welche dann in einem zweiten Schritt über die AppUser Klasse als AppUser Objekt geladen und angezeigt werden.
Einige Nutzerdaten wurden nicht als Filter Parameter verwendet, da die sowieso bereits kleine Datenbank bei noch mehr Parametern nur noch weniger Matches liefert und einige Filterparameter aus persönlicher Sicht einfach nicht Teil der Suche sein sollten.
#### Filter Parameter
- Maximale Altersdifferenz
- Sexualität (Basierend auf dem Geschlecht)
- Religion
- Nationalität
- Nähere Umgebung (Gleiche PLZ)
- Lieblingsfach
- Lieblingsmusik
- ~~Körpergröße~~
- ~~Gewicht~~
- ~~Haarfarbe~~
- ~~Augenfarbe~~

## ✨ Funktionen
- **Registrierung**: Es kann ein neuer Nutzer erstellt werden, wobei entsprechende Profildaten angegeben werden müssen.
- **Anmeldung**: Registrierte Nutzer können sich anmelden und basierend auf ihren angegebenen Informationen nach Partnern suchern. Die bereits Projekttechnisch vorgegebenen Schlüerdaten der Datenbank wurde der Benutzername aus Kombination des `vorname` + `.` + `nachname` erstellt. Dabei entspricht das password dem username.
- **Dating**: Der Nutzer kann verschiedene Filterkrieterien aktivieren/verändern um eine entsprechend zutreffende List an Datinnutzern zu bekommen.

---

## 🚧 Herausforderungen

Während der Entwicklung des Projekts traten verschiedene Herausforderungen auf:
- **GUI-Design**: Gestaltung einer benutzerfreundlichen Oberfläche mit Java Swing. Die möglichkeit das UI selber nach belieben anzupassen ist in swing schwierig und nicht leicht umzusetzen.
- **Datenvalidierung**: Sicherstellen, dass Benutzereingaben vollständig und korrekt sind, um eine Datenkonsitens in der Datenbank zu gewähren.
- **Datenkonvertierung**: Ein großteil der gegebenen Daten wurden manuell angepasst, vereinheitlicht, umgeändert und in einer neuen Datenbank und Tabelle gespeichert. Dies war notwendig, um die AppUser Klassen Enum Werte passend und sicher benutzen zu können. Einige Sachen davon, lassen sich noch in der DBRebuild.java Datei wiederfinden.

---

## 🌟 Mögliche Erweiterungen
- Speichern der Sortierungspräferenzen von Nutzern.
- Hinzufügen einer Löschfunktion für Benutzer.
- Abbrechmöglichkeit des Registrierungs- / Benutzereditierungsvorganges