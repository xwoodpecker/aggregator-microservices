package de.htw.saar.smartcity.aggregator.exporter.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.ExporterApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExporterApplicationPropertiesImpl extends ExporterApplicationProperties {

    @Override
    @Value("${EXPORT_SENSOR_DATA_TYPES}")
    protected void setExportedSensorDataTypes(String[] exportedSensorDataTypes) {
        this.exportedSensorDataTypes = exportedSensorDataTypes;
    }

    @Override
    @Value("${EXPORT_AGGREGATOR_DATA_TYPES}")
    protected void setExportedAggregatorDataTypes(String[] exportedAggregatorDataTypes) {
        this.exportedAggregatorDataTypes = exportedAggregatorDataTypes;

    }
}
