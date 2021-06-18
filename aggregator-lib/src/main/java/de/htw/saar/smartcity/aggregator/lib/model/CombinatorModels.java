package de.htw.saar.smartcity.aggregator.lib.model;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

import java.util.function.DoubleFunction;

public final class CombinatorModels {

    private static final DoubleFunction<Double> roundToTwoDigits = n -> n == Double.NaN ? Double.NaN : Math.round(n * 100) / 100.0;

    private static final CombinatorFunction<Double> doubleAverage =
            (gms) -> roundToTwoDigits.apply(
                    gms.getProducerIdMeasurementMap().values().stream().mapToDouble(Measurement::getValue).average()
                            .orElseThrow(() -> new MeasurementException("Could not generate average")));
    private static final String doubleAverageName = "double-avg";

    private static final CombinatorFunction<Double> doubleMinimum =
            (gms) -> roundToTwoDigits.apply(
                    gms.getProducerIdMeasurementMap().values().stream().mapToDouble(Measurement::getValue).min()
                            .orElseThrow(() -> new MeasurementException("Could not generate minimum")));
    private static final String doubleMinimumName = "double-min";

    private static final CombinatorFunction<Double> doubleMaximum =
            (gms) -> roundToTwoDigits.apply(
                    gms.getProducerIdMeasurementMap().values().stream().mapToDouble(Measurement::getValue).max()
                            .orElseThrow(() -> new MeasurementException("Could not generate maximum")));
    private static final String doubleMaximumName = "double-max";

    private static final CombinatorFunction<Double> doubleSum =
            (gms) -> roundToTwoDigits.apply(gms.getProducerIdMeasurementMap().values().stream().mapToDouble(Measurement::getValue).sum());
    private static final String doubleSumName = "double-sum";

    private static final CombinatorFunction<Integer> integerSum =
            (gms) -> gms.getProducerIdMeasurementMap().values().stream().mapToInt(Measurement::getValue).sum();
    private static final String integerSumName = "integer-sum";


    public static CombinatorModel<Double> averageCombinatorModel = new CombinatorModel<>(doubleAverageName, doubleAverage);

    public static CombinatorModel<Double> minimumCombinatorModel = new CombinatorModel<>(doubleMinimumName, doubleMinimum);

    public static CombinatorModel<Double> maximumCombinatorModel = new CombinatorModel<>(doubleMaximumName, doubleMaximum);

    public static CombinatorModel<Double> doubleSumCombinatorModel = new CombinatorModel<>(doubleSumName, doubleSum);

    public static CombinatorModel<Integer> integerSumCombinatorModel = new CombinatorModel<>(integerSumName, integerSum);
    
}
