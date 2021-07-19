package de.htw.saar.smartcity.virtualization.broker;

public class WeatherData {

    private Double temperature;

    private Double humidity;

    private Boolean rainy;

    public WeatherData() {
    }

    public WeatherData(Double temperature, Double humidity, Boolean rainy) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.rainy = rainy;
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

    public Boolean getRainy() {
        return rainy;
    }

    public void setRainy(Boolean rainy) {
        this.rainy = rainy;
    }
}
