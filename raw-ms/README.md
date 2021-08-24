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