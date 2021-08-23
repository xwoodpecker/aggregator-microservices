package de.htw.saar.smartcity.virtualization.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Locale;


/**
 * The type Open weather api wrapper.
 */
public final class OpenWeatherAPIWrapper {

    private static final Logger log = LoggerFactory.getLogger(OpenWeatherAPIWrapper.class);

    private static String apiKey;

    private OpenWeatherAPIWrapper() {

    }

    /**
     * Gets api key.
     *
     * @return the api key
     */
    public static String getApiKey() {
        return apiKey;
    }

    /**
     * Sets api key.
     *
     * @param apiKey the api key
     */
    public static void setApiKey(String apiKey) {
        OpenWeatherAPIWrapper.apiKey = apiKey;
    }

    /**
     * Gets temperature humidity.
     *
     * @param cityId the city id
     * @return the temperature humidity
     */
    public static WeatherData getTemperatureHumidity(Integer cityId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?id=%s&appid=%s",
                cityId,
                apiKey
        );

        log.info("Requesting weather data from open weather API...");
        RequestEntity<String> requestEntity = new RequestEntity<>(
                HttpMethod.GET, URI.create(url)
        );

        ResponseEntity<String> response
                = restTemplate.exchange(requestEntity, String.class);

        log.info("Weather data received. Extracting temperature and humidity...");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            JsonNode temperatureNode = jsonNode.findValue("temp");
            Double temperature = Math.round((temperatureNode.asDouble() - 273.15) * 100.0) / 100.0;

            JsonNode humidityNode = jsonNode.findValue("humidity");
            Double humidity = Math.round(humidityNode.asDouble() * 100.0) / 100.0;


            JsonNode weatherNode = jsonNode.findValue("weather");
            JsonNode mainNode = weatherNode.findValue("main");
            Boolean rainy = isRainy(mainNode.asText());
            return new WeatherData(temperature, humidity, rainy);

        } catch (JsonProcessingException e) {
            log.error("Could not process weather data.");
            //e.printStackTrace();
        }
        return null;
    }

    /**
     * checks weather the weather string should be considered as rainy
     * @param weather string containing weather data
     * @return if it is rainy
     */
    private static Boolean isRainy(String weather) {
        weather = weather.toLowerCase(Locale.ROOT);
        return (weather.contains("rain") || weather.contains("snow") || weather.contains("extreme"));
    }
}
