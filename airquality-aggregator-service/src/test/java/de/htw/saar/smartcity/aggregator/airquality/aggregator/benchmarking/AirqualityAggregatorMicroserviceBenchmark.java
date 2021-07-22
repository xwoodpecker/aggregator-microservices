package de.htw.saar.smartcity.aggregator.airquality.aggregator.benchmarking;

import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.airquality.aggregator.base.AirqualityAggregatorSetupDataLoader;
import de.htw.saar.smartcity.aggregator.airquality.aggregator.handler.AirqualityAggregatorGroupMeasurementHandler;
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
        AirqualityAggregatorGroupMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.airquality.aggregator.storage.AirqualityAggregatorStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.airquality.aggregator.broker.AirqualityAggregatorPublisher.class,
        de.htw.saar.smartcity.aggregator.airquality.aggregator.base.AirqualityAggregatorSetupDataLoader.class,
        de.htw.saar.smartcity.aggregator.airquality.aggregator.properties.AirqualityAggregatorApplicationProperties.class,
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
public class AirqualityAggregatorMicroserviceBenchmark {

    private final static Integer WARMUP_ITERATIONS = 0;

    private final static Integer MEASUREMENT_ITERATIONS = 1;

    /**
     * Any benchmark, by extending this class, inherits this single @Test method for JUnit to run.
     */

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

    private static AirqualityAggregatorSetupDataLoader airqualityAggregatorSetupDataLoader;

    @Autowired
    void setAirqualityAggregatorSetupDataLoader(AirqualityAggregatorSetupDataLoader airqualityAggregatorSetupDataLoader) {
        this.airqualityAggregatorSetupDataLoader = airqualityAggregatorSetupDataLoader;
    }

    private static AirqualityAggregatorGroupMeasurementHandler airqualityAggregatorGroupMeasurementHandler;


    private final long[] AIRQUALITY_GROUP_ID = new long[] {1L, 2L, 3L};
    private final long[] AIRQUALITY_GROUP_1 = new long[] {1L, 2L};
    private final long[] AIRQUALITY_GROUP_2 = new long[] {3L, 4L};
    private final long[] AIRQUALITY_GROUP_3 = new long[] {5L, 6L};
    private final long[][] AIRQUALITY_GROUP_MEMBERS = new long[][] {AIRQUALITY_GROUP_1, AIRQUALITY_GROUP_2, AIRQUALITY_GROUP_3};

    @Autowired
    void setAirqualityAggregatorGroupMeasurementHandler(AirqualityAggregatorGroupMeasurementHandler airqualityAggregatorGroupMeasurementHandler) {
        AirqualityAggregatorMicroserviceBenchmark.airqualityAggregatorGroupMeasurementHandler = airqualityAggregatorGroupMeasurementHandler;
    }

    @Benchmark
    public void benchmarkHandle() {
        // check if airqualityAggregatorGroupMeasurementHandler is present
        assert(airqualityAggregatorGroupMeasurementHandler != null);

        Measurement<Double> airqualityMeasurement = new Measurement<>();
        airqualityMeasurement.setValue(ThreadLocalRandom.current().nextDouble(-30, 40));
        airqualityMeasurement.setTime(LocalDateTime.now());
        int pos = ThreadLocalRandom.current().nextInt(0,3);
        airqualityAggregatorGroupMeasurementHandler.handleMeasurement(AIRQUALITY_GROUP_ID[pos], AIRQUALITY_GROUP_MEMBERS[pos][0], airqualityMeasurement);
        airqualityMeasurement.setValue(ThreadLocalRandom.current().nextDouble(-30, 40));
        airqualityMeasurement.setTime(LocalDateTime.now());
        airqualityAggregatorGroupMeasurementHandler.handleMeasurement(AIRQUALITY_GROUP_ID[pos], AIRQUALITY_GROUP_MEMBERS[pos][1], airqualityMeasurement);
    }
}