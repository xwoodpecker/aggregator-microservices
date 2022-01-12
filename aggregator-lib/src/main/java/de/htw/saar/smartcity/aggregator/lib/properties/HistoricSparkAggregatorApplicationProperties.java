package de.htw.saar.smartcity.aggregator.lib.properties;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Historic aggregator application properties.
 */
@Configuration
@PropertySource("classpath:application.properties")
public abstract class HistoricSparkAggregatorApplicationProperties extends  HistoricAggregatorApplicationProperties implements MinioApplicationProperties {

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
}
