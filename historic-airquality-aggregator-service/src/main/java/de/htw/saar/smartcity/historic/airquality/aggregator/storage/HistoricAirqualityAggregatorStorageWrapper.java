package de.htw.saar.smartcity.historic.airquality.aggregator.storage;

import de.htw.saar.smartcity.aggregator.lib.properties.MinioApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.storage.HistoricStorageWrapper;
import org.springframework.stereotype.Component;

@Component
public class HistoricAirqualityAggregatorStorageWrapper extends HistoricStorageWrapper {

    public HistoricAirqualityAggregatorStorageWrapper(MinioApplicationProperties applicationProperties) throws Exception {
        super(applicationProperties);
    }
}
