package de.htw.saar.smartcity.aggregator.lib.properties;

public abstract class MicroserviceApplicationProperties extends ApplicationProperties {

    protected String microserviceBucket;

    public abstract void setMicroserviceBucket(String microserviceBucket);

    public String getMicroserviceBucket() {
        return microserviceBucket;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\tmicroserviceBucket: " + microserviceBucket);
        return sb.toString();
    }

}
