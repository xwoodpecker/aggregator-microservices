package de.htw.saar.smartcity.aggregator.lib.base;

import de.htw.saar.smartcity.aggregator.lib.entity.SensorType;
import de.htw.saar.smartcity.aggregator.lib.service.SensorTypeService;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.MicroserviceService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

public abstract class BaseSetupDataLoader extends SetupDataLoader {

    private final BaseMicroserviceApplicationProperties baseMicroserviceApplicationProperties;
    private final SensorTypeService sensorTypeService;

    public BaseSetupDataLoader(MicroserviceService microserviceService, SensorTypeService sensorTypeService, BaseMicroserviceApplicationProperties baseMicroserviceApplicationProperties) {
        super(microserviceService, baseMicroserviceApplicationProperties);
        this.sensorTypeService = sensorTypeService;
        this.baseMicroserviceApplicationProperties = baseMicroserviceApplicationProperties;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup)
            return;

        createMicroserviceIfNotFound(baseMicroserviceApplicationProperties.getMicroserviceName(), baseMicroserviceApplicationProperties.getMicroserviceURL());
        createSensorTypeIfNotFound(baseMicroserviceApplicationProperties.getMicroServiceSensorType());

        alreadySetup = true;
    }

    @Transactional
    SensorType createSensorTypeIfNotFound(String sensorTypeName) {
        SensorType sensorType = sensorTypeService.findSensorTypeByName(sensorTypeName);
        if(sensorType == null) {
            sensorType = new SensorType();
            sensorType.setName(sensorTypeName);
            sensorTypeService.saveSensorType(sensorType);
        }
        return sensorType;
    }

}