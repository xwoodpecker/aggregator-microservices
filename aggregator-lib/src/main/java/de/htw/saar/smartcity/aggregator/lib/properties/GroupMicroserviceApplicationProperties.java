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
     * @param applicationDataType the microservice data type name
     */
    public abstract void setApplicationDataType(String applicationDataType);


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GroupMicroserviceApplicationProperties{");
        sb.append("minioEndpoint='").append(minioEndpoint).append('\'');
        sb.append(", minioAccessKey='").append(minioAccessKey).append('\'');
        sb.append(", minioSecretKey='").append(minioSecretKey).append('\'');
        sb.append(", onlySaveMeasurementValue=").append(onlySaveMeasurementValue);
        sb.append(", minioBucketName='").append(minioBucketName).append('\'');
        sb.append(", memcachedHost='").append(memcachedHost).append('\'');
        sb.append(", memcachedPort='").append(memcachedPort).append('\'');
        sb.append(", applicationDataType='").append(applicationDataType).append('\'');
        sb.append(", microserviceGroupTypeName='").append(microserviceGroupTypeName).append('\'');
        sb.append(", microserviceQueue='").append(microserviceTopicName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
