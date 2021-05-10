package de.htw.saar.smartcity.aggregator.humidity.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HumidityApplicationProperties extends BaseMicroserviceApplicationProperties {

    @Override
    @Value("${HUMIDITY_MICROSERVICE_NAME}")
    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    @Override
    @Value("${HUMIDITY_MICROSERVICE_URL}")
    public void setMicroserviceURL(String microserviceURL) {
        this.microserviceURL = microserviceURL;
    }

    @Override
    @Value("${HUMIDITY_QUEUE}")
    public void setMicroserviceQueue(String microserviceQueue) {
        this.microserviceQueue = microserviceQueue;
    }

    @Override
    @Value("${HUMIDITY_TOPICS}")
    public void setMicroserviceTopics(String[] microserviceTopics) {
        this.microserviceTopics = microserviceTopics;
    }

    @Override
    @Value("${HUMIDITY_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }

    @Override
    @Value("${HUMIDITY_SENSOR_TYPE}")
    public void setMicroserviceSensorType(String microserviceSensorType) {
        this.microserviceSensorType = microserviceSensorType;
    }
}
