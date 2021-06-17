package de.htw.saar.smartcity.aggregator.groups.controller;

import de.htw.saar.smartcity.aggregator.groups.exception.SensorNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/sensors")
public class SensorController {

    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {

        this.sensorService = sensorService;
    }

    @GetMapping("/")
    public ResponseEntity getSensors() {

        return new ResponseEntity(
                sensorService.findAllSensors(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getSensor(@PathVariable Long id) {

        return new ResponseEntity(
                sensorService.findSensorById(id)
                        .orElseThrow(() -> new SensorNotFoundException(id)),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity postSensor(@RequestBody Sensor sensor) {

        return new ResponseEntity(
                sensorService.saveSensor(sensor),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity putSensor(@RequestBody Sensor sensor, @PathVariable Long id) {

        Sensor saved = sensorService.findSensorById(id)
                .map(s -> {
                    s.replaceOwnAttributesWithOther(sensor);
                    return sensorService.updateSensor(s);
                })
                .orElseGet(() -> {
                    sensor.setId(id);
                    return sensorService.saveSensor(sensor);
                });

        return new ResponseEntity(
                saved,
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteSensor(@PathVariable Long id) {;

        sensorService.deleteSensor(
                sensorService.findSensorById(id)
                        .orElseThrow(() -> new SensorNotFoundException(id))
        );

        return new ResponseEntity(HttpStatus.OK);
    }

}
