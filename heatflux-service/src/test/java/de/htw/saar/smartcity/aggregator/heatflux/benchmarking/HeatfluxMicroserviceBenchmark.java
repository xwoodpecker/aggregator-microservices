package de.htw.saar.smartcity.aggregator.heatflux.benchmarking;

import de.htw.saar.smartcity.aggregator.heatflux.base.HeatfluxSetupDataLoader;
import de.htw.saar.smartcity.aggregator.heatflux.handler.HeatfluxGroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
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
        HeatfluxGroupMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.heatflux.storage.HeatfluxStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.heatflux.broker.HeatfluxGroupsPublisher.class,
        de.htw.saar.smartcity.aggregator.heatflux.base.HeatfluxSetupDataLoader.class,
        de.htw.saar.smartcity.aggregator.heatflux.properties.HeatfluxApplicationProperties.class,
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
        de.htw.saar.smartcity.aggregator.lib.repository.GroupTypeRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.TagService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.TagRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.FormulaItemService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.FormulaItemRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.FormulaItemValueService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.FormulaItemValueRepository.class
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
public class HeatfluxMicroserviceBenchmark {

    private final static Integer WARMUP_ITERATIONS = 0;

    private final static Integer MEASUREMENT_ITERATIONS = 1;

    /**
     * Any benchmark, by extending this class, inherits this single @Test method for JUnit to run.
     */

    @Test
    public void executeJmhRunner() throws RunnerException {

        setupTest();

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

    private void setupTest() {

    }


    private static HeatfluxSetupDataLoader HeatfluxSetupDataLoader;

    @Autowired
    void setHeatfluxSetupDataLoader(HeatfluxSetupDataLoader HeatfluxSetupDataLoader) {
        this.HeatfluxSetupDataLoader = HeatfluxSetupDataLoader;
    }

    private static HeatfluxGroupMeasurementHandler heatfluxGroupMeasurementHandler;

    private final long[] HEATFLUX_GROUP_ID = new long[] {7L, 8L, 9L};
    private final long[] HEATFLUX_GROUP_1 = new long[] {22L, 1L, 4L};
    private final long[] HEATFLUX_GROUP_2 = new long[] {23L, 2L, 5L};
    private final long[] HEATFLUX_GROUP_3 = new long[] {24L, 3L, 6L};
    private final long[][] HEATFLUX_GROUP_MEMBERS = new long[][] {HEATFLUX_GROUP_1, HEATFLUX_GROUP_2, HEATFLUX_GROUP_3};


    @Autowired
    void setHeatfluxGroupMeasurementHandler(HeatfluxGroupMeasurementHandler HeatfluxGroupMeasurementHandler) {
        HeatfluxMicroserviceBenchmark.heatfluxGroupMeasurementHandler = HeatfluxGroupMeasurementHandler;
    }

    @Benchmark
    public void benchmarkHandle() {
        // check if HeatfluxGroupMeasurementHandler is present
        assert(heatfluxGroupMeasurementHandler != null);

        int pos = ThreadLocalRandom.current().nextInt(0,3);

        Measurement<Double> dewPointMeasurement = new Measurement<>();
        dewPointMeasurement.setValue(ThreadLocalRandom.current().nextDouble(-40, 30));
        dewPointMeasurement.setTime(LocalDateTime.now());
        heatfluxGroupMeasurementHandler.handleMeasurement(HEATFLUX_GROUP_ID[pos], HEATFLUX_GROUP_MEMBERS[pos][0], dewPointMeasurement);

        Measurement<Double> temperatureMeasurement = new Measurement<>();
        temperatureMeasurement.setValue(ThreadLocalRandom.current().nextDouble(-30, 40));
        temperatureMeasurement.setTime(LocalDateTime.now());
        heatfluxGroupMeasurementHandler.handleMeasurement(HEATFLUX_GROUP_ID[pos], HEATFLUX_GROUP_MEMBERS[pos][1], temperatureMeasurement);

        temperatureMeasurement.setValue(ThreadLocalRandom.current().nextDouble(-30, 40));
        temperatureMeasurement.setTime(LocalDateTime.now());
        heatfluxGroupMeasurementHandler.handleMeasurement(HEATFLUX_GROUP_ID[pos], HEATFLUX_GROUP_MEMBERS[pos][2], temperatureMeasurement);
    }
}