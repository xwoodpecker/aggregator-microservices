package de.htw.saar.smartcity.aggregator.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class GroupCombinator<T extends Function> {

    protected String name;

    protected T function;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public T getFunction() {
        return function;
    }

    public void setFunction(T function) {
        this.function = function;
    }
}
