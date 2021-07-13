package de.htw.saar.smartcity.aggregator.lib.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Configuration
@PropertySource("classpath:application.properties")
public abstract class ExporterApplicationProperties implements MemcachedApplicationProperties {

    protected String memcachedHost;

    protected String memcachedPort;

    //can have values NONE or ALL
    protected String[] exportedSensorDataTypes;

    //can have values NONE or ALL
    protected String[] exportedAggregatorDataTypes;

    protected Long startWithId;

    protected Long endWithId;

    @Value("${MEMCACHED_HOST}")
    private void setMemcachedHost(String memcachedHost) {
        this.memcachedHost = memcachedHost;
    }

    @Value("${MEMCACHED_PORT}")
    private void setMemcachedPort(String memcachedPort) {
        this.memcachedPort = memcachedPort;
    }

    protected abstract void setExportedSensorDataTypes(String[] exportedSensorDataTypes);

    protected abstract void setExportedAggregatorDataTypes(String[] exportedAggregatorDataTypes);

    protected abstract void setStartWithId(Long startWithId);

    protected abstract void setEndWithId(Long endWithId);

    public String getMemcachedHost() {
        return this.memcachedHost;
    }

    public String getMemcachedPort() {
        return this.memcachedPort;
    }

    public String[] getExportedSensorDataTypes() {
        return exportedSensorDataTypes;
    }

    public String[] getExportedAggregatorDataTypes() {
        return exportedAggregatorDataTypes;
    }

    public Long getStartWithId() {
        return startWithId;
    }

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

