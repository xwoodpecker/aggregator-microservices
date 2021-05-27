package de.htw.saar.smartcity.aggregator.lib.properties;

public abstract class GroupMicroserviceApplicationProperties extends MicroserviceApplicationProperties {

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
        sb.append(", microserviceGroupTypeName='").append(microserviceGroupTypeName).append('\'');
        sb.append(", microserviceDataType='").append(microserviceDataTypeName).append('\'');
        sb.append(", microserviceBucket='").append(microserviceBucket).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
