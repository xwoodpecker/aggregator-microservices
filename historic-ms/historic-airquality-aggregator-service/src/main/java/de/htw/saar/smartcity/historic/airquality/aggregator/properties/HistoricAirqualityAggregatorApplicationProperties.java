package de.htw.saar.smartcity.historic.airquality.aggregator.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.HistoricAggregatorApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoricAirqualityAggregatorApplicationProperties extends HistoricAggregatorApplicationProperties {


    @Override
    @Value("${DELETE_RAW_AIRQUALITY_MEASUREMENTS}")
    public void setDeleteRawMeasurements(boolean deleteRawMeasurements) {
        this.deleteRawMeasurements = deleteRawMeasurements;
    }

    @Override
    @Value("${HISTORIC_AIRQUALITY_AGGREGATOR_MICROSERVICE_BUCKET}")
    public void setMinioBucketName(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }

    @Override
    @Value("${AIRQUALITY_SENSOR_TYPE}")
    public void setMicroserviceDataType(String microserviceDataType) {
        this.microserviceDataType = microserviceDataType;
    }


}
