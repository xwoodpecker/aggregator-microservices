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

    private String openWeatherAPIKey;

    private List<String> openWeatherAPICities;

    @Value("${OPEN_WEATHER_API_KEY}")
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
    @Value("#{'${OPEN_WEATHER_API_CITIES}'.split(',')}")
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
}
