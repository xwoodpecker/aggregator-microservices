package de.htw.saar.smartcity.aggregator.temperature.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class TemperatureRawMeasurementHandler extends RawMeasurementHandler {

    protected TemperatureRawMeasurementHandler(StorageWrapper storageWrapper,
                                               Publisher publisher,
                                               RawMicroserviceApplicationProperties rawMicroserviceApplicationProperties,
                                               DataTypeService dataTypeService,
                                               MeasurementFactory measurementFactory) {

        super(storageWrapper, publisher, rawMicroserviceApplicationProperties, dataTypeService, measurementFactory);
    }
}
