package de.htw.saar.smartcity.aggregator.dewpoint.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DewpointApplicationProperties extends GroupMicroserviceApplicationProperties {

    @Override
    @Value("${DEWPOINT_MICROSERVICE_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }

    @Override
    @Value("${DEWPOINT_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceGroupTypeName(String microserviceGroupTypeName) {
        this.microserviceGroupTypeName = microserviceGroupTypeName;
    }

    @Override
    @Value("${DEWPOINT_DATA_TYPE}")
    public void setMicroserviceDataTypeName(String microserviceDataTypeName) {
        this.microserviceDataTypeName = microserviceDataTypeName;

    }

    @Override
    @Value("${DEWPOINT_MICROSERVICE_QUEUE}")
    public void setMicroserviceQueue(String microserviceQueue) {
        this.microserviceQueue = microserviceQueue;
    }

    private String temperatureDataTypeName;

    private String humidityDataTypeName;


    @Value("${TEMPERATURE_DATA_TYPE_NAME}")
    public void setTemperatureDataTypeName(String temperatureDataTypeName) {
        this.temperatureDataTypeName = temperatureDataTypeName;
    }

    @Value("${HUMIDITY_DATA_TYPE_NAME}")
    public void setHumidityDataTypeName(String humidityDataTypeName) {
        this.humidityDataTypeName = humidityDataTypeName;
    }

    public String getTemperatureDataTypeName() {
        return temperatureDataTypeName;
    }


    public String getHumidityDataTypeName() {
        return humidityDataTypeName;
    }
}
