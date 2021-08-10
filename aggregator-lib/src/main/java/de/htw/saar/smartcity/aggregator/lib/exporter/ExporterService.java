package de.htw.saar.smartcity.aggregator.lib.exporter;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.CollectorRegistry;

/**
 * The type Exporter service.
 */
public abstract class ExporterService {

    private final MeterRegistry meterRegistry;

    private final CustomCollector customCollector;


    /**
     * Instantiates a new Exporter service.
     *
     * @param prometheusCollector the prometheus collector
     * @param customCollector     the custom collector
     */
    public ExporterService(CollectorRegistry prometheusCollector, CustomCollector customCollector) {

        this.meterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT, prometheusCollector, Clock.SYSTEM);
        this.customCollector = customCollector;
        this.customCollector.register(prometheusCollector);
    }

}