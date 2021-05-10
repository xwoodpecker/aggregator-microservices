package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.Map;
import java.util.function.Function;

public class MixedGroupCombinator<T> extends Combinator{

    private Function<Map<Long, Measurement<T>>, T> function;

    public MixedGroupCombinator() { }

    public MixedGroupCombinator(Function<Map<Long, Measurement<T>>, T> function, String name) {
        this.function = function;
        this.name = name;
    }

    public Function<Map<Long, Measurement<T>>, T> getFunction() {
        return function;
    }

    public void setFunction(Function<Map<Long, Measurement<T>>, T> function) {
        this.function = function;
    }

}
