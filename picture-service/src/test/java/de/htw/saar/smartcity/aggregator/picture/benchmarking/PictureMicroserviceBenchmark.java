package de.htw.saar.smartcity.aggregator.picture.benchmarking;

import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.picture.base.PictureSetupDataLoader;
import de.htw.saar.smartcity.aggregator.picture.handler.PictureRawMeasurementHandler;
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
        PictureRawMeasurementHandler.class,
        de.htw.saar.smartcity.aggregator.picture.factory.PictureMeasurementFactory.class,
        de.htw.saar.smartcity.aggregator.picture.storage.PictureStorageWrapper.class,
        de.htw.saar.smartcity.aggregator.picture.broker.PicturePublisher.class,
        de.htw.saar.smartcity.aggregator.picture.base.PictureSetupDataLoader.class,
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
@TestPropertySource(locations={"classpath:application.properties"})
@AutoConfigureDataJpa
@RunWith(SpringRunner.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class PictureMicroserviceBenchmark {

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

    private static PictureSetupDataLoader pictureSetupDataLoader;

    @Autowired
    void setPictureSetupDataLoader(PictureSetupDataLoader pictureSetupDataLoader) {
        this.pictureSetupDataLoader = pictureSetupDataLoader;
    }

    private static PictureRawMeasurementHandler pictureRawMeasurementHandler;

    @Autowired
    void setPictureRawMeasurementHandler(PictureRawMeasurementHandler pictureRawMeasurementHandler) {
        this.pictureRawMeasurementHandler = pictureRawMeasurementHandler;
    }



    @Benchmark
    public void benchmark50KbSample() {
        // check if pictureRawMeasurementHandler is present
        assert(pictureRawMeasurementHandler != null);

        SensorMeasurement sensorMeasurement =  new SensorMeasurement();
        char[] data = new char[50*1000];
        String msg = new String(data);

        sensorMeasurement.setMeasurement(msg);
        sensorMeasurement.setSensorName("data/aggregator/test/picture/sensor" + ThreadLocalRandom.current().nextInt(1, 21));
        pictureRawMeasurementHandler.handleMessage(sensorMeasurement);
    }
}