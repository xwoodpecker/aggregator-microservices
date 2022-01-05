package de.htw.saar.smartcity.aggregator.lib.properties;

/**
 * The type Raw microservice application properties.
 */
public abstract class RawBasicApplicationProperties extends MicroserviceApplicationProperties {

    /**
     * The Export sensor data as metric.
     */
    protected boolean exportSensorDataAsMetric;

    /**
     * Sets export sensor data as metric.
     *
     * @param exportSensorDataAsMetric the export sensor data as metric
     */
    public abstract void setExportSensorDataAsMetric(boolean exportSensorDataAsMetric);

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
        final StringBuffer sb = new StringBuffer("RawBasicApplicationProperties{");
        sb.append("minioEndpoint='").append(minioEndpoint).append('\'');
        sb.append(", minioAccessKey='").append(minioAccessKey).append('\'');
        sb.append(", minioSecretKey='").append(minioSecretKey).append('\'');
        sb.append(", onlySaveMeasurementValue=").append(onlySaveMeasurementValue);
        sb.append(", minioBucketName='").append(minioBucketName).append('\'');
        sb.append(", memcachedHost='").append(memcachedHost).append('\'');
        sb.append(", memcachedPort='").append(memcachedPort).append('\'');
        sb.append(", applicationDataType='").append(applicationDataType).append('\'');
        sb.append(", microserviceQueue='").append(microserviceQueue).append('\'');
        sb.append(", exportSensorDataAsMetric=").append(exportSensorDataAsMetric);
        sb.append('}');
        return sb.toString();
    }
}
