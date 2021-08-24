package de.htw.saar.smartcity.aggregator.benchmarking;

import de.htw.saar.smartcity.aggregator.benchmarking.base.BenchmarkingSetupDataLoader;
import de.htw.saar.smartcity.aggregator.benchmarking.handler.BenchmarkingRawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
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

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes={
        BenchmarkingRawMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.benchmarking.factory.BenchmarkingMeasurementFactory.class,
        de.htw.saar.smartcity.aggregator.benchmarking.storage.BenchmarkingStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.benchmarking.broker.BenchmarkingPublisher.class,
        de.htw.saar.smartcity.aggregator.benchmarking.base.BenchmarkingSetupDataLoader.class,
        de.htw.saar.smartcity.aggregator.benchmarking.properties.BenchmarkingApplicationProperties.class,
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
public class MicroserviceBenchmark {


    private final static Integer WARMUP_ITERATIONS = 2;
    private final static Integer MEASUREMENT_ITERATIONS = 10;

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
                .result("/out/result.json") // filename for report
                .jvmArgs("-server")
                .build();

        new Runner(jmhRunnerOptions).run();
    }
    
    private static BenchmarkingSetupDataLoader benchmarkingSetupDataLoader;
    private static StorageWrapper storageWrapper;


    private static int i = 0;

    @Param({"1", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "200", "300", "400", "500", "750", "1000"})
    public int sizeInKB;


    @Autowired
    void setBenchmarkingSetupDataLoader(BenchmarkingSetupDataLoader benchmarkingSetupDataLoader) {
        this.benchmarkingSetupDataLoader = benchmarkingSetupDataLoader;
    }

    @Autowired
    void setStorageWrapper(StorageWrapper storageWrapper) {
        this.storageWrapper = storageWrapper;
    }

    private static BenchmarkingRawMeasurementHandler benchmarkingRawMeasurementHandler;

    @Autowired
    void setBenchmarkingRawMeasurementHandler(BenchmarkingRawMeasurementHandler benchmarkingRawMeasurementHandler) {
        MicroserviceBenchmark.benchmarkingRawMeasurementHandler = benchmarkingRawMeasurementHandler;
    }

    @Benchmark
    public void executeBenchmark() {
        benchmarkWithMessage(generateMessageOfSize(sizeInKB * 1024));
    }

    @TearDown(Level.Iteration)
    public void doTearDown() {
        storageWrapper.deleteObjectsByPrefixRecursive("data/");
    }


    public void benchmarkWithMessage(String message) {
        // check if benchmarkingRawMeasurementHandler is present
        assert(benchmarkingRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();

        sensorMeasurement.setMeasurement(message);
        sensorMeasurement.setSensorName("data/aggregator/benchmark/sensor" + ((i++) % 100 + 1));
        benchmarkingRawMeasurementHandler.handleMessage(sensorMeasurement);
    }

    private static String generateMessageOfSize(int size) {
        char[] data = new char[size];
        Arrays.fill(data,'x');
        return new String(data);
    }
}