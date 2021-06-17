package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.*;
import de.htw.saar.smartcity.aggregator.lib.exception.SensorNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.exception.TagNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository;
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
class SensorServiceTest {

    @Mock
    private SensorRepository sensorRepository;

    @InjectMocks
    private SensorService sensorService;

    private Sensor sensor;

    private List<Sensor> sensors = new ArrayList<>();

    @BeforeEach
    public void init() {
        sensor = new Sensor();
        sensor.setId(1L);
        sensor.setName("test");
        DataType dataType = new DataType();
        dataType.setId(1L);
        dataType.setName("testDataType");
        sensor.setDataType(dataType);
        sensor.setObjectStorePath("test/path1");
        sensor.setLocation("here");
        sensor.setX(12.0);
        sensor.setY(15.0);
        sensor.setInformation("some infos");
        sensor.setGroups(new ArrayList<>());
        sensor.setTags(new ArrayList<>());


        Sensor s = new Sensor();
        s.setId(2L);
        s.setName("another");
        s.setDataType(dataType);
        s.setObjectStorePath("test/path2");
        s.setLocation("here");
        s.setX(2.0);
        s.setY(5.0);
        s.setInformation("other infos");
        s.setGroups(new ArrayList<>());
        s.setTags(new ArrayList<>());

        sensors.add(sensor);
        sensors.add(s);
    }


    @Test
    @Order(1)
    public void saveSensor() {

        Mockito.when(sensorRepository.findSensorByName(ArgumentMatchers.anyString())).thenReturn(null);
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensor);

        Sensor saved = sensorService.saveSensor(sensor);
        assertThat(saved.getObjectStorePath()).isSameAs(sensor.getObjectStorePath());
        assertThat(saved.getDataType()).isSameAs(sensor.getDataType());
        assertThat(saved.getName()).isSameAs(sensor.getName());
        assertThat(saved.getInformation()).isSameAs(sensor.getInformation());
        assertThat(saved.getLocation()).isSameAs(sensor.getLocation());
        assertThat(saved.getX()).isSameAs(sensor.getX());
        assertThat(saved.getY()).isSameAs(sensor.getY());
        Mockito.verify(sensorRepository, Mockito.times(1)).save(any(Sensor.class));
        Mockito.verify(sensorRepository, Mockito.times(1)).findSensorByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(sensorRepository);
    }

    @Test
    @Order(2)
    public void saveSensorException() {

        Mockito.when(sensorRepository.findSensorByName(ArgumentMatchers.anyString())).thenReturn(sensor);

        Exception exception = assertThrows(SensorNameAlreadyInUseException.class, () -> {
            sensorService.saveSensor(sensor);
        });
    }



    @Test
    public void findSensorById() {

        Mockito.when(sensorRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(sensor));

        Optional<Sensor> returned = sensorService.findSensorById(1L);

        Assert.assertTrue(returned.isPresent());
        Assert.assertEquals(sensor, returned.get());
        Mockito.verify(sensorRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(sensorRepository);
    }

    @Test
    void findAllSensors() {

        Mockito.when(sensorRepository.findAll()).thenReturn(sensors);

        List<Sensor> returned = sensorService.findAllSensors();

        Assert.assertEquals(returned.size(), sensors.size());
        Mockito.verify(sensorRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(sensorRepository);
    }
}