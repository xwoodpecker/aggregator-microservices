package de.htw.saar.smartcity.aggregator.lib.properties;

import java.util.Arrays;

public abstract class RawMicroserviceApplicationProperties extends MicroserviceApplicationProperties {


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
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\tmicroserviceTopics:").append(microserviceTopics == null ? "null" : Arrays.asList(microserviceTopics).toString());
        sb.append("\tmicroserviceDataType: " + microserviceDataType);
        return sb.toString();
    }
}
