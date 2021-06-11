package de.htw.saar.smartcity.aggregator.lib.properties;

public abstract class GroupMicroserviceApplicationProperties extends ApplicationProperties {

    protected String microserviceGroupTypeName;

    protected String microserviceDataTypeName;

    public abstract void setMicroserviceGroupTypeName(String microserviceGroupTypeName);

    public String getMicroserviceGroupTypeName() {
        return microserviceGroupTypeName;
    }

    public abstract void setMicroserviceDataTypeName(String microserviceDataTypeName);

    public String getMicroserviceDataTypeName() {
        return microserviceDataTypeName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GroupMicroserviceApplicationProperties{");
        sb.append("microserviceQueue='").append(microserviceQueue).append('\'');
        sb.append(", microserviceBucket='").append(microserviceBucket).append('\'');
        sb.append(", memcachedHost='").append(memcachedHost).append('\'');
        sb.append(", memcachedPort='").append(memcachedPort).append('\'');
        sb.append(", microserviceGroupTypeName='").append(microserviceGroupTypeName).append('\'');
        sb.append(", microserviceDataTypeName='").append(microserviceDataTypeName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
