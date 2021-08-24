# Lösungsstrategie
Das Benchmarking wird mit dem JMH-Framework durchgeführt.
Es werden zuerst Warmup-Iterationen durchgeführt, welche nicht in die Messung miteinbezogen werden.
Dadurch kann das System erstmals anlaufen und das Benchmarking wird somit realistischer. 
Dann werden Mess-Iterationen durchgeführt. Es wird eine durchschnittliche Ausführungszeit ermittelt.
Jede Mess-Iteration wird mit Nachrichten unterschiedlicher Dateigröße durchgeführt.
Dadurch kann ermittelt werden, was die Grundlast zur Datenverarbeitung ist und
wie die Verarbeitungszeit mit ansteigender Größe wächst.

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
Möchte man das Benchmarking des Microservice durchführen, so kann dies durch dies mit den folgenden Befehlen durchgeführt werden.
Hierbei ist zu beachten, dass das Benchmarking im Produktivsystem ausgeführt werden sollte, also im DSL-Cluster.
````
mvn clean install
docker build user/benchmarking-ms .
docker push user/benchmarking-ms
kubectl apply -f ./k8s/volumes-DSL.yaml
kubectl apply -f ./k8s/access.yaml
kubectl apply -f ./k8s/pod-DSL.yaml
````
Ist das Benchmarking abgeschlossen und der Pod im Status *Completed*, so kann über folgende Befehle die Ergebnisdatei ins lokale System kopiert werden.
````
kubectl cp default/access-result-storage:out/result.json ./my-result.json
````

## Konfiguration
Die für den Benchmarking Microservice spezifischen Konfigurationseinträge sind:
* `BENCHMARKING_QUEUE`: Name der Queue, die auf dem Message Broker erzeugt wird
* `BENCHMARKING_TOPICS`: Topics, die in die Queue umgeleitet werden über ein Binding
* `BENCHMARKING_BUCKET`: Bucket im Object Storage, in dem die Daten persistiert werden sollen (optimal wäre es hier einen separaten Bucket auszuwählen)
* `BENCHMARKING_SENSOR_TYPE`: Name des Sensortyps zur Identifikation von Wassermesswerten
* `EXPORT_BENCHMARKING_DATA_AS_METRIC`: false, dient lediglich zum Benchmarking
