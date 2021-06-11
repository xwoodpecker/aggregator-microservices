package de.htw.saar.smartcity.aggregator.lib.properties;

import java.util.Arrays;

public abstract class RawMicroserviceApplicationProperties extends ApplicationProperties {

    protected String[] microserviceTopics;

    protected String microserviceDataType;

    public abstract void setMicroserviceTopics(String[] microserviceTopics);

    public abstract void setMicroserviceDataType(String microserviceDataType);

    public String[] getMicroserviceTopics() {
        return microserviceTopics;
    }

    public String getMicroServiceDataType() {
        return microserviceDataType;
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
