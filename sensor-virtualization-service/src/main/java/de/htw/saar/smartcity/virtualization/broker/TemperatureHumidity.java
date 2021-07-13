package de.htw.saar.smartcity.virtualization.broker;

public class TemperatureHumidity {

    private Double temperature;

    private Double humidity;

    public TemperatureHumidity() {
    }

    public TemperatureHumidity(Double temperature, Double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}
