package de.htw.saar.smartcity.aggregator.airquality.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AirqualityApplicationProperties  extends RawMicroserviceApplicationProperties {

    @Override
    @Value("${AIRQUALITY_QUEUE}")
    public void setMicroserviceTopicName(String microserviceTopicName) {
        this.microserviceTopicName = microserviceTopicName;
    }

    @Override
    @Value("${AIRQUALITY_TOPICS}")
    public void setMicroserviceTopics(String[] microserviceTopics) {
        this.microserviceTopics = microserviceTopics;
    }

    @Override
    @Value("${AIRQUALITY_BUCKET}")
    public void setMinioBucket(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }

    @Override
    @Value("${AIRQUALITY_SENSOR_TYPE}")
    public void setApplicationDataType(String applicationDataType) {
        this.applicationDataType = applicationDataType;
    }

    @Override
    @Value("${EXPORT_AIRQUALITY_DATA_AS_METRIC}")
    public void setExportSensorDataAsMetric(boolean exportSensorDataAsMetric) {
        this.exportSensorDataAsMetric = exportSensorDataAsMetric;
    }


}
