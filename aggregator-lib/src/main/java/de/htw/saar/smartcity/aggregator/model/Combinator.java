package de.htw.saar.smartcity.aggregator.model;

import java.util.List;
import java.util.function.Function;

public class Combinator<T> {

    private Function<List<Measurement<T>>, T> function;
    private String name;

    public Combinator() { }

    public Combinator(Function<List<Measurement<T>>, T> function, String name) {
        this.function = function;
        this.name = name;
    }

    public Function<List<Measurement<T>>, T> getFunction() {
        return function;
    }

    public void setFunction(Function<List<Measurement<T>>, T> function) {
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
