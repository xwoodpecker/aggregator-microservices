package de.htw.saar.smartcity.aggregator.picture.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PictureApplicationProperties extends RawMicroserviceApplicationProperties {

    @Override
    @Value("${PICTURE_QUEUE}")
    public void setMicroserviceTopicName(String microserviceTopicName) {
        this.microserviceTopicName = microserviceTopicName;
    }

    @Override
    @Value("${PICTURE_TOPICS}")
    public void setMicroserviceTopics(String[] microserviceTopics) {
        this.microserviceTopics = microserviceTopics;
    }

    @Override
    @Value("${PICTURE_BUCKET}")
    public void setMinioBucket(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }

    @Override
    @Value("${PICTURE_SENSOR_TYPE}")
    public void setApplicationDataType(String applicationDataType) {
        this.applicationDataType = applicationDataType;
    }

    @Override
    @Value("${EXPORT_PICTURE_DATA_AS_METRIC}")
    public void setExportSensorDataAsMetric(boolean exportSensorDataAsMetric) {
        this.exportSensorDataAsMetric = exportSensorDataAsMetric;
    }


}
