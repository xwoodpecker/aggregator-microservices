package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItem;
import de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository;
import de.htw.saar.smartcity.aggregator.lib.repository.FormulaItemRepository;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.stereotype.Service;

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

}
