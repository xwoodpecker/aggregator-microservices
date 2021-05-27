package de.htw.saar.smartcity.aggregator.heatflux.handler;

import de.htw.saar.smartcity.aggregator.heatflux.properties.HeatfluxApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.GroupCombinator;
import de.htw.saar.smartcity.aggregator.lib.model.Measurement;
import de.htw.saar.smartcity.aggregator.lib.service.CombinatorService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupService;
import de.htw.saar.smartcity.aggregator.lib.service.ProducerService;
import de.htw.saar.smartcity.aggregator.lib.storage.StorageWrapper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class HeatfluxGroupMeasurementHandler extends GroupMeasurementHandler {

    private final HeatfluxApplicationProperties applicationProperties;

    public HeatfluxGroupMeasurementHandler(StorageWrapper storageWrapper,
                                           ProducerService producerService,
                                           GroupService groupService,
                                           CombinatorService combinatorService,
                                           Publisher publisher,
                                           HeatfluxApplicationProperties applicationProperties) {

        super(storageWrapper, producerService, groupService, combinatorService, publisher);
        this.applicationProperties = applicationProperties;
    }

    @Override
    protected void addCombinators() {

        Function<Map<Long, Measurement<Double>>, Map<Producer, Measurement<Double>>> idToProducerMapper = map ->
            map.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> producerService.findProducerById(e.getKey()).get(),
                        e -> e.getValue()));

        Function<Map<Producer, Measurement<Double>>, Optional<Producer>> dewPointProducerGetter = map -> map.keySet()
                .stream()
                .filter(p -> p.getDataType().getName().equals(applicationProperties.getDewpointDataTypeName()))
                .findFirst();

        Function<Map<Producer, Measurement<Double>>, Optional<Producer>> insideTemperatureProducerGetter  = map -> map.keySet()
                .stream()
                .filter(p -> p.getDataType().getName().equals(applicationProperties.getTemperatureDataTypeName()) &&
                        p.getTags().stream().anyMatch(t -> t.getName().equals(applicationProperties.getTagNameInsideTemperature())))
                .findFirst();

        Function<Map<Producer, Measurement<Double>>, Optional<Producer>> outsideTemperatureProducerGetter  = map -> map.keySet()
                .stream()
                .filter(p -> p.getDataType().getName().equals(applicationProperties.getTemperatureDataTypeName()) &&
                        p.getTags().stream().anyMatch(t -> t.getName().equals(applicationProperties.getTagNameOutsideTemperature())))
                .findFirst();


        Function<Map<Long, Measurement<Double>>, Double> heatFluxFunction = (map) -> {

            Map<Producer, Measurement<Double>> producerMap = idToProducerMapper.apply(map);

            Optional<Producer> dewPointProducer = dewPointProducerGetter.apply(producerMap);

            Optional<Producer> outsideTemperatureProducer = outsideTemperatureProducerGetter.apply(producerMap);

            if(dewPointProducer.isPresent() && outsideTemperatureProducer.isPresent()) {

                Double dewPointValue = producerMap.get(dewPointProducer.get()).getValue();
                Double outerTemperatureValue = producerMap.get(outsideTemperatureProducer.get()).getValue();
                /**
                 * 5.0	Single-glazing
                 * 3.0	Double-glazing
                 * 2.2	Triple-glazing
                 * 1.7	Double-glazing with low-e coating
                 * 1.3	Double-glazing with low-e coating and Argon filled
                 * 0.8	Passivhaus requirement
                 * 0.4	Triple-glazing with multiple low-e coatings and Xenon filled
                 */
                Double u = 3.0; //u values could be stored on group level and polled from there
                Double heatflux = (dewPointValue - outerTemperatureValue) * u;
                return Math.round(heatflux * 100) / 100.0;
            }
            return Double.NaN;
        };

        String heatFluxFunctionName = "heatflux-combinator";
        GroupCombinator<Double> groupCombinator =
                new GroupCombinator<>(heatFluxFunctionName, heatFluxFunction);
        groupCombinators.add(groupCombinator);


        Function<Map<Long, Measurement<Double>>, Double> shutterFunction = (map) -> {

            Map<Producer, Measurement<Double>> producerMap = idToProducerMapper.apply(map);

            Optional<Producer> dewPointProducer = dewPointProducerGetter.apply(producerMap);

            Optional<Producer> insideTemperatureProducer = insideTemperatureProducerGetter.apply(producerMap);

            if(dewPointProducer.isPresent() && insideTemperatureProducer.isPresent()) {

                Double dewPointValue = producerMap.get(dewPointProducer.get()).getValue();
                Double insideTemperatureValue = producerMap.get(insideTemperatureProducer.get()).getValue();

                Double heatflux = heatFluxFunction.apply(map);
                Double shutter = heatflux / (insideTemperatureValue - dewPointValue);
                return Math.round(shutter * 100) / 100.0;
            }
            return Double.NaN;
        };

        String shutterCombinatorName = "shutter-combinator";
        GroupCombinator<Double> groupCombinatorShutter =
                new GroupCombinator<>(shutterCombinatorName, shutterFunction);
        groupCombinators.add(groupCombinatorShutter);
    }
}
