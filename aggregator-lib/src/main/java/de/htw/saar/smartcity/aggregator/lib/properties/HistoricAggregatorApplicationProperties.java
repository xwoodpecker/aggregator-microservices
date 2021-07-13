package de.htw.saar.smartcity.aggregator.lib.properties;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:application.properties")
public abstract class HistoricAggregatorApplicationProperties implements MinioApplicationProperties {


    private String minioEndpoint;

    private String minioAccessKey;

    private String minioSecretKey;

    protected String microserviceBucket;

    protected String microserviceDataType;


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

    /**@PostConstruct
    public void printProperties() {
        final Logger log = LoggerFactory.getLogger(MicroserviceApplicationProperties.class);
        log.info(this.toString());

    }**/
}
