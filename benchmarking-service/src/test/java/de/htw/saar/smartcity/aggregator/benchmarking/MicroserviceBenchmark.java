package de.htw.saar.smartcity.aggregator.benchmarking;

import de.htw.saar.smartcity.aggregator.benchmarking.base.BenchmarkingSetupDataLoader;
import de.htw.saar.smartcity.aggregator.benchmarking.handler.BenchmarkingRawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
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
public class MicroserviceBenchmark extends AbstractBenchmark {

    private static BenchmarkingSetupDataLoader benchmarkingSetupDataLoader;
    private static StorageWrapper storageWrapper;

    @Param({"1", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "150", "200", "250", "500", "625", "750", "875", "1000"})
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


    private static int i = 0;
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