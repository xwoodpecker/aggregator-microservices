# Microservices zur Rohdatenverarbeitung

Für die Microservices zur Rohdatenverarbeitung ist die Implementierung folgender Klassen notwendig:

* **RawMicroserviceApplicationProperties**: Definiert die Konfiguration der rohdatenverarbeitenden Microservices. Hier müssen die Schlüssel zugewiesen werden. 
* **RawSetupDataLoader**: Legt nötige Einträge in der Datenbank bei Applikationsstart an. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **BaseReceiver**: Realisiert den Datenempfang vom Message Broker. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **ActivityMicroserviceController**: Stellt die Aktivitätsmetrik über REST bereit. Es ist kein zusätzlicher Implementierungsaufwand notwendig. 
* **RawMeasurementHandler**: Bildet die Datenverarbeitung ab. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **MeasurementFactory**: Erzeugt den Messwert. Hier muss die Bildung der Messwerte definiert werden.
* **StorageWrapper**: Realisiert die Interaktion mit der Datenhaltung. Es ist kein zusätzlicher Implementierungsaufwand notwendig. 
* **Publisher**: Ermöglicht dieWeiterverarbeitung. Es ist kein zusätzlicher Implementierungsaufwand notwendig.

# Getting started

## Maven Archetype
Durch die Installation des [Maven Archetypes](raw-ms-archetype) können alle Klassen für die Projektstruktur generiert werden.
Dazu muss im Nachgang nur folgender Befehl ausgeführt und entsprechend modifiziert werden:
````
mvn archetype:generate -DarchetypeGroupId=de.htw.saar.smartcity -DarchetypeArtifactId=raw-ms-archetype -DarchetypeVersion=0.0.1-SNAPSHOT -DgroupId=de.htw.saar.smartcity -DartifactId=new-raw-ms -Dversion=0.0.1.-SNAPSHOT -Dpackage=de.htw.saar.smartcity.newpackage
````
Zur Änderung gedacht sind die Parameter:
* `-DgroupId`: Die Gruppen-ID des Projekts
* `-DartifactId`: Die Artefakt-ID des Projekts
* `-Dversion`: Die initiale Version des Projekts
* `-Dpackage`: Das Basispaket für die Klassen
