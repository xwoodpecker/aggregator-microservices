package de.htw.saar.smartcity.aggregator.water.handler;

import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class WaterRawMeasurementHandler extends RawMeasurementHandler {

    public WaterRawMeasurementHandler(RawMicroserviceApplicationProperties rawMicroserviceApplicationProperties,
                                      DataTypeService dataTypeService,
                                      MeasurementFactory measurementFactory,
                                      StorageWrapper storageWrapper,
                                      Publisher publisher) {

        super(rawMicroserviceApplicationProperties, dataTypeService, measurementFactory, storageWrapper, publisher);
    }
}
