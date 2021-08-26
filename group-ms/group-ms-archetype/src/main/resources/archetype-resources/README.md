# Anwendung
Dieser Microservice aggregiert Messungen des ???.
Die Sensoren können gruppiert werden. Für die Gruppen können dann Aggregatoren definiert werden. 
Diese bestimmen ???, welche als Aggregate erzeugt werden.

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/my-aggregator-ms .
docker push user/my-aggregator-ms
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
* `MY_AGGREGATOR_MICROSERVICE_QUEUE`: Name der Queue, die auf dem Message Broker erzeugt wird
* `MY_AGGREGATOR_MICROSERVICE_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `MY_SENSOR_TYPE`: Name des Sensortyps zur Identifikation von Messwerten (Sensormesswerte und Aggregate)
* `MY_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME`: Name des des Gruppentyps zur Zuordnung von Producern