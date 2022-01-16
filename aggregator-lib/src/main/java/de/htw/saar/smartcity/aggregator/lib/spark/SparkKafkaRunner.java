package de.htw.saar.smartcity.aggregator.lib.spark;

import de.htw.saar.smartcity.aggregator.lib.properties.KafkaApplicationProperties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class SparkKafkaRunner {

    protected final KafkaApplicationProperties applicationProperties;

    protected final JavaStreamingContext jsc;

    private boolean initialized = true;

    public SparkKafkaRunner(KafkaApplicationProperties applicationProperties,
                            JavaStreamingContext jsc) {

        this.applicationProperties = applicationProperties;
        this.jsc = jsc;
    }


    @EventListener({ContextRefreshedEvent.class})
    void contextRefreshedEvent(ContextRefreshedEvent e) throws InterruptedException {

        if (initialized) {
            sparkKafka();
            initialized = false;
        }
    }

    public void sparkKafka() throws InterruptedException {

        //https://spark.apache.org/docs/3.2.0/streaming-kafka-0-10-integration.html#content
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put("bootstrap.servers", applicationProperties.getKafkaHost());
        kafkaParams.put("key.deserializer", StringDeserializer.class);
        kafkaParams.put("value.deserializer", StringDeserializer.class);
        //use_a_separate_group_id_for_each_stream
        kafkaParams.put("group.id", getKafkaGroupId());
        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", true); // apparently true is the way to go
        kafkaParams.put("metadata.max.age.ms", 1000);
        kafkaParams.put("request.timeout.ms", 60_000);

        JavaInputDStream<ConsumerRecord<String, String>> stream =
                KafkaUtils.createDirectStream(
                        jsc,
                        LocationStrategies.PreferConsistent(),
                        ConsumerStrategies.SubscribePattern(getSubscribePattern(), kafkaParams)
                );

        consume(stream);

        jsc.start();   // Start the computation
        jsc.awaitTermination();
    }

    protected abstract void consume(JavaInputDStream<ConsumerRecord<String, String>> stream);

    protected abstract Pattern getSubscribePattern();

    protected abstract String getKafkaGroupId();

}
