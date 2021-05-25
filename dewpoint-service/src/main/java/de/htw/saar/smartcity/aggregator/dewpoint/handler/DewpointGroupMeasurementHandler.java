package de.htw.saar.smartcity.aggregator.dewpoint.handler;

import de.htw.saar.smartcity.aggregator.dewpoint.properties.DewpointApplicationProperties;
import de.htw.saar.smartcity.aggregator.dewpoint.storage.DewpointStorageWrapper;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.MixedGroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
@Component
public class DewpointGroupMeasurementHandler extends MixedGroupMeasurementHandler {

    private Long temperatureSensorTypeId;
    private Long humiditySensorTypeId;

    private final SensorService sensorService;

    public DewpointGroupMeasurementHandler(DewpointStorageWrapper storageWrapper,
                                           DewpointApplicationProperties dewpointApplicationProperties,
                                           GroupMemberService groupMemberService,
                                           SensorTypeService sensorTypeService,
                                           SensorService sensorService) {
        super(storageWrapper, groupMemberService);
        this.sensorService = sensorService;

        temperatureSensorTypeId = sensorTypeService
                .findSensorTypeByName(dewpointApplicationProperties.getTemperatureSensorTypeName()).getId();

        humiditySensorTypeId = sensorTypeService
                .findSensorTypeByName(dewpointApplicationProperties.getHumiditySensorTypeName()).getId();
    }




    //todo: refactor
    @Override
    protected void addCombinators() {

        Function<Map<Long, Measurement<Double>>, Double> dewpointFunction = (map) -> {

            Map<Sensor, Measurement<Double>> newMap = map.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> sensorService.findSensorById(e.getKey()).get(),
                            e -> e.getValue()));

            Sensor temperatureSensor = newMap.keySet().stream().filter(s -> s.getSensorType().getId() == temperatureSensorTypeId).findFirst().get();
            Sensor humiditySensor = newMap.keySet().stream().filter(s -> s.getSensorType().getId() == humiditySensorTypeId).findFirst().get();

            Double temperatureValue = newMap.get(temperatureSensor).getValue();
            Double humidityValue = newMap.get(humiditySensor).getValue();
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
} **/
