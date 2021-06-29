package de.htw.saar.smartcity.aggregator.humidity.benchmarking;

import de.htw.saar.smartcity.aggregator.humidity.base.HumiditySetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.humidity.handler.HumidityRawMeasurementHandler;
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

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes={
        HumidityRawMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.humidity.factory.HumidityMeasurementFactory.class,
        de.htw.saar.smartcity.aggregator.humidity.storage.HumidityStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.humidity.broker.HumidityPublisher.class,
        de.htw.saar.smartcity.aggregator.humidity.base.HumiditySetupDataLoader.class,
        de.htw.saar.smartcity.aggregator.humidity.properties.HumidityApplicationProperties.class,
        de.htw.saar.smartcity.aggregator.lib.service.DataTypeService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.SensorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.AggregatorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.AggregatorRepository.class
})

@EnableJpaRepositories(basePackages = "de.htw.saar.smartcity.aggregator.lib")
@EntityScan("de.htw.saar.smartcity.aggregator.lib")
@TestPropertySource(locations={"classpath:application.properties"})
@AutoConfigureDataJpa
@RunWith(SpringRunner.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class HumidityMicroserviceBenchmark {

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

    private static HumiditySetupDataLoader humiditySetupDataLoader;

    @Autowired
    void setHumiditySetupDataLoader(HumiditySetupDataLoader humiditySetupDataLoader) {
        this.humiditySetupDataLoader = humiditySetupDataLoader;
    }

    private static HumidityRawMeasurementHandler humidityRawMeasurementHandler;

    @Autowired
    void setHumidityRawMeasurementHandler(HumidityRawMeasurementHandler humidityRawMeasurementHandler) {
        this.humidityRawMeasurementHandler = humidityRawMeasurementHandler;
    }


    @Benchmark
    public void benchmarkHandle() {
        // check if humidityRawMeasurementHandler is present
        assert(humidityRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();
        sensorMeasurement.setMeasurement(String.valueOf(ThreadLocalRandom.current().nextDouble(0, 100)));
        sensorMeasurement.setSensorName("data/aggregator/test/humidity/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        humidityRawMeasurementHandler.handleMessage(sensorMeasurement);
    }
}