package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.*;
import de.htw.saar.smartcity.aggregator.lib.repository.ProducerRepository;
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
class ProducerServiceTest {

    @Mock
    private ProducerRepository producerRepository;

    @InjectMocks
    private ProducerService producerService;

    private Aggregator producer;

    private List<Producer> producers = new ArrayList<>();

    @BeforeEach
    public void init() {
        producer = new Aggregator();
        producer.setId(1L);
        Group ownerGroup = new Group();
        ownerGroup.setId(1L);
        ownerGroup.setName("testGroup");
        producer.setOwnerGroup(ownerGroup);
        Combinator combinator = new Combinator();
        combinator.setId(1L);
        combinator.setName("testCombinator");
        producer.setCombinator(combinator);
        DataType dataType = new DataType();
        dataType.setId(1L);
        dataType.setName("testDataType");
        producer.setDataType(dataType);
        producer.setObjectStorePath("test");
        producer.setGroups(new ArrayList<>());
        producer.setTags(new ArrayList<>());

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
        a.setGroups(new ArrayList<>());
        a.setTags(new ArrayList<>());

        Sensor s = new Sensor();
        s.setId(3L);
        s.setName("testSensor");
        s.setDataType(d);
        s.setObjectStorePath("test");
        Location l = new Location();
        l.setId(1L);
        l.setName("here");
        l.setX(2.0);
        l.setY(5.0);
        s.setLocation(l);
        s.setInformation("infos");
        s.setGroups(new ArrayList<>());
        s.setTags(new ArrayList<>());

        producers.add(producer);
        producers.add(a);
        producers.add(s);
    }


    @Test
    @Order(1)
    public void saveProducer() {

        when(producerRepository.save(any(Producer.class))).thenReturn(producer);

        Producer saved = producerService.saveProducer(producer);
        assertThat(saved.getObjectStorePath()).isSameAs(producer.getObjectStorePath());
        assertThat(saved.getDataType()).isSameAs(producer.getDataType());
        Mockito.verify(producerRepository, Mockito.times(1)).save(any(Producer.class));
        Mockito.verifyNoMoreInteractions(producerRepository);
    }


    @Test
    public void findProducerById() {

        Mockito.when(producerRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(producer));

        Optional<Producer> returned = producerService.findProducerById(1L);

        Assert.assertTrue(returned.isPresent());
        Assert.assertEquals(producer, returned.get());
        Mockito.verify(producerRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(producerRepository);
    }

    @Test
    void findAllProducers() {

        Mockito.when(producerRepository.findAll()).thenReturn(producers);

        List<Producer> returned = producerService.findAllProducers();

        Assert.assertEquals(returned.size(), producers.size());
        Mockito.verify(producerRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(producerRepository);
    }
}