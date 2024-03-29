package de.htw.saar.smartcity.aggregator.lib.exporter;


import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager;
import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.Location;
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

/**
 * The type Custom collector.
 */
public abstract class CustomCollector extends Collector {

    private static final Logger log = LoggerFactory.getLogger(CustomCollector.class);

    private final ExporterApplicationProperties exporterApplicationProperties;

    private final SensorService sensorService;

    private final AggregatorService aggregatorService;

    private MemcachedClientWrapper memcachedClientWrapper;

    private final ActivityManager activityManager;



    /**
     * Instantiates a new Custom collector.
     *
     * @param exporterApplicationProperties the exporter application properties
     * @param sensorService                 the sensor service
     * @param aggregatorService             the aggregator service
     * @param activityManager               the activity manager
     * @throws IOException the io exception
     */
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

    /**
     * collects all the MetricFamilySamples for prometheus
     *
     * @return list of MetricFamilySamples
     */
    public List<MetricFamilySamples> collect() {

        log.info("Started collecting all measurements...");
        long startTime = System.currentTimeMillis();
        List<MetricFamilySamples> mfs = new ArrayList<>();

        //either split by id range or by data type
        if(exporterApplicationProperties.getExportedSensorDataTypes() != null || exporterApplicationProperties.getExportedAggregatorDataTypes() != null) {

            if (exporterApplicationProperties.getExportedSensorDataTypes().length == 1) {
                if (exporterApplicationProperties.getExportedSensorDataTypes()[0].toUpperCase(Locale.ROOT).equals(Constants.COLLECTOR_KEYWORD_ALL)) {
                    mfs.addAll(collectAllSensorGauges());
                } else if (!exporterApplicationProperties.getExportedSensorDataTypes()[0].toUpperCase(Locale.ROOT).equals(Constants.COLLECTOR_KEYWORD_NONE)) {
                    mfs.addAll(collectSensorGaugesByDataType(exporterApplicationProperties.getExportedSensorDataTypes()[0]));
                }
            } else {
                Arrays.stream(exporterApplicationProperties.getExportedSensorDataTypes())
                        .forEach(sdt -> mfs.addAll(collectSensorGaugesByDataType(sdt)));
            }

            if (exporterApplicationProperties.getExportedAggregatorDataTypes().length == 1) {
                if (exporterApplicationProperties.getExportedAggregatorDataTypes()[0].toUpperCase(Locale.ROOT).equals(Constants.COLLECTOR_KEYWORD_ALL)) {
                    mfs.addAll(collectAllAggregatorGauges());
                } else if (!exporterApplicationProperties.getExportedAggregatorDataTypes()[0].toUpperCase(Locale.ROOT).equals(Constants.COLLECTOR_KEYWORD_NONE)) {
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

    /**
     * Collect producer gauges by id range list.
     *
     * @param startWithId the start with id
     * @param endWithId   the end with id
     * @return the list
     */
    public List<GaugeMetricFamily> collectProducerGaugesByIdRange(Long startWithId, Long endWithId) {

        log.info("Started collecting producer measurements...");

        List<Sensor> sensors =  sensorService.findAllSensorsToExportByIdBetween(startWithId, endWithId);
        List<Aggregator> aggregators =  aggregatorService.findAllAggregatorsToExportByIdBetween(startWithId, endWithId);

        List<GaugeMetricFamily> gauges = getGaugesForSensors(sensors);
        gauges.addAll(getGaugesForAggregators(aggregators));

        log.info("Finished collecting producer measurements...");
        return gauges;
    }


    /**
     * Collect all sensor gauges list.
     *
     * @return the list
     */
    public List<GaugeMetricFamily> collectAllSensorGauges() {

        log.info("Started collecting sensor measurements...");

        List<Sensor> sensors =  sensorService.findAllSensorsToExport();

        List<GaugeMetricFamily> gauges = getGaugesForSensors(sensors);

        log.info("Finished collecting sensor measurements...");
        return gauges;
    }


    /**
     * Collect sensor gauges by data type list.
     *
     * @param dataTypeName the data type name
     * @return the list
     */
    public List<GaugeMetricFamily> collectSensorGaugesByDataType(String dataTypeName) {

        log.info("Started collecting sensor measurements for datatype " + dataTypeName + "...");

        List<Sensor> sensors =  sensorService.findAllSensorsToExportByDataTypeName(dataTypeName);

        List<GaugeMetricFamily> gauges = getGaugesForSensors(sensors);

        log.info("Finished collecting sensor measurements...");
        return gauges;
    }

    /**
     * generate gauges for given list of sensors
     *
     * @param sensors the sensors for which the gauges are to be generated
     * @return list of GaugeMetricFamily
     */
    private List<GaugeMetricFamily> getGaugesForSensors(List<Sensor> sensors) {

        List<GaugeMetricFamily> gauges = new ArrayList<>();

        Map<String, Object> objects = getObjectsForKeys(sensors.stream().map(Sensor::getName).collect(Collectors.toList()));

        if(objects != null && !objects.isEmpty()) {
            sensors.removeIf(s -> !objects.containsKey(Constants.MEMCACHED_MEASUREMENT_PREFIX + s.getName()));
            Map<String, List<Sensor>> byDataTypeName = sensors.stream().collect(Collectors.groupingBy(s -> s.getDataType().getName()));

            GaugeMetricFamily labeledGauge
                    = new GaugeMetricFamily("sensor_measurement", "gauge for all sensors ",
                    Arrays.asList("datatype", "sensor_name", "location_name", "location_x", "location_y"));

            for (String dataTypeName : byDataTypeName.keySet()) {

                for (Sensor sensor : byDataTypeName.get(dataTypeName)) {

                    Double value = Utils.convertToDouble(objects.get(Constants.MEMCACHED_MEASUREMENT_PREFIX + sensor.getName()));

                    if (value != null) {
                        Location location = sensor.getLocation();

                        labeledGauge.addMetric(
                                Arrays.asList(dataTypeName,
                                        sensor.getName(),
                                        location == null ? "null" : location.getName(),
                                        location == null ? "null" : String.valueOf(location.getX()),
                                        location == null ? "null" : String.valueOf(location.getY())),
                                value
                        );
                    }
                }

                gauges.add(labeledGauge);
            }
        }
        return gauges;
    }

    /**
     * Collect all aggregator gauges list.
     *
     * @return the list
     */
    public List<GaugeMetricFamily> collectAllAggregatorGauges() {

        log.info("Started collecting group measurements...");
        List<Aggregator> aggregators =  aggregatorService.findAllAggregatorsToExport();

        List<GaugeMetricFamily> gauges = getGaugesForAggregators(aggregators);

        log.info("Finished collecting group measurements...");
        return gauges;
    }


    /**
     * Collect aggregator gauges by data type list.
     *
     * @param dataTypeName the data type name
     * @return the list
     */
    public List<GaugeMetricFamily> collectAggregatorGaugesByDataType(String dataTypeName) {

        log.info("Started collecting group measurements for datatype " + dataTypeName + "...");
        List<Aggregator> aggregators =  aggregatorService.findAllAggregatorsToExportByDataTypeName(dataTypeName);

        List<GaugeMetricFamily> gauges = getGaugesForAggregators(aggregators);

        log.info("Finished collecting group measurements...");
        return gauges;
    }

    /**
     *
     * generate gauges for given list of aggregators
     *
     * @param aggregators the aggregators for which the gauges are to be generated
     * @return list of GaugeMetricFamily
     */
    private List<GaugeMetricFamily> getGaugesForAggregators(List<Aggregator> aggregators) {

        List<GaugeMetricFamily> gauges = new ArrayList<>();

        Map<String, Object> objects = getObjectsForKeys(aggregators.stream()
                .filter(a -> a.getOwnerGroup() != null && a.getCombinator() != null)
                .map(a -> a.getOwnerGroup().getName() + "/" + a.getCombinator().getName())
                .collect(Collectors.toList()));

        if(objects != null && !objects.isEmpty()) {
            aggregators.removeIf(a -> !objects.containsKey(Constants.MEMCACHED_MEASUREMENT_PREFIX + a.getOwnerGroup().getName() + "/" + a.getCombinator().getName()));
            Map<String, List<Aggregator>> byDataTypeName = aggregators.stream().collect(Collectors.groupingBy(a -> a.getDataType().getName()));

            GaugeMetricFamily labeledGauge
                    = new GaugeMetricFamily("group_measurement", "gauge for all groups ",
                    Arrays.asList("group", "datatype", "combinator", "location_name", "location_x", "location_y"));

            for (String dataTypeName : byDataTypeName.keySet()) {

                for (Aggregator aggregator : byDataTypeName.get(dataTypeName)) {

                    Double value = Utils.convertToDouble(
                            objects.get(Constants.MEMCACHED_MEASUREMENT_PREFIX + aggregator.getOwnerGroup().getName() + "/" + aggregator.getCombinator().getName()));

                    if (value != null) {
                        Location location = aggregator.getLocation();

                        labeledGauge.addMetric(
                                Arrays.asList(
                                        aggregator.getOwnerGroup().getName(),
                                        aggregator.getDataType().getName(),
                                        aggregator.getCombinator().getName(),
                                        location == null ? "null" : location.getName(),
                                        location == null ? "null" : String.valueOf(location.getX()),
                                        location == null ? "null" : String.valueOf(location.getY())),
                                value
                        );
                    }
                }

                gauges.add(labeledGauge);
            }
        }
        return gauges;
    }


    /**
     * return the objects for the given keys
     *
     * @param keys list of keys
     * @return map of objects
     */
    private Map<String, Object> getObjectsForKeys(List<String> keys) {

        Map<String, Object> objects = null;
        if(memcachedClientWrapper != null) {


            objects = memcachedClientWrapper.getObjects(
                    keys.stream()
                            .map(k -> Constants.MEMCACHED_MEASUREMENT_PREFIX + k)
                            .collect(Collectors.toList())
            );
        }
        else {
            log.error("No connection to memcached server - Can not access cache!!");
        }
        return objects;
    }


}
