package de.htw.saar.smartcity.aggregator.lib.spark;

import de.htw.saar.smartcity.aggregator.lib.entity.HistoricCombinator;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.factory.MeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.model.*;
import de.htw.saar.smartcity.aggregator.lib.properties.HistoricSparkAggregatorApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.HistoricCombinatorService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.storage.HistoricStorageWrapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class HistoricSparkKafkaRunner<T> extends SparkKafkaRunner {

    /**
     * The Historic combinator models.
     */
    protected List<HistoricCombinatorModel> historicCombinatorModels = new ArrayList<>();

    protected final HistoricSparkAggregatorApplicationProperties applicationProperties;

    protected final MeasurementFactory measurementFactory;

    protected final HistoricStorageWrapper storageWrapper;

    protected final SensorService sensorService;

    private final HistoricCombinatorService historicCombinatorService;

    public HistoricSparkKafkaRunner(HistoricSparkAggregatorApplicationProperties applicationProperties, JavaStreamingContext jsc,
                                    MeasurementFactory measurementFactory, HistoricStorageWrapper storageWrapper,
                                    SensorService sensorService, HistoricCombinatorService historicCombinatorService) {

        super(applicationProperties, jsc);
        this.applicationProperties = applicationProperties;
        this.measurementFactory = measurementFactory;
        this.storageWrapper = storageWrapper;
        this.sensorService = sensorService;
        this.historicCombinatorService = historicCombinatorService;
    }

    @Override
    protected void consume(JavaInputDStream<ConsumerRecord<String, String>> stream) {
        JavaDStream<SensorMeasurement> measurementJavaInputDStream = stream
                .map(x -> new SensorMeasurement(x.key(), x.value()));


        for(TimeInterval timeInterval : applicationProperties.getApplicationBatchIntervalValues()) {

            measurementJavaInputDStream
                    .window(new org.apache.spark.streaming.Duration(timeInterval.interval),
                            new org.apache.spark.streaming.Duration(timeInterval.interval))
                    .foreachRDD(rdd ->
                            process(
                                    timeInterval,
                                    rdd.collect()
                            )
                    );
        }
    }

    protected void process(TimeInterval timeInterval, List<SensorMeasurement> sensorMeasurements) {

        if(sensorMeasurements.size() == 0)
            return;

        List<String> sensorNames = sensorMeasurements.stream()
                .map(s -> s.getSensorName()).distinct().collect(Collectors.toList());

        List<ProcessedSensorMeasurement<T>> sensorTemperatureMeasurements = sensorMeasurements.stream()
                .map(s -> new ProcessedSensorMeasurement<T>(s.getSensorName(), measurementFactory.create(s.getMeasurement())))
                .collect(Collectors.toList());

        for(String sensorName : sensorNames) {

            Sensor s = sensorService.findSensorByName(sensorName);

            for (HistoricCombinatorModel historicCombinatorModel : historicCombinatorModels) {

                Measurement aggregate = getMeasurement(historicCombinatorModel,
                        sensorTemperatureMeasurements.stream()
                                .filter(m -> m.getSensorName().equals(sensorName))
                                .map(stm -> stm.getMeasurement())
                                .collect(Collectors.toList())
                );

                s.getObjectStorePath();

                Calendar cal = Calendar.getInstance();
                LocalDate dt = LocalDate.now();
                String pathFactor = "";
                String pathSuffix = "";
                switch (timeInterval) {
                    case Day:
                        pathFactor = String.format("/%d/%d",
                                cal.get(Calendar.YEAR),
                                cal.get(Calendar.MONTH) + 1
                        );
                        pathSuffix = String.valueOf(dt.getDayOfMonth());
                        break;

                    case Hour:
                        pathFactor = String.format("/%d/%d/%d",
                                cal.get(Calendar.YEAR),
                                cal.get(Calendar.MONTH) + 1,
                                cal.get(Calendar.DAY_OF_MONTH)
                        );
                        pathSuffix = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
                        break;
                }

                if(aggregate != null)
                    storageWrapper.putHistoricMeasurementCheckCompression(
                            s.getObjectStorePath() + pathFactor +  "/historic/" + historicCombinatorModel.getName() + "/" + pathSuffix,
                            aggregate
                    );

            }
        }
    }

    /**
     * combine the measurement by applying the HistoricCombinatorModel to the list of measurements
     * @param historicCombinatorModel the model used to calculate
     * @param measurements the measurements for the computation
     * @return the historic aggregate
     */
    private static Measurement getMeasurement(HistoricCombinatorModel historicCombinatorModel, List<Measurement> measurements) {

        Measurement m = null;
        if(measurements != null) {
            Double combined = (Double) historicCombinatorModel.getFunction().apply(measurements);
            m = new Measurement();
            m.setValue(combined);
            m.setTime(LocalDateTime.now());
        }
        return m;
    }

    @Override
    protected Pattern getSubscribePattern() {
        return Pattern.compile(applicationProperties.getKafkaTopicPattern());
    }

    /**
     * necessary initialization after object construction
     */
    @PostConstruct
    private void init() {
        addHistoricCombinators();
        createHistoricCombinatorsIfNotFound();
    }

    /**
     * Add historic combinators.
     */
    protected abstract void addHistoricCombinators();

    private void createHistoricCombinatorsIfNotFound() {

        for(HistoricCombinatorModel historicCombinatorModel : historicCombinatorModels)
        {
            createHistoricCombinatorIfNotFound(historicCombinatorModel.getName());
        }
    }

    /**
     * Create historic combinator if not found historic combinator.
     *
     * @param historicCombinatorName the historic combinator name
     * @return the historic combinator
     */
    @Transactional
    protected HistoricCombinator createHistoricCombinatorIfNotFound(String historicCombinatorName) {

        HistoricCombinator historicCombinator = historicCombinatorService.findHistoricCombinatorByName(historicCombinatorName);
        if(historicCombinator == null) {
            historicCombinator = new HistoricCombinator();
            historicCombinator.setName(historicCombinatorName);
            historicCombinatorService.saveHistoricCombinator(historicCombinator);
        }
        return historicCombinator;
    }

}
