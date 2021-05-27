package de.htw.saar.smartcity.aggregator.heatflux.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeatfluxApplicationProperties extends GroupMicroserviceApplicationProperties {

    @Override
    @Value("${HEATFLUX_MICROSERVICE_QUEUE}")
    public void setMicroserviceQueue(String microserviceQueue) {
        this.microserviceQueue = microserviceQueue;
    }

    @Override
    @Value("${HEATFLUX_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceGroupTypeName(String microserviceGroupTypeName) {
        this.microserviceGroupTypeName = microserviceGroupTypeName;
    }

    @Override
    @Value("${HEATFLUX_MICROSERVICE_DATA_TYPE_NAME}")
    public void setMicroserviceDataTypeName(String microserviceDataTypeName) {
        this.microserviceDataTypeName = microserviceDataTypeName;
    }

    @Override
    @Value("${HEATFLUX_MICROSERVICE_BUCKET}")
    public void setMicroserviceBucket(String microserviceBucket) {
        this.microserviceBucket = microserviceBucket;
    }

    private String temperatureDataTypeName;

    private String dewpointDataTypeName;

    private String tagNameInsideTemperature;

    private String tagNameOutsideTemperature;

    private String shutterDataTypeName;

    @Value("${TEMPERATURE_DATA_TYPE_NAME}")
    public void setTemperatureDataTypeName(String temperatureDataTypeName) {
        this.temperatureDataTypeName = temperatureDataTypeName;
    }
    @Value("${DEWPOINT_DATA_TYPE_NAME}")
    public void setDewpointDataTypeName(String dewpointDataTypeName) {
        this.dewpointDataTypeName = dewpointDataTypeName;
    }

    @Value("${TAG_NAME_INSIDE_TEMPERATURE}")
    public void setTagNameInsideTemperature(String tagNameInsideTemperature) {
        this.tagNameInsideTemperature = tagNameInsideTemperature;
    }

    @Value("${TAG_NAME_OUTSIDE_TEMPERATURE}")
    public void setTagNameOutsideTemperature(String tagNameOutsideTemperature) {
        this.tagNameOutsideTemperature = tagNameOutsideTemperature;
    }

    @Value("${HEATFLUX_MICROSERVICE_SHUTTER_DATA_TYPE_NAME}")
    public void setShutterDataTypeName(String shutterDataTypeName) {
        this.shutterDataTypeName = shutterDataTypeName;
    }

    public String getTemperatureDataTypeName() {
        return temperatureDataTypeName;
    }

    public String getDewpointDataTypeName() {
        return dewpointDataTypeName;
    }

    public String getTagNameInsideTemperature() {
        return tagNameInsideTemperature;
    }

    public String getTagNameOutsideTemperature() {
        return tagNameOutsideTemperature;
    }

    public String getShutterDataTypeName() {
        return shutterDataTypeName;
    }
}