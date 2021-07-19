package de.htw.saar.smartcity.aggregator.lib.exporter;


import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.properties.ExporterApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.storage.MemcachedClientWrapper;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
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

    private MemcachedClientWrapper memcachedClientWrapper;

    private final ActivityManager activityManager;

    public CustomCollector(ExporterApplicationProperties exporterApplicationProperties,
                           SensorService sensorService,
                           AggregatorService aggregatorService,
                           ActivityManager activityManager) throws IOException {

        this.exporterApplicationProperties = exporterApplicationProperties;
        this.sensorService = sensorService;
        this.aggregatorService = aggregatorService;
        this.activityManager = activityManager;

        this.memcachedClientWrapper =
                new MemcachedClientWrapper(exporterApplicationProperties);

    }

    public List<MetricFamilySamples> collect() {

        log.info("Started collecting all measurements...");
        long startTime = System.currentTimeMillis();
        List<MetricFamilySamples> mfs = new ArrayList<>();

        //either split by id range or by data type
        if(exporterApplicationProperties.getExportedSensorDataTypes() != null || exporterApplicationProperties.getExportedAggregatorDataTypes() != null) {

            if (exporterApplicationProperties.getExportedSensorDataTypes().length == 1) {
                if (exporterApplicationProperties.getExportedSensorDataTypes()[0].toUpperCase(Locale.ROOT).equals("ALL")) {
                    mfs.addAll(collectAllSensorGauges());
                } else if (!exporterApplicationProperties.getExportedSensorDataTypes()[0].toUpperCase(Locale.ROOT).equals("NONE")) {
                    mfs.addAll(collectSensorGaugesByDataType(exporterApplicationProperties.getExportedSensorDataTypes()[0]));
                }
            } else {
                Arrays.stream(exporterApplicationProperties.getExportedSensorDataTypes())
                        .forEach(sdt -> mfs.addAll(collectSensorGaugesByDataType(sdt)));
            }

            if (exporterApplicationProperties.getExportedAggregatorDataTypes().length == 1) {
                if (exporterApplicationProperties.getExportedAggregatorDataTypes()[0].toUpperCase(Locale.ROOT).equals("ALL")) {
                    mfs.addAll(collectAllAggregatorGauges());
                } else if (!exporterApplicationProperties.getExportedAggregatorDataTypes()[0].toUpperCase(Locale.ROOT).equals("NONE")) {
                    mfs.addAll(collectAggregatorGaugesByDataType(exporterApplicationProperties.getExportedAggregatorDataTypes()[0]));
                }
            } else {
                Arrays.stream(exporterApplicationProperties.getExportedAggregatorDataTypes())
                        .forEach(adt -> mfs.addAll(collectAggregatorGaugesByDataType(adt)));
            }
        } else if(exporterApplicationProperties.getStartWithId() != null && exporterApplicationProperties.getEndWithId() != null) {

            mfs.addAll(collectProducerGaugesByIdRange(exporterApplicationProperties.getStartWithId(), exporterApplicationProperties.getEndWithId()));
        }

        long stopTime = System.currentTimeMillis();
        this.activityManager.addTime(stopTime, stopTime - startTime);
        log.info("Finished collecting all measurements...");
        return mfs;
    }

    private List<GaugeMetricFamily> collectProducerGaugesByIdRange(Long startWithId, Long endWithId) {

        log.info("Started collecting producer measurements...");

        List<Sensor> sensors =  sensorService.findAllSensorsToExportByIdBetween(startWithId, endWithId);
        List<Aggregator> aggregators =  aggregatorService.findAllAggregatorsToExportByIdBetween(startWithId, endWithId);

        List<GaugeMetricFamily> gauges = getGaugesForSensors(sensors);
        gauges.addAll(getGaugesForAggregators(aggregators));

        log.info("Finished collecting producer measurements...");
        return gauges;
    }


    protected List<GaugeMetricFamily> collectAllSensorGauges() {

        log.info("Started collecting sensor measurements...");

        List<Sensor> sensors =  sensorService.findAllSensorsToExport();

        List<GaugeMetricFamily> gauges = getGaugesForSensors(sensors);

        log.info("Finished collecting sensor measurements...");
        return gauges;
    }


    protected List<GaugeMetricFamily> collectSensorGaugesByDataType(String dataTypeName) {

        log.info("Started collecting sensor measurements for datatype " + dataTypeName + "...");

        List<Sensor> sensors =  sensorService.findAllSensorsToExportByDataTypeName(dataTypeName);

        List<GaugeMetricFamily> gauges = getGaugesForSensors(sensors);

        log.info("Finished collecting sensor measurements...");
        return gauges;
    }

    private List<GaugeMetricFamily> getGaugesForSensors(List<Sensor> sensors) {

        List<GaugeMetricFamily> gauges = new ArrayList<>();

        Map<String, Object> objects = getObjectsForKeys(sensors.stream().map(Sensor::getName).collect(Collectors.toList()));
        sensors.removeIf(s -> ! objects.containsKey(s.getName()));
        Map<String, List<Sensor>> byDataTypeName = sensors.stream().collect(Collectors.groupingBy(s -> s.getDataType().getName()));

        GaugeMetricFamily labeledGauge
                = new GaugeMetricFamily("sensor_measurement", "gauge for all sensors ",
                Arrays.asList("datatype", "sensorname"));

        for(String dtName : byDataTypeName.keySet()) {

            for (Sensor sensor : byDataTypeName.get(dtName)) {


                Double value = Utils.convertToDouble(objects.get(sensor.getName()));

                if(value != null) {
                    labeledGauge.addMetric(Arrays.asList(dtName, sensor.getName()), value);
                }
            }

            gauges.add(labeledGauge);
        }
        return gauges;
    }

    protected List<GaugeMetricFamily> collectAllAggregatorGauges() {

        log.info("Started collecting group measurements...");
        List<Aggregator> aggregators =  aggregatorService.findAllAggregatorsToExport();

        List<GaugeMetricFamily> gauges = getGaugesForAggregators(aggregators);

        log.info("Finished collecting group measurements...");
        return gauges;
    }


    protected List<GaugeMetricFamily> collectAggregatorGaugesByDataType(String dataTypeName) {

        log.info("Started collecting group measurements for datatype " + dataTypeName + "...");
        List<Aggregator> aggregators =  aggregatorService.findAllAggregatorsToExportByDataTypeName(dataTypeName);

        List<GaugeMetricFamily> gauges = getGaugesForAggregators(aggregators);

        log.info("Finished collecting group measurements...");
        return gauges;
    }

    private List<GaugeMetricFamily> getGaugesForAggregators(List<Aggregator> aggregators) {

        List<GaugeMetricFamily> gauges = new ArrayList<>();

        Map<String, Object> objects = getObjectsForKeys(aggregators.stream()
                .map(a -> a.getOwnerGroup().getName() + "/" + a.getCombinator().getName()).collect(Collectors.toList()));
        aggregators.removeIf(a -> ! objects.containsKey(a.getOwnerGroup().getName() + "/" + a.getCombinator().getName()));
        Map<String, List<Aggregator>> byDataTypeName = aggregators.stream().collect(Collectors.groupingBy(a -> a.getDataType().getName()));

        GaugeMetricFamily labeledGauge
                = new GaugeMetricFamily("group_measurement", "gauge for all groups ",
                Arrays.asList("group", "datatype", "combinator"));

        for(String dtName : byDataTypeName.keySet()) {

            for (Aggregator aggregator : byDataTypeName.get(dtName)) {

                Double value = Utils.convertToDouble(
                        objects.get(aggregator.getOwnerGroup().getName() + "/" + aggregator.getCombinator().getName()));

                if(value != null) {
                    labeledGauge.addMetric(Arrays.asList(
                            aggregator.getOwnerGroup().getName(),
                            aggregator.getDataType().getName(),
                            aggregator.getCombinator().getName()),
                            value
                            );
                }
            }

            gauges.add(labeledGauge);
        }
        return gauges;
    }


    private Map<String, Object> getObjectsForKeys(List<String> keys) {

        Map<String, Object> objects = null;
        if(memcachedClientWrapper != null) {

            objects = memcachedClientWrapper.getObjects(keys);
        }
        else {
            log.error("No connection to memcached server - Can not access cache!!");
        }
        return objects;
    }


}
