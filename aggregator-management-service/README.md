# Lösungsstrategie
Dieses Modul stellt eine konzeptionelle Lösung dar. In Zukunft soll ein weiteres Projekt eine Stammdatenverwaltung
für Sensoren und Aktoren im Smart City Testfeld abbilden. Diese Implementierung dient nur als Hilfe zur Anlage der Metadaten,
die für die Funktionalität benötigt werden. Dazu wird eine REST-API bereitgestellt, welche durch die Integration von Swagger beziehungsweise dem OpenAPI 3.0 Standard bedient werden kann.
Ein Aufbau auf dieser Lösung für eine zukünftige Entwicklung kann ebenfalls realisiert werden. Eine Erweiterung oder die Implementierung einer grafischen Oberfläche wäre denkbar. 
Hier könnte mithilfe von Swagger Codegen einfach eine Client-Anwendung generiert werden.

# Getting started
Zuerst ist in das Verzeichnis des Microservices zu navigieren.
Dann muss das Docker Image erstellt und auf Docker Hub publiziert werden.
```
mvn clean install
docker build -t user/aggregator-management-ms .
docker push user/aggregator-management-ms
```
Anschließend müssen die YAML-Datei für den Microservice ausgeführt werden.
Dadurch wird das Deployment und die Service-Ressource erzeugt.
````
kubectl apply -f ./k8s/deployment.yaml
````
