# Anwendung
Dieser Microservice bereitet Bilddaten auf.
Er wurde für die Verarbeitung von Bildern, welche als Text über MQTT publiziert werden, implementiert.
Er kann aber auch für andere Sensoren genutzt werden.

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/picture-ms .
docker push user/picture-ms
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
Die für den Picture Microservice spezifischen Konfigurationseinträge sind:
* `PICTURE_QUEUE`: Name der Queue, die auf dem Message Broker erzeugt wird
* `PICTURE_TOPICS`: Topics, die in die Queue umgeleitet werden über ein Binding
* `PICTURE_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `PICTURE_SENSOR_TYPE`: Name des Sensortyps zur Identifikation von Messungen des Bildtyps
* `EXPORT_PICTURE_DATA_AS_METRIC`: false, Bilddaten können nicht exportiert werden