package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItem;
import de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository;
import de.htw.saar.smartcity.aggregator.lib.repository.FormulaItemRepository;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Formula item service.
 */
@Service
public class FormulaItemService {


    private FormulaItemRepository formulaItemRepository;

    /**
     * Instantiates a new Formula item service.
     *
     * @param formulaItemRepository the formula item repository
     */
    public FormulaItemService(FormulaItemRepository formulaItemRepository) {
        this.formulaItemRepository = formulaItemRepository;
    }

    /**
     * Find formula item by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<FormulaItem> findFormulaItemById(Long id) {
        return formulaItemRepository.findById(id);
    }

    /**
     * Find all formula items list.
     *
     * @return the list
     */
    public List<FormulaItem> findAllFormulaItems() {
        return formulaItemRepository.findAll();
    }

    /**
     * Save formula item formula item.
     *
     * @param formulaItem the formula item
     * @return the formula item
     */
    public FormulaItem saveFormulaItem(FormulaItem formulaItem) {
        return formulaItemRepository.save(formulaItem);
    }

    /**
     * Find formula item by name formula item.
     *
     * @param formulaItemName the formula item name
     * @return the formula item
     */
    public FormulaItem findFormulaItemByName(String formulaItemName) {
        return formulaItemRepository.findFormulaItemByName(formulaItemName);
    }
}
