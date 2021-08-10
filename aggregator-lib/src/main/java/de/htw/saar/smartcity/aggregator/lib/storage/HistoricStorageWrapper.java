package de.htw.saar.smartcity.aggregator.lib.storage;

import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.properties.MinioApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Historic storage wrapper.
 */
public abstract class HistoricStorageWrapper {

    private static final Logger log = LoggerFactory.getLogger(HistoricStorageWrapper.class);

    private final MinioClientWrapper minioClientWrapper;


    /**
     * Instantiates a new Historic storage wrapper.
     *
     * @param applicationProperties the application properties
     * @throws Exception the exception
     */
    public HistoricStorageWrapper(MinioApplicationProperties applicationProperties) throws Exception {

        this.minioClientWrapper = new MinioClientWrapper(applicationProperties);
    }

    /**
     * Gets measurements by prefix.
     *
     * @param prefix the prefix
     * @return the measurements by prefix
     */
    public List<Measurement> getMeasurementsByPrefix(String prefix) {

        return minioClientWrapper.getObjectsWithPrefix(prefix, Measurement.class);
    }

    /**
     * Put historic measurement string.
     *
     * @param objName the obj name
     * @param m       the m
     * @return the string
     */
    public String putHistoricMeasurement(String objName, Measurement m) {

        return minioClientWrapper.putObject(m, objName) ? objName : null;
    }


    /**
     * Delete and return measurements by prefix list.
     *
     * @param prefix the prefix
     * @return the list
     */
    public List<Measurement> deleteAndReturnMeasurementsByPrefix(String prefix) {

        List<String> objectNames = minioClientWrapper.getObjectNamesWithPrefix(prefix);
        List<Measurement> measurements = new ArrayList<>();
        objectNames.forEach(o -> measurements.add(minioClientWrapper.getObject(o, Measurement.class)));
        objectNames.forEach(minioClientWrapper::deleteObject);
        return measurements;
    }

    /**
     * Delete objects by name.
     *
     * @param objectNames the object names
     */
    public void deleteObjectsByName(List<String> objectNames) {

        objectNames.forEach(minioClientWrapper::deleteObject);
    }
}
