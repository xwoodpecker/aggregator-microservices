package de.htw.saar.smartcity.aggregator.dewpoint.handler;

import de.htw.saar.smartcity.aggregator.dewpoint.properties.DewpointApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.model.GroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.service.*;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
public class DewpointGroupMeasurementHandler extends GroupMeasurementHandler {

    private final DewpointApplicationProperties applicationProperties;

    public DewpointGroupMeasurementHandler(StorageWrapper storageWrapper,
                                           ProducerService producerService,
                                           GroupService groupService,
                                           CombinatorService combinatorService,
                                           Publisher publisher,
                                           DewpointApplicationProperties applicationProperties) {

        super(storageWrapper, producerService, groupService, combinatorService, publisher);
        this.applicationProperties = applicationProperties;

    }

    //todo: refactor
    @Override
    protected void addCombinators() {

        Function<Map<Long, Measurement<Double>>, Double> dewpointFunction = (map) -> {

            Map<Producer, Measurement<Double>> newMap = map.entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> producerService.findProducerById(e.getKey()).get(),
                            e -> e.getValue()));

            Producer temperatureProducer = newMap.keySet()
                    .stream()
                    .filter(p -> p.getDataType().getName().equals(applicationProperties.getTemperatureDataTypeName()))
                    .findFirst()
                    .get();
            Producer humidityProducer = newMap.keySet()
                    .stream()
                    .filter(p -> p.getDataType().getName().equals(applicationProperties.getHumidityDataTypeName()))
                    .findFirst()
                    .get();

            Double temperatureValue = newMap.get(temperatureProducer).getValue();
            Double humidityValue = newMap.get(humidityProducer).getValue();
            Double b = 243.12;
            Double a = 17.62;
            Double alpha = Math.log(humidityValue/100) + a * temperatureValue / (b + temperatureValue);
            Double dewpointValue = b * alpha / (a - alpha);
            return Math.round(dewpointValue * 100) / 100.0;
        };

        String dewpointFunctionName = "dewpoint-combinator";
        GroupCombinator<Double> groupCombinator =
                new GroupCombinator<>(dewpointFunctionName, dewpointFunction);
        groupCombinators.add(groupCombinator);
    }
}
