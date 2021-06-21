package de.htw.saar.smartcity.aggregator.exporter.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.MemcachedApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.properties.MicroserviceApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ExporterApplicationProperties implements MemcachedApplicationProperties {

    protected String memcachedHost;

    protected String memcachedPort;

    @Value("${MEMCACHED_HOST}")
    private void setMemcachedHost(String memcachedHost) {
        this.memcachedHost = memcachedHost;
    }

    @Value("${MEMCACHED_PORT}")
    private void setMemcachedPort(String memcachedPort) {
        this.memcachedPort = memcachedPort;
    }

    public String getMemcachedHost() {
        return this.memcachedHost;
    }

    public String getMemcachedPort() {
        return this.memcachedPort;
    }

    @PostConstruct
    public void printProperties() {
        final Logger log = LoggerFactory.getLogger(MicroserviceApplicationProperties.class);
        log.info(this.toString());

    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExporterApplicationProperties{");
        sb.append("memcachedHost='").append(memcachedHost).append('\'');
        sb.append(", memcachedPort='").append(memcachedPort).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
