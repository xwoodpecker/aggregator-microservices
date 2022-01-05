package de.htw.saar.smartcity.aggregator.lib.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Historic aggregator application properties.
 */
@Configuration
@PropertySource("classpath:application.properties")
public abstract class HistoricAggregatorApplicationProperties implements MinioApplicationProperties {

    private String minioEndpoint;

    private String minioAccessKey;

    private String minioSecretKey;

    private Boolean onlySaveMeasurementValue;

    /**
     * The Microservice bucket.
     */
    protected String minioBucketName;

    /**
     * The Microservice data type.
     */
    protected String microserviceDataType;

    /**
     * The Delete raw measurements.
     */
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

    /**
     * Sets delete raw measurements.
     *
     * @param deleteRawMeasurements the delete raw measurements
     */
    protected abstract void setDeleteRawMeasurements(boolean deleteRawMeasurements);

    /**
     * Sets only save measurement value.
     *
     * @param onlySaveMeasurementValue the only save measurement value
     */
    @Value("${ONLY_SAVE_MEASUREMENT_VALUE}")
    public void setOnlySaveMeasurementValue(Boolean onlySaveMeasurementValue) {
        this.onlySaveMeasurementValue = onlySaveMeasurementValue;
    }

    /**
     * Sets microservice bucket.
     *
     * @param minioBucketName the microservice bucket
     */
    protected abstract void setMinioBucketName(String minioBucketName);

    /**
     * Sets microservice data type.
     *
     * @param microserviceDataType the microservice data type
     */
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

    public String getMinioBucketName() {
        return this.minioBucketName;
    }

    /**
     * Gets micro service data type.
     *
     * @return the micro service data type
     */
    public String getMicroServiceDataType() {
        return microserviceDataType;
    }

    /**
     * Gets delete raw measurements.
     *
     * @return the delete raw measurements
     */
    public boolean getDeleteRawMeasurements() {
        return deleteRawMeasurements;
    }

    @Override
    public Boolean getOnlySaveMeasurementValue() {
        return onlySaveMeasurementValue;
    }

    /**@PostConstruct
    public void printProperties() {
        final Logger log = LoggerFactory.getLogger(MicroserviceApplicationProperties.class);
        log.info(this.toString());

    }**/
}
