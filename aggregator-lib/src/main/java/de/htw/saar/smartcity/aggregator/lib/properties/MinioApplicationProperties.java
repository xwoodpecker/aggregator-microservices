package de.htw.saar.smartcity.aggregator.lib.properties;

/**
 * The interface Minio application properties.
 */
public interface MinioApplicationProperties {


    /**
     * Gets minio endpoint.
     *
     * @return the minio endpoint
     */
    String getMinioEndpoint();

    /**
     * Gets minio access key.
     *
     * @return the minio access key
     */
    String getMinioAccessKey();

    /**
     * Gets minio secret key.
     *
     * @return the minio secret key
     */
    String getMinioSecretKey();

    /**
     * Gets microservice bucket.
     *
     * @return the microservice bucket
     */
    String getMicroserviceBucket();

    /**
     * Gets only save measurement value.
     *
     * @return the only save measurement value
     */
    Boolean getOnlySaveMeasurementValue();


}
