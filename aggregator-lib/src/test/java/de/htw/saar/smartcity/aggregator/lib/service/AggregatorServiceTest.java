package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.Combinator;
import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import de.htw.saar.smartcity.aggregator.lib.repository.AggregatorRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class AggregatorServiceTest {

    @Mock
    private AggregatorRepository aggregatorRepository;

    @InjectMocks
    private AggregatorService aggregatorService;

    private Aggregator aggregator;

    private List<Aggregator> aggregators = new ArrayList<>();

    private List<Aggregator> aggregatorsToExport = new ArrayList<>();

    @BeforeEach
    public void init() {
        aggregator = new Aggregator();
        aggregator.setId(1L);
        Group ownerGroup = new Group();
        ownerGroup.setId(1L);
        ownerGroup.setName("testGroup");
        aggregator.setOwnerGroup(ownerGroup);
        Combinator combinator = new Combinator();
        combinator.setId(1L);
        combinator.setName("testCombinator");
        aggregator.setCombinator(combinator);
        DataType dataType = new DataType();
        dataType.setId(1L);
        dataType.setName("testDataType");
        aggregator.setDataType(dataType);
        aggregator.setObjectStorePath("test");
        aggregator.setExportAsMetric(true);
        aggregator.setGroups(new ArrayList<>());
        aggregator.setTags(new ArrayList<>());

        Aggregator a = new Aggregator();
        a.setId(1L);
        Group o = new Group();
        o.setId(2L);
        o.setName("anotherGroup");
        a.setOwnerGroup(o);
        Combinator c = new Combinator();
        c.setId(2L);
        c.setName("anotherCombinator");
        a.setCombinator(c);
        DataType d = new DataType();
        d.setId(2L);
        d.setName("anotherDataType");
        a.setDataType(d);
        a.setObjectStorePath("test");
        a.setExportAsMetric(false);
        a.setGroups(new ArrayList<>());
        a.setTags(new ArrayList<>());

        aggregators.add(aggregator);
        aggregators.add(a);

        aggregatorsToExport.add(aggregator);

    }


    @Test
    @Order(1)
    public void saveAggregator() {

        when(aggregatorRepository.save(any(Aggregator.class))).thenReturn(aggregator);

        Aggregator saved = aggregatorService.saveAggregator(aggregator);
        assertThat(saved.getCombinator()).isSameAs(aggregator.getCombinator());
        assertThat(saved.getObjectStorePath()).isSameAs(aggregator.getObjectStorePath());
        assertThat(saved.getCombinator()).isSameAs(aggregator.getCombinator());
        assertThat(saved.getOwnerGroup()).isSameAs(aggregator.getOwnerGroup());
        assertThat(saved.getDataType()).isSameAs(aggregator.getDataType());
        assertThat(saved.getExportAsMetric()).isSameAs(aggregator.getExportAsMetric());
        Mockito.verify(aggregatorRepository, Mockito.times(1)).save(any(Aggregator.class));
        Mockito.verifyNoMoreInteractions(aggregatorRepository);
    }


    @Test
    public void findAggregatorById() {

        Mockito.when(aggregatorRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(aggregator));

        Optional<Aggregator> returned = aggregatorService.findAggregatorById(1L);

        Assert.assertTrue(returned.isPresent());
        Assert.assertEquals(aggregator, returned.get());
        Mockito.verify(aggregatorRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(aggregatorRepository);
    }

    @Test
    void findAllAggregators() {

        Mockito.when(aggregatorRepository.findAll()).thenReturn(aggregators);

        List<Aggregator> returned = aggregatorService.findAllAggregators();

        Assert.assertEquals(returned.size(), aggregators.size());
        Mockito.verify(aggregatorRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(aggregatorRepository);
    }


    @Test
    void findAllAggregatorsToExport() {

        Mockito.when(aggregatorRepository.findAllByExportAsMetricTrue()).thenReturn(aggregatorsToExport);

        List<Aggregator> returned = aggregatorService.findAllAggregatorsToExport();

        Assert.assertEquals(returned.size(), aggregatorsToExport.size());
        Mockito.verify(aggregatorRepository, Mockito.times(1)).findAllByExportAsMetricTrue();
        Mockito.verifyNoMoreInteractions(aggregatorRepository);
    }
}