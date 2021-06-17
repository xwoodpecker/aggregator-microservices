package de.htw.saar.smartcity.aggregator.benchmarking;

import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.temperature.handler.TemperatureRawMeasurementHandler;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
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
        de.htw.saar.smartcity.aggregator.temperature.handler.TemperatureRawMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.temperature.factory.TemperatureMeasurementFactory.class,
        de.htw.saar.smartcity.aggregator.temperature.storage.TemperatureStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.temperature.broker.TemperaturePublisher.class,
        de.htw.saar.smartcity.aggregator.temperature.properties.TemperatureApplicationProperties.class,
        de.htw.saar.smartcity.aggregator.lib.service.DataTypeService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.SensorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository.class
})

@EnableJpaRepositories(basePackages = "de.htw.saar.smartcity.aggregator.lib")
@EntityScan("de.htw.saar.smartcity.aggregator.lib")
@TestPropertySource(locations={"classpath:temperature.properties"})
@AutoConfigureDataJpa
@RunWith(SpringRunner.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class TemperatureMicroserviceBenchmark extends AbstractBenchmark {

    private static TemperatureRawMeasurementHandler temperatureRawMeasurementHandler;

    @Autowired
    void setTemperatureRawMeasurementHandler(TemperatureRawMeasurementHandler temperatureRawMeasurementHandler) {
        TemperatureMicroserviceBenchmark.temperatureRawMeasurementHandler = temperatureRawMeasurementHandler;
    }

    @Benchmark
    public void benchmarkHandle() {
        // check if temperatureRawMeasurementHandler is present
        assert(temperatureRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();
        sensorMeasurement.setMeasurement(String.valueOf(ThreadLocalRandom.current().nextDouble(-30, 40)));
        sensorMeasurement.setSensorName("data/aggregator/temperature/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        temperatureRawMeasurementHandler.handleMessage(sensorMeasurement);
    }
}