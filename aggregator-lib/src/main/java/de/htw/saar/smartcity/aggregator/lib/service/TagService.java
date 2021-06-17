package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.exception.SensorNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.exception.TagNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Tag service.
 */
@Service
public class TagService {

    private TagRepository tagRepository;

    /**
     * Instantiates a new Tag service.
     *
     * @param tagRepository the tag repository
     */
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    /**
     * Find tag by name tag.
     *
     * @param name the name
     * @return the tag
     */
    public Tag findTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    /**
     * Save tag tag.
     *
     * @param tag the tag
     * @return the tag
     */
    public Tag saveTag(Tag tag) {

        if (tagRepository.findTagByName(tag.getName()) != null)
            throw new TagNameAlreadyInUseException(tag.getName());

        return tagRepository.save(tag);
    }

    public Tag updateTag(Tag tag) {

        return tagRepository.save(tag);
    }

    /**
     * Find tag by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Tag> findTagById(Long id) {
        return tagRepository.findById(id);
    }

    /**
     * Find all tags list.
     *
     * @return the list
     */
    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    /**
     * Delete tag.
     *
     * @param tag the tag
     */
    public void deleteTag(Tag tag) {

        tag.getProducers().forEach(p -> p.getTags().removeIf(t -> t.equals(tag)));
        tag.setProducers(null);
        tagRepository.deleteById(tag.getId());
    }

}
