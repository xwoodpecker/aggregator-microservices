package de.htw.saar.smartcity.aggregator.humidity.aggregator.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HumidityAggregatorApplicationProperties extends GroupMicroserviceApplicationProperties {

    @Override
    @Value("${HUMIDITY_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceQueue(String microserviceQueue) {
        this.microserviceQueue = microserviceQueue;
    }

    @Override
    @Value("${HUMIDITY_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceGroupTypeName(String microserviceGroupTypeName) {
        this.microserviceGroupTypeName = microserviceGroupTypeName;
    }

    @Override
    @Value("${HUMIDITY_SENSOR_TYPE}")
    public void setApplicationDataType(String applicationDataType) {
        this.applicationDataType = applicationDataType;
    }

    @Override
    @Value("${HUMIDITY_AGGREGATOR_MICROSERVICE_BUCKET}")
    public void setMinioBucket(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }


}
