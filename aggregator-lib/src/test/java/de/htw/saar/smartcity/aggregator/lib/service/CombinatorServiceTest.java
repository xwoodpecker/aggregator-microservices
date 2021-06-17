package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Combinator;
import de.htw.saar.smartcity.aggregator.lib.exception.CombinatorNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.CombinatorRepository;
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
class CombinatorServiceTest {

    @Mock
    private CombinatorRepository combinatorRepository;

    @InjectMocks
    private CombinatorService combinatorService;

    private Combinator combinator;

    private List<Combinator> combinators = new ArrayList<>();

    @BeforeEach
    public void init() {
        combinator = new Combinator();
        combinator.setId(1L);
        combinator.setName("test");

        Combinator f = new Combinator();
        f.setId(2L);
        f.setName("another");

        combinators.add(combinator);
        combinators.add(f);
    }


    @Test
    @Order(1)
    public void saveCombinator() {

        Mockito.when(combinatorRepository.findCombinatorByName(ArgumentMatchers.anyString())).thenReturn(null);
        when(combinatorRepository.save(any(Combinator.class))).thenReturn(combinator);

        Combinator saved = combinatorService.saveCombinator(combinator);
        assertThat(saved.getName()).isSameAs(combinator.getName());
        Mockito.verify(combinatorRepository, Mockito.times(1)).save(any(Combinator.class));
        Mockito.verify(combinatorRepository, Mockito.times(1)).findCombinatorByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(combinatorRepository);
    }

    @Test
    @Order(2)
    public void saveCombinatorException() {

        Mockito.when(combinatorRepository.findCombinatorByName(ArgumentMatchers.anyString())).thenReturn(combinator);

        Exception exception = assertThrows(CombinatorNameAlreadyInUseException.class, () -> {
            combinatorService.saveCombinator(combinator);
        });
    }

    @Test
    public void findCombinatorByName() {

        Mockito.when(combinatorRepository.findCombinatorByName(ArgumentMatchers.anyString())).thenReturn(combinator);

        Combinator returned = combinatorService.findCombinatorByName("test");

        Assert.assertEquals(combinator, returned);
        Mockito.verify(combinatorRepository, Mockito.times(1)).findCombinatorByName(ArgumentMatchers.anyString());
        Mockito.verifyNoMoreInteractions(combinatorRepository);
    }

    @Test
    public void findCombinatorById() {

        Mockito.when(combinatorRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(combinator));

        Optional<Combinator> returned = combinatorService.findCombinatorById(1L);

        Assert.assertTrue(returned.isPresent());
        Assert.assertEquals(combinator, returned.get());
        Mockito.verify(combinatorRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verifyNoMoreInteractions(combinatorRepository);
    }

    @Test
    void findAllCombinators() {

        Mockito.when(combinatorRepository.findAll()).thenReturn(combinators);

        List<Combinator> returned = combinatorService.findAllCombinators();

        Assert.assertEquals(returned.size(), combinators.size());
        Mockito.verify(combinatorRepository, Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(combinatorRepository);
    }
}