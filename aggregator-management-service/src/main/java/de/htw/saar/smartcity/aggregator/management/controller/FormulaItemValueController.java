package de.htw.saar.smartcity.aggregator.management.controller;

import de.htw.saar.smartcity.aggregator.management.exception.FormulaItemValueNotFoundException;
import de.htw.saar.smartcity.aggregator.management.exception.FormulaValueItemInUseException;
import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItemValue;
import de.htw.saar.smartcity.aggregator.lib.service.FormulaItemValueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The type Formula item value controller.
 */
@RestController
@RequestMapping(path = "/formulaItemValues")
public class FormulaItemValueController {

    private final FormulaItemValueService formulaItemValueService;

    /**
     * Instantiates a new Formula item value controller.
     *
     * @param formulaItemValueService the formula item value service
     */
    public FormulaItemValueController(FormulaItemValueService formulaItemValueService) {

        this.formulaItemValueService = formulaItemValueService;
    }

    /**
     * Gets formula item values.
     *
     * @return the formula item values
     */
    @GetMapping("/")
    public ResponseEntity getFormulaItemValues() {

        return new ResponseEntity(
                formulaItemValueService.findAllFormulaItemValues(),
                HttpStatus.OK
        );
    }

    /**
     * Gets formula item value.
     *
     * @param id the id
     * @return the formula item value
     */
    @GetMapping("/{id}")
    public ResponseEntity getFormulaItemValue(@PathVariable Long id) {

        return new ResponseEntity(
                formulaItemValueService.findFormulaItemValueById(id)
                        .orElseThrow(() -> new FormulaItemValueNotFoundException(id)),
                HttpStatus.OK
        );
    }

    /**
     * Post formula item value response entity.
     *
     * @param formulaItemValue the formula item value
     * @return the response entity
     */
    @PostMapping("/")
    public ResponseEntity postFormulaItemValue(@RequestBody FormulaItemValue formulaItemValue) {

        return new ResponseEntity(formulaItemValueService.saveFormulaItemValue(formulaItemValue), HttpStatus.OK);
    }

    /**
     * Put formula item value response entity.
     *
     * @param formulaItemValue the formula item value
     * @param id               the id
     * @return the response entity
     */
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

    /**
     * Delete formula item value response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteFormulaItemValue(@PathVariable Long id) {

        FormulaItemValue formulaItemValue = formulaItemValueService.findFormulaItemValueById(id)
                .orElseThrow(() -> new FormulaItemValueNotFoundException(id));

        //PRIO 2 - todo: verify
        if(formulaItemValue.getGroups().isEmpty())
            formulaItemValueService.deleteFormulaValueItem(formulaItemValue);
        else
            throw new FormulaValueItemInUseException(id);

        return new ResponseEntity(HttpStatus.OK);

    }
}
