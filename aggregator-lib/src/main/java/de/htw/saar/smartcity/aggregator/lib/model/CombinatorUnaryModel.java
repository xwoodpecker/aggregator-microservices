package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.Objects;

public class CombinatorUnaryModel<T> extends CombinatorModel<T,T> {

    public CombinatorUnaryModel(String name, CombinatorFunction<T, T> function) {
        super(name, function);
    }
}
