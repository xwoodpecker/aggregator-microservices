package de.htw.saar.smartcity.aggregator.management.controller;

import de.htw.saar.smartcity.aggregator.management.exception.TagInUseException;
import de.htw.saar.smartcity.aggregator.management.exception.TagNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The type Tag controller.
 */
@RestController
@RequestMapping(path = "/tags")
public class TagController {

    private final TagService tagService;

    /**
     * Instantiates a new Tag controller.
     *
     * @param tagService the tag service
     */
    public TagController(TagService tagService) {

        this.tagService = tagService;
    }

    /**
     * Gets tags.
     *
     * @return the tags
     */
    @GetMapping("/")
    public ResponseEntity getTags() {

        return new ResponseEntity(
                tagService.findAllTags(),
                HttpStatus.OK
        );
    }

    /**
     * Gets tag.
     *
     * @param id the id
     * @return the tag
     */
    @GetMapping("/{id}")
    public ResponseEntity getTag(@PathVariable Long id) {

        return new ResponseEntity(
                tagService.findTagById(id)
                        .orElseThrow(() -> new TagNotFoundException(id)),
                HttpStatus.OK
        );
    }

    /**
     * Post tag response entity.
     *
     * @param tag the tag
     * @return the response entity
     */
    @PostMapping("/")
    public ResponseEntity postTag(@RequestBody Tag tag) {

        return new ResponseEntity(tagService.saveTag(tag), HttpStatus.OK);
    }

    /**
     * Delete tag response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteTag(@PathVariable Long id) {

        Tag tag = tagService.findTagById(id)
                .orElseThrow(() -> new TagNotFoundException(id));

        //PRIO 2 - todo: verify
        if(tag.getProducers().isEmpty())
            tagService.deleteTag(tag);
        else
            throw new TagInUseException(id);

        return new ResponseEntity(HttpStatus.OK);

    }

    /**
     * Put tag response entity.
     *
     * @param tag the tag
     * @param id  the id
     * @return the response entity
     */
    @PutMapping("/{id}")
    public ResponseEntity putTag(@RequestBody Tag tag, @PathVariable Long id) {

        Optional<Tag> optOldTag = tagService.findTagById(id);
        Tag saved;

        if(optOldTag.isPresent()) {

            Tag oldTag = optOldTag.get();
            oldTag.setName(tag.getName());
            saved = tagService.updateTag(oldTag);

        } else {

            tag.setId(id);
            saved = tagService.saveTag(tag);
        }

        return new ResponseEntity(saved, HttpStatus.OK);
    }
}
