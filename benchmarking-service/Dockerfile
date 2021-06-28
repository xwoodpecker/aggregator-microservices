FROM openjdk:14-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "de.htw.saar.smartcity.aggregator.benchmarking.MicroserviceBenchmark"]