package ${package}.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.HistoricAggregatorApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HistoricAggregatorApplicationPropertiesImpl extends HistoricAggregatorApplicationProperties {

    //todo: change keys

    @Override
    @Value("${DELETE_RAW_MEASUREMENTS}")
    public void setDeleteRawMeasurements(boolean deleteRawMeasurements) {
        this.deleteRawMeasurements = deleteRawMeasurements;
    }

    @Override
    @Value("${MY_HISTORIC_MICROSERVICE_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }

    @Override
    @Value("${MY_DATA_TYPE}")
    public void setMicroserviceDataType(String microserviceDataType) {
        this.microserviceDataType = microserviceDataType;
    }


}
