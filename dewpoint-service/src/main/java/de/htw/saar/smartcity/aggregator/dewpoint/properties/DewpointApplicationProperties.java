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
    @Value("${DEWPOINT_MICROSERVICE_URL}")
    public void setMicroserviceURL(String microserviceURL) {
        this.microserviceURL = microserviceURL;
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

    private String temperatureSensorTypeName;

    private String humiditySensorTypeName;

    @Value("${TEMPERATURE_SENSOR_TYPE_NAME}")
    public void setTemperatureSensorTypeName(String temperatureSensorTypeName) {
        this.temperatureSensorTypeName = temperatureSensorTypeName;
    }

    @Value("${HUMIDITY_SENSOR_TYPE_NAME}")
    public void setHumiditySensorTypeName(String humiditySensorTypeName) {
        this.humiditySensorTypeName = humiditySensorTypeName;
    }

    public String getHumiditySensorTypeName() {
        return humiditySensorTypeName;
    }

    public String getTemperatureSensorTypeName() {
        return temperatureSensorTypeName;
    }

}
