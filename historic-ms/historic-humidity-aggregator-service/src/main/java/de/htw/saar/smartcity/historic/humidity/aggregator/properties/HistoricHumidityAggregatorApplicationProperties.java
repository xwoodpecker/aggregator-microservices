package de.htw.saar.smartcity.historic.humidity.aggregator.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.HistoricMicroserviceAggregatorApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoricHumidityAggregatorApplicationProperties extends HistoricMicroserviceAggregatorApplicationProperties {


    @Override
    @Value("${DELETE_RAW_HUMIDITY_MEASUREMENTS}")
    public void setDeleteRawMeasurements(boolean deleteRawMeasurements) {
        this.deleteRawMeasurements = deleteRawMeasurements;
    }

    @Override
    @Value("${HISTORIC_HUMIDITY_AGGREGATOR_MICROSERVICE_BUCKET}")
    public void setMinioBucketName(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }

    @Override
    @Value("${HUMIDITY_SENSOR_TYPE}")
    public void setMicroserviceDataType(String microserviceDataType) {
        this.microserviceDataType = microserviceDataType;
    }



}
