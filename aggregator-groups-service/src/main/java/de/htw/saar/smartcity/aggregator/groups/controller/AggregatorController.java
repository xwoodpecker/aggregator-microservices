package de.htw.saar.smartcity.aggregator.groups.controller;

import de.htw.saar.smartcity.aggregator.groups.exception.*;
import de.htw.saar.smartcity.aggregator.lib.entity.*;
import de.htw.saar.smartcity.aggregator.lib.service.AggregatorService;
import de.htw.saar.smartcity.aggregator.lib.service.CombinatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/aggregators")
public class AggregatorController {

    private final AggregatorService aggregatorService;
    private final CombinatorService combinatorService;

    public AggregatorController(AggregatorService aggregatorService, CombinatorService combinatorService) {

        this.aggregatorService = aggregatorService;
        this.combinatorService = combinatorService;
    }

    @GetMapping("/")
    public ResponseEntity getAggregators() {

        return new ResponseEntity(
                aggregatorService.findAllAggregators(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getAggregator(@PathVariable Long id) {

        return new ResponseEntity(
                aggregatorService.findAggregatorById(id)
                        .orElseThrow(() -> new AggregatorNotFoundException(id)),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity postAggregator(@RequestBody Aggregator aggregator) {

        return new ResponseEntity(aggregatorService.saveAggregator(aggregator), HttpStatus.OK);
    }



    @PutMapping("/{id}")
    public ResponseEntity putAggregator(@RequestBody Aggregator aggregator, @PathVariable Long id) {

        Optional<Aggregator> optOldAggregator = aggregatorService.findAggregatorById(id);
        Aggregator saved;

        if(optOldAggregator.isPresent()) {

            Aggregator oldAggregator = optOldAggregator.get();
            oldAggregator.setDataType(aggregator.getDataType());
            oldAggregator.setOwnerGroup(aggregator.getOwnerGroup());
            saved = aggregatorService.saveAggregator(oldAggregator);

        } else {

            aggregator.setId(id);
            saved = aggregatorService.saveAggregator(aggregator);
        }

        return new ResponseEntity(saved, HttpStatus.OK);
    }

    @PutMapping("/{aggregatorId}/combinators/{combinatorId}")
    public ResponseEntity putCombinator(@PathVariable Long aggregatorId, @PathVariable Long combinatorId) {

        Aggregator aggregator = aggregatorService.findAggregatorById(aggregatorId)
                .orElseThrow(() -> new AggregatorNotFoundException(aggregatorId));

        Combinator combinator = combinatorService.findCombinatorById(combinatorId)
                .orElseThrow(() -> new CombinatorNotFoundException(combinatorId));

        combinator.getAggregators().add(aggregator);
        aggregator.setCombinator(combinator);

        aggregator = aggregatorService.saveAggregator(aggregator);

        return new ResponseEntity(aggregator, HttpStatus.OK);
    }

    @DeleteMapping("/{aggregatorId}/combinators/{combinatorId}")
    public ResponseEntity deleteCombinator(@PathVariable Long aggregatorId, @PathVariable Long combinatorId) {

        Aggregator aggregator = aggregatorService.findAggregatorById(aggregatorId)
                .orElseThrow(() -> new AggregatorNotFoundException(aggregatorId));

        Combinator combinator = combinatorService.findCombinatorById(combinatorId)
                .orElseThrow(() -> new CombinatorNotFoundException(combinatorId));

        combinator.getAggregators().remove(aggregator);
        aggregator.setCombinator(null);

        aggregator = aggregatorService.saveAggregator(aggregator);

        return new ResponseEntity(aggregator, HttpStatus.OK);
    }
}
