package de.htw.saar.smartcity.historic.temperature.aggregator.storage;

import de.htw.saar.smartcity.aggregator.lib.properties.MinioApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.storage.HistoricStorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class HistoricTemperatureAggregatorStorageWrapper extends HistoricStorageWrapper {

    public HistoricTemperatureAggregatorStorageWrapper(MinioApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
