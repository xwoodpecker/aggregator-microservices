# Anwendung
Dieser Microservice aggregiert Messungen des [Temperature-Service](../../raw-ms/temperature-service) sowie 
des [Dewpoint-Service](../../group-ms/dewpoint-service).
Die Producer können gruppiert werden. Für die Gruppen kann dann ein Aggregator definiert werden. 
Dieser bestimmen Wärmefluss- und Kondensationswerte, welche als Aggregate erzeugt werden.
In einer Gruppe müssen jeweils zwei Temperaturwerte und ein Taupunktwert vorliegen.
Die Temperaturwerte müssen mit einem Tag als Innen- und Außentemperatur gekennzeichnet werden,
damit eine Unterschiedung erfolgen kann.
Für die Gruppe kann mittels den FormulaItems beziehungsweise den FormulaItemValues eine Zuweisung von 
Isolationswerten erfolgen. Diese werden zur Berechnung verwendet.

Der Wärmeflusswert wird wie folgt berechnet:
````
Double heatflux = (dewPointValue - outsideTemperatureValue) * u
````
Im Nachfolgenden die Kalkulation des Kondensationswertes:
````
Double shutter = heatflux / (insideTemperatureValue - dewPointValue);
````

# Getting Started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/heatflux-ms .
docker push user/heatflux-ms
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
* `HEATFLUX_MICROSERVICE_QUEUE`: Name der Queue, die auf dem Message Broker erzeugt wird
* `HEATFLUX_MICROSERVICE_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen
* `HEATFLUX_MICROSERVICE_DATA_TYPE_NAME`: Name des Datentyps zur Identifikation von Wärmeflussmesswerten
* `HEATFLUX_MICROSERVICE_SHUTTER_DATA_TYPE_NAME`: Name des Datentyps zur Identifikation von Kondensationsmesswerten
* `TEMPERATURE_SENSOR_TYPE`: Name des Sensortyps zur Identifiaktion von Temperaturmesswerten
* `DEWPOINT_DATA_TYPE`: Name des Datentyps zur Identifiaktion von Taupunktmesswerten
* `HEATFLUX_MICROSERVICE_GROUP_TYPE_NAME`: Name des Gruppentyps zur Zuordnung von Producern
* `TAG_NAME_INSIDE_TEMPERATURE`: Der Name des Tags zur Kennzeichnung von Innentemperaturen
* `TAG_NAME_OUTSIDE_TEMPERATURE`: Der Name des Tags zur Kennzeichnung von Außentemperaturen
* `FORMULA_ITEM_NAME_U_VALUE`: Der Name des FormulaItems, das den U-Wert bestimmt
* `FORMULA_ITEM_NAME_U_VALUE_DEFAULTS`: Standardwerte für U-Werte, die angelegt werden bei Applikationsstart
    * 5.0 - Single-glazing
    * 3.0 - Double-glazing
    * 2.2 - Triple-glazing
    * 1.7 - Double-glazing with low-e coating
    * 1.3 - Double-glazing with low-e coating and Argon filled
    * 0.8 - Passivhaus requirement
    * 0.4 - Triple-glazing with multiple low-e coatings and Xenon filled
* `FORMULA_ITEM_NAME_U_VALUE_DEFAULT`: Der U-Wert, der bei fehlender Hinterlegung zur Berechnung verwendet wird