package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItem;
import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItemValue;
import de.htw.saar.smartcity.aggregator.lib.repository.FormulaItemRepository;
import de.htw.saar.smartcity.aggregator.lib.repository.FormulaItemValueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormulaItemValueService {


    private FormulaItemValueRepository formulaItemValueRepository;

    public FormulaItemValueService(FormulaItemValueRepository formulaItemValueRepository) {
        this.formulaItemValueRepository = formulaItemValueRepository;
    }

    public Optional<FormulaItemValue> findFormulaItemValueById(Long id) {
        return formulaItemValueRepository.findById(id);
    }

    public List<FormulaItemValue> findAllFormulaItemValues() {
        return formulaItemValueRepository.findAll();
    }

    public FormulaItemValue saveFormulaItemValue(FormulaItemValue formulaItemValue) {
        return formulaItemValueRepository.save(formulaItemValue);
    }

    public FormulaItemValue findFormulaItemValueByValue(String value) {
        return formulaItemValueRepository.findFormulaItemValueByValue(value);
    }
}
