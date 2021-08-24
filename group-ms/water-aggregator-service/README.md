# Anwendung
Dieser Microservice aggregiert Messungen des [Water-Service](../../raw-ms/water-service).
Die Sensoren können gruppiert werden. Für die Gruppen kann dann ein Aggregator definiert werden, der eine Summe bildet,
wie viele Sensoren der Gruppe Nässe melden. Der Aggregatmesswert stellt den Wert dieser Summe dar.

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/water-aggregator-ms .
docker push user/water-aggregator-ms
```
Anschließend müssen die YAML-Datei für den Microservice ausgeführt werden.
Dadurch wird das Deployment und die Service-Ressource erzeugt.
````
kubectl apply -f ./k8s/deployment.yaml
````
Möchte man eine automatische Skalierung des Microservices aktivieren so muss ebenfalls der Horizontal Pod Autoscaler bereitgestellt werden
````
kubectl apply -f ./k8s/hpa_activity.yaml
````

## Konfiguration
Die für den Microservice spezifischen Konfigurationseinträge sind:
* `WATER_AGGREGATOR_MICROSERVICE_QUEUE`: Name der Queue, die auf dem Message Broker erzeugt wird
* `WATER_AGGREGATOR_MICROSERVICE_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `WATER_SENSOR_TYPE`: Name des Sensortyps zur Identifikation von Wassermesswerten, die aggregiert werden sollen 
* `WATER_AGGREGATOR_MICROSERVICE_DATA_TYPE_NAME`: Name des Datentyps des gebildeten Aggregats
* `WATER_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME`: Name des des Gruppentyps zur Zuordnung von Producern