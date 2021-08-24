# Lösungsstrategie
Die Lösung stellt ein Proof-Of-Concept dar. Das Modul dient der Simulation von Sensoren.
Dazu erzeugt es virtuelle Sensoren, die analog zu physischen Sensoren Messwerte erheben und publizieren.
Hierbei können Sensoren zufällige Werte erzeugen. Es kann aber auch auf echte Wetterdaten zurückgegriffen werden.
Zur Abfrage dieser Daten wird auf die API von Open Weather zugegriffen. Diese kann 60 mal die Minute kostenfrei abgerufen werden. 
Darauf ist bei der Konfiguration zu achten. Das Simulationsmodul kann genutzt werden, um fehlende Sensoren zu ersetzen.
Ebenso kann es für das Testen des Systems unter großer Last eingesetzt werden.

# Getting started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/virtualization-ms .
docker push user/virtualization-ms
```
Anschließend müssen die YAML-Datei für den Microservice ausgeführt werden.
Dadurch wird das Deployment erzeugt.
````
kubectl apply -f ./k8s/deployment.yaml
````

## Konfiguration
Die wichtigsten Konfigurationseigenschaften sind:
* `OPEN_WEATHER_API_KEY`: Key der Open Weather API, entnommen von der [Homepage](https://openweathermap.org/appid)
* `OPEN_WEATHER_API_CITIES`: Städteliste, die Anzahl sollte mit dem Intervall so abgestimmt werden, dass nicht mehr als 60 Anfragen pro Minute gestellt werden.
  Pro Stadt erfolgt eine Anfrage. Aus dieser werden dann die Temperatur-, Luftfeuchtigkeits- und Wasserinformationen extrahiert.
  Es folgen exemplarisch einige Städte mit zugehöriger ID:
    * Saarbruecken - 2842647
    * Moscow - 524901
    * Antarctica - 6255152
    * Rome - 3169070
    * NYC - 5128581
    * Tokyo - 1850147
    * Homburg - 2899449
    * Merzig - 2871675
    * Neunkirchen - 2864435
    * Saarlouis - 2842632
    * St.Wendel - 2841463
    * Stuttgart - 2825297
    * Muenchen - 2867714
    * Berlin - 2950159
    * Potsdam - 2852458
    * Bremen - 2944388
    * Hamburg - 2911298
    * Wiesbaden - 2809346
    * Schwerin - 2834282
    * Hannover - 2910831
    * Duesseldorf - 2934246
    * Mainz - 2874225
    * Dresden - 2935022
    * Magdeburg - 2874545
    * Kiel - 2891122
    * Erfurt - 2929670
* `TEMPERATURE_AGENT_COUNT`: Anzahl an TemperatureAgents, die zufällige Daten generieren und senden
* `HUMIDITY_AGENT_COUNT`: Anzahl an Humidity, die zufällige Daten generieren und senden
* `PICTURE_AGENT_COUNT`: Anzahl an PictureAgents, die ein Bild senden
* `WATER_AGENT_COUNT`: Anzahl an WaterAgents, die zufällige Daten generieren und senden
* `AIRQUALITY_AGENT_COUNT`: Anzahl an AirqualityAgents, die zufällige Daten generieren und senden
* `SIMULATION_INTERVAL`: Intervall in Sekunden in der Daten an den Message Broker versendet werden
