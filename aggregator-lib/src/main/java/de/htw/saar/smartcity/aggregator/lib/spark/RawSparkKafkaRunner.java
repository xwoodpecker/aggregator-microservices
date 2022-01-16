package de.htw.saar.smartcity.aggregator.lib.spark;

import com.google.common.collect.Lists;
import de.htw.saar.smartcity.aggregator.lib.handler.RawMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.SensorMeasurement;
import de.htw.saar.smartcity.aggregator.lib.properties.SparkBasicApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.utils.Utils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class RawSparkKafkaRunner extends SparkKafkaRunner{

    private final SparkBasicApplicationProperties applicationProperties;
    private final RawMeasurementHandler rawMeasurementHandler;

    public RawSparkKafkaRunner(SparkBasicApplicationProperties applicationProperties, JavaStreamingContext jsc, RawMeasurementHandler rawMeasurementHandler) {

        super(applicationProperties, jsc);
        this.applicationProperties = applicationProperties;
        this.rawMeasurementHandler = rawMeasurementHandler;
    }

    @Override
    protected void consume(JavaInputDStream<ConsumerRecord<String, String>> stream) {

        JavaDStream<SensorMeasurement> measurementJavaInputDStream = stream
                .map(x -> new SensorMeasurement(x.key(), x.value()));

        measurementJavaInputDStream
                .foreachRDD(rdd ->
                        process(
                                rdd.collect()
                        )
                );
    }


    private void process(List<SensorMeasurement> measurements) {

        if(measurements.size() == 0)
            return;

        measurements = Lists.reverse(measurements).stream()
                .filter(Utils.distinctByKeys(SensorMeasurement::getSensorName))
                .collect(Collectors.toList());

        for(SensorMeasurement sensorMeasurement : measurements) {

            rawMeasurementHandler.handleMessage(sensorMeasurement);
        }
    }


    @Override
    protected Pattern getSubscribePattern() {

        return Pattern.compile(applicationProperties.getKafkaTopicPattern());
    }


}
