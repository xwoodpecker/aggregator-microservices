package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.Map;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public final class GroupCombinators {

    private static final DoubleFunction<Double> roundToTwoDigits = n -> n == Double.NaN ? Double.NaN : Math.round(n * 100) / 100.0;

    private static final Function<Map<Long, Measurement<Double>>, Double> doubleAverage =
            (map) -> roundToTwoDigits.apply(map.values().stream().mapToDouble(Measurement::getValue).average().orElse(Double.NaN));
    private static final String doubleAverageName = "double-avg";

    private static final Function<Map<Long, Measurement<Double>>, Double> doubleMinimum =
            (map) -> roundToTwoDigits.apply(map.values().stream().mapToDouble(Measurement::getValue).min().orElse(Double.NaN));
    private static final String doubleMinimumName = "double-min";

    private static final Function<Map<Long, Measurement<Double>>, Double> doubleMaximum =
            (map) -> roundToTwoDigits.apply(map.values().stream().mapToDouble(Measurement::getValue).max().orElse(Double.NaN));
    private static final String doubleMaximumName = "double-max";

    private static final Function<Map<Long, Measurement<Double>>, Double> doubleSum =
            (map) -> roundToTwoDigits.apply(map.values().stream().mapToDouble(Measurement::getValue).sum());
    private static final String doubleSumName = "double-sum";

    private static final Function<Map<Long, Measurement<Integer>>, Integer> integerSum =
            (map) -> map.values().stream().mapToInt(Measurement::getValue).sum();
    private static final String integerSumName = "integer-sum";


    public static GroupCombinator<Double> averageGroupCombinator = new GroupCombinator<>(doubleAverageName, doubleAverage);

    public static GroupCombinator<Double> minimumGroupCombinator = new GroupCombinator<>(doubleMinimumName, doubleMinimum);

    public static GroupCombinator<Double> maximumGroupCombinator = new GroupCombinator<>(doubleMaximumName, doubleMaximum);

    public static GroupCombinator<Double> doubleSumGroupCombinator = new GroupCombinator<>(doubleSumName, doubleSum);

    public static GroupCombinator<Integer> integerSumGroupCombinator = new GroupCombinator<>(integerSumName, integerSum);
    
}
