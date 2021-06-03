package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findTagByName(String name) {
        return tagRepository.findTagByName(name);
    }

    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Optional<Tag> findTagById(Long id) {
        return tagRepository.findById(id);
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }

    public void deleteTag(Tag tag) {

        tag.getProducers().forEach(p -> p.getTags().removeIf(t -> t.equals(tag)));
        tag.setProducers(null);
        tagRepository.deleteById(tag.getId());
    }
}
