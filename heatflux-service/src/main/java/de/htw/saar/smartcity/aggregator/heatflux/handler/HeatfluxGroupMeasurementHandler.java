package de.htw.saar.smartcity.aggregator.heatflux.handler;

import de.htw.saar.smartcity.aggregator.heatflux.properties.HeatfluxApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.broker.Publisher;
import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItemValue;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;
import de.htw.saar.smartcity.aggregator.lib.handler.GroupMeasurementHandler;
import de.htw.saar.smartcity.aggregator.lib.model.CombinatorUnaryModel;
import de.htw.saar.smartcity.aggregator.lib.model.CombinatorUnaryOperator;
import de.htw.saar.smartcity.aggregator.lib.model.CombinatorModel;
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

/**
 * 5.0	Single-glazing
 * 3.0	Double-glazing
 * 2.2	Triple-glazing
 * 1.7	Double-glazing with low-e coating
 * 1.3	Double-glazing with low-e coating and Argon filled
 * 0.8	Passivhaus requirement
 * 0.4	Triple-glazing with multiple low-e coatings and Xenon filled
 */

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

        Function<Map<Long, Measurement<Double>>, Map<Producer, Measurement<Double>>> idToProducerMapper =
            map -> map
                .entrySet()
                .stream()
                .collect(Collectors.toMap(e -> producerService.findProducerById(
                        e.getKey()).get(),
                        e -> e.getValue())
                );

        Function<Map<Producer, Measurement<Double>>, Optional<Producer>> dewPointProducerGetter =
            map -> map
                .keySet()
                .stream()
                .filter(p -> p.getDataType().getName()
                    .equals(applicationProperties.getDewpointDataTypeName()))
                .findFirst();

        Function<Map<Producer, Measurement<Double>>, Optional<Producer>> insideTemperatureProducerGetter =
            map -> map
                .keySet()
                .stream()
                .filter(p -> p.getDataType().getName()
                    .equals(applicationProperties.getTemperatureDataTypeName()) &&
                    p.getTags().stream()
                        .anyMatch(
                            t -> t.getName().equals(applicationProperties.getTagNameInsideTemperature())
                        ))
                .findFirst();

        Function<Map<Producer, Measurement<Double>>, Optional<Producer>> outsideTemperatureProducerGetter =
            map -> map
                .keySet()
                .stream()
                .filter(p -> p.getDataType().getName()
                    .equals(applicationProperties.getTemperatureDataTypeName()) &&
                    p.getTags().stream()
                        .anyMatch(
                            t -> t.getName().equals(applicationProperties.getTagNameOutsideTemperature())
                        ))
                .findFirst();


        CombinatorUnaryOperator<Double> heatFluxFunction = (gms) -> {

            log.info("Start heatflux combinator function");
            Map<Producer, Measurement<Double>> producerMap =
                idToProducerMapper.apply(gms.getProducerIdMeasurementMap());

            Optional<Producer> dewPointProducer = dewPointProducerGetter.apply(producerMap);
            Optional<Producer> outsideTemperatureProducer = outsideTemperatureProducerGetter.apply(producerMap);

            Double dewPointValue = producerMap.get(dewPointProducer
                .orElseThrow(() -> new MeasurementException("No dewpoint value present.")))
                .getValue();
            Double outsideTemperatureValue = producerMap.get(outsideTemperatureProducer
                .orElseThrow(() -> new MeasurementException("No outside temperature value present.")))
                .getValue();

            Double u = 3.0;

            Group group = groupService.findGroupById(gms.getGroupId())
                .orElseThrow(() -> new MeasurementException("No valid group set"));

            Optional<FormulaItemValue> formulaItemValue = group.getValues()
                .stream()
                .filter(g -> g.getFormulaItem().getName()
                    .equals(applicationProperties.getFormulaItemNameUValue()))
                .findFirst();

            try {
                if(formulaItemValue.isPresent())
                    u = Double.valueOf(formulaItemValue.get().getValue());
                else
                    u = Double.valueOf(applicationProperties.getFormulaItemNameUValueDefault());
            } catch (NumberFormatException numberFormatException) {
                log.error("Formula item value could not be converted to a number");
            }

            Double heatflux = (dewPointValue - outsideTemperatureValue) * u;

            log.info("End heatflux combinator function");
            return Math.round(heatflux * 100) / 100.0;
        };

        String heatFluxFunctionName = "heatflux-combinator";
        CombinatorUnaryModel<Double> combinatorModel =
            new CombinatorUnaryModel<>(heatFluxFunctionName, heatFluxFunction);
        combinatorModels.add(combinatorModel);


        CombinatorUnaryOperator<Double> shutterFunction = (gms) -> {

            log.info("Start shutter combinator function");

            Map<Producer, Measurement<Double>> producerMap =
                idToProducerMapper.apply(gms.getProducerIdMeasurementMap());

            Optional<Producer> dewPointProducer = dewPointProducerGetter.apply(producerMap);
            Optional<Producer> insideTemperatureProducer = insideTemperatureProducerGetter.apply(producerMap);

            Double dewPointValue = producerMap.get(dewPointProducer
                .orElseThrow(() -> new MeasurementException("No dewpoint value present.")))
                .getValue();
            Double insideTemperatureValue = producerMap.get(insideTemperatureProducer
                .orElseThrow(() -> new MeasurementException("No inside temperature value present.")))
                .getValue();

            Double heatflux = heatFluxFunction.apply(gms);
            Double shutter = heatflux / (insideTemperatureValue - dewPointValue);

            log.info("End shutter combinator function");
            return Math.round(shutter * 100) / 100.0;
        };

        String shutterCombinatorName = "shutter-combinator";
        CombinatorUnaryModel<Double> combinatorModelShutter =
                new CombinatorUnaryModel<>(shutterCombinatorName, shutterFunction);
        combinatorModels.add(combinatorModelShutter);
    }
}
