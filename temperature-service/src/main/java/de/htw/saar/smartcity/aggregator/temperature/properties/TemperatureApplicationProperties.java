package de.htw.saar.smartcity.aggregator.temperature.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemperatureApplicationProperties  extends BaseMicroserviceApplicationProperties {

    @Override
    @Value("${TEMPERATURE_MICROSERVICE_NAME}")
    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;

    }

    @Override
    @Value("${TEMPERATURE_QUEUE}")
    public void setMicroserviceQueue(String microserviceQueue) {
        this.microserviceQueue = microserviceQueue;
    }

    @Override
    @Value("${TEMPERATURE_TOPICS}")
    public void setMicroserviceTopics(String[] microserviceTopics) {
        this.microserviceTopics = microserviceTopics;
    }

    @Override
    @Value("${TEMPERATURE_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }

    @Override
    @Value("${TEMPERATURE_SENSOR_TYPE}")
    public void setMicroserviceSensorType(String microserviceSensorType) {
        this.microserviceSensorType = microserviceSensorType;
    }


}
