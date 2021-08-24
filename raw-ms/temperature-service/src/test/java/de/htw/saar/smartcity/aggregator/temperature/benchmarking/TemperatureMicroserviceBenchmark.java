package de.htw.saar.smartcity.aggregator.temperature.benchmarking;

import de.htw.saar.smartcity.aggregator.temperature.base.TemperatureSetupDataLoader;
import de.htw.saar.smartcity.aggregator.temperature.handler.TemperatureRawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
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

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes={
        TemperatureRawMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.temperature.factory.TemperatureMeasurementFactory.class,
        de.htw.saar.smartcity.aggregator.temperature.storage.TemperatureStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.temperature.broker.TemperaturePublisher.class,
        de.htw.saar.smartcity.aggregator.temperature.base.TemperatureSetupDataLoader.class,
        de.htw.saar.smartcity.aggregator.temperature.properties.TemperatureApplicationProperties.class,
        de.htw.saar.smartcity.aggregator.lib.service.DataTypeService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.SensorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.AggregatorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.AggregatorRepository.class
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
public class TemperatureMicroserviceBenchmark  {

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


    private static TemperatureSetupDataLoader temperatureSetupDataLoader;

    @Autowired
    void setTemperatureSetupDataLoader(TemperatureSetupDataLoader temperatureSetupDataLoader) {
        this.temperatureSetupDataLoader = temperatureSetupDataLoader;
    }
    
    private static TemperatureRawMeasurementHandler temperatureRawMeasurementHandler;

    @Autowired
    void setTemperatureRawMeasurementHandler(TemperatureRawMeasurementHandler temperatureRawMeasurementHandler) {
        this.temperatureRawMeasurementHandler = temperatureRawMeasurementHandler;
    }

    @Benchmark
    public void benchmarkHandle() {
        // check if temperatureRawMeasurementHandler is present
        assert(temperatureRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();
        sensorMeasurement.setMeasurement(String.valueOf(ThreadLocalRandom.current().nextDouble(-30, 40)));
        sensorMeasurement.setSensorName("data/aggregator/test/temperature/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        temperatureRawMeasurementHandler.handleMessage(sensorMeasurement);
    }
}