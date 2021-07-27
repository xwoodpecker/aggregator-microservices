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

public abstract class StorageWrapper {

    private static final Logger log = LoggerFactory.getLogger(StorageWrapper.class);

    private final MicroserviceApplicationProperties applicationProperties;

    private final SensorService sensorService;

    private final AggregatorService aggregatorService;

    private final MinioClientWrapper minioClientWrapper;

    private MemcachedClientWrapper memcachedClientWrapper = null;

    public StorageWrapper(MicroserviceApplicationProperties applicationProperties, SensorService sensorService, AggregatorService aggregatorService) throws Exception {

        this.applicationProperties = applicationProperties;
        this.sensorService = sensorService;
        this.aggregatorService = aggregatorService;


        this.minioClientWrapper = new MinioClientWrapper(applicationProperties);
        this.memcachedClientWrapper = new MemcachedClientWrapper(applicationProperties);
    }


    public String putMeasurementAndCache(String name, Measurement m) {

        String objectStorePath = putMeasurement(name, m);
        cacheMeasurement(name, m);
        return objectStorePath;
    }

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

    public boolean cacheMeasurement(String key, Measurement m) {

        if(memcachedClientWrapper!= null) {

            return memcachedClientWrapper.putObject(Constants.MEMCACHED_MEASUREMENT_PREFIX + key, m.getValue());
        }
        else {
            log.warn("No memcached connection established! Caching will be skipped.");
            return false;
        }
    }

    public void putSensor(Sensor s) {
        sensorService.updateSensor(s);
        //putObject(s, s.getName() + "/" + SENSOR_INFO_FILENAME);
    }

    public void putAggregator(Aggregator a) {
        aggregatorService.saveAggregator(a);
    }

    public Sensor getSensor(String name) {
        return sensorService.findSensorByName(name);
        //getObject(name + "/" + SENSOR_INFO_FILENAME, Sensor.class);
    }

    public boolean putTempGroupMeasurement(String groupName, TempGroupMeasurement tempGroupMeasurement) {

        return memcachedClientWrapper.putObject(Constants.MEMCACHED_TEMPORARY_PREFIX + groupName, tempGroupMeasurement);

    }

    public TempGroupMeasurement getTempGroupMeasurement(String groupName) {

        return memcachedClientWrapper.getObject(Constants.MEMCACHED_TEMPORARY_PREFIX + groupName);

    }

    public void deleteTempGroupMeasurement(String groupName) {

        memcachedClientWrapper.deleteObject(Constants.MEMCACHED_TEMPORARY_PREFIX + groupName);
    }

    public String getPresignedObjectUrl(String objName) {

        return minioClientWrapper.getPresignedObjectUrl(objName);
    }

    public List<Measurement> getMeasurementsByPrefix(String prefix) {

        return minioClientWrapper.getObjectsWithPrefix(prefix, Measurement.class);
    }

    public String putHistoricMeasurement(String objName, Measurement m) {

        return minioClientWrapper.putObject(m, objName) ? objName : null;
    }

    public void deleteObjectsByPrefixRecursive(String prefix) {

        List<String> objectNames = minioClientWrapper.getObjectNamesWithPrefixRecursive(prefix);
        objectNames.forEach(minioClientWrapper::deleteObject);
    }
}
