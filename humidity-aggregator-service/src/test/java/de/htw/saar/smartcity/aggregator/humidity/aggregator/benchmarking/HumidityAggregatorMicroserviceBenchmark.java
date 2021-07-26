package de.htw.saar.smartcity.aggregator.humidity.aggregator.benchmarking;

import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.humidity.aggregator.base.HumidityAggregatorSetupDataLoader;
import de.htw.saar.smartcity.aggregator.humidity.aggregator.handler.HumidityAggregatorGroupMeasurementHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes={
        HumidityAggregatorGroupMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.humidity.aggregator.storage.HumidityAggregatorStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.humidity.aggregator.broker.HumidityAggregatorPublisher.class,
        de.htw.saar.smartcity.aggregator.humidity.aggregator.base.HumidityAggregatorSetupDataLoader.class,
        de.htw.saar.smartcity.aggregator.humidity.aggregator.properties.HumidityAggregatorApplicationProperties.class,
        de.htw.saar.smartcity.aggregator.lib.service.DataTypeService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.SensorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.AggregatorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.AggregatorRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.ProducerService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.ProducerRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.GroupService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.GroupRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.CombinatorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.CombinatorRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.GroupTypeRepository.class
})

@EnableJpaRepositories(basePackages = "de.htw.saar.smartcity.aggregator.lib")
@EntityScan("de.htw.saar.smartcity.aggregator.lib")
@TestPropertySource(locations={"classpath:application-test.properties"})
@AutoConfigureDataJpa
@RunWith(SpringRunner.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class HumidityAggregatorMicroserviceBenchmark {

    private final static Integer WARMUP_ITERATIONS = 0;

    private final static Integer MEASUREMENT_ITERATIONS = 1;



    @Test
    public void executeJmhRunner() throws RunnerException {
        Options jmhRunnerOptions = new OptionsBuilder()
                // set the class name regex for benchmarks to search for to the current class
                .include("\\." + this.getClass().getSimpleName() + "\\.")
                .warmupIterations(WARMUP_ITERATIONS)
                .measurementIterations(MEASUREMENT_ITERATIONS)
                // do not use forking or the benchmark methods will not see references stored within its class
                .forks(0)
                // multiple threads
                .threads(1)
                .shouldDoGC(true)
                .shouldFailOnError(true)
                .resultFormat(ResultFormatType.JSON)
                //.result("/dev/null") // filename for report
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(jmhRunnerOptions).run();
    }

    private static HumidityAggregatorSetupDataLoader humidityAggregatorSetupDataLoader;

    @Autowired
    void setHumidityAggregatorSetupDataLoader(HumidityAggregatorSetupDataLoader humidityAggregatorSetupDataLoader) {
        this.humidityAggregatorSetupDataLoader = humidityAggregatorSetupDataLoader;
    }

    private static HumidityAggregatorGroupMeasurementHandler humidityAggregatorGroupMeasurementHandler;

    private final long[] HUMIDITY_GROUP_ID = new long[] {1L, 2L, 3L};
    private final long[] HUMIDITY_GROUP_1 = new long[] {1L, 2L};
    private final long[] HUMIDITY_GROUP_2 = new long[] {3L, 4L};
    private final long[] HUMIDITY_GROUP_3 = new long[] {5L, 6L};
    private final long[][] HUMIDITY_GROUP_MEMBERS = new long[][] {HUMIDITY_GROUP_1, HUMIDITY_GROUP_2, HUMIDITY_GROUP_3};

    @Autowired
    void setHumidityAggregatorGroupMeasurementHandler(HumidityAggregatorGroupMeasurementHandler humidityAggregatorGroupMeasurementHandler) {
        HumidityAggregatorMicroserviceBenchmark.humidityAggregatorGroupMeasurementHandler = humidityAggregatorGroupMeasurementHandler;
    }

    @Benchmark
    public void benchmarkHandle() {
        // check if humidityAggregatorGroupMeasurementHandler is present
        assert(humidityAggregatorGroupMeasurementHandler != null);

        Measurement<Double> humidityMeasurement = new Measurement<>();
        humidityMeasurement.setValue(ThreadLocalRandom.current().nextDouble(0, 100));
        humidityMeasurement.setTime(LocalDateTime.now());
        int pos = ThreadLocalRandom.current().nextInt(0,3);
        humidityAggregatorGroupMeasurementHandler.handleMeasurement(HUMIDITY_GROUP_ID[pos], HUMIDITY_GROUP_MEMBERS[pos][0], humidityMeasurement);
        humidityMeasurement.setValue(ThreadLocalRandom.current().nextDouble(0, 100));
        humidityMeasurement.setTime(LocalDateTime.now());
        humidityAggregatorGroupMeasurementHandler.handleMeasurement(HUMIDITY_GROUP_ID[pos], HUMIDITY_GROUP_MEMBERS[pos][1], humidityMeasurement);
    }
}