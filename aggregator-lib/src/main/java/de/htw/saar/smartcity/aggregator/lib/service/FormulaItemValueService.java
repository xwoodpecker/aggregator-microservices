package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItemValue;
import de.htw.saar.smartcity.aggregator.lib.repository.FormulaItemValueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Formula item value service.
 */
@Service
public class FormulaItemValueService {


    private FormulaItemValueRepository formulaItemValueRepository;

    /**
     * Instantiates a new Formula item value service.
     *
     * @param formulaItemValueRepository the formula item value repository
     */
    public FormulaItemValueService(FormulaItemValueRepository formulaItemValueRepository) {
        this.formulaItemValueRepository = formulaItemValueRepository;
    }

    /**
     * Find formula item value by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<FormulaItemValue> findFormulaItemValueById(Long id) {
        return formulaItemValueRepository.findById(id);
    }

    /**
     * Find all formula item values list.
     *
     * @return the list
     */
    public List<FormulaItemValue> findAllFormulaItemValues() {
        return formulaItemValueRepository.findAll();
    }

    /**
     * Save formula item value
     *
     * @param formulaItemValue the formula item value
     * @return the formula item value
     */
    public FormulaItemValue saveFormulaItemValue(FormulaItemValue formulaItemValue) {
        return formulaItemValueRepository.save(formulaItemValue);
    }

    /**
     * Find formula item value by value formula item value.
     *
     * @param value the value
     * @return the formula item value
     */
    public FormulaItemValue findFormulaItemValueByValue(String value) {
        return formulaItemValueRepository.findFormulaItemValueByValue(value);
    }

    /**
     * Delete formula value item.
     *
     * @param formulaItemValue the formula item value
     */
    public void deleteFormulaValueItem(FormulaItemValue formulaItemValue) {

        formulaItemValue.getGroups().forEach(g -> g.getValues().removeIf(v -> v.equals(formulaItemValue)));
        formulaItemValue.setGroups(null);
        formulaItemValueRepository.deleteById(formulaItemValue.getId());
    }
}
