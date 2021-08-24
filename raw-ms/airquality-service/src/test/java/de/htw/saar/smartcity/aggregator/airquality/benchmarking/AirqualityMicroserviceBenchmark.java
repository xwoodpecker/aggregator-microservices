package de.htw.saar.smartcity.aggregator.airquality.benchmarking;

import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.airquality.base.AirqualitySetupDataLoader;
import de.htw.saar.smartcity.aggregator.airquality.handler.AirqualityRawMeasurementHandler;
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
        AirqualityRawMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.airquality.factory.AirqualityMeasurementFactory.class,
        de.htw.saar.smartcity.aggregator.airquality.storage.AirqualityStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.airquality.broker.AirqualityPublisher.class,
        de.htw.saar.smartcity.aggregator.airquality.base.AirqualitySetupDataLoader.class,
        de.htw.saar.smartcity.aggregator.airquality.properties.AirqualityApplicationProperties.class,
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
public class AirqualityMicroserviceBenchmark {

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


    private static AirqualitySetupDataLoader airqualitySetupDataLoader;

    @Autowired
    void setAirqualitySetupDataLoader(AirqualitySetupDataLoader airqualitySetupDataLoader) {
        this.airqualitySetupDataLoader = airqualitySetupDataLoader;
    }
    
    private static AirqualityRawMeasurementHandler airqualityRawMeasurementHandler;

    @Autowired
    void setAirqualityRawMeasurementHandler(AirqualityRawMeasurementHandler airqualityRawMeasurementHandler) {
        this.airqualityRawMeasurementHandler = airqualityRawMeasurementHandler;
    }

    @Benchmark
    public void benchmarkHandle() {
        // check if airqualityRawMeasurementHandler is present
        assert(airqualityRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();
        sensorMeasurement.setMeasurement(String.valueOf(ThreadLocalRandom.current().nextDouble(-30, 40)));
        sensorMeasurement.setSensorName("data/aggregator/test/airquality/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        airqualityRawMeasurementHandler.handleMessage(sensorMeasurement);
    }
}