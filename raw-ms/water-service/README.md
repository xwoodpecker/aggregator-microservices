# Anwendung
Dieser Microservice bereitet Messungen auf, die angeben, ob der Sensor nass ist.
Er wurde für die Verarbeitung von Messungen der GrovePi Water Sensoren implementiert.
Er kann aber auch für andere Sensoren genutzt werden.

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/water-ms .
docker push user/water-ms
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
Die für den Water Microservice spezifischen Konfigurationseinträge sind:
* `WATER_QUEUE`: Name der Queue, die auf dem Message Broker erzeugt wird
* `WATER_TOPICS`: Topics, die in die Queue umgeleitet werden über ein Binding
* `WATER_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `WATER_SENSOR_TYPE`: Name des Sensortyps zur Identifikation von Wassermesswerten
* `EXPORT_WATER_DATA_AS_METRIC`: true/false, je nachdem ob ein Export ins Monitoring erfolgen soll