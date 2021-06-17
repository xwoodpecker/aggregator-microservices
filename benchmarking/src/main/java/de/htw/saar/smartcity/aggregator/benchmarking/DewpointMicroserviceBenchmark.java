package de.htw.saar.smartcity.aggregator.benchmarking;

import de.htw.saar.smartcity.aggregator.dewpoint.handler.DewpointGroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
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
        DewpointGroupMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.dewpoint.storage.DewpointStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.dewpoint.broker.DewpointPublisher.class,
        de.htw.saar.smartcity.aggregator.dewpoint.properties.DewpointApplicationProperties.class,
        de.htw.saar.smartcity.aggregator.lib.service.DataTypeService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.SensorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.ProducerService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.ProducerRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.GroupService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.GroupRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.CombinatorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.CombinatorRepository.class
})

@EnableJpaRepositories(basePackages = "de.htw.saar.smartcity.aggregator.lib")
@EntityScan("de.htw.saar.smartcity.aggregator.lib")
@TestPropertySource(locations={"classpath:dewpoint.properties"})
@AutoConfigureDataJpa
@RunWith(SpringRunner.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class DewpointMicroserviceBenchmark extends AbstractBenchmark {


    private static DewpointGroupMeasurementHandler dewpointGroupMeasurementHandler;

    private final long[] DEWPOINT_GROUP_ID = new long[] {4L, 5L, 6L};
    private final long[] TEMPERATURE_SENSOR_ID = new long[] {1L, 2L, 3L};
    private final long[] HUMIDITY_SENSOR_ID = new long[] {7L, 8L, 8L};


    @Autowired
    void setDewpointGroupMeasurementHandler(DewpointGroupMeasurementHandler dewpointGroupMeasurementHandler) {
        DewpointMicroserviceBenchmark.dewpointGroupMeasurementHandler = dewpointGroupMeasurementHandler;
    }

    @Benchmark
    public void benchmarkHandle() {
        // check if dewpointGroupMeasurementHandler is present
        assert(dewpointGroupMeasurementHandler != null);

        Measurement<Double> temperatureMeasurement = new Measurement<>();
        temperatureMeasurement.setValue(ThreadLocalRandom.current().nextDouble(-30, 40));
        temperatureMeasurement.setTime(LocalDateTime.now());
        int pos = ThreadLocalRandom.current().nextInt(0,3);
        dewpointGroupMeasurementHandler.handleMeasurement(DEWPOINT_GROUP_ID[pos], TEMPERATURE_SENSOR_ID[pos], temperatureMeasurement);
        Measurement<Double> humidityMeasurement = new Measurement<>();
        humidityMeasurement.setValue(ThreadLocalRandom.current().nextDouble(0, 100));
        humidityMeasurement.setTime(LocalDateTime.now());
        dewpointGroupMeasurementHandler.handleMeasurement(DEWPOINT_GROUP_ID[pos], HUMIDITY_SENSOR_ID[pos], humidityMeasurement);
    }
}