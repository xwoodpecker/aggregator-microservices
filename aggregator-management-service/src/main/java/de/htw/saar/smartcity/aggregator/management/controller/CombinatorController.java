package de.htw.saar.smartcity.aggregator.management.controller;

import de.htw.saar.smartcity.aggregator.management.exception.CombinatorNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.service.CombinatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Combinator controller.
 */
@RestController
@RequestMapping(path = "/combinators")
public class CombinatorController {

    private final CombinatorService combinatorService;

    /**
     * Instantiates a new Combinator controller.
     *
     * @param combinatorService the combinator service
     */
    public CombinatorController(CombinatorService combinatorService) {

        this.combinatorService = combinatorService;
    }

    /**
     * Gets combinators.
     *
     * @return the combinators
     */
    @GetMapping("/")
    public ResponseEntity getCombinators() {

        return new ResponseEntity(
                combinatorService.findAllCombinators(),
                HttpStatus.OK
        );
    }

    /**
     * Gets combinator.
     *
     * @param id the id
     * @return the combinator
     */
    @GetMapping("/{id}")
    public ResponseEntity getCombinator(@PathVariable Long id) {

        return new ResponseEntity(
                combinatorService.findCombinatorById(id)
                        .orElseThrow(() -> new CombinatorNotFoundException(id)),
                HttpStatus.OK
        );
    }

    /**@PostMapping("/")
    public ResponseEntity postCombinator(@RequestBody Combinator combinator) {

        return new ResponseEntity(combinatorService.saveCombinator(combinator), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity putCombinator(@RequestBody Combinator combinator, @PathVariable Long id) {

        Optional<Combinator> optOldCombinator = combinatorService.findCombinatorById(id);
        Combinator saved;

        if(optOldCombinator.isPresent()) {

            Combinator oldCombinator = optOldCombinator.get();
            oldCombinator.setName(combinator.getName());
            saved = combinatorService.saveCombinator(oldCombinator);

        } else {

            combinator.setId(id);
            saved = combinatorService.saveCombinator(combinator);
        }

        return new ResponseEntity(saved, HttpStatus.OK);
    }**/
}
