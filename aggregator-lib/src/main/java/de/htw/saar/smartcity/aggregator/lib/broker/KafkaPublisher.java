package de.htw.saar.smartcity.aggregator.lib.broker;

import de.htw.saar.smartcity.aggregator.lib.properties.KafkaApplicationProperties;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * The type Kafka publisher.
 */
public abstract class KafkaPublisher implements Publisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaPublisher.class);

    private final KafkaProducer<String, String> kafkaProducer;
    private final Properties kafkaProducerProperties;

    /**
     * application settings for the kafka server
     */
    private final KafkaApplicationProperties applicationProperties;

    /**
     * Instantiates a new Kafka publisher.
     *
     * @param applicationProperties the application properties
     */
    protected KafkaPublisher(KafkaApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;

        this.kafkaProducerProperties = new Properties();
        this.kafkaProducerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, applicationProperties.getKafkaHost());
        this.kafkaProducer = new KafkaProducer<>(kafkaProducerProperties);
    }

    public void publish(String topic, String message) {
        ProducerRecord producerRecord = new ProducerRecord<>(topic, topic, message);

        Callback callback = (RecordMetadata metadata, Exception e) -> {
            if (e == null) {
                log.info("Message <{}> sent to Kafka topic <{}>", message, topic);
            } else {
                log.error("An error occurred while sending message to Kafka.");
            }
        };

        kafkaProducer.send(producerRecord, callback);
    }
}
