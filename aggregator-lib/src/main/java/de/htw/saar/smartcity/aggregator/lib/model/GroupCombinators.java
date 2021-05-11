package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.List;
import java.util.function.Function;

public final class GroupCombinators {

    private static final Function<List<Measurement<Double>>, Double> average =
            (list) -> list.stream().mapToDouble(Measurement::getValue).average().orElse(Double.NaN);
    private static final String averageName = "avg";

    private static final Function<List<Measurement<Double>>, Double> minimum =
            (list) -> list.stream().mapToDouble(Measurement::getValue).min().orElse(Double.NaN);
    private static final String minimumName = "min";

    private static final Function<List<Measurement<Double>>, Double> maximum =
            (list) -> list.stream().mapToDouble(Measurement::getValue).max().orElse(Double.NaN);
    private static final String maximumName = "max";

    private static final Function<List<Measurement<Double>>, Double> doubleSum =
            (list) -> list.stream().mapToDouble(Measurement::getValue).sum();
    private static final String doubleSumName = "sum";

    private static final Function<List<Measurement<Integer>>, Integer> integerSum =
            (list) -> list.stream().mapToInt(Measurement::getValue).sum();
    private static final String integerSumName = "sum";


    public static BaseGroupCombinator averageBaseGroupCombinator = new BaseGroupCombinator(average, averageName);

    public static BaseGroupCombinator minimumBaseGroupCombinator = new BaseGroupCombinator(minimum, minimumName);

    public static BaseGroupCombinator maximumBaseGroupCombinator = new BaseGroupCombinator(maximum, maximumName);

    public static BaseGroupCombinator doubleSumBaseGroupCombinator = new BaseGroupCombinator(doubleSum, doubleSumName);

    public static BaseGroupCombinator integerSumBaseGroupCombinator = new BaseGroupCombinator(integerSum, integerSumName);
    
}
