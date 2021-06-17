package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItem;
import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItemValue;
import de.htw.saar.smartcity.aggregator.lib.repository.FormulaItemValueRepository;
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
class FormulaItemValueServiceTest {

    @Mock
    private FormulaItemValueRepository formulaItemValueRepository;

    @InjectMocks
    private FormulaItemValueService formulaItemValueService;

    private FormulaItemValue formulaItemValue;

    private List<FormulaItemValue> formulaItemValues = new ArrayList<>();

    @BeforeEach
    public void init() {

        FormulaItem formulaItem = new FormulaItem();
        formulaItem.setId(1L);
        formulaItem.setName("test");

        FormulaItem fi = new FormulaItem();
        fi.setId(2L);
        fi.setName("another");

        formulaItemValue = new FormulaItemValue();
        formulaItemValue.setId(1L);
        formulaItemValue.setValue("test");
        formulaItemValue.setGroups(new ArrayList<>());
        formulaItemValue.setFormulaItem(formulaItem);

        FormulaItemValue f = new FormulaItemValue();
        f.setId(2L);
        f.setValue("another");
        f.setGroups(new ArrayList<>());
        f.setFormulaItem(fi);

        formulaItemValues.add(formulaItemValue);
        formulaItemValues.add(f);
    }


    @Test
    @Order(1)
    public void saveFormulaItemValue() {

        when(formulaItemValueRepository.save(any(FormulaItemValue.class))).thenReturn(formulaItemValue);

        FormulaItemValue saved = formulaItemValueService.saveFormulaItemValue(formulaItemValue);
        assertThat(saved.getValue()).isSameAs(formulaItemValue.getValue());
        assertThat(saved.getFormulaItem()).isSameAs(formulaItemValue.getFormulaItem());
        Mockito.verify(formulaItemValueRepository, Mockito.times(1)).save(any(FormulaItemValue.class));
        Mockito.verifyNoMoreInteractions(formulaItemValueRepository);
    }

    @Test
    public void findFormulaItemValueById() {

        Mockito.when(formulaItemValueRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(formulaItemValue));

        Optional<FormulaItemValue> returned = formulaItemValueService.findFormulaItemValueById(1L);

        Assert.assertTrue(returned.isPresent());
        Assert.assertEquals(formulaItemValue, returned.get());
        Mockito.verify(formulaItemValueRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(formulaItemValueRepository);
    }

    @Test
    void findAllFormulaItemValues() {

        Mockito.when(formulaItemValueRepository.findAll()).thenReturn(formulaItemValues);

        List<FormulaItemValue> returned = formulaItemValueService.findAllFormulaItemValues();

        Assert.assertEquals(returned.size(), formulaItemValues.size());
        Mockito.verify(formulaItemValueRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(formulaItemValueRepository);
    }
}