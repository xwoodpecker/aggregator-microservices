package de.htw.saar.smartcity.aggregator.heatflux.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class HeatfluxApplicationProperties extends GroupMicroserviceApplicationProperties {

    @Override
    @Value("${HEATFLUX_MICROSERVICE_QUEUE}")
    public void setMicroserviceTopicName(String microserviceTopicName) {
        this.microserviceTopicName = microserviceTopicName;
    }

    @Override
    @Value("${HEATFLUX_MICROSERVICE_GROUP_TYPE_NAME}")
    public void setMicroserviceGroupTypeName(String microserviceGroupTypeName) {
        this.microserviceGroupTypeName = microserviceGroupTypeName;
    }

    @Override
    @Value("${HEATFLUX_MICROSERVICE_DATA_TYPE_NAME}")
    public void setApplicationDataType(String applicationDataType) {
        this.applicationDataType = applicationDataType;
    }

    @Override
    @Value("${HEATFLUX_MICROSERVICE_BUCKET}")
    public void setMinioBucket(String minioBucketName) {
        this.minioBucketName = minioBucketName;
    }

    private String temperatureDataTypeName;

    private String dewpointDataTypeName;

    private String tagNameInsideTemperature;

    private String tagNameOutsideTemperature;

    private String shutterDataTypeName;

    private String formulaItemNameUValue;

    private List<String> formulaItemNameUValueDefaults;

    private String formulaItemNameUValueDefault;

    @Value("${TEMPERATURE_SENSOR_TYPE}")
    public void setTemperatureDataTypeName(String temperatureDataTypeName) {
        this.temperatureDataTypeName = temperatureDataTypeName;
    }
    @Value("${DEWPOINT_DATA_TYPE}")
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

    @Value("${FORMULA_ITEM_NAME_U_VALUE}")
    public void setFormulaItemNameUValue(String formulaItemNameUValue) {
        this.formulaItemNameUValue = formulaItemNameUValue;
    }

    @Value("#{'${FORMULA_ITEM_NAME_U_VALUE_DEFAULTS}'.split(',')}")
    public void setFormulaItemNameUValueDefaults(List<String> formulaItemNameUValueDefaults) {
        this.formulaItemNameUValueDefaults = formulaItemNameUValueDefaults;
    }

    @Value("${FORMULA_ITEM_NAME_U_VALUE_DEFAULT}")
    public void setFormulaItemNameUValueDefault(String formulaItemNameUValueDefault) {
        this.formulaItemNameUValueDefault = formulaItemNameUValueDefault;
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

    public String getFormulaItemNameUValue() {
        return formulaItemNameUValue;
    }

    public List<String> getFormulaItemNameUValueDefaults() {
        return formulaItemNameUValueDefaults;
    }

    public String getFormulaItemNameUValueDefault() {
        return formulaItemNameUValueDefault;
    }
}
