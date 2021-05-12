package de.htw.saar.smartcity.aggregator.core.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.saar.smartcity.aggregator.lib.entity.Microservice;
import de.htw.saar.smartcity.aggregator.lib.model.SensorGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class GroupMeasurementBuilder {

    private RestTemplate restTemplate;

    private ConcurrentMap<Long, SensorGroupMeasurement> sensorGroupMeasurements = new ConcurrentHashMap<>();

    public GroupMeasurementBuilder(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void addSensorMeasurementForGroup(Group group, SensorMeasurement sensorMeasurement) {
        SensorGroupMeasurement sensorGroupMeasurement = sensorGroupMeasurements.getOrDefault(group.getId(), new SensorGroupMeasurement(group));
        sensorGroupMeasurement.addSensorMeasurement(sensorMeasurement);
        if(sensorGroupMeasurement.allValuesSet())
        {
            for(Microservice microservice : group.getMicroservices()) {
                postSensorGroupMeasurement(microservice.getUrl() + "/", sensorGroupMeasurement);
            }
            sensorGroupMeasurement.reset();
        }
        sensorGroupMeasurements.put(group.getId(), sensorGroupMeasurement);
    }

    private void postSensorGroupMeasurement(String url, SensorGroupMeasurement sensorGroupMeasurement) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(sensorGroupMeasurement);
            HttpEntity<String> request =
                    new HttpEntity<>(json, headers);
            ResponseEntity<String> responseEntity = restTemplate.
                    postForEntity(url, request, String.class);
            HttpStatus status = responseEntity.getStatusCode();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
