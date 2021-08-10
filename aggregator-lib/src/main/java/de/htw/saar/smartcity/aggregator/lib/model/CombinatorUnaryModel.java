package de.htw.saar.smartcity.aggregator.lib.model;

/**
 * The type Combinator unary model.
 *
 * @param <T> the type parameter for input and output
 */
public class CombinatorUnaryModel<T> extends CombinatorModel<T,T> {

    /**
     * Instantiates a new Combinator unary model.
     *
     * @param name     the name
     * @param function the function
     */
    public CombinatorUnaryModel(String name, CombinatorFunction<T, T> function) {
        super(name, function);
    }
}
