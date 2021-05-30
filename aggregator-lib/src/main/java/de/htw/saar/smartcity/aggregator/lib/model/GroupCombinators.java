package de.htw.saar.smartcity.aggregator.lib.model;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

import java.util.Map;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public final class GroupCombinators {

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


    public static GroupCombinator<Double> averageGroupCombinator = new GroupCombinator<>(doubleAverageName, doubleAverage);

    public static GroupCombinator<Double> minimumGroupCombinator = new GroupCombinator<>(doubleMinimumName, doubleMinimum);

    public static GroupCombinator<Double> maximumGroupCombinator = new GroupCombinator<>(doubleMaximumName, doubleMaximum);

    public static GroupCombinator<Double> doubleSumGroupCombinator = new GroupCombinator<>(doubleSumName, doubleSum);

    public static GroupCombinator<Integer> integerSumGroupCombinator = new GroupCombinator<>(integerSumName, integerSum);
    
}
