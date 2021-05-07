package de.htw.saar.smartcity.aggregator.service;

import de.htw.saar.smartcity.aggregator.entity.Sensor;
import de.htw.saar.smartcity.aggregator.repository.SensorRepository;
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
}
