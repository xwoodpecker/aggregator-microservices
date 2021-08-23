package de.htw.saar.smartcity.aggregator.management.controller;

import de.htw.saar.smartcity.aggregator.management.exception.*;
import de.htw.saar.smartcity.aggregator.lib.entity.*;
import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.CombinatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The type Aggregator controller.
 */
@RestController
@RequestMapping(path = "/aggregators")
public class AggregatorController {

    private final AggregatorService aggregatorService;
    private final CombinatorService combinatorService;

    /**
     * Instantiates a new Aggregator controller.
     *
     * @param aggregatorService the aggregator service
     * @param combinatorService the combinator service
     */
    public AggregatorController(AggregatorService aggregatorService, CombinatorService combinatorService) {

        this.aggregatorService = aggregatorService;
        this.combinatorService = combinatorService;
    }

    /**
     * Gets aggregators.
     *
     * @return the aggregators
     */
    @GetMapping("/")
    public ResponseEntity getAggregators() {

        return new ResponseEntity(
                aggregatorService.findAllAggregators(),
                HttpStatus.OK
        );
    }

    /**
     * Gets aggregator.
     *
     * @param id the id
     * @return the aggregator
     */
    @GetMapping("/{id}")
    public ResponseEntity getAggregator(@PathVariable Long id) {

        return new ResponseEntity(
                aggregatorService.findAggregatorById(id)
                        .orElseThrow(() -> new AggregatorNotFoundException(id)),
                HttpStatus.OK
        );
    }

    /**
     * Post aggregator response entity.
     *
     * @param aggregator the aggregator
     * @return the response entity
     */
    @PostMapping("/")
    public ResponseEntity postAggregator(@RequestBody Aggregator aggregator) {

        return new ResponseEntity(aggregatorService.saveAggregator(aggregator), HttpStatus.OK);
    }


    /**
     * Put aggregator response entity.
     *
     * @param aggregator the aggregator
     * @param id         the id
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity putAggregator(@RequestBody Aggregator aggregator, @PathVariable Long id) {

        Optional<Aggregator> optOldAggregator = aggregatorService.findAggregatorById(id);
        Aggregator saved;

        if(optOldAggregator.isPresent()) {

            Aggregator oldAggregator = optOldAggregator.get();
            oldAggregator.setExportAsMetric(aggregator.getExportAsMetric());
            oldAggregator.setObjectStorePath(aggregator.getObjectStorePath());
            oldAggregator.setDataType(aggregator.getDataType());
            oldAggregator.setOwnerGroup(aggregator.getOwnerGroup());
            saved = aggregatorService.saveAggregator(oldAggregator);

        } else {

            aggregator.setId(id);
            saved = aggregatorService.saveAggregator(aggregator);
        }

        return new ResponseEntity(saved, HttpStatus.OK);
    }


    /**
     * Delete aggregator response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteAggregator(@PathVariable Long id) {

        Aggregator aggregator = aggregatorService.findAggregatorById(id)
                .orElseThrow(() -> new AggregatorNotFoundException(id));

        //PRIO 2 - todo: verify
        if(aggregator.getGroups().isEmpty() && aggregator.getOwnerGroup() == null)
            aggregatorService.deleteAggregator(aggregator);
        else
            throw new AggregatorInUseException(id);

        return new ResponseEntity(HttpStatus.OK);

    }


    /**
     * Put combinator response entity.
     *
     * @param aggregatorId the aggregator id
     * @param combinatorId the combinator id
     * @return the response entity
     */
    @PutMapping("/{aggregatorId}/combinators/{combinatorId}")
    public ResponseEntity putCombinator(@PathVariable Long aggregatorId, @PathVariable Long combinatorId) {

        Aggregator aggregator = aggregatorService.findAggregatorById(aggregatorId)
                .orElseThrow(() -> new AggregatorNotFoundException(aggregatorId));

        Combinator combinator = combinatorService.findCombinatorById(combinatorId)
                .orElseThrow(() -> new CombinatorNotFoundException(combinatorId));

        //combinator.getAggregators().add(aggregator);
        aggregator.setCombinator(combinator);

        aggregator = aggregatorService.saveAggregator(aggregator);

        return new ResponseEntity(aggregator, HttpStatus.OK);
    }

    /**
     * Delete combinator response entity.
     *
     * @param aggregatorId the aggregator id
     * @param combinatorId the combinator id
     * @return the response entity
     */
    @DeleteMapping("/{aggregatorId}/combinators/{combinatorId}")
    public ResponseEntity deleteCombinator(@PathVariable Long aggregatorId, @PathVariable Long combinatorId) {

        Aggregator aggregator = aggregatorService.findAggregatorById(aggregatorId)
                .orElseThrow(() -> new AggregatorNotFoundException(aggregatorId));

        Combinator combinator = combinatorService.findCombinatorById(combinatorId)
                .orElseThrow(() -> new CombinatorNotFoundException(combinatorId));

        //combinator.getAggregators().remove(aggregator);
        aggregator.setCombinator(null);

        aggregator = aggregatorService.saveAggregator(aggregator);

        return new ResponseEntity(aggregator, HttpStatus.OK);
    }
}
