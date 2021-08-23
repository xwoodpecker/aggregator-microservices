package de.htw.saar.smartcity.sensor.app.properties;

import de.htw.saar.smartcity.aggregator.lib.properties.AbstractBrokerApplicationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The type Application properties.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class SensorApplicationProperties extends AbstractBrokerApplicationProperties {

    private String temperatureSensorName;
    private String humiditySensorName;
    private String airqualitySensorName;
    private String waterSensorName;

    private Integer temperatureAndHumiditySensorPin;
    private Integer airqualitySensorPin;
    private Integer waterSensorPin;

    private Integer interval;

    /**
     * Sets temperature sensor name.
     *
     * @param temperatureSensorName the temperature sensor name
     */
    @Value("${TEMPERATURE_SENSOR_NAME:#{null}}")
    public void setTemperatureSensorName(String temperatureSensorName) {
        this.temperatureSensorName = temperatureSensorName;
    }

    /**
     * Sets humidity sensor name.
     *
     * @param humiditySensorName the humidity sensor name
     */
    @Value("${HUMIDITY_SENSOR_NAME:#{null}}")
    public void setHumiditySensorName(String humiditySensorName) {
        this.humiditySensorName = humiditySensorName;
    }

    /**
     * Sets airquality sensor name.
     *
     * @param airqualitySensorName the airquality sensor name
     */
    @Value("${AIRQUALITY_SENSOR_NAME:#{null}}")
    public void setAirqualitySensorName(String airqualitySensorName) {
        this.airqualitySensorName = airqualitySensorName;
    }

    /**
     * Sets water sensor name.
     *
     * @param waterSensorName the water sensor name
     */
    @Value("${WATER_SENSOR_NAME:#{null}}")
    public void setWaterSensorName(String waterSensorName) {
        this.waterSensorName = waterSensorName;
    }

    /**
     * Sets temperature and humidity sensor pin.
     *
     * @param temperatureAndHumiditySensorPin the temperature and humidity sensor pin
     */
    @Value("#{new Integer('${TEMPERATURE_AND_HUMIDITY_SENSOR_PIN}')}")
    public void setTemperatureAndHumiditySensorPin(Integer temperatureAndHumiditySensorPin) {
        this.temperatureAndHumiditySensorPin = temperatureAndHumiditySensorPin;
    }

    /**
     * Sets airquality sensor pin.
     *
     * @param airqualitySensorPin the airquality sensor pin
     */
    @Value("#{new Integer('${AIRQUALITY_SENSOR_PIN}')}")
    public void setAirqualitySensorPin(Integer airqualitySensorPin) {
        this.airqualitySensorPin = airqualitySensorPin;
    }

    /**
     * Sets water sensor pin.
     *
     * @param waterSensorPin the water sensor pin
     */
    @Value("#{new Integer('${WATER_SENSOR_PIN}')}")
    public void setWaterSensorPin(Integer waterSensorPin) {
        this.waterSensorPin = waterSensorPin;
    }

    /**
     * Sets interval. (in seconds)
     *
     * @param interval the interval (in seconds)
     */
    @Value("#{new Integer('${INTERVAL}')}")
    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    /**
     * Gets temperature sensor name.
     *
     * @return the temperature sensor name
     */
    public String getTemperatureSensorName() {
        return temperatureSensorName;
    }

    /**
     * Gets humidity sensor name.
     *
     * @return the humidity sensor name
     */
    public String getHumiditySensorName() {
        return humiditySensorName;
    }

    /**
     * Gets airquality sensor name.
     *
     * @return the airquality sensor name
     */
    public String getAirqualitySensorName() {
        return airqualitySensorName;
    }

    /**
     * Gets water sensor name.
     *
     * @return the water sensor name
     */
    public String getWaterSensorName() {
        return waterSensorName;
    }

    /**
     * Gets interval. (in seconds)
     *
     * @return the interval
     */
    public Integer getInterval() {
        return interval;
    }

    /**
     * Gets temperature and humidity sensor pin.
     *
     * @return the temperature and humidity sensor pin
     */
    public Integer getTemperatureAndHumiditySensorPin() {
        return temperatureAndHumiditySensorPin;
    }

    /**
     * Gets airquality sensor pin.
     *
     * @return the airquality sensor pin
     */
    public Integer getAirqualitySensorPin() {
        return airqualitySensorPin;
    }

    /**
     * Gets water sensor pin.
     *
     * @return the water sensor pin
     */
    public Integer getWaterSensorPin() {
        return waterSensorPin;
    }
}
