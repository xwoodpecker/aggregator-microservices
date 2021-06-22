package de.htw.saar.smartcity.aggregator.exporter.base;

import de.htw.saar.smartcity.aggregator.lib.exporter.CustomCollector;
import de.htw.saar.smartcity.aggregator.lib.exporter.ExporterService;
import io.prometheus.client.CollectorRegistry;
import org.springframework.stereotype.Component;


@Component
public class ExporterServiceImpl extends ExporterService {

    public ExporterServiceImpl(CollectorRegistry prometheusCollector, CustomCollector customCollector) {
        super(prometheusCollector, customCollector);
    }
}

