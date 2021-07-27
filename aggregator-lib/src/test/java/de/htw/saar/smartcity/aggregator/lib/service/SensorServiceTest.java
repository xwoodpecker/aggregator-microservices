package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.Location;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.exception.SensorNameAlreadyInUseException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private List<Sensor> sensorsToExport = new ArrayList<>();

    private String testDataTypeName = "testDataType";

    private List<Sensor> sensorsToExportTestDataType = new ArrayList<>();

    @BeforeEach
    public void init() {
        sensor = new Sensor();
        sensor.setId(1L);
        sensor.setName("test");
        DataType dataType = new DataType();
        dataType.setId(1L);
        dataType.setName(testDataTypeName);
        sensor.setDataType(dataType);
        sensor.setObjectStorePath("test/path1");
        sensor.setExportAsMetric(true);
        Location l1 = new Location();
        l1.setId(1L);
        l1.setName("here");
        l1.setX(2.0);
        l1.setY(5.0);
        sensor.setLocation(l1);
        sensor.setInformation("some infos");
        sensor.setGroups(new ArrayList<>());
        sensor.setTags(new ArrayList<>());


        Sensor s = new Sensor();
        s.setId(2L);
        s.setName("another");
        s.setDataType(dataType);
        s.setObjectStorePath("test/path2");
        s.setExportAsMetric(false);
        Location l2 = new Location();
        l2.setId(1L);
        l2.setName("there");
        l2.setX(12.0);
        l2.setY(15.0);
        s.setLocation(l2);
        s.setInformation("other infos");
        s.setGroups(new ArrayList<>());
        s.setTags(new ArrayList<>());

        Sensor s2 = new Sensor();
        s2.setId(3L);
        s2.setName("and another one");
        s2.setDataType(dataType);
        s2.setObjectStorePath("test/path3");
        s2.setExportAsMetric(false);
        s2.setLocation(l2);
        s2.setInformation("other infos");
        s2.setGroups(new ArrayList<>());
        s2.setTags(new ArrayList<>());

        sensors.add(sensor);
        sensors.add(s);
        sensors.add(s2);

        sensorsToExportTestDataType.add(sensor);

        sensorsToExport.add(sensor);
    }


    @Test
    @Order(1)
    public void saveSensor() {

        Mockito.when(sensorRepository.findSensorByName(ArgumentMatchers.anyString())).thenReturn(null);
        when(sensorRepository.save(any(Sensor.class))).thenReturn(sensor);

        Sensor saved = sensorService.saveSensor(sensor);
        assertThat(saved.getObjectStorePath()).isSameAs(sensor.getObjectStorePath());
        assertThat(saved.getExportAsMetric()).isSameAs(sensor.getExportAsMetric());
        assertThat(saved.getDataType()).isSameAs(sensor.getDataType());
        assertThat(saved.getName()).isSameAs(sensor.getName());
        assertThat(saved.getInformation()).isSameAs(sensor.getInformation());
        assertThat(saved.getLocation()).isSameAs(sensor.getLocation());
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


    @Test
    void findAllSensorsToExport() {

        Mockito.when(sensorRepository.findAllByExportAsMetricTrue()).thenReturn(sensorsToExport);

        List<Sensor> returned = sensorService.findAllSensorsToExport();

        Assert.assertEquals(returned.size(), sensorsToExport.size());
        Mockito.verify(sensorRepository, Mockito.times(1)).findAllByExportAsMetricTrue();
        Mockito.verifyNoMoreInteractions(sensorRepository);
    }


    @Test
    void findAllSensorsToExportByDataTypeName() {
        Mockito.when(sensorRepository.findAllByDataTypeNameAndExportAsMetricTrue(testDataTypeName)).thenReturn(sensorsToExportTestDataType);

        List<Sensor> returned = sensorService.findAllSensorsToExportByDataTypeName(testDataTypeName);

        Assert.assertEquals(returned.size(), sensorsToExportTestDataType.size());
        Mockito.verify(sensorRepository, Mockito.times(1)).findAllByDataTypeNameAndExportAsMetricTrue(testDataTypeName);
        Mockito.verifyNoMoreInteractions(sensorRepository);
    }

    @Test
    void findAllSensorsToExportByIdBetween() {
        Mockito.when(sensorRepository.findAllByIdBetweenAndExportAsMetricTrue(1L, 2L)).thenReturn(sensorsToExport);

        List<Sensor> returned = sensorService.findAllSensorsToExportByIdBetween(1L, 2L);

        Assert.assertEquals(returned.size(), sensorsToExport.size());
        Mockito.verify(sensorRepository, Mockito.times(1)).findAllByIdBetweenAndExportAsMetricTrue(1L, 2L);
        Mockito.verifyNoMoreInteractions(sensorRepository);
    }
}