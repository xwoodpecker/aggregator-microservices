package de.htw.saar.smartcity.aggregator.lib.properties;

/**
 * The type Group microservice application properties.
 */
public abstract class GroupMicroserviceApplicationProperties extends MicroserviceApplicationProperties {

    /**
     * The Microservice group type name.
     */
    protected String microserviceGroupTypeName;

    /**
     * The Microservice data type name.
     */
    protected String microserviceDataTypeName;

    /**
     * Sets microservice group type name.
     *
     * @param microserviceGroupTypeName the microservice group type name
     */
    public abstract void setMicroserviceGroupTypeName(String microserviceGroupTypeName);

    /**
     * Gets microservice group type name.
     *
     * @return the microservice group type name
     */
    public String getMicroserviceGroupTypeName() {
        return microserviceGroupTypeName;
    }

    /**
     * Sets microservice data type name.
     *
     * @param microserviceDataTypeName the microservice data type name
     */
    public abstract void setMicroserviceDataTypeName(String microserviceDataTypeName);

    /**
     * Gets microservice data type name.
     *
     * @return the microservice data type name
     */
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
