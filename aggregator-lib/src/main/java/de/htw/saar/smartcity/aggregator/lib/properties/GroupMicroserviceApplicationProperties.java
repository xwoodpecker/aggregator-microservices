package de.htw.saar.smartcity.aggregator.lib.properties;

/**
 * The type Group microservice application properties.
 */
public abstract class GroupMicroserviceApplicationProperties extends MicroserviceApplicationProperties implements GroupApplicationProperties {

    /**
     * The Microservice group type name.
     */
    protected String microserviceGroupTypeName;

    /**
     * Sets microservice group type name.
     *
     * @param microserviceGroupTypeName the microservice group type name
     */
    public abstract void setGroupTypeName(String microserviceGroupTypeName);

    /**
     * Gets microservice group type name.
     *
     * @return the microservice group type name
     */
    @Override
    public String getGroupTypeName() {
        return microserviceGroupTypeName;
    }


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
