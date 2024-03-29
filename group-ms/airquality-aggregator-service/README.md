# Anwendung
Dieser Microservice aggregiert Messungen des [Airquality-Service](../../raw-ms/airquality-service).
Die Sensoren können gruppiert werden. Für die Gruppen können dann Aggregatoren definiert werden. 
Diese bestimmen Minima, Maxima und Durchschnittswerte, welche als Aggregate erzeugt werden.

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/airquality-aggregator-ms .
docker push user/airquality-aggregator-ms
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
* `AIRQUALITY_AGGREGATOR_MICROSERVICE_QUEUE`: Name der Queue, die auf dem Message Broker erzeugt wird
* `AIRQUALITY_AGGREGATOR_MICROSERVICE_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `AIRQUALITY_SENSOR_TYPE`: Name des Sensortyps zur Identifikation von Luftqualitätsmesswerten (Sensormesswerte und Aggregate)
* `AIRQUALITY_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME`: Name des des Gruppentyps zur Zuordnung von Producern