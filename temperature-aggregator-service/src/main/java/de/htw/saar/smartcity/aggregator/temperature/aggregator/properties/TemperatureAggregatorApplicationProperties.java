package de.htw.saar.smartcity.aggregator.temperature.aggregator.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemperatureAggregatorApplicationProperties extends GroupMicroserviceApplicationProperties {

    @Override
    @Value("${TEMPERATURE_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceQueue(String microserviceQueue) {
        this.microserviceQueue = microserviceQueue;
    }

    @Override
    @Value("${TEMPERATURE_AGGREGATOR_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceGroupTypeName(String microserviceGroupTypeName) {
        this.microserviceGroupTypeName = microserviceGroupTypeName;
    }

    @Override
    @Value("${TEMPERATURE_SENSOR_TYPE}")
    public void setMicroserviceDataTypeName(String microserviceDataTypeName) {
        this.microserviceDataTypeName = microserviceDataTypeName;
    }

    @Override
    @Value("${TEMPERATURE_AGGREGATOR_MICROSERVICE_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }


}
