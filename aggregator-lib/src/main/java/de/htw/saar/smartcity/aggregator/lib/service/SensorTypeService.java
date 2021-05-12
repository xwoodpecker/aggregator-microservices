package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.repository.SensorTypeRepository;
import de.htw.saar.smartcity.aggregator.lib.entity.SensorType;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class SensorTypeService {

    private SensorTypeRepository sensorTypeRepository;

    public SensorTypeService(SensorTypeRepository sensorTypeRepository) {
        this.sensorTypeRepository = sensorTypeRepository;
    }

    public SensorType findSensorTypeByName(String name) {
        return sensorTypeRepository.findSensorTypeByName(name);
    }

    public SensorType saveSensorType(SensorType sensorType) {
        return sensorTypeRepository.save(sensorType);
    }

    public Optional<SensorType> findSensorTypeById(Long id) {
        return sensorTypeRepository.findById(id);
    }
}
