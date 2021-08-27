package de.htw.saar.smartcity.aggregator.exporter.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.ExporterApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExporterApplicationPropertiesImpl extends ExporterApplicationProperties {

    @Override
    @Value("${EXPORT_SENSOR_DATA_TYPES:#{null}}")
    protected void setExportedSensorDataTypes(String[] exportedSensorDataTypes) {
        this.exportedSensorDataTypes = exportedSensorDataTypes;
    }

    @Override
    @Value("${EXPORT_AGGREGATOR_DATA_TYPES:#{null}}")
    protected void setExportedAggregatorDataTypes(String[] exportedAggregatorDataTypes) {
        this.exportedAggregatorDataTypes = exportedAggregatorDataTypes;
    }

    @Override
    @Value("${EXPORT_START_WITH_ID:#{null}}")
    protected void setStartWithId(Long startWithId) {
        this.startWithId = startWithId;
    }

    @Override
    @Value("${EXPORT_END_WITH_ID:#{null}}")
    protected void setEndWithId(Long endWithId) {
        this.endWithId = endWithId;
    }

}
