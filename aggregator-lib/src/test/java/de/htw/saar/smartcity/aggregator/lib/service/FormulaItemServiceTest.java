package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.FormulaItem;
import de.htw.saar.smartcity.aggregator.lib.exception.FormulaItemNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.FormulaItemRepository;
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
class FormulaItemServiceTest {

    @Mock
    private FormulaItemRepository formulaItemRepository;

    @InjectMocks
    private FormulaItemService formulaItemService;

    private FormulaItem formulaItem;

    private List<FormulaItem> formulaItems = new ArrayList<>();

    @BeforeEach
    public void init() {
        formulaItem = new FormulaItem();
        formulaItem.setId(1L);
        formulaItem.setName("test");

        FormulaItem f = new FormulaItem();
        f.setId(2L);
        f.setName("another");

        formulaItems.add(formulaItem);
        formulaItems.add(f);
    }


    @Test
    @Order(1)
    public void saveFormulaItem() {

        Mockito.when(formulaItemRepository.findFormulaItemByName(ArgumentMatchers.anyString())).thenReturn(null);
        when(formulaItemRepository.save(any(FormulaItem.class))).thenReturn(formulaItem);

        FormulaItem saved = formulaItemService.saveFormulaItem(formulaItem);
        assertThat(saved.getName()).isSameAs(formulaItem.getName());
        Mockito.verify(formulaItemRepository, Mockito.times(1)).save(any(FormulaItem.class));
        Mockito.verify(formulaItemRepository, Mockito.times(1)).findFormulaItemByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(formulaItemRepository);
    }

    @Test
    @Order(2)
    public void saveFormulaItemException() {

        Mockito.when(formulaItemRepository.findFormulaItemByName(ArgumentMatchers.anyString())).thenReturn(formulaItem);

        Exception exception = assertThrows(FormulaItemNameAlreadyInUseException.class, () -> {
            formulaItemService.saveFormulaItem(formulaItem);
        });
    }

    @Test
    public void findFormulaItemByName() {

        Mockito.when(formulaItemRepository.findFormulaItemByName(ArgumentMatchers.anyString())).thenReturn(formulaItem);

        FormulaItem returned = formulaItemService.findFormulaItemByName("test");

        Assert.assertEquals(formulaItem, returned);
        Mockito.verify(formulaItemRepository, Mockito.times(1)).findFormulaItemByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(formulaItemRepository);
    }

    @Test
    public void findFormulaItemById() {

        Mockito.when(formulaItemRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(formulaItem));

        Optional<FormulaItem> returned = formulaItemService.findFormulaItemById(1L);

        Assert.assertTrue(returned.isPresent());
        Assert.assertEquals(formulaItem, returned.get());
        Mockito.verify(formulaItemRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(formulaItemRepository);
    }

    @Test
    void findAllFormulaItems() {

        Mockito.when(formulaItemRepository.findAll()).thenReturn(formulaItems);

        List<FormulaItem> returned = formulaItemService.findAllFormulaItems();

        Assert.assertEquals(returned.size(), formulaItems.size());
        Mockito.verify(formulaItemRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(formulaItemRepository);
    }
}