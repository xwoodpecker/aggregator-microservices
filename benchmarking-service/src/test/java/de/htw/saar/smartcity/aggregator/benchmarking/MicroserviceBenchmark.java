package de.htw.saar.smartcity.aggregator.benchmarking;

import de.htw.saar.smartcity.aggregator.benchmarking.base.BenchmarkingSetupDataLoader;
import de.htw.saar.smartcity.aggregator.benchmarking.handler.BenchmarkingRawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
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
import java.util.concurrent.ThreadLocalRandom;
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

    private static String message1KB = generateMessageOfSize(1024);
    private static String message10KB = generateMessageOfSize(10*1024);
    private static String message100KB = generateMessageOfSize(100*1024);

    @Autowired
    void setBenchmarkingSetupDataLoader(BenchmarkingSetupDataLoader benchmarkingSetupDataLoader) {
        this.benchmarkingSetupDataLoader = benchmarkingSetupDataLoader;
    }

    private static BenchmarkingRawMeasurementHandler benchmarkingRawMeasurementHandler;

    @Autowired
    void setBenchmarkingRawMeasurementHandler(BenchmarkingRawMeasurementHandler benchmarkingRawMeasurementHandler) {
        MicroserviceBenchmark.benchmarkingRawMeasurementHandler = benchmarkingRawMeasurementHandler;
    }


    @Benchmark
    public void benchmark1KB() {
        // check if benchmarkingRawMeasurementHandler is present
        assert(benchmarkingRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();

        sensorMeasurement.setMeasurement(message1KB);
        sensorMeasurement.setSensorName("data/aggregator/benchmark/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        benchmarkingRawMeasurementHandler.handleMessage(sensorMeasurement);
    }


    @Benchmark
    public void benchmark10KB() {
        // check if benchmarkingRawMeasurementHandler is present
        assert(benchmarkingRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();

        sensorMeasurement.setMeasurement(message10KB);
        sensorMeasurement.setSensorName("data/aggregator/benchmark/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        benchmarkingRawMeasurementHandler.handleMessage(sensorMeasurement);
    }

    @Benchmark
    public void benchmark100KB() {
        // check if benchmarkingRawMeasurementHandler is present
        assert(benchmarkingRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();

        sensorMeasurement.setMeasurement(message100KB);
        sensorMeasurement.setSensorName("data/aggregator/benchmark/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        benchmarkingRawMeasurementHandler.handleMessage(sensorMeasurement);
    }

    private static String generateMessageOfSize(int size) {
        char[] data = new char[size];
        Arrays.fill(data,'x');
        return new String(data);
    }
}