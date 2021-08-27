package de.htw.saar.smartcity.aggregator.management.controller;

import de.htw.saar.smartcity.aggregator.lib.entity.Location;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.service.LocationService;
import de.htw.saar.smartcity.aggregator.lib.service.ProducerService;
import de.htw.saar.smartcity.aggregator.lib.service.TagService;
import de.htw.saar.smartcity.aggregator.management.exception.LocationNotFoundException;
import de.htw.saar.smartcity.aggregator.management.exception.ProducerNotFoundException;
import de.htw.saar.smartcity.aggregator.management.exception.TagNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Producer controller.
 */
@RestController
@RequestMapping(path = "/producers")
public class ProducerController {

    private final ProducerService producerService;
    private final TagService tagService;
    private final LocationService locationService;

    /**
     * Instantiates a new Producer controller.
     *
     * @param producerService the producer service
     * @param tagService      the tag service
     * @param locationService the location service
     */
    public ProducerController(ProducerService producerService, TagService tagService, LocationService locationService) {

        this.producerService = producerService;
        this.tagService = tagService;
        this.locationService = locationService;
    }

    /**
     * Put tag response entity.
     *
     * @param producerId the producer id
     * @param tagId      the tag id
     * @return the response entity
     */
    @PutMapping("/{producerId}/tags/{tagId}")
    public ResponseEntity putTag(@PathVariable Long producerId, @PathVariable Long tagId) {

        Producer producer = producerService.findProducerById(producerId)
                .orElseThrow(() -> new ProducerNotFoundException(producerId));

        Tag tag = tagService.findTagById(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId));


        tag.getProducers().add(producer);
        producer.getTags().add(tag);

        producer = producerService.saveProducer(producer);

        return new ResponseEntity(producer, HttpStatus.OK);
    }

    /**
     * Delete tag response entity.
     *
     * @param producerId the producer id
     * @param tagId      the tag id
     * @return the response entity
     */
    @DeleteMapping("/{producerId}/tags/{tagId}")
    public ResponseEntity deleteTag(@PathVariable Long producerId, @PathVariable Long tagId) {

        Producer producer = producerService.findProducerById(producerId)
                .orElseThrow(() -> new ProducerNotFoundException(producerId));

        Tag tag = tagService.findTagById(tagId)
                .orElseThrow(() -> new TagNotFoundException(tagId));


        tag.getProducers().remove(producer);
        producer.getTags().remove(tag);

        producer = producerService.saveProducer(producer);

        return new ResponseEntity(producer, HttpStatus.OK);
    }

    @PutMapping("/{producerId}/location/{locationId}")
    public ResponseEntity putLocation(@PathVariable Long producerId, @PathVariable Long locationId) {

        Producer producer = producerService.findProducerById(producerId)
                .orElseThrow(() -> new ProducerNotFoundException(producerId));

        Location location = locationService.findLocationById(locationId)
                .orElseThrow(() -> new LocationNotFoundException(locationId));


        location.getProducers().add(producer);
        producer.setLocation(location);

        producer = producerService.saveProducer(producer);

        return new ResponseEntity(producer, HttpStatus.OK);
    }

    @DeleteMapping("/{producerId}/location/{locationId}")
    public ResponseEntity deleteLocation(@PathVariable Long producerId, @PathVariable Long locationId) {

        Producer producer = producerService.findProducerById(producerId)
                .orElseThrow(() -> new ProducerNotFoundException(producerId));

        Location location = locationService.findLocationById(locationId)
                .orElseThrow(() -> new LocationNotFoundException(locationId));


        location.getProducers().remove(producer);
        producer.setLocation(null);

        producer = producerService.saveProducer(producer);

        return new ResponseEntity(producer, HttpStatus.OK);
    }

}
