package de.htw.saar.smartcity.aggregator.dewpoint.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.MixedGroupMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DewpointApplicationProperties extends MixedGroupMicroserviceApplicationProperties {

    @Override
    @Value("${DEWPOINT_MICROSERVICE_NAME}")
    public void setMicroserviceName(String microserviceName) {
        this.microserviceName = microserviceName;
    }

    @Override
    @Value("${DEWPOINT_MICROSERVICE_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }

    @Override
    @Value("${DEWPOINT_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceGroupTypeName(String microserviceGroupTypeName) {
        this.microserviceGroupTypeName = microserviceGroupTypeName;
    }

    @Override
    @Value("${DEWPOINT_MICROSERVICE_QUEUE}")
    public void setMicroserviceQueue(String microserviceQueue) {
        this.microserviceQueue = microserviceQueue;
    }

    private String temperatureSensorTypeName;

    private String humiditySensorTypeName;

    private String dewPointDataType;

    @Value("${TEMPERATURE_SENSOR_TYPE_NAME}")
    public void setTemperatureSensorTypeName(String temperatureSensorTypeName) {
        this.temperatureSensorTypeName = temperatureSensorTypeName;
    }

    @Value("${HUMIDITY_SENSOR_TYPE_NAME}")
    public void setHumiditySensorTypeName(String humiditySensorTypeName) {
        this.humiditySensorTypeName = humiditySensorTypeName;
    }

    @Value("${DEWPOINT_DATA_TYPE}")
    public void setDewPointDataType(String dewPointDataType) {
        this.dewPointDataType = dewPointDataType;
    }

    public String getHumiditySensorTypeName() {
        return humiditySensorTypeName;
    }

    public String getTemperatureSensorTypeName() {
        return temperatureSensorTypeName;
    }

    public String getDewPointDataType() {
        return dewPointDataType;
    }
}
