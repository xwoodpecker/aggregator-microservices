package de.htw.saar.smartcity.virtualization.api;

/**
 * The type Weather data.
 */
public class WeatherData {

    private Double temperature;

    private Double humidity;

    private Boolean rainy;

    /**
     * Instantiates a new Weather data.
     */
    public WeatherData() {
    }

    /**
     * Instantiates a new Weather data.
     *
     * @param temperature the temperature
     * @param humidity    the humidity
     * @param rainy       the rainy
     */
    public WeatherData(Double temperature, Double humidity, Boolean rainy) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.rainy = rainy;
    }

    /**
     * Gets temperature.
     *
     * @return the temperature
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     * Sets temperature.
     *
     * @param temperature the temperature
     */
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    /**
     * Gets humidity.
     *
     * @return the humidity
     */
    public Double getHumidity() {
        return humidity;
    }

    /**
     * Sets humidity.
     *
     * @param humidity the humidity
     */
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    /**
     * Gets rainy.
     *
     * @return the rainy
     */
    public Boolean getRainy() {
        return rainy;
    }

    /**
     * Sets rainy.
     *
     * @param rainy the rainy
     */
    public void setRainy(Boolean rainy) {
        this.rainy = rainy;
    }
}
