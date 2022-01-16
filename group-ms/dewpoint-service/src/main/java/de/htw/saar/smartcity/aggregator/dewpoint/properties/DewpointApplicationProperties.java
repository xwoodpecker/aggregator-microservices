package de.htw.saar.smartcity.aggregator.dewpoint.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DewpointApplicationProperties extends GroupMicroserviceApplicationProperties {

    @Override
    @Value("${DEWPOINT_MICROSERVICE_BUCKET}")
    public void setMinioBucket(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }

    @Override
    @Value("${DEWPOINT_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setGroupTypeName(String microserviceGroupTypeName) {
        this.microserviceGroupTypeName = microserviceGroupTypeName;
    }

    @Override
    @Value("${DEWPOINT_DATA_TYPE}")
    public void setApplicationDataType(String applicationDataType) {
        this.applicationDataType = applicationDataType;

    }

    @Override
    @Value("${DEWPOINT_MICROSERVICE_QUEUE}")
    public void setMicroserviceTopicName(String microserviceTopicName) {
        this.microserviceTopicName = microserviceTopicName;
    }

    private String temperatureDataTypeName;

    private String humidityDataTypeName;


    @Value("${TEMPERATURE_SENSOR_TYPE}")
    public void setTemperatureDataTypeName(String temperatureDataTypeName) {
        this.temperatureDataTypeName = temperatureDataTypeName;
    }

    @Value("${HUMIDITY_SENSOR_TYPE}")
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
