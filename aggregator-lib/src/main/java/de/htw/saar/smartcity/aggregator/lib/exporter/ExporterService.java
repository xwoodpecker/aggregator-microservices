package de.htw.saar.smartcity.aggregator.lib.exporter;

import io.prometheus.client.CollectorRegistry;

/**
 * The type Exporter service.
 */
public abstract class ExporterService {

    /**
     * Instantiates a new Exporter service.
     *
     * @param prometheusCollector the prometheus collector
     * @param customCollector     the custom collector
     */
    public ExporterService(CollectorRegistry prometheusCollector, CustomCollector customCollector) {

        customCollector.register(prometheusCollector);
    }



}