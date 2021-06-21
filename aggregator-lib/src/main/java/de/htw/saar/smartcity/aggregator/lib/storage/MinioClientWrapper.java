package de.htw.saar.smartcity.aggregator.lib.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.saar.smartcity.aggregator.lib.properties.MinioApplicationProperties;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MinioClientWrapper {

    private static final Logger log = LoggerFactory.getLogger(MinioClientWrapper.class);

    private final MinioApplicationProperties applicationProperties;

    private final MinioClient minioClient;

    public MinioClientWrapper(MinioApplicationProperties applicationProperties) {

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


    public <T> List<T> getObjectsWithPrefix(String prefix, Class<T> target) {

        List<T> results = new ArrayList<>();
        Iterator<Result<Item>> iterator = minioClient.listObjects(
                ListObjectsArgs.builder()
                        .bucket(applicationProperties.getMicroserviceBucket())
                        .prefix(prefix)
                        .recursive(false) //only get measurements in the folder not in sub folders
                        //.maxKeys(100)
                        .build()).iterator();

        while(iterator.hasNext()) {
            try {
                T obj = getObject(iterator.next().get().objectName(), target);
                if(obj != null)
                    results.add(obj);
            }
             catch (Exception e) {
                log.warn("Could not get an object with prefix " + prefix);
            }
        }
        return results;
    }

}
