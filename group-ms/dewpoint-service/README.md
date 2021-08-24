# Anwendung
Dieser Microservice aggregiert Messungen des [Temperature-Service](../../raw-ms/temperature-service) sowie 
des [Humidity-Service](../../raw-ms/humidity-service).
Die Producer können gruppiert werden. Für die Gruppen kann dann ein Aggregator definiert werden. 
Dieser bestimmen einen Taupunktwert, welche als Aggregate erzeugt werden.
Der Taupunkt wird wie folgt berechnet:
````
Double alpha = Math.log(humidityValue / 100) + A * temperatureValue / (B + temperatureValue);
Double dewpointValue = B * alpha / (A - alpha);
return Math.round(dewpointValue * 100) / 100.0;
````

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/dewpoint-ms .
docker push user/dewpoint-ms
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
* `DEWPOINT_MICROSERVICE_QUEUE`: Name der Queue, die auf dem Message Broker erzeugt wird
* `DEWPOINT_MICROSERVICE_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `DEWPOINT_DATA_TYPE`: Name des Datentyps zur Identifikation von Taupunktmesswerten
* `TEMPERATURE_SENSOR_TYPE`: Name des Sensortyps zur Identifiaktion von Temperaturmesswerten
* `HUMIDITY_SENSOR_TYPE`: Name des Sensortyps zur Identifiaktion von Luftfeuchtigkeitsmesswerten
* `DEWPOINT_MICROSERVICE_GROUP_TYPE_NAME`: Name des Gruppentyps zur Zuordnung von Producern