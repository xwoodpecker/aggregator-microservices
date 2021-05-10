package de.htw.saar.smartcity.aggregator.dewpoint.handler;

import de.htw.saar.smartcity.aggregator.dewpoint.properties.DewpointApplicationProperties;
import de.htw.saar.smartcity.aggregator.dewpoint.storage.DewpointStorageWrapper;
import de.htw.saar.smartcity.aggregator.humidity.factory.HumidityMeasurementFactory;
import de.htw.saar.smartcity.aggregator.lib.entity.SensorType;
import de.htw.saar.smartcity.aggregator.lib.handler.MixedGroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.repository.SensorTypeRepository;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.MixedGroupCombinator;
import de.htw.saar.smartcity.aggregator.temperature.factory.TemperatureMeasurementFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;

@Component
public class DewpointGroupMeasurementHandler extends MixedGroupMeasurementHandler {


    private final DewpointApplicationProperties dewpointApplicationProperties;

    private final Long temperatureSensorTypeId;
    private final Long humiditySensorTypeId;
    private final Function<Map<SensorType, Measurement<Double>>, Double> dewpointFunction;
    private final String dewpointFunctionName;

    public DewpointGroupMeasurementHandler(DewpointStorageWrapper storageWrapper,
                                           DewpointApplicationProperties dewpointApplicationProperties, SensorTypeRepository sensorTypeService) {
        super(storageWrapper);
        this.dewpointApplicationProperties = dewpointApplicationProperties;


        temperatureSensorTypeId = sensorTypeService
                .findSensorTypeByName(dewpointApplicationProperties.getTemperatureSensorTypeName()).getId();

        humiditySensorTypeId = sensorTypeService
                .findSensorTypeByName(dewpointApplicationProperties.getHumiditySensorTypeName()).getId();

        dewpointFunction = (map) -> {
            Double temperatureValue = map.get(temperatureSensorTypeId).getValue();
            Double humidityValue = map.get(humiditySensorTypeId).getValue();
            Double b = 243.12;
            Double a = 17.62;
            Double alpha = Math.log(humidityValue/100) + a * temperatureValue / (b + temperatureValue);
            Double dewpointValue = b * alpha / (a - alpha);
            return dewpointValue;
        };

        dewpointFunctionName = "dewpoint";
    }


    @Override
    protected void addMeasurementFactories() {

        sensorTypeIdMeasurementFactoryMap.put(temperatureSensorTypeId, new TemperatureMeasurementFactory());
        sensorTypeIdMeasurementFactoryMap.put(humiditySensorTypeId, new HumidityMeasurementFactory());
    }


    @Override
    protected void addCombinators() {
        MixedGroupCombinator mixedGroupCombinator = new MixedGroupCombinator();
        mixedGroupCombinator.setFunction(dewpointFunction);
        mixedGroupCombinator.setName(dewpointFunctionName);
        mixedGroupCombinators.add(mixedGroupCombinator);
    }
}
