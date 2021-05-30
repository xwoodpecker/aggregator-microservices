package de.htw.saar.smartcity.aggregator.lib.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.model.TempGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.TempGroupMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

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


    public String putMeasurement(String name, Measurement m) {

        String objName = String.format("%s/%d/%d/%d/%d/%d:%d",
                name,
                m.getTime().getYear(),
                m.getTime().getMonthValue(),
                m.getTime().getDayOfMonth(),
                m.getTime().getHour(),
                m.getTime().getMinute(),
                m.getTime().getSecond());

        return putObject(m, objName) ? objName : null;
    }


    public String getPresignedObjectUrl(String name) {

        String url = null;
        try {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(applicationProperties.getMicroserviceBucket())
                            .object(name)
                            .expiry(2, TimeUnit.MINUTES) //todo: align with message durability in queue?
                            .build());

        } catch (Exception e){
            log.error("Url generation failed.");
            e.printStackTrace();
        }
        log.info("Url to Object: " + url);

        return url;
    }


    public void putSensor(Sensor s) {
        sensorService.saveSensor(s);
        //putObject(s, s.getName() + "/" + SENSOR_INFO_FILENAME);
    }

    public Sensor getSensor(String name) {
        return sensorService.findSensorByName(name);
        //getObject(name + "/" + SENSOR_INFO_FILENAME, Sensor.class);
    }

    public boolean putTempGroupMeasurement(String groupName, TempGroupMeasurement tempGroupMeasurement) {

        String name = groupName + "/temp";

        return putObject(tempGroupMeasurement, name);

    }


    public TempGroupMeasurement getTempGroupMeasurement(String groupName) {

        String name = groupName + "/temp";

        return getObject(name, TempGroupMeasurement.class);

    }



    public void deleteTempGroupMeasurement(String groupName) {

        String name = groupName + "/temp";

        deleteObject(name);
    }

    private boolean putObject(Object o, String name) {

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
            return false;
        }
        log.info("Upload Successful: " + o);
        return true;
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


    private boolean deleteObject(String name) {

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(applicationProperties.getMicroserviceBucket())
                            .object(name)
                            .build());

        } catch (ErrorResponseException ere) {
            log.info("Object could not be deleted");
            return false;

        } catch (Exception e) {
            log.error("DeleteObject failed.");
            e.printStackTrace();
            return false;
        }
        log.info("DeleteObject: " + name);
        return true;
    }

}
