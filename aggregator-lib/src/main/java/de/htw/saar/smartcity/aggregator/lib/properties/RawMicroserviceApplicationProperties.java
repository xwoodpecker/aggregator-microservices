package de.htw.saar.smartcity.aggregator.lib.properties;

import java.util.Arrays;

public abstract class RawMicroserviceApplicationProperties extends MicroserviceApplicationProperties{

    protected String[] microserviceTopics;

    protected String microserviceDataType;

    protected boolean exportSensorDataAsMetric;

    public abstract void setMicroserviceTopics(String[] microserviceTopics);

    public abstract void setMicroserviceDataType(String microserviceDataType);

    public abstract void setExportSensorDataAsMetric(boolean exportSensorDataAsMetric);

    public String[] getMicroserviceTopics() {
        return microserviceTopics;
    }

    public String getMicroServiceDataType() {
        return microserviceDataType;
    }

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
        sb.append('}');
        return sb.toString();
    }

}
