package de.htw.saar.smartcity.aggregator.lib.properties;

import org.springframework.beans.factory.annotation.Value;

/**
 * The type Raw microservice application properties.
 */
public abstract class BasicApplicationProperties implements MinioApplicationProperties, MemcachedApplicationProperties {

    protected String minioEndpoint;

    protected String minioAccessKey;

    protected String minioSecretKey;

    protected Boolean onlySaveMeasurementValue;

    /**
     * The Minio bucket name.
     */
    protected String minioBucketName;

    /**
     * The Memcached host.
     */
    protected String memcachedHost;

    /**
     * The Memcached port.
     */
    protected String memcachedPort;

    /**
     * The application data type.
     */
    protected String applicationDataType;

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
     * Sets only save measurement value.
     *
     * @param onlySaveMeasurementValue the only save measurement value
     */
    @Value("${ONLY_SAVE_MEASUREMENT_VALUE}")
    public void setOnlySaveMeasurementValue(Boolean onlySaveMeasurementValue) {
        this.onlySaveMeasurementValue = onlySaveMeasurementValue;
    }

    /**
     * Sets minio bucket.
     *
     * @param minioBucketName the microservice bucket
     */
    protected abstract void setMinioBucket(String minioBucketName);

    @Value("${MEMCACHED_HOST}")
    private void setMemcachedHost(String memcachedHost) {
        this.memcachedHost = memcachedHost;
    }

    @Value("${MEMCACHED_PORT}")
    private void setMemcachedPort(String memcachedPort) {
        this.memcachedPort = memcachedPort;
    }

    /**
     * Sets microservice data type.
     *
     * @param applicationDataType the microservice data type
     */
    public abstract void setApplicationDataType(String applicationDataType);

    @Override
    public String getMinioEndpoint() {
        return minioEndpoint;
    }

    @Override
    public String getMinioAccessKey() {
        return minioAccessKey;
    }

    @Override
    public String getMinioSecretKey() {
        return minioSecretKey;
    }

    @Override
    public Boolean getOnlySaveMeasurementValue() {
        return onlySaveMeasurementValue;
    }

    public String getMinioBucketName() {
        return minioBucketName;
    }

    @Override
    public String getMemcachedHost() {
        return memcachedHost;
    }

    @Override
    public String getMemcachedPort() {
        return memcachedPort;
    }

    /**
     * Gets micro service data type.
     *
     * @return the micro service data type
     */
        public String getApplicationDataType() {
        return applicationDataType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BasicApplicationProperties{");
        sb.append("minioEndpoint='").append(minioEndpoint).append('\'');
        sb.append(", minioAccessKey='").append(minioAccessKey).append('\'');
        sb.append(", minioSecretKey='").append(minioSecretKey).append('\'');
        sb.append(", onlySaveMeasurementValue=").append(onlySaveMeasurementValue);
        sb.append(", minioBucketName='").append(minioBucketName).append('\'');
        sb.append(", memcachedHost='").append(memcachedHost).append('\'');
        sb.append(", memcachedPort='").append(memcachedPort).append('\'');
        sb.append(", applicationDataType='").append(applicationDataType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
