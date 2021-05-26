package de.htw.saar.smartcity.aggregator.dewpoint.handler;

import de.htw.saar.smartcity.aggregator.dewpoint.properties.DewpointApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.handler.MixedGroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.MixedGroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.service.*;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class DewpointGroupMeasurementHandler extends MixedGroupMeasurementHandler {

    private final SensorService sensorService;
    private final DewpointApplicationProperties applicationProperties;

    public DewpointGroupMeasurementHandler(StorageWrapper storageWrapper,
                                           ProducerService producerService,
                                           GroupService groupService,
                                           CombinatorService combinatorService,
                                           Publisher publisher,
                                           SensorService sensorService, DewpointApplicationProperties applicationProperties) {

        super(storageWrapper, producerService, groupService, combinatorService, publisher);
        this.sensorService = sensorService;
        this.applicationProperties = applicationProperties;

    }

    //todo: refactor
    @Override
    protected void addCombinators() {

        Function<Map<Long, Measurement<Double>>, Double> dewpointFunction = (map) -> {

            Map<Sensor, Measurement<Double>> newMap = map.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> sensorService.findSensorById(e.getKey()).get(),
                            e -> e.getValue()));

            Sensor temperatureSensor = newMap.keySet()
                    .stream()
                    .filter(s -> s.getDataType().getName().equals(applicationProperties.getTemperatureSensorTypeName()))
                    .findFirst()
                    .get();
            Sensor humiditySensor = newMap.keySet()
                    .stream()
                    .filter(s -> s.getDataType().getName().equals(applicationProperties.getHumiditySensorTypeName()))
                    .findFirst()
                    .get();

            Double temperatureValue = newMap.get(temperatureSensor).getValue();
            Double humidityValue = newMap.get(humiditySensor).getValue();
            Double b = 243.12;
            Double a = 17.62;
            Double alpha = Math.log(humidityValue/100) + a * temperatureValue / (b + temperatureValue);
            Double dewpointValue = b * alpha / (a - alpha);
            return Math.round(dewpointValue * 100) / 100.0;
        };

        String dewpointFunctionName = "dewpoint-combinator";
        MixedGroupCombinator mixedGroupCombinator = new MixedGroupCombinator();
        mixedGroupCombinator.setFunction(dewpointFunction);
        mixedGroupCombinator.setName(dewpointFunctionName);
        mixedGroupCombinators.add(mixedGroupCombinator);
    }
}
