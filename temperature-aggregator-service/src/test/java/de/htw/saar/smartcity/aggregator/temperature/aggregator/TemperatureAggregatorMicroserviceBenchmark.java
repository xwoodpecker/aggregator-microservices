package de.htw.saar.smartcity.aggregator.temperature.aggregator;

import de.htw.saar.smartcity.aggregator.temperature.aggregator.base.TemperatureAggregatorSetupDataLoader;
import de.htw.saar.smartcity.aggregator.temperature.aggregator.handler.TemperatureAggregatorGroupMeasurementHandler;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes={
        TemperatureAggregatorGroupMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.temperature.aggregator.storage.TemperatureAggregatorStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.temperature.aggregator.broker.TemperatureAggregatorPublisher.class,
        de.htw.saar.smartcity.aggregator.temperature.aggregator.base.TemperatureAggregatorSetupDataLoader.class,
        de.htw.saar.smartcity.aggregator.temperature.aggregator.properties.TemperatureAggregatorApplicationProperties.class,
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
@TestPropertySource(locations={"classpath:application.properties"})
@AutoConfigureDataJpa
@RunWith(SpringRunner.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class TemperatureAggregatorMicroserviceBenchmark {

    private final static Integer WARMUP_ITERATIONS = 2;
    private final static Integer MEASUREMENT_ITERATIONS = 4;

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

    private static TemperatureAggregatorSetupDataLoader temperatureAggregatorSetupDataLoader;

    @Autowired
    void setTemperatureAggregatorSetupDataLoader(TemperatureAggregatorSetupDataLoader temperatureAggregatorSetupDataLoader) {
        this.temperatureAggregatorSetupDataLoader = temperatureAggregatorSetupDataLoader;
    }

    private static TemperatureAggregatorGroupMeasurementHandler temperatureAggregatorGroupMeasurementHandler;



    //todo

    @Autowired
    void setTemperatureAggregatorGroupMeasurementHandler(TemperatureAggregatorGroupMeasurementHandler temperatureAggregatorGroupMeasurementHandler) {
        TemperatureAggregatorMicroserviceBenchmark.temperatureAggregatorGroupMeasurementHandler = temperatureAggregatorGroupMeasurementHandler;
    }

    @Benchmark
    public void benchmarkHandle() {
        // check if temperatureAggregatorGroupMeasurementHandler is present
        assert(temperatureAggregatorGroupMeasurementHandler != null);

        //todo
    }
}