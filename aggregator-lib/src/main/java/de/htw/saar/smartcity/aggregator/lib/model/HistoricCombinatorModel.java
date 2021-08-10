package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.Objects;

/**
 * The type Historic combinator model.
 *
 * @param <T> the type parameter for input and output
 */
public class HistoricCombinatorModel<T> {

    private String name;

    private HistoricCombinatorFunction<T> function;


    /**
     * Instantiates a new Historic combinator model.
     *
     * @param name     the name
     * @param function the function
     */
    public HistoricCombinatorModel(String name, HistoricCombinatorFunction<T> function) {
        this.name = name;
        this.function = function;
    }

    /**
     * Gets function.
     *
     * @return the function
     */
    public HistoricCombinatorFunction<T> getFunction() {
        return function;
    }

    /**
     * Sets function.
     *
     * @param function the function
     */
    public void setFunction(HistoricCombinatorFunction<T> function) {
        this.function = function;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoricCombinatorModel<?> that = (HistoricCombinatorModel<?>) o;
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
