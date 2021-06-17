package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import de.htw.saar.smartcity.aggregator.lib.exception.GroupNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.GroupRepository;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;

    @InjectMocks
    private GroupService groupService;

    private Group group;

    private List<Group> groups = new ArrayList<>();

    @BeforeEach
    public void init() {
        group = new Group();
        group.setId(1L);
        group.setName("test");
        GroupType groupType = new GroupType();
        groupType.setId(1L);
        groupType.setName("testGroupType");
        group.setGroupType(groupType);
        group.setActive(true);
        group.setValues(new ArrayList<>());
        group.setProducers(new ArrayList<>());
        group.setAggregators(new ArrayList<>());


        Group s = new Group();
        s.setId(2L);
        s.setName("another");
        group.setGroupType(groupType);
        group.setActive(false);
        group.setValues(new ArrayList<>());
        group.setProducers(new ArrayList<>());
        group.setAggregators(new ArrayList<>());

        groups.add(group);
        groups.add(s);
    }


    @Test
    @Order(1)
    public void saveGroup() {

        Mockito.when(groupRepository.findGroupByName(ArgumentMatchers.anyString())).thenReturn(null);
        when(groupRepository.save(any(Group.class))).thenReturn(group);

        Group saved = groupService.saveGroup(group);
        assertThat(saved.getName()).isSameAs(group.getName());
        assertThat(saved.getGroupType()).isSameAs(group.getGroupType());
        Mockito.verify(groupRepository, Mockito.times(1)).save(any(Group.class));
        Mockito.verify(groupRepository, Mockito.times(1)).findGroupByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(groupRepository);
    }

    @Test
    @Order(2)
    public void saveGroupException() {

        Mockito.when(groupRepository.findGroupByName(ArgumentMatchers.anyString())).thenReturn(group);

        Exception exception = assertThrows(GroupNameAlreadyInUseException.class, () -> {
            groupService.saveGroup(group);
        });
    }



    @Test
    public void findGroupById() {

        Mockito.when(groupRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(group));

        Optional<Group> returned = groupService.findGroupById(1L);

        Assert.assertTrue(returned.isPresent());
        Assert.assertEquals(group, returned.get());
        Mockito.verify(groupRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(groupRepository);
    }

    @Test
    void findAllGroups() {

        Mockito.when(groupRepository.findAll()).thenReturn(groups);

        List<Group> returned = groupService.findAllGroups();

        Assert.assertEquals(returned.size(), groups.size());
        Mockito.verify(groupRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(groupRepository);
    }
}