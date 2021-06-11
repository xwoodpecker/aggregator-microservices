FROM maven as builder
WORKDIR /app
COPY . /app
RUN mvn package


FROM openjdk:14-alpine
WORKDIR /app
COPY --from=builder /app/target/metrics-exporter-service-0.0.1-SNAPSHOT-jar-with-dependencies.jar /app/app.jar
CMD ["java", "-cp", "/app/app.jar" , "de.htw.saar.smartcity.aggregator.exporter.Main"]