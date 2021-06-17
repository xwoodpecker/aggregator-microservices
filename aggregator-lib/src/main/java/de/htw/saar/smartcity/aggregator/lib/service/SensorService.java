package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.exception.GroupTypeNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.exception.SensorNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Sensor service.
 */
@Service
public class SensorService {

    private SensorRepository sensorRepository;


    /**
     * Instantiates a new Sensor service.
     *
     * @param sensorRepository the sensor repository
     */
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    /**
     * Save sensor sensor.
     *
     * @param sensor the sensor
     * @return the sensor
     */
    public Sensor saveSensor(Sensor sensor) {

        if (sensorRepository.findSensorByName(sensor.getName()) != null)
            throw new SensorNameAlreadyInUseException(sensor.getName());

        return sensorRepository.save(sensor);
    }

    /**
     * Find sensor by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Sensor> findSensorById(Long id) {
        return sensorRepository.findById(id);
    }

    /**
     * Find sensor by name sensor.
     *
     * @param name the name
     * @return the sensor
     */
    public Sensor findSensorByName(String name) {
        return sensorRepository.findSensorByName(name);
    }

    /**
     * Find all sensors list.
     *
     * @return the list
     */
    public List<Sensor> findAllSensors() {
        return sensorRepository.findAll();
    }

    /**
     * Delete sensor.
     *
     * @param sensor the sensor
     */
    public void deleteSensor(Sensor sensor) {

        sensor.getGroups().forEach(g -> g.getProducers().removeIf(s -> s.equals(sensor)));
        sensor.setGroups(null);
        sensor.getTags().forEach(t -> t.getProducers().removeIf(s -> s.equals(sensor)));
        sensor.setTags(null);
        sensorRepository.deleteById(sensor.getId());
    }
}
