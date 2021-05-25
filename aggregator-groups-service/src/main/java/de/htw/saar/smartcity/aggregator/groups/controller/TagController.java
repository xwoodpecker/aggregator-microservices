package de.htw.saar.smartcity.aggregator.groups.controller;

import de.htw.saar.smartcity.aggregator.groups.exception.GroupNotFoundException;
import de.htw.saar.smartcity.aggregator.groups.exception.ProducerNotFoundException;
import de.htw.saar.smartcity.aggregator.groups.exception.TagInUseException;
import de.htw.saar.smartcity.aggregator.groups.exception.TagNotFoundException;
import de.htw.saar.smartcity.aggregator.groups.properties.GroupsApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.service.GroupService;
import de.htw.saar.smartcity.aggregator.lib.service.ProducerService;
import de.htw.saar.smartcity.aggregator.lib.service.SensorService;
import de.htw.saar.smartcity.aggregator.lib.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {

        this.tagService = tagService;
    }

    @GetMapping("/")
    public ResponseEntity getTags() {

        return new ResponseEntity(
                tagService.findAllTags(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getTag(@PathVariable Long id) {

        return new ResponseEntity(
                tagService.findTagById(id)
                        .orElseThrow(() -> new TagNotFoundException(id)),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity postTag(@RequestBody Tag tag) {

        return new ResponseEntity(tagService.saveTag(tag), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTag(@PathVariable Long id) {

        Tag tag = tagService.findTagById(id)
                .orElseThrow(() -> new TagNotFoundException(id));

        //todo: verify
        if(tag.getProducers().isEmpty())
            tagService.deleteTag(tag);
        else
            throw new TagInUseException(id);

        return new ResponseEntity(HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity putTag(@RequestBody Tag tag, @PathVariable Long id) {

        Optional<Tag> optOldTag = tagService.findTagById(id);
        Tag saved;

        if(optOldTag.isPresent()) {

            Tag oldTag = optOldTag.get();
            oldTag.setName(tag.getName());
            saved = tagService.saveTag(oldTag);

        } else {

            tag.setId(id);
            saved = tagService.saveTag(tag);
        }

        return new ResponseEntity(saved, HttpStatus.OK);
    }
}
