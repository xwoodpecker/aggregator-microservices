package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import de.htw.saar.smartcity.aggregator.lib.exception.GroupTypeNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.GroupTypeRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class GroupTypeServiceTest {

    @Mock
    private GroupTypeRepository groupTypeRepository;

    @InjectMocks
    private GroupTypeService groupTypeService;

    private GroupType groupType;

    private List<GroupType> groupTypes = new ArrayList<>();

    @BeforeEach
    public void init() {
        groupType = new GroupType();
        groupType.setId(1L);
        groupType.setName("test");

        GroupType g = new GroupType();
        g.setId(2L);
        g.setName("another");

        groupTypes.add(groupType);
        groupTypes.add(g);
    }


    @Test
    @Order(1)
    public void saveGroupType() {

        Mockito.when(groupTypeRepository.findGroupTypeByName(ArgumentMatchers.anyString())).thenReturn(null);
        when(groupTypeRepository.save(any(GroupType.class))).thenReturn(groupType);

        GroupType saved = groupTypeService.saveGroupType(groupType);
        assertThat(saved.getName()).isSameAs(groupType.getName());
        Mockito.verify(groupTypeRepository, Mockito.times(1)).save(any(GroupType.class));
        Mockito.verify(groupTypeRepository, Mockito.times(1)).findGroupTypeByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(groupTypeRepository);
    }

    @Test
    @Order(2)
    public void saveGroupTypeException() {

        Mockito.when(groupTypeRepository.findGroupTypeByName(ArgumentMatchers.anyString())).thenReturn(groupType);

        Exception exception = assertThrows(GroupTypeNameAlreadyInUseException.class, () -> {
            groupTypeService.saveGroupType(groupType);
        });
    }

    @Test
    public void findGroupTypeByName() {

        Mockito.when(groupTypeRepository.findGroupTypeByName(ArgumentMatchers.anyString())).thenReturn(groupType);

        GroupType returned = groupTypeService.findGroupTypeByName("test");

        Assert.assertEquals(groupType, returned);
        Mockito.verify(groupTypeRepository, Mockito.times(1)).findGroupTypeByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(groupTypeRepository);
    }

    @Test
    public void findGroupTypeById() {

        Mockito.when(groupTypeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(groupType));

        Optional<GroupType> returned = groupTypeService.findGroupTypeById(1L);

        Assert.assertTrue(returned.isPresent());
        Assert.assertEquals(groupType, returned.get());
        Mockito.verify(groupTypeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(groupTypeRepository);
    }

    @Test
    void findAllGroupTypes() {

        Mockito.when(groupTypeRepository.findAll()).thenReturn(groupTypes);

        List<GroupType> returned = groupTypeService.findAllGroupTypes();

        Assert.assertEquals(returned.size(), groupTypes.size());
        Mockito.verify(groupTypeRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(groupTypeRepository);
    }
}