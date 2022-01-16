package de.htw.saar.smartcity.aggregator.lib.spark;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.AggregationMeasurement;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.properties.SparkGroupApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class GroupSparkKafkaRunner extends SparkKafkaRunner {

    private final SparkGroupApplicationProperties applicationProperties;
    private final GroupMeasurementHandler groupMeasurementHandler;

    public GroupSparkKafkaRunner(SparkGroupApplicationProperties applicationProperties, JavaStreamingContext jsc, GroupMeasurementHandler groupMeasurementHandler) {

        super(applicationProperties, jsc);
        this.groupMeasurementHandler = groupMeasurementHandler;
        this.applicationProperties = applicationProperties;
    }

    @Override
    protected void consume(JavaInputDStream<ConsumerRecord<String, String>> stream) {

        JavaDStream<AggregationMeasurement> measurementJavaInputDStream = stream
                .map(x -> {
                    String[] parts = x.key().split("\\.");
                    Measurement measurement = null;
                    Long producerId = null;
                    Long groupId = null;
                    if (parts != null & parts.length == 3) {

                        try (InputStream is = URI.create(x.value()).toURL().openConnection().getInputStream()) {
                            ObjectMapper objectMapper = new ObjectMapper();
                            measurement = objectMapper.readValue(is.readAllBytes(), Measurement.class);
                        } catch ( IOException ex ) { }

                        try {
                            groupId = Long.valueOf(parts[1]);
                            producerId = Long.valueOf(parts[2]);

                        } catch (NumberFormatException e) { }
                    }
                    AggregationMeasurement aggregationMeasurement = new AggregationMeasurement(groupId, producerId, measurement);
                    return aggregationMeasurement;
                });

        measurementJavaInputDStream
                .foreachRDD(rdd ->
                        process(
                                rdd.collect()
                        )
                );
    }

    private void process(List<AggregationMeasurement> aggregationMeasurements) {

        if(aggregationMeasurements.size() == 0)
            return;

        aggregationMeasurements = Lists.reverse(aggregationMeasurements).stream()
                .filter(Utils.distinctByKeys(AggregationMeasurement::getGroupId, AggregationMeasurement::getProducerId))
                .collect(Collectors.toList());

        for(AggregationMeasurement aggregationMeasurement : aggregationMeasurements) {

            if(aggregationMeasurement.getMeasurement() != null)
                groupMeasurementHandler.handleMeasurement(aggregationMeasurement.getGroupId(), aggregationMeasurement.getProducerId(), aggregationMeasurement.getMeasurement());
        }
    }

    @Override
    protected Pattern getSubscribePattern() {

        return Pattern.compile(applicationProperties.getGroupTypeName() + ".*");
    }
}
