package de.htw.saar.smartcity.aggregator.exporter.base;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;
import org.springframework.stereotype.Component;


@Component
public class ExporterService {

    private final MeterRegistry meterRegistry;
    private final CustomCollector customCollector;


    public ExporterService(CollectorRegistry prometheusCollector, CustomCollector customCollector) {

        this.meterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT, prometheusCollector, Clock.SYSTEM);
        this.customCollector = customCollector;

        /**Gauge.builder("my.test", () -> 42)
                .description("My test")
                .register(meterRegistry); **/

        this.customCollector.register(prometheusCollector);
    }


}
