package de.htw.saar.smartcity.aggregator.temperature.handler;

import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.service.SensorTypeService;
import de.htw.saar.smartcity.aggregator.lib.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class TemperatureMeasurementHandler extends MeasurementHandler {


    public TemperatureMeasurementHandler(BaseMicroserviceApplicationProperties baseMicroserviceApplicationProperties, SensorTypeService sensorTypeService, MeasurementFactory measurementFactory, StorageWrapper storageWrapper) {
        super(baseMicroserviceApplicationProperties, sensorTypeService, measurementFactory, storageWrapper);
    }
}
