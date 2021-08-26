package de.htw.saar.smartcity.virtualization.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.AbstractBrokerApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * The type Virtualization application properties.
 */
@Configuration
public class VirtualizationApplicationProperties extends AbstractBrokerApplicationProperties {

    private Integer temperatureAgentCount;

    private Integer humidityAgentCount;

    private Integer pictureAgentCount;

    private Integer waterAgentCount;

    private Integer airqualityAgentCount;

    private Integer interval;

    private String openWeatherAPIKey;

    private List<String> openWeatherAPICities;

    @Value("${OPEN_WEATHER_API_KEY:#{null}}")
    private void setOpenWeatherAPIKey(String openWeatherAPIKey) {
        this.openWeatherAPIKey = openWeatherAPIKey;
    }

    /**
     * Gets open weather api key.
     *
     * @return the open weather api key
     */
    public String getOpenWeatherAPIKey() {
        return openWeatherAPIKey;
    }

    /**
     * Sets open weather api cities.
     *
     * @param openWeatherAPICities the open weather api cities
     */
    @Value("#{'${OPEN_WEATHER_API_CITIES:}'.split(',')}")
    public void setOpenWeatherAPICities(List<String> openWeatherAPICities) {
        this.openWeatherAPICities = openWeatherAPICities;
    }

    /**
     * Gets open weather api cities.
     *
     * @return the open weather api cities
     */
    public List<String> getOpenWeatherAPICities() {
        return openWeatherAPICities;
    }


    /**
     * Gets temperature agent count.
     *
     * @return the temperature agent count
     */
    public Integer getTemperatureAgentCount() {
        return temperatureAgentCount;
    }

    /**
     * Sets temperature agent count.
     *
     * @param temperatureAgentCount the temperature agent count
     */
    @Value("#{new Integer('${TEMPERATURE_AGENT_COUNT}')}")
    public void setTemperatureAgentCount(Integer temperatureAgentCount) {
        this.temperatureAgentCount = temperatureAgentCount;
    }

    /**
     * Gets humidity agent count.
     *
     * @return the humidity agent count
     */
    public Integer getHumidityAgentCount() {
        return humidityAgentCount;
    }

    /**
     * Sets humidity agent count.
     *
     * @param humidityAgentCount the humidity agent count
     */
    @Value("#{new Integer('${HUMIDITY_AGENT_COUNT}')}")
    public void setHumidityAgentCount(Integer humidityAgentCount) {
        this.humidityAgentCount = humidityAgentCount;
    }

    /**
     * Gets picture agent count.
     *
     * @return the picture agent count
     */
    public Integer getPictureAgentCount() {
        return pictureAgentCount;
    }

    /**
     * Sets picture agent count.
     *
     * @param pictureAgentCount the picture agent count
     */
    @Value("#{new Integer('${PICTURE_AGENT_COUNT}')}")
    public void setPictureAgentCount(Integer pictureAgentCount) {
        this.pictureAgentCount = pictureAgentCount;
    }

    /**
     * Gets water agent count.
     *
     * @return the water agent count
     */
    public Integer getWaterAgentCount() {
        return waterAgentCount;
    }

    /**
     * Sets water agent count.
     *
     * @param waterAgentCount the water agent count
     */
    @Value("#{new Integer('${WATER_AGENT_COUNT}')}")
    public void setWaterAgentCount(Integer waterAgentCount) {
        this.waterAgentCount = waterAgentCount;
    }

    /**
     * Gets airquality agent count.
     *
     * @return the airquality agent count
     */
    public Integer getAirqualityAgentCount() {
        return airqualityAgentCount;
    }

    /**
     * Sets airquality agent count.
     *
     * @param airqualityAgentCount the airquality agent count
     */
    @Value("#{new Integer('${AIRQUALITY_AGENT_COUNT}')}")
    public void setAirqualityAgentCount(Integer airqualityAgentCount) {
        this.airqualityAgentCount = airqualityAgentCount;
    }

    /**
     * Gets interval.
     *
     * @return the interval
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * Sets interval.
     *
     * @param interval the interval
     */
    @Value("#{new Integer('${SIMULATION_INTERVAL}')}")
    public void setInterval(Integer interval) {
        this.interval = interval;
    }
}
