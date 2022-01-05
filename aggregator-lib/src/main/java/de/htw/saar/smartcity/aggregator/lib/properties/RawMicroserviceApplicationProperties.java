package de.htw.saar.smartcity.aggregator.lib.properties;

import java.util.Arrays;

/**
 * The type Raw microservice application properties.
 */
public abstract class RawMicroserviceApplicationProperties extends MicroserviceApplicationProperties implements RawMeasurementHandlerProperties {

    /**
     * The Export sensor data as metric.
     */
    protected boolean exportSensorDataAsMetric;

    /**
     * The Microservice topics.
     */
    protected String[] microserviceTopics;

    /**
     * Sets export sensor data as metric.
     *
     * @param exportSensorDataAsMetric the export sensor data as metric
     */
    public abstract void setExportSensorDataAsMetric(boolean exportSensorDataAsMetric);

    /**
     * Sets microservice topics.
     *
     * @param microserviceTopics the microservice topics
     */
    public abstract void setMicroserviceTopics(String[] microserviceTopics);


    /**
     * Get microservice topics string [ ].
     *
     * @return the string [ ]
     */
    public String[] getMicroserviceTopics() {
        return microserviceTopics;
    }

    /**
     * Gets export sensor data as metric.
     *
     * @return the export sensor data as metric
     */
    public boolean getExportSensorDataAsMetric() {
        return this.exportSensorDataAsMetric;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RawMicroserviceApplicationProperties{");
        sb.append("minioEndpoint='").append(minioEndpoint).append('\'');
        sb.append(", minioAccessKey='").append(minioAccessKey).append('\'');
        sb.append(", minioSecretKey='").append(minioSecretKey).append('\'');
        sb.append(", onlySaveMeasurementValue=").append(onlySaveMeasurementValue);
        sb.append(", minioBucketName='").append(minioBucketName).append('\'');
        sb.append(", memcachedHost='").append(memcachedHost).append('\'');
        sb.append(", memcachedPort='").append(memcachedPort).append('\'');
        sb.append(", applicationDataType='").append(applicationDataType).append('\'');
        sb.append(", microserviceTopicName='").append(microserviceTopicName).append('\'');
        sb.append(", exportSensorDataAsMetric=").append(exportSensorDataAsMetric);
        sb.append(", microserviceTopics=").append(microserviceTopics == null ? "null" : Arrays.asList(microserviceTopics).toString());
        sb.append('}');
        return sb.toString();
    }
}
