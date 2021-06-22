package de.htw.saar.smartcity.aggregator.exporter.base;

import de.htw.saar.smartcity.aggregator.lib.exporter.CustomCollector;
import de.htw.saar.smartcity.aggregator.lib.properties.ExporterApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import org.springframework.stereotype.Component;

@Component
public class CustomCollectorImpl extends CustomCollector {

    public CustomCollectorImpl(ExporterApplicationProperties exporterApplicationProperties, SensorService sensorService, AggregatorService aggregatorService) {
        super(exporterApplicationProperties, sensorService, aggregatorService);
    }
}
