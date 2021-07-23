package de.htw.saar.smartcity.aggregator.groups.controller;

import de.htw.saar.smartcity.aggregator.groups.exception.ProducerNotFoundException;
import de.htw.saar.smartcity.aggregator.groups.exception.TagNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.service.LocationService;
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
    private final LocationService locationService;

    public ProducerController(ProducerService producerService, TagService tagService, LocationService locationService) {

        this.producerService = producerService;
        this.tagService = tagService;
        this.locationService = locationService;
    }

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

    /**@PutMapping("/{producerId}/location/{locationId}")
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
    }**/

}
