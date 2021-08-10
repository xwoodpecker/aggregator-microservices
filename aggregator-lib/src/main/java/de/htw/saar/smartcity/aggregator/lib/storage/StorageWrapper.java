package de.htw.saar.smartcity.aggregator.lib.storage;

import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.TempGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * The type Storage wrapper.
 */
public abstract class StorageWrapper {

    private static final Logger log = LoggerFactory.getLogger(StorageWrapper.class);

    private final MicroserviceApplicationProperties applicationProperties;

    private final SensorService sensorService;

    private final AggregatorService aggregatorService;

    private final MinioClientWrapper minioClientWrapper;

    private MemcachedClientWrapper memcachedClientWrapper = null;

    /**
     * Instantiates a new Storage wrapper.
     *
     * @param applicationProperties the application properties
     * @param sensorService         the sensor service
     * @param aggregatorService     the aggregator service
     * @throws Exception the exception
     */
    public StorageWrapper(MicroserviceApplicationProperties applicationProperties, SensorService sensorService, AggregatorService aggregatorService) throws Exception {

        this.applicationProperties = applicationProperties;
        this.sensorService = sensorService;
        this.aggregatorService = aggregatorService;


        this.minioClientWrapper = new MinioClientWrapper(applicationProperties);
        this.memcachedClientWrapper = new MemcachedClientWrapper(applicationProperties);
    }


    /**
     * Put measurement and cache string.
     *
     * @param name the name
     * @param m    the m
     * @return the string
     */
    public String putMeasurementAndCache(String name, Measurement m) {

        String objectStorePath = putMeasurement(name, m);
        cacheMeasurement(name, m);
        return objectStorePath;
    }

    /**
     * Put measurement string.
     *
     * @param name the name
     * @param m    the m
     * @return the string
     */
    public String putMeasurement(String name, Measurement m) {

        String objName = String.format("%s/%d/%d/%d/%d/%d:%d",
                name,
                m.getTime().getYear(),
                m.getTime().getMonthValue(),
                m.getTime().getDayOfMonth(),
                m.getTime().getHour(),
                m.getTime().getMinute(),
                m.getTime().getSecond());

        return minioClientWrapper.putObject(m, objName) ? objName : null;
    }

    /**
     * Cache measurement boolean.
     *
     * @param key the key
     * @param m   the m
     * @return the boolean
     */
    public boolean cacheMeasurement(String key, Measurement m) {

        if(memcachedClientWrapper!= null) {

            return memcachedClientWrapper.putObject(Constants.MEMCACHED_MEASUREMENT_PREFIX + key, m.getValue());
        }
        else {
            log.warn("No memcached connection established! Caching will be skipped.");
            return false;
        }
    }

    /**
     * Put sensor.
     *
     * @param s the s
     */
    public void putSensor(Sensor s) {
        sensorService.updateSensor(s);
        //putObject(s, s.getName() + "/" + SENSOR_INFO_FILENAME);
    }

    /**
     * Put aggregator.
     *
     * @param a the a
     */
    public void putAggregator(Aggregator a) {
        aggregatorService.saveAggregator(a);
    }

    /**
     * Gets sensor.
     *
     * @param name the name
     * @return the sensor
     */
    public Sensor getSensor(String name) {
        return sensorService.findSensorByName(name);
        //getObject(name + "/" + SENSOR_INFO_FILENAME, Sensor.class);
    }

    /**
     * Put temp group measurement boolean.
     *
     * @param groupName            the group name
     * @param tempGroupMeasurement the temp group measurement
     * @return the boolean
     */
    public boolean putTempGroupMeasurement(String groupName, TempGroupMeasurement tempGroupMeasurement) {

        return memcachedClientWrapper.putObject(Constants.MEMCACHED_TEMPORARY_PREFIX + groupName, tempGroupMeasurement);

    }

    /**
     * Gets temp group measurement.
     *
     * @param groupName the group name
     * @return the temp group measurement
     */
    public TempGroupMeasurement getTempGroupMeasurement(String groupName) {

        return memcachedClientWrapper.getObject(Constants.MEMCACHED_TEMPORARY_PREFIX + groupName);

    }

    /**
     * Delete temp group measurement.
     *
     * @param groupName the group name
     */
    public void deleteTempGroupMeasurement(String groupName) {

        memcachedClientWrapper.deleteObject(Constants.MEMCACHED_TEMPORARY_PREFIX + groupName);
    }

    /**
     * Gets presigned object url.
     *
     * @param objName the obj name
     * @return the presigned object url
     */
    public String getPresignedObjectUrl(String objName) {

        return minioClientWrapper.getPresignedObjectUrl(objName);
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
     * Delete objects by prefix recursive.
     *
     * @param prefix the prefix
     */
    public void deleteObjectsByPrefixRecursive(String prefix) {

        List<String> objectNames = minioClientWrapper.getObjectNamesWithPrefixRecursive(prefix);
        objectNames.forEach(minioClientWrapper::deleteObject);
    }
}
