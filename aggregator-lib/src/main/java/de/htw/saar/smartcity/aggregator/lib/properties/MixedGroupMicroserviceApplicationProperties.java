package de.htw.saar.smartcity.aggregator.lib.properties;

public abstract class MixedGroupMicroserviceApplicationProperties extends MicroserviceApplicationProperties {

    protected String microserviceGroupTypeName;

    public abstract void setMicroserviceGroupTypeName(String microserviceGroupTypeName);

    public String getMicroserviceGroupTypeName() {
        return microserviceGroupTypeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\tmicroserviceGroupTypeName: " + microserviceGroupTypeName);
        return sb.toString();
    }

}
