# Microservices zur Aggregatbildung

Für die Microservices zur Aggregation in Echtzeit ist die Implementierung folgender Klassen notwendig:

* **GroupMicroserviceApplicationProperties**: Definiert die Konfiguration der weiterverarbeitenden Microservices. Hier müssen die Schlüssel zugewiesen werden.
* **GroupSetupDataLoader**: Legt nötige Einträge in der Datenbank bei Applikationsstart an. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **GroupReceiver**: Realisiert den Datenempfang vom Message Broker. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **ActivityMicroserviceController**: Stellt die Aktivitätsmetrik über REST bereit. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **GroupMeasurementHandler**: Bildet die Datenverarbeitung ab. Hier muss addCombinators implementiert werden. Dies ermöglicht das Hinzufügen von Klassen des Typs
CombinatorModel, die zur Aggregatbildung verwendet werden können. StorageWrapper: Realisiert die Interaktion mit der Datenhaltung. Es ist kein zusätzlicher Implementierungsaufwand notwendig.
* **Publisher**: Ermöglicht dieWeiterverarbeitung. Es ist kein zusätzlicher Implementierungsaufwand notwendig.