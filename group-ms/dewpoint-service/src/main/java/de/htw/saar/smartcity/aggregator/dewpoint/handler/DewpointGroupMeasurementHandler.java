package de.htw.saar.smartcity.aggregator.dewpoint.handler;

import de.htw.saar.smartcity.aggregator.dewpoint.properties.DewpointApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.CombinatorUnaryModel;
import de.htw.saar.smartcity.aggregator.lib.model.CombinatorUnaryOperator;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.service.*;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;


@Component
public class DewpointGroupMeasurementHandler extends GroupMeasurementHandler {

    private final DewpointApplicationProperties applicationProperties;

    private final static Double A = 17.62;
    private final static Double B = 243.12;

    protected DewpointGroupMeasurementHandler(StorageWrapper storageWrapper,
                                              Publisher publisher,
                                              ProducerService producerService,
                                              GroupService groupService,
                                              CombinatorService combinatorService,
                                              DewpointApplicationProperties applicationProperties) {

        super(storageWrapper, publisher, producerService, groupService, combinatorService);
        this.applicationProperties = applicationProperties;
    }


    @Override
    protected void addCombinators() {

        CombinatorUnaryOperator<Double> dewpointFunction = (gms) -> {

            Map<Producer, Measurement<Double>> newMap = gms.getProducerIdMeasurementMap().entrySet()
                    .stream()
                    .collect(Collectors.toMap(e -> producerService.findProducerById(e.getKey()).get(),
                            e -> e.getValue()));

            Producer temperatureProducer = newMap.keySet()
                    .stream()
                    .filter(p -> p.getDataType().getName().equals(applicationProperties.getTemperatureDataTypeName()))
                    .findFirst()
                    .orElseThrow(() -> new MeasurementException("No temperature present"));
            Producer humidityProducer = newMap.keySet()
                    .stream()
                    .filter(p -> p.getDataType().getName().equals(applicationProperties.getHumidityDataTypeName()))
                    .findFirst()
                    .orElseThrow(() -> new MeasurementException("No humidity present"));

            Double temperatureValue = newMap.get(temperatureProducer).getValue();
            Double humidityValue = newMap.get(humidityProducer).getValue();
            Double alpha = Math.log(humidityValue / 100) + A * temperatureValue / (B + temperatureValue);
            Double dewpointValue = B * alpha / (A - alpha);
            return Math.round(dewpointValue * 100) / 100.0;
        };

        String dewpointFunctionName = "dewpoint-combinator";
        CombinatorUnaryModel<Double> combinatorModel =
                new CombinatorUnaryModel<>(dewpointFunctionName, dewpointFunction);
        combinatorModels.add(combinatorModel);
    }
}
