package de.htw.saar.smartcity.aggregator.groups.controller;

import de.htw.saar.smartcity.aggregator.groups.exception.AggregatorNotFoundException;
import de.htw.saar.smartcity.aggregator.groups.exception.CombinatorNotFoundException;
import de.htw.saar.smartcity.aggregator.groups.exception.ProducerNotFoundException;
import de.htw.saar.smartcity.aggregator.groups.exception.TagNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.Combinator;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.service.ProducerService;
import de.htw.saar.smartcity.aggregator.lib.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/producers")
public class ProducerController {

    private final ProducerService producerService;
    private final TagService tagService;

    public ProducerController(ProducerService producerService, TagService tagService) {

        this.producerService = producerService;
        this.tagService = tagService;
    }

    @PutMapping("/{producerId}/tags/{tagId}")
    public ResponseEntity putTag(@PathVariable Long producerId, @PathVariable Long tagId) {

        Producer producer = producerService.findProducerById(producerId)
                .orElseThrow(() -> new ProducerNotFoundException(producerId));

        Tag tag = tagService.findTagById(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId));

        //todo: develop & test

        tag.getProducers().add(producer);
        producer.getTags().add(tag);

        producer = producerService.saveProducer(producer);

        return new ResponseEntity(producer, HttpStatus.OK);
    }

    @DeleteMapping("/{producerId}/tags/{tagId}")
    public ResponseEntity deleteTag(@PathVariable Long producerId, @PathVariable Long tagId) {

        Producer producer = producerService.findProducerById(producerId)
                .orElseThrow(() -> new ProducerNotFoundException(producerId));

        Tag tag = tagService.findTagById(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId));

        //todo: develop & test

        tag.getProducers().remove(producer);
        producer.getTags().remove(tag);

        producer = producerService.saveProducer(producer);

        return new ResponseEntity(producer, HttpStatus.OK);
    }

}
