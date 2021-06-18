package de.htw.saar.smartcity.historic.temperature.aggregator.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.HistoricAggregatorApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoricTemperatureAggregatorApplicationProperties extends HistoricAggregatorApplicationProperties {

    @Override
    @Value("${HISTORIC_TEMPERATURE_AGGREGATOR_MICROSERVICE_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }

    @Override
    @Value("${TEMPERATURE_DATA_TYPE_NAME}")
    public void setMicroserviceDataType(String microserviceDataType) {
        this.microserviceDataType = microserviceDataType;
    }


}
