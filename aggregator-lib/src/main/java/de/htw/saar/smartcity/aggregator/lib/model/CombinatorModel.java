package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.Objects;

public class CombinatorModel<T> {

    private String name;

    private CombinatorFunction<T> function;


    public CombinatorModel(String name, CombinatorFunction<T> function) {
        this.name = name;
        this.function = function;
    }

    public CombinatorFunction<T> getFunction() {
        return function;
    }

    public void setFunction(CombinatorFunction<T> function) {
        this.function = function;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CombinatorModel<?> that = (CombinatorModel<?>) o;
        return Objects.equals(function, that.function) && Objects.equals(name, that.name);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GroupCombinator{");
        sb.append("function=").append(function);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}