package de.htw.saar.smartcity.aggregator.lib.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.saar.smartcity.aggregator.lib.properties.ApplicationProperties;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class MinioClientWrapper {

    private static final Logger log = LoggerFactory.getLogger(MinioClientWrapper.class);

    private final ApplicationProperties applicationProperties;

    private final MinioClient minioClient;

    public MinioClientWrapper(ApplicationProperties applicationProperties) {

        this.applicationProperties = applicationProperties;

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
                            .bucket(this.applicationProperties.getMicroserviceBucket())
                            .build());
            if (!found) {
                // Create a new bucket
                this.minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(this.applicationProperties.getMicroserviceBucket())
                                .build());
            } else {
                log.info("Bucket already exists.");
            }
        } catch (Exception e) {
            log.error("Bucket creation failed.");
            e.printStackTrace();
        }
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
        log.info("Url to object: " + url);

        return url;
    }

    public boolean putObject(Object o, String name) {

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
            log.error("Upload to object store failed.");
            e.printStackTrace();
            return false;
        }
        log.info("Upload to object store successful: " + o);
        return true;
    }




    public <T> T getObject(String name, Class<T> target) {

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
            log.info("No Object with name found in object store.");

        } catch (Exception e) {
            log.error("GetObject from object store failed.");
            e.printStackTrace();
        }
        log.info("GetObject from object store: " + object);
        return object;
    }


    public boolean deleteObject(String name) {

        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(applicationProperties.getMicroserviceBucket())
                            .object(name)
                            .build());

        } catch (ErrorResponseException ere) {
            log.info("Object could not be deleted from object store.");
            return false;

        } catch (Exception e) {
            log.error("DeleteObject from object store failed.");
            e.printStackTrace();
            return false;
        }
        log.info("DeleteObject from object store: " + name);
        return true;
    }
}
