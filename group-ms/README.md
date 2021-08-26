# Microservices zur Aggregatbildung

Für die Microservices zur Aggregation in Echtzeit ist die Implementierung folgender Klassen notwendig:

* **GroupMicroserviceApplicationProperties**: Definiert die Konfiguration der weiterverarbeitenden Microservices. Hier müssen die Schlüssel zugewiesen werden.
* **GroupSetupDataLoader**: Legt nötige Einträge in der Datenbank bei Applikationsstart an. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **GroupReceiver**: Realisiert den Datenempfang vom Message Broker. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **ActivityMicroserviceController**: Stellt die Aktivitätsmetrik über REST bereit. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **GroupMeasurementHandler**: Bildet die Datenverarbeitung ab. Hier muss addCombinators implementiert werden. Dies ermöglicht das Hinzufügen von Klassen des Typs
CombinatorModel, die zur Aggregatbildung verwendet werden können. StorageWrapper: Realisiert die Interaktion mit der Datenhaltung. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **Publisher**: Ermöglicht dieWeiterverarbeitung. Es ist kein zusätzlicher Implementierungsaufwand notwendig.

# Getting started

## Maven Archetype
Durch die Installation des [Maven Archetypes](group-ms-archetype) können alle Klassen für die Projektstruktur generiert werden.
Dazu muss im Nachgang nur folgender Befehl ausgeführt und entsprechend modifiziert werden:
````
mvn archetype:generate -DarchetypeGroupId=de.htw.saar.smartcity -DarchetypeArtifactId=group-ms-archetype -DarchetypeVersion=0.0.1-SNAPSHOT -DgroupId=de.htw.saar.smartcity -DartifactId=new-group-ms -Dversion=0.0.1.-SNAPSHOT -Dpackage=de.htw.saar.smartcity.newpackage.aggregator
````
Zur Änderung gedacht, sind die Parameter:
* `-DgroupId`: Die Gruppen-ID des Projekts
* `-DartifactId`: Die Artefakt-ID des Projekts
* `-Dversion`: Die initiale Version des Projekts
* `-Dpackage`: Das Basispaket für die Klassen