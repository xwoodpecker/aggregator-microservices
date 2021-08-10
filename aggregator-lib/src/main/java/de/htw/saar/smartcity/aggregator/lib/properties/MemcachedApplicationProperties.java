package de.htw.saar.smartcity.aggregator.lib.properties;

/**
 * The interface Memcached application properties.
 */
public interface MemcachedApplicationProperties {

    /**
     * Gets memcached host.
     *
     * @return the memcached host
     */
    String getMemcachedHost();

    /**
     * Gets memcached port.
     *
     * @return the memcached port
     */
    String getMemcachedPort();
}
