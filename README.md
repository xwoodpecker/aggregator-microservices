# Szenario
Die htw saar beschäftigt sich mit der Erforschung und dem Einsatz von IoT-Technologien.
In diesem Rahmen wurde das Smart City Testfeld ins Leben gerufen, welches dazu
dient ein städtisches Umfeld zu simulieren. Wesentlicher Bestandteil einer Smart-City-
Infrastruktur stellen Sensoren dar, die verschiedenste Daten messen. Diese Daten sollen
ausgewertet und aggregiert werden, um zusätzliches Wissen zu gewinnen. Das Projekt
befasst sich mit der Erstellung eines erweiterbaren Service, der Rohdaten speichert, Aggregate
bildet und die gewonnen Informationen zur Anzeige bereitstellt.

# Anforderungen und Ziele

![](_markdown-images/architecture.png)

Die Model City ist mit Sensoren
ausgestattet, welche Messwerte liefern. Dazu werden GrovePi Sensoren mit einem
Raspberry Pi verbunden. Hier soll eine Anwendung entwickelt werden, welche die gemessenen
Daten über MQTT (Message Queuing Telemetry Transport) bereitstellt. Die
erstmals einzusetzenden Sensortypen sind Temperatur- und Luftfeuchtigkeits-, Wasser- und
Luftqualitätssensoren. Eine Kernanforderung ist es beliebige Sensortypen zu unterstützen und
möglichst unkompliziert anbinden zu können. Die Implementierung von
virtuellen Sensoren kann genutzt werden, um die Erweiterbarkeit zu testen.

Das Backend soll in einem Kubernetes Cluster bereitgestellt werden. Eine zentrale
Komponente des Backends ist der Message Broker. Hier wird ein RabbitMQ Server eingesetzt.
Dieser empfängt und verteilt die Sensordaten. Dazu verwendet er ein Publish-
Subscribe-Muster. Intern werden die ankommenden Nachrichten in Queues gebunden
und über AMQP (Advanced Message Queuing Protocol) bereitgestellt. Dadurch wird
eine Mehrfachverarbeitung von Nachrichten verhindert. Ziel ist die Entwicklung einer
Architektur zur Verarbeitung von Sensordaten. Es sollen sowohl Rohdaten, als auch in
Echtzeit aggregierte Werte gespeichert werden. Man soll die Möglichkeit haben Sensoren
in Gruppen zusammenzufassen. Damit wird es zum Beispiel möglich eine Durchschnittstemperatur
über mehrere Sensoren zu ermitteln. Ebenso soll es möglich sein,
verschiedene Sensortypen zu gruppieren und gegebenenfalls auch einen neuen Messwert
zu berechnen. Als Anwendungsbeispiel könnte man sich verschiedene Sensoren vorstellen,
die diverse Schadstoffe in der Luft messen. Nun sollen diese Werte in einem einzigen
Luftqualitätsindex zusammengefasst werden. Des Weiteren soll eine Routine ablaufen,
die für längere Zeiträume Aggregate berechnet, wie beispielsweise am Tages-, Wochen- oder
Monatsende. Alle gemessenen und berechneten Daten sollen über eine REST API
zur Verfügung gestellt werden.

Eine zentrale Anforderung ist das möglichst flexible Hinzufügen neuer Sensoren. Dazu
soll eine Microservices-Architektur verwendet werden. Es wird eine Code-Basis bereitgestellt,
welche lediglich Funktionalität in Form von Schnittstellen bietet. Die Verarbeitung
der Sensordaten erfolgt in separaten Microservices, welche auf dieser aufbauen.
Innerhalb eines Microservices muss die Verarbeitung der Daten definiert werden, welche
Aggregate zu bilden sind und wie die Daten über REST abgerufen werden können. Um
eine Weiterverarbeitung erhobener Messwerte zu ermöglichen, werden Referenzen auf die
diese wiederum auf dem Message Broker publiziert. Andere Microservices können diese
Referenzen dann empfangen und daraus weitere Aggregate bilden. Ein Microservice
zur Verwaltung der Stammdaten, wie Sensorinformationen und Gruppen muss ebenso
implementiert werden.

Eine Anbindung verschiedenster Frontend-Lösungen wird durch eine Datenbereitstellung
mittels REST realisiert. Im Zuge des Projekts soll eine Visualisierung, bzw. ein
Monitoring mittels Prometheus und Grafana realisiert werden. Hiermit kann man ein
Dashboard gestalten, um die erfassten Daten über den Browser auszulesen.


## User Stories und Use Cases

### Funktionale Anforderungen

#### Must-Have
* **Flexible Anbindung von neuen Sensortypen:** Die Anbindung muss unabhängig vom
  Aufbau der Sensordaten sein. Für jeden Sensor müssen die Rohdaten gesammelt
  und gespeichert werden.
* **Stamm- und Metadaten verwalten:** Es muss zudem möglich sein, Metadaten zu Sensoren
  einzupflegen. Eine Gruppierung von Sensoren zwecks Berechnung von Aggregaten
  muss ebenso realisiert werden. Hierbei ist die Verwaltung diverser Stammdaten
  nötig.
* **Echtzeitdatenverarbeitung:** Einige Aggregate sollen in Echtzeit berechnet werden. Dazu
  zählen Gruppierungen, also Zusammenschlüsse von Sensoren gleichen Typs.
  Hier ist es angedacht, eine Durchschnittsbildung sowie eine Findung von Minima
  und Maxima durchzuführen. Es ist zudem die Möglichkeit offenzuhalten, andere
  Berechnungen auszuführen.
* **Historische Daten verarbeiten:** Historische Daten sollen ebenfalls verarbeitet werden.
  Dazu soll eine separate Routine geschaffen werden, die beispielsweise am Tages-,
  Monats- oder Jahresende alle Daten analysiert und neue zusammengefassteWerte
  berechnet.
* **Sensoranbindung:** Es sollen die vorhandenen GrovePi-Sensoren angebunden werden. Dabei
  handelt es sich um die Modelle Temperature&Humidity, Water und Airquality. Zur
  Anbindung soll der bereitgestellte Raspberry Pi verwendet werden. Dieser kann mit
  den Sensoren verknüpft werden und so Daten auslesen. Aufseiten des Raspberry Pi
  sind somit Anwendungen zu realisieren, die die Sensoren abfragen und die Messwerte
  über MQTT an den Message Broker senden. Für alle vorhandenen Sensoren
  sind aufbauend auf der erweiterbaren Architektur und der Basismodule Microservices
  zu erstellen, die diese Sensordaten verarbeiten.
* **Bereitstellung der Daten:** Die Rohdaten und verarbeiteten Daten müssen bereitgestellt
  werden, damit sie in einem Dashboard visualisiert werden können.
#### Should-Have
* **Echtzeitdatenverarbeitung:** Es sollen Gruppen verschiedener Sensortypen gebildet werden 
  können. Ein Beispiel wäre hier eine Gruppierung von Temperatur- und Luftfeuchtigkeitsdaten
  zu Tauwerten. Dazu muss immer jeweils ein Temperatur- und
  Luftfeuchtigkeitssensor zu einer Gruppe zusammengefasst werden. Liegen zu beiden
  Sensoren Messwerte vor, so kann ein Aggregat gebildet werden. Es ist eine
  Code-Basis zu implementieren, welche das Erstellen neuer Microservices dieser Art
  möglichst einfach und flexibel gestaltet.
#### Nice-To-Have
* **Komplexe Aggregate:** Eine Abbildung komplexer Aggregate mit mehreren Sensortypen
  gleicher und unterschiedlicher Art sowie einer Verrechnung in Formeln könnte realisiert
werden.
* **Monitoring:** Ein Monitoring der einzelnen Microservices sowie die Benachrichtigung im
  Fehlerfall, bei falschen Messwerten, ausfallenden Sensoren oder Ähnlichem wäre
  eine sinnvolle Erweiterung.
  
### Nichtfunktionale Anforderungen

#### Must-Have
* **Flexibel neue Funktionalität anbinden:** Neue Implementierungen sollen mit geringem
  Aufwand realisierbar sein. Neue Funktionalität soll in die bestehende Architektur
  flexibel integriert werden können.
* **Bereitstellung mit Kubernetes:** Die Anwendungen sind für das DSL-Cluster der Hochschule
  für Technik und Wirtschaft des Saarlandes bereitzustellen.
* **Zustandslosigkeit:** Die Anwendungen sind zustandslos zu implementieren, damit eine
  horizontale Skalierung möglich ist.
* **Ausgelagerte Konfiguration** Die Konfiguration muss ausgelagert, beispielsweise über
  Umgebungsvariablen erfolgen. Dadurch wird ermöglicht, dass die Anwendungen
  über Kubernetes konfigurierbar sind. Die Konfiguration wird zentralisiert durch
  Kubernetes verwaltet. Ändern sich Adressen oder Ports von abhängigen Anwendungen,
  so kann der Aufwand einer Anpassung gering gehalten werden.
* **Dashboard:** Das Dashboard soll nach Möglichkeit mittels Prometheus und Grafana erstellt
  werden. Dazu müssen Metriken über eine REST-API bereitgestellt werden.
  
#### Should-Have
* **Auto-Scaling:** Es könnten je nach Bedarf mehr Anwendungen gestartet werden. Dieser
  Vorgang kann automatisiert erfolgen. Es wird die Auslastung beobachtet. Je nach
  Aktivitätsgrad wird die Anzahl an Programminstanzen erhöht oder reduziert.
  
# Lösungsstrategie
Zur Lösung wurde ein multi-modulares Projekt erstellt. Zur Implementierung wurde Java 14 eingesetzt.
Das Projekt beinhaltet eine erweiterbare [Code-Basis](/aggregator-lib) und Microservices, die diese abstrahieren.
Dazu zählen [Microservices zur Rohdatenverarbeitung](/raw-ms) und [Microservices zur Aggregatbildung](/group-ms).
Aggregate über längere Zeiträume werden durch die [historischen Microservices](/historic-ms) realisiert.
Um eine Visualisierung durch Prometheus und Grafana zu ermöglichen, wird das [Exportmodul](/prometheus-exporter-service) benötigt.
Zudem ist eine Anwendung zur [Anbindung der GrovePi-Sensoren](/sensor-applications) für die Raspberry Pi 4 Computer vorhanden.
Zwei Hilfsmodule sind ebenso verfügbar. Es steht ein [Simulationsmodul](/sensor-virtualization-service) bereit, mit dem Sensoren virtualisiert werden können.
Des Weiteren können Metadaten zu den Entitäten, wie den Sensoren und den Gruppen eingepflegt werden.
Dies wird durch ein [Verwaltungsmodul](/aggregator-management-service) ermöglicht, welches eine REST-Schnittstelle zur Verfügung stellt.
Erklärungen und Modelle zu den Unterprojekten sind in den entsprechenden README-Dateien zu finden.

# Getting started
Um dieses Projekt lokal aufzusetzen, muss zuerst dieses Git-Repository geklont werden:
````
git clone https://github.com/htw-saar/BachelorthesisMaverickStuder.git
````
Anschließend kann das Projekt in einer beliebigen IDE bearbeitet werden.

Möchte man das Projekt in einer lokalen Testumgebung starten, so wird die Verwendung von [minikube](https://minikube.sigs.k8s.io/docs/start/) empfohlen.
Die folgenden Anwendungen müssten anschließend über Kubernetes bereitgestellt werden:
* **RabbitMQ:** Unten wird beschrieben, wie ein eigenes Deployment durchgeführt werden kann.
* **Memcached:** Über eigenen helm Chart
````
helm install my-memcached bitnami/memcached
````
* **MySQL:** Über eigenen helm Chart
````
helm install my-mysql bitnami/mysql
````
Anschließend kann über den nachfolgenden Befehl das Passwort im Base64-Format ausgelesen werden.
Der Nutzername ist stets *user*.
````
kubectl get secret --namespace default my-mysql -o jsonpath="{.data.mysql-root-password}"
````
Es stehen im Verzeichnis [_db-exports](/_db-exports) Datenbank-Dumps, die bereits Testdaten beinhalten.
* **MinIO:** Über eigenen helm Chart
````
helm install my-minio bitnami/minio
````
Hier kann ebenfalls der Nutzername und das Passwort ausgelesen werden. Diese sind auch mit Base64 codiert.
````
kubectl get secret --namespace default my-minio -o jsonpath="{.data.access-key}"
kubectl get secret --namespace default my-minio -o jsonpath="{.data.secret-key}"
````
* **Prometheus und Grafana:** Über helm Chart des kube-prometheus-stack
````
helm install my-prom prometheus-community/kube-prometheus-stack
````
Die Abfrage der Login-Informationen ist analog. Bei einem regulären Deployment ist der Nutzername *admin*
und das Passwort *prom-operator*.
````
kubectl get secret my-prom-grafana -o jsonpath="{.data.admin-user}" 
kubectl get secret my-prom-grafana -o jsonpath="{.data.admin-user}" 
````
Durch editieren der Deployments können, wenn dies gewünscht ist, die Services auch als NodePort konfiguriert werden.
Damit wird die direkte Ansteuerung möglich. Dadurch kann ein Port-Forwarding erspart bleiben.

* **Prometheus Adapter:** Über eigenen helm Chart
````
helm install my-prom-adapter prometheus-community/prometheus-adapter
````
Hier muss das Deployment in der Regel noch angepasst werden, damit die korrekte Prometheus-URL definiert ist.
Ebenso kann das Abfrage-Intervall verändert werden.
````
kubectl edit deployment my-prom-adapter-prometheus-adapter
````
Folgende Einträge sind zu setzen:
```
- --prometheus-url=http://my-prom-kube-prometheus-st-prometheus:9090
- --metrics-relist-interval=30s
```

Im Verzeichnis [k8s](/k8s) sind diverse Unterverzeichnisse zu finden:
* Die folgenden Konfigurationen müssen getätigt werden:
  * Konfigurationen sind in zentralen ConfigMaps beziehungsweise Secrets zu definieren.
    Deren Ausgestaltung für die Testumgebung und für das erste Deployment im DSL-Cluster sind auf dem Pfad [k8s/config](/k8s/config) zu finden.
  * Zur Überwachung der Microservices über Prometheus muss ein eigener [ServiceMonitor](/k8s/ms-service-monitor.yaml) erzeugt werden.
    Dieser sorgt für das Einfließen aller angebotenen Daten der Microservices.
* Die nachfolgenden Deployments können je nach Bedarf ebenfalls nützlich sein:
  * Möchte man einen Metrics Server in Kubernetes bereitstellen, um zusätzliche Metriken der Nodes auswerten zu können,
    so kann das entsprechende [Deployment](/k8s/metrics-server) verwendet werden.
  * Ein eigener [RabbitMQ Server](/k8s/rabbitmq) kann ebenfalls bereitgestellt werden. 
    Dazu müssen folgende Befehle im Verzeichnis ausgeführt werden:
````
docker build -t user/rabbitmq-mqtt .
docker push user/rabbitmq-mqtt
kubectl apply -f .\rabbitmq-deployment.yaml
````

Das Grafana-Dashboard der Testumgebung wurde als [JSON-Datei](_graf-dashboards/dashboard.json) exportiert.
Möchte man eine neue Testumgebung aufbauen, kann man es importieren.
Gleichermaßen kann es als Referenz oder Basis dienen, wenn ein neues Dashboard erstellt werden soll.
Ebenfalls interessant ist das RabbitMQ Dashboard. Eine ausführliche Anleitung zur Installation kann auf der 
[Homepage](https://www.rabbitmq.com/prometheus.html) nachgeschlagen werden.
Die schnellste Möglichkeit das Dashboard hinzuzufügen, ist der Import über die Dashboard-ID.
````
https://grafana.com/grafana/dashboards/4279
````

Die Deployments der einzelnen Microservices werden in den jeweiligen README-Dateien erläutert.

## Konfiguration
Sämtliche Konfigurationseigenschaften können entweder in den jeweiligen `src/main/resources/application.properties` eingetragen oder in der zentralen ConifgMap beziehungsweise den Secrets definiert werden.
Die wichtigsten Konfigurationseigenschaften sind:
* `BROKER_HOST`: Message Broker Hostname
* `BROKER_PORT_AMQP`: Message Broker Port für das AMQP Protokoll 
* `BROKER_PORT_MQTT`: Message Broker Port für das MQTT Protokoll
* `BROKER_USERNAME`: Username für die Verbindung zum Message Broker
* `BROKER_PASSWORD`: Passwort für die Verbindung zum Message Broker
* `CA_FILE`: CA Zertifikatdatei als Text
* `CLIENT_CERT_FILE`: Client Zertifikatdatei als Text
* `CLIENT_KEY_FILE`: Client Schlüsseldatei als Text
* `MINIO_ENDPOINT`: Endpunkt des MinIO Servers
* `MINIO_ACCESSKEY`: Zugriffsschlüssel für die Verbindung zum MinIO Server
* `MINIO_SECRETKEY`: Geheimschlüssel für die Verbindung zum MinIO Server
* `MEMCACHED_HOST`: Memcached Server Hostname
* `MEMCACHED_PORT`: Memcached Server Port
* `spring.datasource.url`: Datenbank URL (hier MySQL Server URL)
* `spring.datasource.username`: Datenbank Username (hier MySQL Server Username)
* `spring.datasource.password`: Datenbank Passwort (hier MySQL Server Passwort)

Die spezifischen Konfigurationen können in den README-Dateien der entsprechenden Microservices nachvollzogen werden.

## Built With
* [Spring Boot](https://spring.io/projects/spring-boot) - Framework für Microservices
* [Maven](https://maven.apache.org/) - Verwaltung von Abhängigkeiten, Build-Prozess
* [Hibernate](https://hibernate.org/orm/) - Object-Relationales Mapping
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - Library zur Datenabfrage
* [RabbitMQ](https://www.rabbitmq.com/) - Message Broker Lösung, Library für AMQP-Client
* [eclipse paho](https://www.eclipse.org/paho/) - Framework für das Senden an den Broker über MQTT
* [Jackson](https://github.com/FasterXML/jackson) - JSON-Serialisierung
* [Prometheus](https://prometheus.io/) - Monitoring der Microservices
* [Grafana](https://grafana.com/) - Visualisierung der Metriken
* [Micrometer](https://micrometer.io/) - Metriken für Prometheus bereitstellen
* [MySQL](https://www.mysql.com/) - Datenbanksystem, Nutzung für Stammdaten
* [Memcached](https://memcached.org/) - Caching-Server, Client-Library
* [MinIO](https://min.io/) - Object Storage Lösung, Client-Library
* [GrovePI](https://github.com/DexterInd/GrovePi) - Hersteller der Sensoren, Library zur Ansteuerung
* [Swagger](https://swagger.io/) / [springdoc](https://springdoc.org/) - OpenAPI-Konforme Schnittstellenspezifikation
* [JUnit](https://junit.org/) - Framework zum Testen
* [Java Microbenchmark Harness](https://github.com/openjdk/jmh) - Testen der Gesamtfunktionalität, Auswertung der Performance
* [H2 Database Engine](https://www.h2database.com/html/main.html) - In-Memory Datenbank
* [Docker](https://www.docker.com/) - Anwendung zur Containervirtualisierung
* [Kubernetes](https://kubernetes.io/) - Orchestrierung der Container
* [minikube](https://minikube.sigs.k8s.io/docs/start/) - Lokale Testumgebung


## License
This project is licensed under the GNU General Public License v3.0

## Acknowledgments
* [Prof. Dr. Markus Esch](https://www.htwsaar.de/htw/ingwi/fakultaet/personen/profile/markus-esch) - Projektbetreuung
* Dominic Büch - Projektbetreuung
* [Baeldung](https://www.baeldung.com/) - Große Auswahl an Guides zu Java, insbesondere Spring
* [Sprint Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/1.5.0.RELEASE/reference/html/index.html) - Dokumentation zu Spring Data JPA
* [Kubernetes - Documentation](https://kubernetes.io/docs/home/) / [Kubernetes - Training](https://learn.kubernetes.anynines.com/training-overview/) - Alles rund um Kubernetes
* [RabbitMQ](https://www.rabbitmq.com/) - RabbitMQ Dokumentation, Anleitungen für Server und Client Konfiguration und Bereitstellung
* [Autoscaling Spring Boot with the Horizontal Pod Autoscaler and custom metrics on Kubernetes](https://github.com/learnk8s/spring-boot-k8s-hpa) - Referenz zur Umsetzung der automatischen Skalierung
* [Stackoverflow](https://stackoverflow.com/) - Bringt Licht ins Dunkle