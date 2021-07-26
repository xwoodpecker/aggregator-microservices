package de.htw.saar.smartcity.aggregator.lib.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public abstract class HistoricAggregatorApplicationProperties implements MinioApplicationProperties {


    private String minioEndpoint;

    private String minioAccessKey;

    private String minioSecretKey;

    protected String microserviceBucket;

    protected String microserviceDataType;

    protected boolean deleteRawMeasurements;


    @Value("${MINIO_ENDPOINT}")
    private void setMinioEndpoint(String minioEndpoint) {
        this.minioEndpoint = minioEndpoint;
    }

    @Value("${MINIO_ACCESSKEY}")
    private void setMinioAccessKey(String minioAccessKey) {
        this.minioAccessKey = minioAccessKey;
    }

    @Value("${MINIO_SECRETKEY}")
    private void setMinioSecretKey(String minioSecretKey) {
        this.minioSecretKey = minioSecretKey;
    }

    protected abstract void setDeleteRawMeasurements(boolean deleteRawMeasurements);

    protected abstract void setMicroserviceBucket(String microserviceBucket);

    public abstract void setMicroserviceDataType(String microserviceDataType);

    public String getMinioEndpoint() {
        return minioEndpoint;
    }

    public String getMinioAccessKey() {
        return minioAccessKey;
    }

    public String getMinioSecretKey() {
        return minioSecretKey;
    }

    public String getMicroserviceBucket() {
        return this.microserviceBucket;
    }

    public String getMicroServiceDataType() {
        return microserviceDataType;
    }

    public boolean getDeleteRawMeasurements() {
        return deleteRawMeasurements;
    }

    /**@PostConstruct
    public void printProperties() {
        final Logger log = LoggerFactory.getLogger(MicroserviceApplicationProperties.class);
        log.info(this.toString());

    }**/
}
