package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class GroupCombinator<T> {

    private String name;

    private Function<Map<Long, Measurement<T>>, T> function;


    public GroupCombinator(String name, Function<Map<Long, Measurement<T>>, T> function) {
        this.name = name;
        this.function = function;
    }

    public Function<Map<Long, Measurement<T>>, T> getFunction() {
        return function;
    }

    public void setFunction(Function<Map<Long, Measurement<T>>, T> function) {
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
        GroupCombinator<?> that = (GroupCombinator<?>) o;
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
