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