package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.repository.TagRepository;
import org.checkerframework.checker.nullness.Opt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    private Tag tag;

    private List<Tag> tags = new ArrayList<>();

    @BeforeEach
    public void init() {
        tag = new Tag();
        tag.setId(1L);
        tag.setName("test");
        tag.setProducers(new ArrayList<>());

        Tag t = new Tag();
        t.setId(2L);
        t.setName("another");
        t.setProducers(new ArrayList<>());

        tags.add(tag);
        tags.add(t);
    }

    @Test
    public void saveTag() {

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        Tag saved = tagService.saveTag(tag);
        assertThat(saved.getName()).isSameAs(tag.getName());
        Mockito.verify(tagRepository, Mockito.times(1)).save(any(Tag.class));
        Mockito.verifyNoMoreInteractions(tagRepository);
    }

    @Test
    public void findTagByName() {

        Mockito.when(tagRepository.findTagByName(ArgumentMatchers.anyString())).thenReturn(tag);

        Tag returned = tagService.findTagByName("test");

        Assert.assertEquals(tag, returned);
        Mockito.verify(tagRepository, Mockito.times(1)).findTagByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(tagRepository);
    }

    @Test
    public void findTagById() {

        Mockito.when(tagRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(tag));

        Optional<Tag> returned = tagService.findTagById(1L);

        Assert.assertTrue(returned.isPresent());
        Assert.assertEquals(tag, returned.get());
        Mockito.verify(tagRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(tagRepository);
    }

    @Test
    void findAllTags() {

        Mockito.when(tagRepository.findAll()).thenReturn(tags);

        List<Tag> returned = tagService.findAllTags();

        Assert.assertEquals(returned.size(), tags.size());
        Mockito.verify(tagRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(tagRepository);
    }

    //delete cant be tested ?
}