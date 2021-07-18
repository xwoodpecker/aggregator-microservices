package de.htw.saar.smartcity.virtualization.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


public final class OpenWeatherAPIWrapper {

    private static final Logger log = LoggerFactory.getLogger(OpenWeatherAPIWrapper.class);

    private OpenWeatherAPIWrapper() {

    }

    //todo: put in config
    private final static String APP_ID = "a82a6669f501863632f16529cff1f36a";

    public static TemperatureHumidity getTemperatureHumidity(Integer cityId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?id=%s&appid=%s",
                cityId,
                APP_ID
        );

        //todo: comment in
        //log.info("Requesting weather data from open weather API...");
        RequestEntity<String> requestEntity = new RequestEntity<>(
                HttpMethod.GET, URI.create(url)
        );

        ResponseEntity<String> response
                = restTemplate.exchange(requestEntity, String.class);

        //todo: comment in
        //log.info("Weather data received. Extracting temperature and humidity...");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            JsonNode temperatureNode = jsonNode.findValue("temp");
            Double temperature = Math.round((temperatureNode.asDouble() - 273.15) * 100.0) / 100.0;

            JsonNode humidityNode = jsonNode.findValue("humidity");
            Double humidity = Math.round(humidityNode.asDouble() * 100.0) / 100.0;
            return new TemperatureHumidity(temperature, humidity);

        } catch (JsonProcessingException e) {
            log.error("Could not process weather data.");
            //todo: comment in
            //e.printStackTrace();
        }
        return null;
    }
}
