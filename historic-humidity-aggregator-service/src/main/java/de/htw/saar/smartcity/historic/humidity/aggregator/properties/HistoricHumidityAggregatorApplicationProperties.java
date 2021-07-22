package de.htw.saar.smartcity.historic.humidity.aggregator.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.HistoricAggregatorApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoricHumidityAggregatorApplicationProperties extends HistoricAggregatorApplicationProperties {

    @Override
    @Value("${HISTORIC_HUMIDITY_AGGREGATOR_MICROSERVICE_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }

    @Override
    @Value("${HUMIDITY_SENSOR_TYPE}")
    public void setMicroserviceDataType(String microserviceDataType) {
        this.microserviceDataType = microserviceDataType;
    }


}
