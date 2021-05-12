package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    private SensorRepository sensorRepository;


    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Sensor saveSensor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public Optional<Sensor> findSensorById(Long id) {
        return sensorRepository.findById(id);
    }

    public Sensor findSensorByName(String name) {
        return sensorRepository.findSensorByName(name);
    }

    public List<Sensor> findAllSensors() {
        return sensorRepository.findAll();
    }

    public void deleteSensor(Sensor sensor) {
        sensor.getGroups().forEach(g -> g.getMembers().removeIf(m -> m.getId() == sensor.getId()));
        sensorRepository.deleteById(sensor.getId());
    }
}
