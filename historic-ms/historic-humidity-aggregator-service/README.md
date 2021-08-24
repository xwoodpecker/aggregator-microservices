# Anwendung
Dieser Microservice aggregiert Messungen des [Humidity-Service](../../raw-ms/humidity-service).
Es werden historische Aggregate erzeugt. Dies erfolgt stunden-, tages-, monats- und jahresweise.
Die Aggregate stehen anschließend im Object Storage in der Verzeichnisstruktur bereit.

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/historic-humidity-ms .
docker push user/historic-humidity-ms
```
Anschließend müssen die YAML-Datei für den Microservice ausgeführt werden.
Dadurch wird das Deployment und die Service-Ressource erzeugt.
````
kubectl apply -f ./k8s/deployment.yaml
````

## Konfiguration
Die für den Microservice spezifischen Konfigurationseinträge sind:
* `HISTORIC_HUMIDITY_AGGREGATOR_MICROSERVICE_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `HUMIDITY_SENSOR_TYPE`: Name des Sensortyps zur Identifikation von Luftfeuchtigkeitsmesswerten (Sensormesswerte und Aggregate)