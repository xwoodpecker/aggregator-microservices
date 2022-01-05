package de.htw.saar.smartcity.aggregator.water.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WaterApplicationProperties extends RawMicroserviceApplicationProperties {

    @Override
    @Value("${WATER_QUEUE}")
    public void setMicroserviceTopicName(String microserviceTopicName) {
        this.microserviceTopicName = microserviceTopicName;
    }

    @Override
    @Value("${WATER_TOPICS}")
    public void setMicroserviceTopics(String[] microserviceTopics) {
        this.microserviceTopics = microserviceTopics;
    }

    @Override
    @Value("${WATER_BUCKET}")
    public void setMinioBucket(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }

    @Override
    @Value("${WATER_SENSOR_TYPE}")
    public void setApplicationDataType(String applicationDataType) {
        this.applicationDataType = applicationDataType;
    }

    @Override
    @Value("${EXPORT_WATER_DATA_AS_METRIC}")
    public void setExportSensorDataAsMetric(boolean exportSensorDataAsMetric) {
        this.exportSensorDataAsMetric = exportSensorDataAsMetric;
    }

}
