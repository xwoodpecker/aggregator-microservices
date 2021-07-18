package de.htw.saar.smartcity.aggregator.lib.model;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public final class CombinatorModels {

    private static final DoubleFunction<Double> roundToTwoDigits = n -> n == Double.NaN ? Double.NaN : Math.round(n * 100) / 100.0;

    private static final CombinatorUnaryOperator<Double> doubleAverage =
            (gms) -> roundToTwoDigits.apply(
                    gms.getProducerIdMeasurementMap().values().stream().mapToDouble(Measurement::getValue).average()
                            .orElseThrow(() -> new MeasurementException("Could not generate average")));
    private static final String doubleAverageName = "double-avg";

    private static final CombinatorUnaryOperator<Double> doubleMinimum =
            (gms) -> roundToTwoDigits.apply(
                    gms.getProducerIdMeasurementMap().values().stream().mapToDouble(Measurement::getValue).min()
                            .orElseThrow(() -> new MeasurementException("Could not generate minimum")));
    private static final String doubleMinimumName = "double-min";

    private static final CombinatorUnaryOperator<Double> doubleMaximum =
            (gms) -> roundToTwoDigits.apply(
                    gms.getProducerIdMeasurementMap().values().stream().mapToDouble(Measurement::getValue).max()
                            .orElseThrow(() -> new MeasurementException("Could not generate maximum")));
    private static final String doubleMaximumName = "double-max";

    private static final CombinatorUnaryOperator<Double> doubleSum =
            (gms) -> roundToTwoDigits.apply(gms.getProducerIdMeasurementMap().values().stream().mapToDouble(Measurement::getValue).sum());
    private static final String doubleSumName = "double-sum";

    private static final CombinatorUnaryOperator<Integer> integerSum =
            (gms) -> gms.getProducerIdMeasurementMap().values().stream().mapToInt(Measurement::getValue).sum();
    private static final String integerSumName = "integer-sum";

    private static final CombinatorFunction<Boolean, Long> booleanTrueCount =
            (gms) -> gms.getProducerIdMeasurementMap().values().stream().filter(m -> m.getValue()).count();
    private static final String booleanTrueCountName = "true-count";

    private static final CombinatorFunction<Boolean, Long> booleanFalseCount =
            (gms) -> gms.getProducerIdMeasurementMap().values().stream().filter(m -> !m.getValue()).count();
    private static final String booleanFalseCountName = "false-count";



    public static CombinatorUnaryModel<Double> averageCombinatorModel = new CombinatorUnaryModel<>(doubleAverageName, doubleAverage);

    public static CombinatorUnaryModel<Double> minimumCombinatorModel = new CombinatorUnaryModel<>(doubleMinimumName, doubleMinimum);

    public static CombinatorUnaryModel<Double> maximumCombinatorModel = new CombinatorUnaryModel<>(doubleMaximumName, doubleMaximum);

    public static CombinatorUnaryModel<Double> doubleSumCombinatorModel = new CombinatorUnaryModel<>(doubleSumName, doubleSum);

    public static CombinatorUnaryModel<Integer> integerSumCombinatorModel = new CombinatorUnaryModel<>(integerSumName, integerSum);

    public static CombinatorModel<Boolean, Long> booleanTrueCountCombinatorModel = new CombinatorModel<>(booleanTrueCountName, booleanTrueCount);

    public static CombinatorModel<Boolean, Long> booleanFalseCountCombinatorModel = new CombinatorModel<>(booleanFalseCountName, booleanFalseCount);

    
}
