package de.htw.saar.smartcity.aggregator.exporter;

import io.prometheus.client.CollectorRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "de.htw.saar.smartcity.aggregator")
@EntityScan("de.htw.saar.smartcity.aggregator.lib")
@EnableJpaRepositories("de.htw.saar.smartcity.aggregator.lib")
public class ExporterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExporterApplication.class, args);
    }

    @Bean
    CollectorRegistry prometheusCollector() { return CollectorRegistry.defaultRegistry; }

}
