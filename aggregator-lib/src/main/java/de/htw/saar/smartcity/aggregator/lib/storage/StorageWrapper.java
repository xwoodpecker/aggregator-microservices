package de.htw.saar.smartcity.aggregator.lib.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.model.GroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.model.BaseMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public abstract class StorageWrapper {


    private static final String SENSOR_INFO_FILENAME = "sensor-info";

    private static final Logger log = LoggerFactory.getLogger(StorageWrapper.class);

    private final MicroserviceApplicationProperties applicationProperties;

    private final SensorService sensorService;

    private final MinioClient minioClient;



    public StorageWrapper(MicroserviceApplicationProperties applicationProperties, SensorService sensorService) {

        this.applicationProperties = applicationProperties;
        this.sensorService = sensorService;

        this.minioClient =
                MinioClient.builder()
                        .endpoint(this.applicationProperties.getMinioEndpoint())
                        .credentials(this.applicationProperties.getMinioAccessKey(), this.applicationProperties.getMinioSecretKey())
                        .build();

        // Create bucket if it does not exist.
        boolean found;
        try {
            found = this.minioClient.bucketExists(
                    BucketExistsArgs.builder()
                            .bucket(applicationProperties.getMicroserviceBucket())
                            .build());
            if (!found) {
                // Create a new bucket
                this.minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(applicationProperties.getMicroserviceBucket())
                                .build());
            } else {
                log.info("Bucket already exists.");
            }
        } catch (Exception e) {
            log.error("Bucket creation failed.");
            e.printStackTrace();
        }

    }


    public void putMeasurement(Measurement m) {
        String folderName = "";
        if(m instanceof BaseMeasurement) {
            folderName = ((BaseMeasurement)m).getSensor().getName();
        } else if (m instanceof GroupMeasurement) {
            GroupMeasurement gm = (GroupMeasurement)m;
            folderName = gm.getGroupName() + "/" + gm.getAggregateName();
        }
        String name = String.format("%s/%d/%d/%d/%d/%d:%d",
                folderName,
                m.getTime().getYear(),
                m.getTime().getMonthValue(),
                m.getTime().getDayOfMonth(),
                m.getTime().getHour(),
                m.getTime().getMinute(),
                m.getTime().getSecond());
        putObject(m, name);

    }

    /**
    public void putBaseMeasurement(BaseMeasurement m)  {
        String name = String.format("%s/%d/%d/%d/%d/%d:%d",
                m.getSensor().getName(),
                m.getTime().getYear(),
                m.getTime().getMonthValue(),
                m.getTime().getDayOfMonth(),
                m.getTime().getHour(),
                m.getTime().getMinute(),
                m.getTime().getSecond());
        putObject(m, name);
    }

    public void putGroupMeasurement(GroupMeasurement m)  {
        String name = String.format("%s/%d/%d/%d/%d/%d:%d",
                m.getGroupName(),
                m.getTime().getYear(),
                m.getTime().getMonthValue(),
                m.getTime().getDayOfMonth(),
                m.getTime().getHour(),
                m.getTime().getMinute(),
                m.getTime().getSecond());
        putObject(m, name);
    }
     **/


    public Measurement getMeasurement(String name)  {
        return getObject(name, Measurement.class);
    }

    public void putSensor(Sensor s) {
        sensorService.saveSensor(s);
        //putObject(s, s.getName() + "/" + SENSOR_INFO_FILENAME);
    }

    public Sensor getSensor(String name) {
        return sensorService.findSensorByName(name);
        //getObject(name + "/" + SENSOR_INFO_FILENAME, Sensor.class);
    }

    private void putObject(Object o, String name) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            objectMapper.writeValue(byteArrayOutputStream, o);
            InputStream is = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(applicationProperties.getMicroserviceBucket())
                            .object(name)
                            .stream(is, -1, 10485760)
                            //.contentType()
                            .build());
        } catch (Exception e){
            log.error("Upload failed.");
            e.printStackTrace();
        }
        log.info("Upload Successful: " + o);
    }

    private <T> T getObject(String name, Class<T> target) {
        T object = null;
        try {
            InputStream is = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(applicationProperties.getMicroserviceBucket())
                            .object(name)
                            .build());
            ObjectMapper objectMapper = new ObjectMapper();
            object = objectMapper.readValue(is.readAllBytes(), target);
        } catch (ErrorResponseException ere) {
            log.info("No Object with key found.");
        } catch (Exception e) {
            log.error("GetObject failed.");
            e.printStackTrace();
        }
        log.info("GetObject: " + object);
        return object;
    }

}
