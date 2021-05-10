package de.htw.saar.smartcity.aggregator.lib.properties;

import java.util.Arrays;

public abstract class BaseMicroserviceApplicationProperties extends MicroserviceApplicationProperties {

    protected String microserviceQueue;

    protected String[] microserviceTopics;

    protected String microserviceSensorType;

    public abstract void setMicroserviceQueue(String microserviceQueue);

    public abstract void setMicroserviceTopics(String[] microserviceTopics);

    public abstract void setMicroserviceSensorType(String microserviceSensorType);

    public String getMicroserviceQueue() {
        return microserviceQueue;
    }

    public String[] getMicroserviceTopics() {
        return microserviceTopics;
    }

    public String getMicroServiceSensorType() {
        return microserviceSensorType;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\tmicroserviceQueue: " + microserviceQueue);
        sb.append("\tmicroserviceTopics:").append(microserviceTopics == null ? "null" : Arrays.asList(microserviceTopics).toString());
        sb.append("\tmicroserviceSensorType: " + microserviceSensorType);
        return sb.toString();
    }
}
