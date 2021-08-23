package de.htw.saar.smartcity.aggregator.management.controller;

import de.htw.saar.smartcity.aggregator.management.exception.FormulaItemNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.service.FormulaItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Formula item controller.
 */
@RestController
@RequestMapping(path = "/formulaItems")
public class FormulaItemController {

    private final FormulaItemService formulaItemService;

    /**
     * Instantiates a new Formula item controller.
     *
     * @param formulaItemService the formula item service
     */
    public FormulaItemController(FormulaItemService formulaItemService) {

        this.formulaItemService = formulaItemService;
    }

    /**
     * Gets formula items.
     *
     * @return the formula items
     */
    @GetMapping("/")
    public ResponseEntity getFormulaItems() {

        return new ResponseEntity(
                formulaItemService.findAllFormulaItems(),
                HttpStatus.OK
        );
    }

    /**
     * Gets formula item.
     *
     * @param id the id
     * @return the formula item
     */
    @GetMapping("/{id}")
    public ResponseEntity getFormulaItem(@PathVariable Long id) {

        return new ResponseEntity(
                formulaItemService.findFormulaItemById(id)
                        .orElseThrow(() -> new FormulaItemNotFoundException(id)),
                HttpStatus.OK
        );
    }

    /**@PostMapping("/")
    public ResponseEntity postFormulaItem(@RequestBody FormulaItem formulaItem) {

        return new ResponseEntity(formulaItemService.saveFormulaItem(formulaItem), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity putFormulaItem(@RequestBody FormulaItem formulaItem, @PathVariable Long id) {

        Optional<FormulaItem> optOldFormulaItem = formulaItemService.findFormulaItemById(id);
        FormulaItem saved;

        if(optOldFormulaItem.isPresent()) {

            FormulaItem oldFormulaItem = optOldFormulaItem.get();
            oldFormulaItem.setName(formulaItem.getName());
            saved = formulaItemService.saveFormulaItem(oldFormulaItem);

        } else {

            formulaItem.setId(id);
            saved = formulaItemService.saveFormulaItem(formulaItem);
        }

        return new ResponseEntity(saved, HttpStatus.OK);
    }**/
}
