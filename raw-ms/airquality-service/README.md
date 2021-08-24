# Anwendung
Dieser Microservice bereitet Luftqualitätsmesswerte auf.
Er wurde für die Verarbeitung von Messungen der GrovePi Airquality Sensoren implementiert.
Er kann aber auch für andere Sensoren genutzt werden.

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/airquality-ms .
docker push user/airquality-ms
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
Die für den Airquality Microservice spezifischen Konfigurationseinträge sind:
* `AIRQUALITY_QUEUE`: Name der Queue, die auf dem Message Broker erzeugt wird
* `AIRQUALITY_TOPICS`: Topics, die in die Queue umgeleitet werden über ein Binding
* `AIRQUALITY_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `AIRQUALITY_SENSOR_TYPE`: Name des Sensortyps zur Identifikation von Luftqualitätsmesswerten
* `EXPORT_AIRQUALITY_DATA_AS_METRIC`: true/false, je nachdem ob ein Export ins Monitoring erfolgen soll