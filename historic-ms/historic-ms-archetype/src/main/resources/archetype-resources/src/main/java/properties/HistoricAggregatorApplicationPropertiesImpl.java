package $ import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

{package}.properties;

@Configuration
public class HistoricAggregatorApplicationPropertiesImpl extends HistoricMicroserviceAggregatorApplicationProperties {

    //todo: change keys

    @Override
    @Value("${DELETE_RAW_MEASUREMENTS}")
    public void setDeleteRawMeasurements(boolean deleteRawMeasurements) {
        this.deleteRawMeasurements = deleteRawMeasurements;
    }

    @Override
    @Value("${MY_HISTORIC_MICROSERVICE_BUCKET}")
    public void setMinioBucketName(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }

    @Override
    @Value("${MY_DATA_TYPE}")
    public void setMicroserviceDataType(String microserviceDataType) {
        this.microserviceDataType = microserviceDataType;
    }


}
