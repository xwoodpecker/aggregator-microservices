package de.htw.saar.smartcity.historic.humidity.aggregator;

import de.htw.saar.smartcity.aggregator.lib.properties.MinioApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.storage.HistoricStorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class HistoricHumidityAggregatorStorageWrapper extends HistoricStorageWrapper {

    public HistoricHumidityAggregatorStorageWrapper(MinioApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
