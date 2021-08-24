# Anwendung
Dieser Microservice aggregiert Messungen des [Temperature-Service](../../raw-ms/temperature-service).
Es werden historisch Aggregate erzeugt. Dies erfolgt stunden-, tages-, monats- und jahresweise.
Die Aggregate stehen anschließend im Object Storage in der Verzeichnisstruktur bereit.

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/historic-temperature-ms .
docker push user/historic-temperature-ms
```
Anschließend müssen die YAML-Datei für den Microservice ausgeführt werden.
Dadurch wird das Deployment und die Service-Ressource erzeugt.
````
kubectl apply -f ./k8s/deployment.yaml
````

## Konfiguration
Die für den Microservice spezifischen Konfigurationseinträge sind:
* `HISTORIC_TEMPERATURE_AGGREGATOR_MICROSERVICE_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `TEMPERATURE_SENSOR_TYPE`: Name des Sensortyps zur Identifikation von Temperaturmesswerten (Sensormesswerte und Aggregate)