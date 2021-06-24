package de.htw.saar.smartcity.aggregator.benchmarking;

import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.picture.handler.PictureRawMeasurementHandler;
import org.junit.runner.RunWith;
import org.openjdk.jmh.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes={
        PictureRawMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.picture.factory.PictureMeasurementFactory.class,
        de.htw.saar.smartcity.aggregator.picture.storage.PictureStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.picture.broker.PicturePublisher.class,
        de.htw.saar.smartcity.aggregator.picture.properties.PictureApplicationProperties.class,
        de.htw.saar.smartcity.aggregator.lib.service.DataTypeService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.SensorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository.class,
        de.htw.saar.smartcity.aggregator.lib.service.AggregatorService.class,
        de.htw.saar.smartcity.aggregator.lib.repository.AggregatorRepository.class
})

@EnableJpaRepositories(basePackages = "de.htw.saar.smartcity.aggregator.lib")
@EntityScan("de.htw.saar.smartcity.aggregator.lib")
@TestPropertySource(locations={"classpath:picture.properties"})
@AutoConfigureDataJpa
@RunWith(SpringRunner.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PictureMicroserviceBenchmark extends AbstractBenchmark {

    private static PictureRawMeasurementHandler pictureRawMeasurementHandler;

    @Autowired
    void setPictureRawMeasurementHandler(PictureRawMeasurementHandler pictureRawMeasurementHandler) {
        PictureMicroserviceBenchmark.pictureRawMeasurementHandler = pictureRawMeasurementHandler;
    }

    @Benchmark
    public void benchmarkHandle() {
        // check if pictureRawMeasurementHandler is present
        assert(pictureRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();
        String encodedString = "";
        try {
            File f = new ClassPathResource("/images/image1.jpeg").getFile();
            byte[] bytes = new FileInputStream(f).readAllBytes();
            encodedString = Base64.getEncoder().encodeToString(bytes);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        sensorMeasurement.setMeasurement(encodedString);
        sensorMeasurement.setSensorName("data/aggregator/picture/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        pictureRawMeasurementHandler.handleMessage(sensorMeasurement);
    }


    @Benchmark
    public void benchmark1KB() {
        // check if pictureRawMeasurementHandler is present
        assert(pictureRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();
        char[] data = new char[1000];
        String msg = new String(data);

        sensorMeasurement.setMeasurement(msg);
        sensorMeasurement.setSensorName("data/aggregator/picture/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        pictureRawMeasurementHandler.handleMessage(sensorMeasurement);
    }


    @Benchmark
    public void benchmark100KB() {
        // check if pictureRawMeasurementHandler is present
        assert(pictureRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();
        char[] data = new char[1000*100];
        String msg = new String(data);

        sensorMeasurement.setMeasurement(msg);
        sensorMeasurement.setSensorName("data/aggregator/picture/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        pictureRawMeasurementHandler.handleMessage(sensorMeasurement);
    }

    @Benchmark
    public void benchmark1MB() {
        // check if pictureRawMeasurementHandler is present
        assert(pictureRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();
        char[] data = new char[1000*1000];
        String msg = new String(data);

        sensorMeasurement.setMeasurement(msg);
        sensorMeasurement.setSensorName("data/aggregator/picture/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        pictureRawMeasurementHandler.handleMessage(sensorMeasurement);
    }
}