package ${package}.benchmarking;

import ${package}.base.GroupSetupDataLoaderImpl;
import ${package}.handler.GroupMeasurementHandlerImpl;
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
        GroupMeasurementHandlerImpl.class,
        ${package}.storage.StorageWrapperImpl.class,
        ${package}.broker.PublisherImpl.class,
        ${package}.base.GroupSetupDataLoaderImpl.class,
        ${package}.properties.GroupMicroserviceApplicationPropertiesImpl.class,
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
public class AggregatorMicroserviceBenchmark {

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

    private static GroupSetupDataLoaderImpl groupSetupDataLoader;

    @Autowired
    void setGroupSetupDataLoader(GroupSetupDataLoaderImpl groupSetupDataLoader) {
        this.groupSetupDataLoader = groupSetupDataLoader;
    }

    private static GroupMeasurementHandlerImpl groupMeasurementHandler;

    @Autowired
    void setGroupMeasurementHandler(GroupMeasurementHandlerImpl groupMeasurementHandler) {
        this.groupMeasurementHandler = groupMeasurementHandler;
    }


    @Benchmark
    public void benchmarkHandle() {
        // check if groupMeasurementHandler is present
        assert(groupMeasurementHandler != null);

        //todo : implement benchmark
        //temperatureAggregatorGroupMeasurementHandler.handleMeasurement();
    }
}