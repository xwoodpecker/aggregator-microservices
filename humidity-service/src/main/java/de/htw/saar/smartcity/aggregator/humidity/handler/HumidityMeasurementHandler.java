package de.htw.saar.smartcity.aggregator.humidity.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Producer;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.handler.MeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.BaseMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.SensorTypeService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class HumidityMeasurementHandler extends MeasurementHandler {

    public HumidityMeasurementHandler(BaseMicroserviceApplicationProperties baseMicroserviceApplicationProperties,
                                      SensorTypeService sensorTypeService,
                                      MeasurementFactory measurementFactory,
                                      StorageWrapper storageWrapper,
                                      Producer producer) {

        super(baseMicroserviceApplicationProperties, sensorTypeService, measurementFactory, storageWrapper, producer);
    }
}
