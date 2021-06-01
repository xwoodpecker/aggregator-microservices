package de.htw.saar.smartcity.aggregator.groups.controller;

import de.htw.saar.smartcity.aggregator.groups.exception.FormulaItemValueNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItemValue;
import de.htw.saar.smartcity.aggregator.lib.service.FormulaItemValueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/formulaItemValues")
public class FormulaItemValueController {

    private final FormulaItemValueService formulaItemValueService;

    public FormulaItemValueController(FormulaItemValueService formulaItemValueService) {

        this.formulaItemValueService = formulaItemValueService;
    }

    @GetMapping("/")
    public ResponseEntity getFormulaItemValues() {

        return new ResponseEntity(
                formulaItemValueService.findAllFormulaItemValues(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getFormulaItemValue(@PathVariable Long id) {

        return new ResponseEntity(
                formulaItemValueService.findFormulaItemValueById(id)
                        .orElseThrow(() -> new FormulaItemValueNotFoundException(id)),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity postFormulaItemValue(@RequestBody FormulaItemValue formulaItemValue) {

        return new ResponseEntity(formulaItemValueService.saveFormulaItemValue(formulaItemValue), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity putFormulaItemValue(@RequestBody FormulaItemValue formulaItemValue, @PathVariable Long id) {

        Optional<FormulaItemValue> optOldFormulaItemValue = formulaItemValueService.findFormulaItemValueById(id);
        FormulaItemValue saved;

        if(optOldFormulaItemValue.isPresent()) {

            FormulaItemValue oldFormulaItemValue = optOldFormulaItemValue.get();
            oldFormulaItemValue.setValue(formulaItemValue.getValue());
            oldFormulaItemValue.setFormulaItem(formulaItemValue.getFormulaItem());
            saved = formulaItemValueService.saveFormulaItemValue(oldFormulaItemValue);

        } else {

            formulaItemValue.setId(id);
            saved = formulaItemValueService.saveFormulaItemValue(formulaItemValue);
        }

        return new ResponseEntity(saved, HttpStatus.OK);
    }
}
