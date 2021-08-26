# Anwendung
Dieser Microservice bereitet ??? auf.
Er wurde für die Verarbeitung von Messungen der ??? Sensoren implementiert.

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/my-ms .
docker push user/my-ms
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
Die für den Temperature Microservice spezifischen Konfigurationseinträge sind:
* `MY_QUEUE`: Name der Queue, die auf dem Message Broker erzeugt wird
* `MY_TOPICS`: Topics, die in die Queue umgeleitet werden über ein Binding
* `MY_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `MY_SENSOR_TYPE`: Name des Sensortyps zur Identifikation von Messwerten
* `EXPORT_MY_DATA_AS_METRIC`: true/false, je nachdem ob ein Export ins Monitoring erfolgen soll