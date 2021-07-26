package de.htw.saar.smartcity.aggregator.benchmarking.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BenchmarkingApplicationProperties  extends RawMicroserviceApplicationProperties {

    @Override
    @Value("${BENCHMARKING_QUEUE}")
    public void setMicroserviceQueue(String microserviceQueue) {
        this.microserviceQueue = microserviceQueue;
    }

    @Override
    @Value("${BENCHMARKING_TOPICS}")
    public void setMicroserviceTopics(String[] microserviceTopics) {
        this.microserviceTopics = microserviceTopics;
    }

    @Override
    @Value("${BENCHMARKING_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }

    @Override
    @Value("${BENCHMARKING_SENSOR_TYPE}")
    public void setMicroserviceDataType(String microserviceDataType) {
        this.microserviceDataType = microserviceDataType;
    }

    @Override
    @Value("${EXPORT_BENCHMARKING_DATA_AS_METRIC}")
    public void setExportSensorDataAsMetric(boolean exportSensorDataAsMetric) {
        this.exportSensorDataAsMetric = exportSensorDataAsMetric;
    }




}
