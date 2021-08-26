# Microservices zur historischen Aggregation

Für die Microservices zur historischen Aggregation ist die Implementierung folgender Klassen notwendig:

* **HistoricAggregatorApplicationProperties**: Hier wird die Konfiguration der Microservices
  zur historischen Aggregation festgelegt. Hier müssen die Schlüssel zugewiesen
  werden. 
* **HistoricAggregatorScheduler**: Führt die Aggregation aus. Hier muss addHistoricCombinators
  implementiert werden. Dies ermöglicht das Hinzufügen von Klassen des
  Typs HistoricCombinatorModel, die zur Aggregatbildung verwendet werden können.
* **HistoricStorageWrapper**: Realisiert die Interaktion mit der Datenhaltung. Für die Historisierung
  ist ein kleinerer Funktionsumfang ausreichend. Es ist kein zusätzlicher
  Implementierungsaufwand nötig.

# Getting started

## Maven Archetype
Durch die Installation des [Maven Archetypes](historic-ms-archetype) können alle Klassen für die Projektstruktur generiert werden.
Dazu muss im Nachgang nur folgender Befehl ausgeführt und entsprechend modifiziert werden:
````
mvn archetype:generate -DarchetypeGroupId=de.htw.saar.smartcity -DarchetypeArtifactId=historic-ms-archetype -DarchetypeVersion=0.0.1-SNAPSHOT -DgroupId=de.htw.saar.smartcity -DartifactId=new-historic-ms -Dversion=0.0.1.-SNAPSHOT -Dpackage=de.htw.saar.smartcity.historic.newpackage
````
Zur Änderung gedacht, sind die Parameter:
* `-DgroupId`: Die Gruppen-ID des Projekts
* `-DartifactId`: Die Artefakt-ID des Projekts
* `-Dversion`: Die initiale Version des Projekts
* `-Dpackage`: Das Basispaket für die Klassen
