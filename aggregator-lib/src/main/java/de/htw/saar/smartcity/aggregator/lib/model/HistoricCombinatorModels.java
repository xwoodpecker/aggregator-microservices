package de.htw.saar.smartcity.aggregator.lib.model;

import de.htw.saar.smartcity.aggregator.lib.exception.MeasurementException;

import java.util.function.DoubleFunction;

public final class HistoricCombinatorModels {

    private static final DoubleFunction<Double> roundToTwoDigits = n -> n == Double.NaN ? Double.NaN : Math.round(n * 100) / 100.0;

    private static final HistoricCombinatorFunction<Double> doubleAverage =
            (m) -> roundToTwoDigits.apply(
                    m.stream().mapToDouble(Measurement::getValue).average()
                            .orElseThrow(() -> new MeasurementException("Could not generate average")));
    private static final String doubleAverageName = "double-avg";

    private static final HistoricCombinatorFunction<Double> doubleMinimum =
            (m) -> roundToTwoDigits.apply(
                    m.stream().mapToDouble(Measurement::getValue).min()
                            .orElseThrow(() -> new MeasurementException("Could not generate minimum")));
    private static final String doubleMinimumName = "double-min";

    private static final HistoricCombinatorFunction<Double> doubleMaximum =
            (m) -> roundToTwoDigits.apply(
                    m.stream().mapToDouble(Measurement::getValue).max()
                            .orElseThrow(() -> new MeasurementException("Could not generate maximum")));
    private static final String doubleMaximumName = "double-max";

    private static final HistoricCombinatorFunction<Double> doubleSum =
            (m) -> roundToTwoDigits.apply(m.stream().mapToDouble(Measurement::getValue).sum());
    private static final String doubleSumName = "double-sum";

    private static final HistoricCombinatorFunction<Integer> integerSum =
            (m) -> m.stream().mapToInt(Measurement::getValue).sum();
    private static final String integerSumName = "integer-sum";


    public static HistoricCombinatorModel<Double> averageHistoricCombinatorModel = new HistoricCombinatorModel<>(doubleAverageName, doubleAverage);

    public static HistoricCombinatorModel<Double> minimumHistoricCombinatorModel = new HistoricCombinatorModel<>(doubleMinimumName, doubleMinimum);

    public static HistoricCombinatorModel<Double> maximumHistoricCombinatorModel = new HistoricCombinatorModel<>(doubleMaximumName, doubleMaximum);

    public static HistoricCombinatorModel<Double> doubleSumHistoricCombinatorModel = new HistoricCombinatorModel<>(doubleSumName, doubleSum);

    public static HistoricCombinatorModel<Integer> integerSumHistoricCombinatorModel = new HistoricCombinatorModel<>(integerSumName, integerSum);
    
}
