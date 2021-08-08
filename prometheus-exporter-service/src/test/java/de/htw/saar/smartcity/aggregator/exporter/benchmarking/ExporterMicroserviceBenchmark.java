package de.htw.saar.smartcity.aggregator.exporter.benchmarking;

import de.htw.saar.smartcity.aggregator.exporter.base.CustomCollectorImpl;
import de.htw.saar.smartcity.aggregator.lib.base.Constants;
import de.htw.saar.smartcity.aggregator.lib.storage.MemcachedClientWrapper;
import org.junit.Before;
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

import java.util.concurrent.TimeUnit;

@SpringBootTest(classes={
        de.htw.saar.smartcity.aggregator.exporter.base.CustomCollectorImpl.class,
        de.htw.saar.smartcity.aggregator.exporter.properties.ExporterApplicationPropertiesImpl.class,
        de.htw.saar.smartcity.aggregator.lib.service.SensorService.class,
        de.htw.saar.smartcity.aggregator.lib.service.AggregatorService.class,
        de.htw.saar.smartcity.aggregator.lib.broker.ActivityManager.class,
        de.htw.saar.smartcity.aggregator.lib.storage.MemcachedClientWrapper.class,
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
public class ExporterMicroserviceBenchmark {

    private final static Integer WARMUP_ITERATIONS = 2;

    private final static Integer MEASUREMENT_ITERATIONS = 10;

    private static MemcachedClientWrapper memcachedClientWrapper;

    @Autowired
    void setMeasurementIterations(MemcachedClientWrapper memcachedClientWrapper) {
        this.memcachedClientWrapper = memcachedClientWrapper;
    }

    @Before
    public void initMemcached() {
        int expirationTime = 10 * (WARMUP_ITERATIONS+MEASUREMENT_ITERATIONS+1);
        for(int i = 1; i <= 10000; i++) {
            //expiry set to 1 minute
            memcachedClientWrapper.putObjectWithExpiration(Constants.MEMCACHED_MEASUREMENT_PREFIX + "data/aggregator/benchmarking/exporter/sensor" + i, 1.0, expirationTime);
        }
    }

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
                .shouldFailOnError(true)
                .jvmArgs("-server")
                .build();

        new Runner(jmhRunnerOptions).run();
    }



    private static CustomCollectorImpl customCollector;

    @Autowired
    void setCustomCollector(CustomCollectorImpl customCollector) {
        this.customCollector = customCollector;
    }


    @Benchmark
    public void benchmarkHandle() {
        // check if customCollector is present
        assert(customCollector != null);

        customCollector.collect();
    }
}