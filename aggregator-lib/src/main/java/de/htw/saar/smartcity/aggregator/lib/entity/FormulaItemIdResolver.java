package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import de.htw.saar.smartcity.aggregator.lib.service.FormulaItemService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The type Formula item id resolver.
 */
public class FormulaItemIdResolver implements ObjectIdResolver {

    /**
     * the formula item service
     */
    private final FormulaItemService formulaItemService;

    /**
     * Instantiates a new Formula item id resolver.
     *
     * @param formulaItemService the formula item service
     */
    public FormulaItemIdResolver(@Autowired FormulaItemService formulaItemService) {
        this.formulaItemService = formulaItemService;
    }

    @Override
    public void bindItem(ObjectIdGenerator.IdKey idKey, Object o) {

    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey idKey) {
        if(idKey.key instanceof Long)
            return formulaItemService.findFormulaItemById((Long)idKey.key).get();
        else
            return null;
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object o) {
        return this;
    }

    @Override
    public boolean canUseFor(ObjectIdResolver objectIdResolver) {
        return false;
    }
}