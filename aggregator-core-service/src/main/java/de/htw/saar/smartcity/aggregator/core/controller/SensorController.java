package de.htw.saar.smartcity.aggregator.core.controller;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @GetMapping("/")
    public ResponseEntity getSensors() {
        List<Sensor> sensors = sensorService.findAllSensors();

        return new ResponseEntity(sensors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getSensor(@PathVariable Long id) {
        ResponseEntity response;
        Optional<Sensor> sensor = sensorService.findSensorById(id);

        if(sensor.isPresent())
            response = new ResponseEntity(sensor.get(), HttpStatus.OK);
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sensor with the given id found.");

        return response;
    }

    @PostMapping("/")
    public ResponseEntity postSensor(@RequestBody Sensor sensor) {
        Sensor saved = sensorService.saveSensor(sensor);

        return new ResponseEntity(saved, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity putSensor(@RequestBody Sensor sensor, @PathVariable Long id) {
        Sensor saved = sensorService.findSensorById(id)
                .map(s -> {
                    s.replaceOwnAttributesWithOther(sensor);
                    return sensorService.saveSensor(s);
                })
                .orElseGet(() -> {
                    sensor.setId(id);
                    return sensorService.saveSensor(sensor);
                });

        return new ResponseEntity(saved, HttpStatus.OK);
    }

}
