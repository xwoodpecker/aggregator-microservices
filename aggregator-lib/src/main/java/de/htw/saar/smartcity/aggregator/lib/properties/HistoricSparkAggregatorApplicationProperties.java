package de.htw.saar.smartcity.aggregator.lib.properties;


import de.htw.saar.smartcity.aggregator.lib.model.TimeInterval;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import shaded.parquet.it.unimi.dsi.fastutil.longs.LongComparators;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Historic aggregator application properties.
 */
@Configuration
@PropertySource("classpath:application.properties")
public abstract class HistoricSparkAggregatorApplicationProperties extends  HistoricAggregatorApplicationProperties implements MinioApplicationProperties, KafkaApplicationProperties {

    @Value("${application.name}")
    private String appName;

    @Value("${spark.master.uri}")
    private String masterUri;

    @Value("${kafka.uri}")
    private String kafkaUri;

    private String[] applicationBatchIntervals;

    private List<TimeInterval> applicationBatchIntervalValues;

    @Bean
    public SparkConf sparkConf(){

        SparkConf sparkConf = new SparkConf()
                .setAppName(appName)
                .setMaster(masterUri);

        return sparkConf;
    }

    @Bean
    public SparkSession sparkSession(){

        return SparkSession
                .builder()
                .sparkContext(javaStreamingContext().ssc().sparkContext())
                .appName(appName)
                .getOrCreate();
    }

    @Bean
    public JavaStreamingContext javaStreamingContext() {

        return new JavaStreamingContext(sparkConf(),
                Durations.milliseconds(
                        applicationBatchIntervalValues.stream().map(timeInterval -> timeInterval.interval)
                                .min(LongComparators.NATURAL_COMPARATOR)
                                .orElse(TimeInterval.Hour.interval)
                )
        );

    }

    /**
     * The Kafka topic pattern.
     */
    protected String kafkaTopicPattern;

    /**
     * Sets kafka topic pattern.
     *
     * @param kafkaTopicPattern the kafka topic pattern
     */
    protected abstract void setKafkaTopicPattern(String kafkaTopicPattern);

    /**
     * Gets kafka topic pattern.
     *
     * @return the kafka topic pattern
     */
    public String getKafkaTopicPattern() {
        return kafkaTopicPattern;
    }


    public String getKafkaHost() {
        return kafkaUri;
    }

    public List<TimeInterval> getApplicationBatchIntervalValues() {
        return applicationBatchIntervalValues;
    }


    @Value("${application.batch.intervals}")
    public void setApplicationBatchIntervals(String[] applicationBatchIntervals) {

        this.applicationBatchIntervals = applicationBatchIntervals;
        this.applicationBatchIntervalValues = Arrays.stream(applicationBatchIntervals).map(a -> TimeInterval.valueOfLabel(a)).collect(Collectors.toList());
    }
}
