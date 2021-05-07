package de.htw.saar.smartcity.aggregator.model;

import java.util.List;
import java.util.function.Function;

public final class Combinators {

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


    public static Combinator AverageCombinator = new Combinator(average, averageName);

    public static Combinator MinimumCombinator = new Combinator(minimum, minimumName);

    public static Combinator MaximumCombinator = new Combinator(maximum, maximumName);

    public static Combinator DoubleSumCombinator = new Combinator(doubleSum, doubleSumName);

    public static Combinator IntegerSumCombinator = new Combinator(integerSum, integerSumName);
    
}
