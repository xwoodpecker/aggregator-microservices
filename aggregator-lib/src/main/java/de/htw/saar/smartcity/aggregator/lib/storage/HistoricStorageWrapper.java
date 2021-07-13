package de.htw.saar.smartcity.aggregator.lib.storage;

import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.properties.MinioApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class HistoricStorageWrapper {

    private static final Logger log = LoggerFactory.getLogger(HistoricStorageWrapper.class);

    private final MinioClientWrapper minioClientWrapper;


    public HistoricStorageWrapper(MinioApplicationProperties applicationProperties) throws Exception {

        this.minioClientWrapper = new MinioClientWrapper(applicationProperties);
    }

    public List<Measurement> getMeasurementsByPrefix(String prefix) {

        return minioClientWrapper.getObjectsWithPrefix(prefix, Measurement.class);
    }

    public String putHistoricMeasurement(String objName, Measurement m) {

        return minioClientWrapper.putObject(m, objName) ? objName : null;
    }
}
