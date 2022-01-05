package de.htw.saar.smartcity.aggregator.temperature.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemperatureApplicationProperties  extends RawMicroserviceApplicationProperties {

    @Override
    @Value("${TEMPERATURE_QUEUE}")
    public void setMicroserviceTopicName(String microserviceTopicName) {
        this.microserviceTopicName = microserviceTopicName;
    }

    @Override
    @Value("${TEMPERATURE_TOPICS}")
    public void setMicroserviceTopics(String[] microserviceTopics) {
        this.microserviceTopics = microserviceTopics;
    }

    @Override
    @Value("${TEMPERATURE_BUCKET}")
    public void setMinioBucket(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }

    @Override
    @Value("${TEMPERATURE_SENSOR_TYPE}")
    public void setApplicationDataType(String applicationDataType) {
        this.applicationDataType = applicationDataType;
    }

    @Override
    @Value("${EXPORT_TEMPERATURE_DATA_AS_METRIC}")
    public void setExportSensorDataAsMetric(boolean exportSensorDataAsMetric) {
        this.exportSensorDataAsMetric = exportSensorDataAsMetric;
    }


}
