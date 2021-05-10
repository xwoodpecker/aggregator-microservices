package de.htw.saar.smartcity.aggregator.lib.properties;

public abstract class MicroserviceApplicationProperties extends ApplicationProperties {

    protected String microserviceName;
    protected  String microserviceURL;
    protected String microserviceBucket;


    public abstract void setMicroserviceName(String microserviceName);

    public abstract void setMicroserviceURL(String microserviceURL);

    public abstract void setMicroserviceBucket(String microserviceBucket);


    public String getMicroserviceName() {
        return microserviceName;
    }

    public String getMicroserviceURL() {
        return microserviceURL;
    }

    public String getMicroserviceBucket() {
        return microserviceBucket;
    }


    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("\tmicroserviceName: " + microserviceName);
        sb.append("\tmicroserviceURL: " + microserviceURL);
        sb.append("\tmicroserviceBucket: " + microserviceBucket);
        return sb.toString();
    }

}
