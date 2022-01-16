package de.htw.saar.smartcity.aggregator.lib.properties;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public abstract class SparkGroupApplicationProperties extends BasicApplicationProperties implements KafkaApplicationProperties, GroupApplicationProperties {

    @Value("${application.name}")
    private String appName;

    @Value("${spark.master.uri}")
    private String masterUri;

    @Value("${kafka.uri}")
    private String kafkaUri;

    @Value("${application.batch.interval:#{30L}}")
    private Long applicationBatchInterval;


    /**
     * The group type name.
     */
    protected String groupTypeName;

    /**
     * Sets group type name.
     *
     * @param groupTypeName the group type name
     */
    public abstract void setGroupTypeName(String groupTypeName);

    /**
     * Gets group type name.
     *
     * @return the group type name
     */
    public String getGroupTypeName() {
        return groupTypeName;
    }

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
        return new JavaStreamingContext(sparkConf(), Durations.seconds(applicationBatchInterval));

    }

    public String getAppName() {
        return appName;
    }

    public String getMasterUri() {
        return masterUri;
    }

    public String getKafkaHost() {
        return kafkaUri;
    }

    public Long getApplicationBatchInterval() {
        return applicationBatchInterval;
    }
}
