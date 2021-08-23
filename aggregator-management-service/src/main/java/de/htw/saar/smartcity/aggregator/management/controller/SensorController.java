package de.htw.saar.smartcity.aggregator.management.controller;

import de.htw.saar.smartcity.aggregator.management.exception.SensorNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Sensor controller.
 */
@RestController
@RequestMapping(path = "/sensors")
public class SensorController {

    private final SensorService sensorService;

    /**
     * Instantiates a new Sensor controller.
     *
     * @param sensorService the sensor service
     */
    public SensorController(SensorService sensorService) {

        this.sensorService = sensorService;
    }

    /**
     * Gets sensors.
     *
     * @return the sensors
     */
    @GetMapping("/")
    public ResponseEntity getSensors() {

        return new ResponseEntity(
                sensorService.findAllSensors(),
                HttpStatus.OK
        );
    }

    /**
     * Gets sensor.
     *
     * @param id the id
     * @return the sensor
     */
    @GetMapping("/{id}")
    public ResponseEntity getSensor(@PathVariable Long id) {

        return new ResponseEntity(
                sensorService.findSensorById(id)
                        .orElseThrow(() -> new SensorNotFoundException(id)),
                HttpStatus.OK
        );
    }

    /**
     * Post sensor response entity.
     *
     * @param sensor the sensor
     * @return the response entity
     */
    @PostMapping("/")
    public ResponseEntity postSensor(@RequestBody Sensor sensor) {

        return new ResponseEntity(
                sensorService.saveSensor(sensor),
                HttpStatus.OK
        );
    }

    /**
     * Put sensor response entity.
     *
     * @param sensor the sensor
     * @param id     the id
     * @return the response entity
     */
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

    /**
     * Delete sensor response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteSensor(@PathVariable Long id) {;

        sensorService.deleteSensor(
                sensorService.findSensorById(id)
                        .orElseThrow(() -> new SensorNotFoundException(id))
        );

        return new ResponseEntity(HttpStatus.OK);
    }

}
