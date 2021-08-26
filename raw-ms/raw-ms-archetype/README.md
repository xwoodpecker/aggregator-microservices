# Anwendung
Dieses Modul stellt die Definition eines Maven Archetypes dar.
Hierbei handelt es such um ein Template, welches zur Erstellung von Microservices zur Verarbeitung von Rohdaten dienen soll.
Die Initialisierung eines neuen Moduls basierend auf dem Archetyp führt zur Generierung der Projektstruktur.
Dabei werden diverse Klassen erstellt, die nicht explizit zu implementieren sind. Codefragmente sowie Konfigurationen,
welche eine Änderung benötigten, sind durch entsprechende `todo` Kommentare gekennzeichnet.

# Getting Started
Um den Archetype zu nutzen, muss lediglich die Installation im lokalen Repository erfolgen.
Dazu ist folgender Befehl aufzurufen.
```
mvn clean install
```