package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Combinator;
import de.htw.saar.smartcity.aggregator.lib.exception.CombinatorNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.CombinatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Combinator service.
 */
@Service
public class CombinatorService {
    
    private CombinatorRepository combinatorRepository;

    /**
     * Instantiates a new Combinator service.
     *
     * @param combinatorRepository the combinator repository
     */
    public CombinatorService(CombinatorRepository combinatorRepository) {
        this.combinatorRepository = combinatorRepository;
    }

    /**
     * Find combinator by name combinator.
     *
     * @param name the name
     * @return the combinator
     */
    public Combinator findCombinatorByName(String name) {
        return combinatorRepository.findCombinatorByName(name);
    }

    /**
     * Save combinator combinator.
     *
     * @param combinator the combinator
     * @return the combinator
     */
    public Combinator saveCombinator(Combinator combinator) {

        if (combinatorRepository.findCombinatorByName(combinator.getName()) != null)
            throw new CombinatorNameAlreadyInUseException(combinator.getName());

        return combinatorRepository.save(combinator);
    }

    /**
     * Find combinator by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Combinator> findCombinatorById(Long id) {
        return combinatorRepository.findById(id);
    }

    /**
     * Find all combinators list.
     *
     * @return the list
     */
    public List<Combinator> findAllCombinators() {
        return combinatorRepository.findAll();
    }
}
