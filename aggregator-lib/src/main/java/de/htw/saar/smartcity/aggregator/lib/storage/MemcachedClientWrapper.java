package de.htw.saar.smartcity.aggregator.lib.storage;

import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.properties.MemcachedApplicationProperties;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;


public class MemcachedClientWrapper {

    private static final Logger log = LoggerFactory.getLogger(MemcachedClientWrapper.class);

    private final MemcachedClient mcc;

    public MemcachedClientWrapper(MemcachedApplicationProperties applicationProperties) throws IOException {


        String address = applicationProperties.getMemcachedHost() + ":" + applicationProperties.getMemcachedPort();
        MemcachedClientBuilder builder = new XMemcachedClientBuilder(
                AddrUtil.getAddresses(address));

        mcc = builder.build();
    }

    public boolean putObject(String key, Object o) {

        boolean result;
        try {
            result = mcc.set(key, Constants.MEMCACHED_EXPIRATION, o);
        } catch (Exception e){
            log.error("Caching failed.");
            e.printStackTrace();
            return false;
        }
        log.info("Caching Successful for key " + key + ": " + o);
        return result;
    }


    public <T> T getObject(String key) {

        T obj = null;
        try {
            obj = mcc.get(key);
        } catch (Exception e){
            log.error("Retrieving cached object failed.");
            e.printStackTrace();
        }
        log.info("Retrieved cached object successfully: " + obj);
        return obj;
    }

    public <T> Map<String, T> getObjects(Collection<String> keys) {

        Map<String, T> map = null;
        try {
            map = mcc.get(keys);
        } catch (Exception e){
            log.error("Retrieving cached object failed.");
            e.printStackTrace();
        }
        log.info("Retrieved cached object successfully: " + map);
        return map;
    }
}
