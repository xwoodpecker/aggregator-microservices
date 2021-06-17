package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.exception.DataTypeNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository;
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
class DataTypeServiceTest {
    
    @Mock
    private DataTypeRepository dataTypeRepository;

    @InjectMocks
    private DataTypeService dataTypeService;

    private DataType dataType;

    private List<DataType> dataTypes = new ArrayList<>();

    @BeforeEach
    public void init() {
        dataType = new DataType();
        dataType.setId(1L);
        dataType.setName("test");
        dataType.setUnit("test");

        DataType d = new DataType();
        d.setId(2L);
        d.setName("another");
        d.setUnit("another");

        dataTypes.add(dataType);
        dataTypes.add(d);
    }


    @Test
    @Order(1)
    public void saveDataType() {

        Mockito.when(dataTypeRepository.findDataTypeByName(ArgumentMatchers.anyString())).thenReturn(null);
        when(dataTypeRepository.save(any(DataType.class))).thenReturn(dataType);

        DataType saved = dataTypeService.saveDataType(dataType);
        assertThat(saved.getName()).isSameAs(dataType.getName());
        Mockito.verify(dataTypeRepository, Mockito.times(1)).save(any(DataType.class));
        Mockito.verify(dataTypeRepository, Mockito.times(1)).findDataTypeByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(dataTypeRepository);
    }

    @Test
    @Order(2)
    public void saveDataTypeException() {

        Mockito.when(dataTypeRepository.findDataTypeByName(ArgumentMatchers.anyString())).thenReturn(dataType);

        Exception exception = assertThrows(DataTypeNameAlreadyInUseException.class, () -> {
            dataTypeService.saveDataType(dataType);
        });
    }

    @Test
    public void findDataTypeByName() {

        Mockito.when(dataTypeRepository.findDataTypeByName(ArgumentMatchers.anyString())).thenReturn(dataType);

        DataType returned = dataTypeService.findDataTypeByName("test");

        Assert.assertEquals(dataType, returned);
        Mockito.verify(dataTypeRepository, Mockito.times(1)).findDataTypeByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(dataTypeRepository);
    }

    @Test
    public void findDataTypeById() {

        Mockito.when(dataTypeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(dataType));

        Optional<DataType> returned = dataTypeService.findDataTypeById(1L);

        Assert.assertTrue(returned.isPresent());
        Assert.assertEquals(dataType, returned.get());
        Mockito.verify(dataTypeRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(dataTypeRepository);
    }

    @Test
    void findAllDataTypes() {

        Mockito.when(dataTypeRepository.findAll()).thenReturn(dataTypes);

        List<DataType> returned = dataTypeService.findAllDataTypes();

        Assert.assertEquals(returned.size(), dataTypes.size());
        Mockito.verify(dataTypeRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(dataTypeRepository);
    }
}