package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SensorTypeIdResolver implements ObjectIdResolver {
    private final SensorTypeService sensorTypeService;

    public SensorTypeIdResolver(@Autowired SensorTypeService sensorTypeService) {
        this.sensorTypeService = sensorTypeService;
    }

    @Override
    public void bindItem(ObjectIdGenerator.IdKey idKey, Object o) {

    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey idKey) {
        if(idKey.key instanceof Long)
            return sensorTypeService.findSensorTypeById((Long)idKey.key).get();
        else
            return null;
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object o) {
        return this;
    }

    @Override
    public boolean canUseFor(ObjectIdResolver objectIdResolver) {
        return false;
    }
}
