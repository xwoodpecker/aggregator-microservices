package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.List;
import java.util.function.Function;

public class BaseGroupCombinator<T> extends GroupCombinator<Function<List<Measurement<T>>, T>> {

    private Function<List<Measurement<T>>, T> function;

    public BaseGroupCombinator() { }

    public BaseGroupCombinator(Function<List<Measurement<T>>, T> function, String name) {
        this.function = function;
        this.name = name;
    }

    public Function<List<Measurement<T>>, T> getFunction() {
        return function;
    }

    public void setFunction(Function<List<Measurement<T>>, T> function) {
        this.function = function;
    }

}
