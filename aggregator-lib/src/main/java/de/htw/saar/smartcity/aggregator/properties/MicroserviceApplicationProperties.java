package de.htw.saar.smartcity.aggregator.properties;

import java.util.Arrays;

public abstract class MicroserviceApplicationProperties extends ApplicationProperties {

    protected String microserviceName;
    protected  String microserviceURL;
    protected String microserviceQueue;
    protected String[] microserviceTopics;
    protected String microserviceBucket;


    public abstract void setMicroserviceName(String microserviceName);

    public abstract void setMicroserviceURL(String microserviceURL);

    public abstract void setMicroserviceQueue(String microserviceQueue);

    public abstract void setMicroserviceTopics(String[] microserviceTopics);

    public abstract void setMicroserviceBucket(String microserviceBucket);

    public String getMicroserviceName() {
        return microserviceName;
    }

    public String getMicroserviceQueue() {
        return microserviceQueue;
    }

    public String[] getMicroserviceTopics() {
        return microserviceTopics;
    }

    public String getMicroserviceBucket() {
        return microserviceBucket;
    }

    public String getMicroserviceURL() {
        return microserviceURL;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\tmicroserviceName: " + microserviceName);
        sb.append("\tmicroserviceURL: " + microserviceURL);
        sb.append("\tmicroserviceQueue: " + microserviceQueue);
        sb.append("\tmicroserviceTopics:");
        Arrays.stream(microserviceTopics).forEach(s -> sb.append(s+", "));
        sb.append("\tmicroserviceBucket: " + microserviceBucket);
        return sb.toString();
    }
}
