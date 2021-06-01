package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItem;
import de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository;
import de.htw.saar.smartcity.aggregator.lib.repository.FormulaItemRepository;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormulaItemService {


    private FormulaItemRepository formulaItemRepository;

    public FormulaItemService(FormulaItemRepository formulaItemRepository) {
        this.formulaItemRepository = formulaItemRepository;
    }

    public Optional<FormulaItem> findFormulaItemById(Long id) {
        return formulaItemRepository.findById(id);
    }

    public List<FormulaItem> findAllFormulaItems() {
        return formulaItemRepository.findAll();
    }

    public FormulaItem saveFormulaItem(FormulaItem formulaItem) {
        return formulaItemRepository.save(formulaItem);
    }

    public FormulaItem findFormulaItemByName(String formulaItemName) {
        return formulaItemRepository.findFormulaItemByName(formulaItemName);
    }
}
