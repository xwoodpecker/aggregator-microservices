package de.htw.saar.smartcity.aggregator.dewpoint.handler;

import de.htw.saar.smartcity.aggregator.dewpoint.properties.DewpointApplicationProperties;
import de.htw.saar.smartcity.aggregator.dewpoint.storage.DewpointStorageWrapper;
import de.htw.saar.smartcity.aggregator.humidity.factory.HumidityMeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.handler.MixedGroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.MixedGroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.service.SensorTypeService;
import de.htw.saar.smartcity.aggregator.temperature.factory.TemperatureMeasurementFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class DewpointGroupMeasurementHandler extends MixedGroupMeasurementHandler {


    private final DewpointApplicationProperties dewpointApplicationProperties;
    private final SensorTypeService sensorTypeService;

    private Long temperatureSensorTypeId;
    private Long humiditySensorTypeId;

    public DewpointGroupMeasurementHandler(DewpointStorageWrapper storageWrapper,
                                           DewpointApplicationProperties dewpointApplicationProperties, SensorTypeService sensorTypeService) {
        super(storageWrapper);
        this.dewpointApplicationProperties = dewpointApplicationProperties;
        this.sensorTypeService = sensorTypeService;

        temperatureSensorTypeId = sensorTypeService
                .findSensorTypeByName(dewpointApplicationProperties.getTemperatureSensorTypeName()).getId();

        humiditySensorTypeId = sensorTypeService
                .findSensorTypeByName(dewpointApplicationProperties.getHumiditySensorTypeName()).getId();
    }


    @Override
    protected void addMeasurementFactories() {

        sensorTypeIdMeasurementFactoryMap.put(temperatureSensorTypeId, new TemperatureMeasurementFactory());
        sensorTypeIdMeasurementFactoryMap.put(humiditySensorTypeId, new HumidityMeasurementFactory());
    }


    @Override
    protected void addCombinators() {

        Function<Map<Long, Measurement<Double>>, Double> dewpointFunction = (map) -> {
            Double temperatureValue = map.get(temperatureSensorTypeId).getValue();
            Double humidityValue = map.get(humiditySensorTypeId).getValue();
            Double b = 243.12;
            Double a = 17.62;
            Double alpha = Math.log(humidityValue/100) + a * temperatureValue / (b + temperatureValue);
            Double dewpointValue = b * alpha / (a - alpha);
            return Math.round(dewpointValue * 100) / 100.0;
        };

        String dewpointFunctionName = "dewpoint";
        MixedGroupCombinator mixedGroupCombinator = new MixedGroupCombinator();
        mixedGroupCombinator.setFunction(dewpointFunction);
        mixedGroupCombinator.setName(dewpointFunctionName);
        mixedGroupCombinators.add(mixedGroupCombinator);
    }
}
