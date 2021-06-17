package de.htw.saar.smartcity.aggregator.lib.storage;

import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.model.TempGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class StorageWrapper {

    private static final String SENSOR_INFO_FILENAME = "sensor-info";

    private static final Logger log = LoggerFactory.getLogger(StorageWrapper.class);

    private final MicroserviceApplicationProperties applicationProperties;

    private final SensorService sensorService;

    private final AggregatorService aggregatorService;

    private final MinioClientWrapper minioClientWrapper;

    private MemcachedClientWrapper memcachedClientWrapper = null;

    public StorageWrapper(MicroserviceApplicationProperties applicationProperties, SensorService sensorService, AggregatorService aggregatorService) {

        this.applicationProperties = applicationProperties;
        this.sensorService = sensorService;
        this.aggregatorService = aggregatorService;


        this.minioClientWrapper = new MinioClientWrapper(applicationProperties);

        try {

            this.memcachedClientWrapper
                    = new MemcachedClientWrapper(applicationProperties.getMemcachedHost(),
                                                 applicationProperties.getMemcachedPort()
            );
        }
        catch (IOException exception) {

            log.error("Could not initialize memcached connection!");
        }
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

            return memcachedClientWrapper.putObject(key, m.getValue());
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

        String name = groupName + "/temp";

        return minioClientWrapper.putObject(tempGroupMeasurement, name);

    }

    public TempGroupMeasurement getTempGroupMeasurement(String groupName) {

        String name = groupName + "/temp";

        return minioClientWrapper.getObject(name, TempGroupMeasurement.class);

    }

    public void deleteTempGroupMeasurement(String groupName) {

        String name = groupName + "/temp";

        minioClientWrapper.deleteObject(name);
    }

    public String getPresignedObjectUrl(String objName) {

        return minioClientWrapper.getPresignedObjectUrl(objName);
    }

}
