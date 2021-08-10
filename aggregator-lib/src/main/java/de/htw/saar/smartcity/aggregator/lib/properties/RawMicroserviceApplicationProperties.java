package de.htw.saar.smartcity.aggregator.lib.properties;

import java.util.Arrays;

/**
 * The type Raw microservice application properties.
 */
public abstract class RawMicroserviceApplicationProperties extends MicroserviceApplicationProperties {

    /**
     * The Microservice topics.
     */
    protected String[] microserviceTopics;

    /**
     * The Microservice data type.
     */
    protected String microserviceDataType;

    /**
     * The Export sensor data as metric.
     */
    protected boolean exportSensorDataAsMetric;

    /**
     * Sets microservice topics.
     *
     * @param microserviceTopics the microservice topics
     */
    public abstract void setMicroserviceTopics(String[] microserviceTopics);

    /**
     * Sets microservice data type.
     *
     * @param microserviceDataType the microservice data type
     */
    public abstract void setMicroserviceDataType(String microserviceDataType);

    /**
     * Sets export sensor data as metric.
     *
     * @param exportSensorDataAsMetric the export sensor data as metric
     */
    public abstract void setExportSensorDataAsMetric(boolean exportSensorDataAsMetric);

    /**
     * Get microservice topics string [ ].
     *
     * @return the string [ ]
     */
    public String[] getMicroserviceTopics() {
        return microserviceTopics;
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
        sb.append("microserviceQueue='").append(microserviceQueue).append('\'');
        sb.append(", microserviceBucket='").append(microserviceBucket).append('\'');
        sb.append(", memcachedHost='").append(memcachedHost).append('\'');
        sb.append(", memcachedPort='").append(memcachedPort).append('\'');
        sb.append(", microserviceTopics=").append(microserviceTopics == null ? "null" : Arrays.asList(microserviceTopics).toString());
        sb.append(", microserviceDataType='").append(microserviceDataType).append('\'');
        sb.append(", exportSensorDataAsMetric=").append(exportSensorDataAsMetric);
        sb.append('}');
        return sb.toString();
    }
}
