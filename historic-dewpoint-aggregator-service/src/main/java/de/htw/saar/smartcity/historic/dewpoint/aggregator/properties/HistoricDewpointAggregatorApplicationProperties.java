package de.htw.saar.smartcity.historic.dewpoint.aggregator.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.HistoricAggregatorApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoricDewpointAggregatorApplicationProperties extends HistoricAggregatorApplicationProperties {


    @Override
    @Value("${DELETE_RAW_DEWPOINT_MEASUREMENTS}")
    public void setDeleteRawMeasurements(boolean deleteRawMeasurements) {
        this.deleteRawMeasurements = deleteRawMeasurements;
    }

    @Override
    @Value("${HISTORIC_DEWPOINT_AGGREGATOR_MICROSERVICE_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }

    @Override
    @Value("${DEWPOINT_DATA_TYPE}")
    public void setMicroserviceDataType(String microserviceDataType) {
        this.microserviceDataType = microserviceDataType;
    }


}
