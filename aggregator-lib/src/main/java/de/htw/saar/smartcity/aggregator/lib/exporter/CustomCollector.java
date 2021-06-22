package de.htw.saar.smartcity.aggregator.lib.exporter;


import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.properties.ExporterApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.storage.MemcachedClientWrapper;
import io.prometheus.client.Collector;
import io.prometheus.client.GaugeMetricFamily;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class CustomCollector extends Collector {


    private static final Logger log = LoggerFactory.getLogger(CustomCollector.class);

    private final ExporterApplicationProperties exporterApplicationProperties;

    private final SensorService sensorService;
    private final AggregatorService aggregatorService;

    private MemcachedClientWrapper memcachedClientWrapper = null;

    public CustomCollector(ExporterApplicationProperties exporterApplicationProperties, SensorService sensorService, AggregatorService aggregatorService) {

        this.exporterApplicationProperties = exporterApplicationProperties;
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
        if(exporterApplicationProperties.getExportedSensorDataTypes().length == 1) {
            if(exporterApplicationProperties.getExportedSensorDataTypes()[0].toUpperCase(Locale.ROOT).equals("ALL")) {
                mfs.addAll(generateAllSensorGauges());
            } else if(!exporterApplicationProperties.getExportedSensorDataTypes()[0].toUpperCase(Locale.ROOT).equals("NONE")) {
                mfs.addAll(generateSensorGaugesByDataType(exporterApplicationProperties.getExportedSensorDataTypes()[0]));
            }
        }
        else {
            Arrays.stream(exporterApplicationProperties.getExportedSensorDataTypes())
                    .forEach(sdt -> mfs.addAll(generateSensorGaugesByDataType(sdt)));
        }

        if(exporterApplicationProperties.getExportedAggregatorDataTypes().length == 1) {
            if(exporterApplicationProperties.getExportedAggregatorDataTypes()[0].toUpperCase(Locale.ROOT).equals("ALL")) {
                mfs.addAll(generateAllAggregatorGauges());
            } else if(!exporterApplicationProperties.getExportedAggregatorDataTypes()[0].toUpperCase(Locale.ROOT).equals("NONE")) {
                mfs.addAll(generateAggregatorGaugesByDataType(exporterApplicationProperties.getExportedAggregatorDataTypes()[0]));
            }
        }
        else {
            Arrays.stream(exporterApplicationProperties.getExportedAggregatorDataTypes())
                    .forEach(adt -> mfs.addAll(generateAggregatorGaugesByDataType(adt)));
        }

        return mfs;
    }


    protected List<GaugeMetricFamily> generateAllSensorGauges() {

        log.info("Started generating sensor measurements...");

        List<Sensor> sensors =  sensorService.findAllSensorsToExport();

        List<GaugeMetricFamily> gauges = getGaugesForSensors(sensors);

        log.info("Finished generating sensor measurements...");
        return gauges;
    }


    protected List<GaugeMetricFamily> generateSensorGaugesByDataType(String dataTypeName) {

        log.info("Started generating sensor measurements for datatype " + dataTypeName + "...");

        List<Sensor> sensors =  sensorService.findAllSensorsToExportByDataTypeName(dataTypeName);

        List<GaugeMetricFamily> gauges = getGaugesForSensors(sensors);

        log.info("Finished generating sensor measurements...");
        return gauges;
    }

    private List<GaugeMetricFamily> getGaugesForSensors(List<Sensor> sensors) {

        List<GaugeMetricFamily> gauges = new ArrayList<>();

        Map<String, Double> objects = getObjectsForKeys(sensors.stream().map(Sensor::getName).collect(Collectors.toList()));
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
        return gauges;
    }

    protected List<GaugeMetricFamily> generateAllAggregatorGauges() {

        log.info("Started generating group measurements...");
        List<Aggregator> aggregators =  aggregatorService.findAllAggregatorsToExport();

        List<GaugeMetricFamily> gauges = getGaugesForAggregators(aggregators);

        log.info("Finished generating group measurements...");
        return gauges;
    }


    protected List<GaugeMetricFamily> generateAggregatorGaugesByDataType(String dataTypeName) {

        log.info("Started generating group measurements for datatype " + dataTypeName + "...");
        List<Aggregator> aggregators =  aggregatorService.findAllAggregatorsToExportByDataTypeName(dataTypeName);

        List<GaugeMetricFamily> gauges = getGaugesForAggregators(aggregators);

        log.info("Finished generating group measurements...");
        return gauges;
    }

    private List<GaugeMetricFamily> getGaugesForAggregators(List<Aggregator> aggregators) {

        List<GaugeMetricFamily> gauges = new ArrayList<>();

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
