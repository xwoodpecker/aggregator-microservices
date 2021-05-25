package de.htw.saar.smartcity.aggregator.lib.properties;

import java.util.Arrays;

public abstract class RawMicroserviceApplicationProperties extends MicroserviceApplicationProperties {


    protected String[] microserviceTopics;

    protected String microserviceSensorType;

    public abstract void setMicroserviceTopics(String[] microserviceTopics);

    public abstract void setMicroserviceSensorType(String microserviceSensorType);

    public String[] getMicroserviceTopics() {
        return microserviceTopics;
    }

    public String getMicroServiceSensorType() {
        return microserviceSensorType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\tmicroserviceTopics:").append(microserviceTopics == null ? "null" : Arrays.asList(microserviceTopics).toString());
        sb.append("\tmicroserviceSensorType: " + microserviceSensorType);
        return sb.toString();
    }
}
