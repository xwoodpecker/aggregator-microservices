package de.htw.saar.smartcity.aggregator.exporter.base;


import de.htw.saar.smartcity.aggregator.exporter.properties.ExporterApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.storage.MemcachedClientWrapper;
import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomCollector extends Collector {


    private static final Logger log = LoggerFactory.getLogger(CustomCollector.class);

    private final SensorService sensorService;
    private final AggregatorService aggregatorService;
    private MemcachedClientWrapper memcachedClientWrapper = null;

    public CustomCollector(SensorService sensorService, AggregatorService aggregatorService, ExporterApplicationProperties exporterApplicationProperties) {
        this.sensorService = sensorService;
        this.aggregatorService = aggregatorService;

        try {
            this.memcachedClientWrapper =
                    new MemcachedClientWrapper(exporterApplicationProperties);
        } catch (IOException exception) {
            log.error("Memcached server connection failed!");
            exception.printStackTrace();
        }
    }

    public List<MetricFamilySamples> collect() {

        List<MetricFamilySamples> mfs = new ArrayList<>();
        mfs.addAll(generateSensorGauges());
        mfs.addAll(generateGroupGauges());
        return mfs;
    }


    private List<GaugeMetricFamily> generateSensorGauges() {

        log.info("Started generating sensor measurements...");
        List<GaugeMetricFamily> gauges = new ArrayList<>();

        List<Sensor> sensors =  sensorService.findAllSensorsToExport();
        Map<String, Double> objects = getObjectsForKeys(sensors.stream().map(s -> s.getName()).collect(Collectors.toList()));
        sensors.removeIf(s -> ! objects.containsKey(s.getName()));
        Map<String, List<Sensor>> byDataTypeName = sensors.stream().collect(Collectors.groupingBy(s -> s.getDataType().getName()));

        GaugeMetricFamily labeledGauge
                = new GaugeMetricFamily("sensor_measurement", "gauge for all sensors ",
                Arrays.asList("datatype", "sensorname"));

        for(String dtName : byDataTypeName.keySet()) {

            for (Sensor sensor : byDataTypeName.get(dtName)) {

                labeledGauge.addMetric(Arrays.asList(dtName, sensor.getName()), objects.get(sensor.getName()));
            }

            gauges.add(labeledGauge);
        }

        log.info("Finished generating sensor measurements...");
        return gauges;
    }

    private List<GaugeMetricFamily> generateGroupGauges() {

        log.info("Started generating group measurements...");
        List<GaugeMetricFamily> gauges = new ArrayList<>();

        List<Aggregator> aggregators =  aggregatorService.findAllAggregatorsToExport();
        Map<String, Double> objects = getObjectsForKeys(aggregators.stream()
                .map(a -> a.getOwnerGroup().getName() + "/" + a.getCombinator().getName()).collect(Collectors.toList()));
        aggregators.removeIf(a -> ! objects.containsKey(a.getOwnerGroup().getName() + "/" + a.getCombinator().getName()));
        Map<String, List<Aggregator>> byDataTypeName = aggregators.stream().collect(Collectors.groupingBy(a -> a.getDataType().getName()));

        GaugeMetricFamily labeledGauge
                = new GaugeMetricFamily("group_measurement", "gauge for all groups ",
                Arrays.asList("group", "datatype", "combinator"));

        for(String dtName : byDataTypeName.keySet()) {

            for (Aggregator aggregator : byDataTypeName.get(dtName)) {

                labeledGauge.addMetric(Arrays.asList(
                        aggregator.getOwnerGroup().getName(),
                        aggregator.getDataType().getName(),
                        aggregator.getCombinator().getName()),
                        objects.get(aggregator.getOwnerGroup().getName() + "/" + aggregator.getCombinator().getName()));
            }

            gauges.add(labeledGauge);
        }

        log.info("Finished generating group measurements...");
        return gauges;
    }


    private Map<String, Double> getObjectsForKeys(List<String> keys) {

        Map<String, Double> objects = null;
        if(memcachedClientWrapper != null) {

            objects = memcachedClientWrapper.getObjects(keys);
        }
        else {
            log.error("No connection to memcached server - Can not access cache!!");
        }
        return objects;
    }


}
