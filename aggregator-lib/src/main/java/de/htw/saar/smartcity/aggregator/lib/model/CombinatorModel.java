package de.htw.saar.smartcity.aggregator.lib.model;

import java.util.Objects;

/**
 * The type Combinator model.
 *
 * @param <T> the type parameter for input
 * @param <R> the type parameter for output
 */
public class CombinatorModel<T, R> {

    private String name;

    private CombinatorFunction<T, R> function;


    /**
     * Instantiates a new Combinator model.
     *
     * @param name     the name
     * @param function the function
     */
    public CombinatorModel(String name, CombinatorFunction<T, R> function) {
        this.name = name;
        this.function = function;
    }

    /**
     * Gets function.
     *
     * @return the function
     */
    public CombinatorFunction<T, R> getFunction() {
        return function;
    }

    /**
     * Sets function.
     *
     * @param function the function
     */
    public void setFunction(CombinatorFunction<T, R> function) {
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
        CombinatorModel<?, ?> that = (CombinatorModel<?, ?>) o;
        return Objects.equals(name, that.name) && Objects.equals(function, that.function);
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
