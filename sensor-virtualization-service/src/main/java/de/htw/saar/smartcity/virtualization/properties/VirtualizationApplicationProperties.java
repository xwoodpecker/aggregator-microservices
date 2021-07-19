package de.htw.saar.smartcity.virtualization.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.AbstractBrokerApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class VirtualizationApplicationProperties extends AbstractBrokerApplicationProperties {

    private String openWeatherAPIKey;

    private List<String> openWeatherAPICities;

    @Value("${OPEN_WEATHER_API_KEY}")
    private void setOpenWeatherAPIKey(String openWeatherAPIKey) {
        this.openWeatherAPIKey = openWeatherAPIKey;
    }

    public String getOpenWeatherAPIKey() {
        return openWeatherAPIKey;
    }

    @Value("#{'${OPEN_WEATHER_API_CITIES}'.split(',')}")
    public void setOpenWeatherAPICities(List<String> openWeatherAPICities) {
        this.openWeatherAPICities = openWeatherAPICities;
    }

    public List<String> getOpenWeatherAPICities() {
        return openWeatherAPICities;
    }
}
