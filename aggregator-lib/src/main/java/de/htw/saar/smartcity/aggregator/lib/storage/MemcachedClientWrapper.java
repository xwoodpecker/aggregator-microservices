package de.htw.saar.smartcity.aggregator.lib.storage;

import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.properties.MemcachedApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


/**
 * The type Memcached client wrapper.
 */
public class MemcachedClientWrapper {

    private static final Logger log = LoggerFactory.getLogger(MemcachedClientWrapper.class);

    private final MemcachedClient mcc;

    /**
     * Instantiates a new Memcached client wrapper.
     *
     * @param applicationProperties the application properties
     * @throws IOException the io exception
     */
    public MemcachedClientWrapper(MemcachedApplicationProperties applicationProperties) throws IOException {

        String address = applicationProperties.getMemcachedHost() + ":" + applicationProperties.getMemcachedPort();
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses(address));

        mcc = builder.build();
    }

    /**
     * Put object boolean.
     *
     * @param key the key
     * @param o   the o
     * @return the boolean
     */
    public boolean putObject(String key, Object o) {

        boolean result;
        try {
            result = mcc.set(key, Constants.MEMCACHED_EXPIRATION, o);
        } catch (Exception e){
            log.error("Caching failed.");
            //e.printStackTrace();
            return false;
        }
        log.info("Caching Successful for key " + key + ": " + o);
        return result;
    }


    /**
     * Put object with expiration boolean.
     *
     * @param key            the key
     * @param o              the o
     * @param expirationTime the expiration time
     * @return the boolean
     */
    public boolean putObjectWithExpiration(String key, Object o, Integer expirationTime) {

        boolean result;
        try {
            result = mcc.set(key, expirationTime, o);
        } catch (Exception e){
            log.error("Caching failed.");
            //e.printStackTrace();
            return false;
        }
        log.info("Caching Successful for key " + key + ": " + o);
        return result;
    }


    /**
     * Gets object.
     *
     * @param <T> the type parameter
     * @param key the key
     * @return the object
     */
    public <T> T getObject(String key) {

        T obj;
        try {
            obj = mcc.get(key);
        } catch (Exception e){
            log.error("Retrieving cached object failed.");
            //e.printStackTrace();
            return null;
        }
        log.info("Retrieved cached object successfully: " + obj);
        return obj;
    }

    /**
     * Delete object boolean.
     *
     * @param key the key
     * @return the boolean
     */
    public boolean deleteObject(String key) {

        boolean deleted;
        try {
            deleted = mcc.delete(key);
        } catch (Exception e){
            log.error("Deleting cached object failed.");
            return false;
        }
        log.info("Deleting cached object successfully.");
        return deleted;
    }

    /**
     * Gets objects.
     *
     * @param <T>  the type parameter
     * @param keys the keys
     * @return the objects
     */
    public <T> Map<String, T> getObjects(List<String> keys) {

        int size = keys.size();

        if(size > Constants.MEMCACHED_UPPER_LIMIT_GET_OBJECTS)
        {
            int partitionCount = (int) Math.ceil(size / Constants.MEMCACHED_UPPER_LIMIT_GET_OBJECTS);
            int partitionSize = size / partitionCount;
            Map<String, T> finalResult = new HashMap<>();

            IntStream.range(0, partitionCount)
                    .mapToObj(
                            n -> keys.subList(n * partitionSize, n == partitionCount-1 ? size : (n + 1) * partitionSize)
                    )
                    .forEach( k -> {
                        Map<String, T> map = getObjectsSub(k);
                        if(map != null) {
                            finalResult.putAll(map);
                        }
                    });
            return finalResult;
        } else {
            return getObjectsSub(keys);
        }
    }

    private <T> Map<String, T> getObjectsSub(List<String> keys) {
        Map<String, T> map;
        try {
            map = mcc.get(keys, 30*1000);
        } catch (Exception e){
            log.error("Retrieving cached object failed.");
            //e.printStackTrace();
            return null;
        }
        log.info(Utils.limitLoggedMsg("Retrieved " + keys.size() + " cached objects successfully: " + map, Constants.MAX_LOG_MESSAGE_SIZE));
        return map;
    }




}
