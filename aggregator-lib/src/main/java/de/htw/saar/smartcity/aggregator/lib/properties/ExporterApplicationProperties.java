package de.htw.saar.smartcity.aggregator.lib.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Arrays;

/**
 * The type Exporter application properties.
 */
@Configuration
@PropertySource("classpath:application.properties")
public abstract class ExporterApplicationProperties implements MemcachedApplicationProperties {

    /**
     * The Memcached host.
     */
    protected String memcachedHost;

    /**
     * The Memcached port.
     */
    protected String memcachedPort;

    /**
     * The Exported sensor data types.
     */
//can have values NONE or ALL
    protected String[] exportedSensorDataTypes;

    /**
     * The Exported aggregator data types.
     */
//can have values NONE or ALL
    protected String[] exportedAggregatorDataTypes;

    /**
     * The Start with id.
     */
    protected Long startWithId;

    /**
     * The End with id.
     */
    protected Long endWithId;

    @Value("${MEMCACHED_HOST}")
    private void setMemcachedHost(String memcachedHost) {
        this.memcachedHost = memcachedHost;
    }

    @Value("${MEMCACHED_PORT}")
    private void setMemcachedPort(String memcachedPort) {
        this.memcachedPort = memcachedPort;
    }

    /**
     * Sets exported sensor data types.
     *
     * @param exportedSensorDataTypes the exported sensor data types
     */
    protected abstract void setExportedSensorDataTypes(String[] exportedSensorDataTypes);

    /**
     * Sets exported aggregator data types.
     *
     * @param exportedAggregatorDataTypes the exported aggregator data types
     */
    protected abstract void setExportedAggregatorDataTypes(String[] exportedAggregatorDataTypes);

    /**
     * Sets start with id.
     *
     * @param startWithId the start with id
     */
    protected abstract void setStartWithId(Long startWithId);

    /**
     * Sets end with id.
     *
     * @param endWithId the end with id
     */
    protected abstract void setEndWithId(Long endWithId);

    public String getMemcachedHost() {
        return this.memcachedHost;
    }

    public String getMemcachedPort() {
        return this.memcachedPort;
    }

    /**
     * Get exported sensor data types string [ ].
     *
     * @return the string [ ]
     */
    public String[] getExportedSensorDataTypes() {
        return exportedSensorDataTypes;
    }

    /**
     * Get exported aggregator data types string [ ].
     *
     * @return the string [ ]
     */
    public String[] getExportedAggregatorDataTypes() {
        return exportedAggregatorDataTypes;
    }

    /**
     * Gets start with id.
     *
     * @return the start with id
     */
    public Long getStartWithId() {
        return startWithId;
    }

    /**
     * Gets end with id.
     *
     * @return the end with id
     */
    public Long getEndWithId() {
        return endWithId;
    }


    /**@PostConstruct
    public void printProperties() {
        final Logger log = LoggerFactory.getLogger(MicroserviceApplicationProperties.class);
        log.info(this.toString());

    }**/

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExporterApplicationProperties{");
        sb.append("memcachedHost='").append(memcachedHost).append('\'');
        sb.append(", memcachedPort='").append(memcachedPort).append('\'');
        sb.append(", exportedSensorDataTypes=").append(exportedSensorDataTypes == null ? "null" : Arrays.asList(exportedSensorDataTypes).toString());
        sb.append(", exportedAggregatorDataTypes=").append(exportedAggregatorDataTypes == null ? "null" : Arrays.asList(exportedAggregatorDataTypes).toString());
        sb.append('}');
        return sb.toString();
    }
}

