# Lösungsstrategie
Die Lösung stellt ein Proof-Of-Concept dar. Zur Umsetzung stand ein Raspberry Pi 4 Computer mit 4GB RAM zur Verfügung.
Mit dem GrovePi+ Add-on Board konnten verschiedene Sensoren angeschlossen werden.
Es lagen die Sensortypen Temperature&Humidity, Airquality sowie Water vor.
Die Sensoranwendungen lesen über die digitalen beziehungsweise analogen Schnittstellen die Sensormesswerte aus.
Anschließend werden die Messungen je nach Konfiguration über MQTT an den Message Broker gesandt.

# Getting started
Die JAR-Datei kann direkt auf dem PI gebaut werden. Durch die nachfolgenden Kommandos kann die JAR auf dem lokalen System gebaut werden
und anschließend durch Verbindung mit dem PI rüberkopiert werden.
````
mvn clean install
scp ./target/sensorDemo-1.0-SNAPSHOT.jar pi@192.168.178.41:/home/pi/javaSensorDemo/sensorDemo-1.0-SNAPSHOT.jar
````
Die JAR kann dann direkt ausgeführt werden mit:
````
java -jar sensorDemo-1.0-SNAPSHOT.jar  >./log.txt 2>&1 &
````
Oder über ein eigenes oder vordefiniertes Shell-Skript beim Systemstart ausgeführt werden. 

## Konfiguration
Die wichtigsten Konfigurationseigenschaften sind:
* `TEMPERATURE_SENSOR_NAME`: Name des Topics des Temperatursensors
* `HUMIDITY_SENSOR_NAME`: Name des Topics des Luftfeuchtigkeitssensors
* `AIRQUALITY_SENSOR_NAME`: Name des Topics des Luftqualitätssensors
* `WATER_SENSOR_NAME`: Name des Topics des Wassersensors
* `TEMPERATURE_AND_HUMIDITY_SENSOR_PIN`: Anschluss-PIN des Temperatur- und Luffeuchtigkeitssensors
* `AIRQUALITY_SENSOR_PIN`: Anschluss-PIN des Luftqualitätssensors
* `WATER_SENSOR_PIN`: Anschluss-PIN des Wassersensors
* `INTERVAL`: Intervall der Abfrage und des Publish

