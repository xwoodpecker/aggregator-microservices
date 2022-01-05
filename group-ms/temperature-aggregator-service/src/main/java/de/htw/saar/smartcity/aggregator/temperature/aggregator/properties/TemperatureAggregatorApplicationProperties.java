package de.htw.saar.smartcity.aggregator.temperature.aggregator.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemperatureAggregatorApplicationProperties extends GroupMicroserviceApplicationProperties {

    @Override
    @Value("${TEMPERATURE_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceTopicName(String microserviceTopicName) {
        this.microserviceTopicName = microserviceTopicName;
    }

    @Override
    @Value("${TEMPERATURE_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceGroupTypeName(String microserviceGroupTypeName) {
        this.microserviceGroupTypeName = microserviceGroupTypeName;
    }

    @Override
    @Value("${TEMPERATURE_SENSOR_TYPE}")
    public void setApplicationDataType(String applicationDataType) {
        this.applicationDataType = applicationDataType;
    }

    @Override
    @Value("${TEMPERATURE_AGGREGATOR_MICROSERVICE_BUCKET}")
    public void setMinioBucket(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }


}
