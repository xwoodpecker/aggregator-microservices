package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.HistoricCombinator;
import de.htw.saar.smartcity.aggregator.lib.exception.HistoricCombinatorNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.HistoricCombinatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type HistoricCombinator service.
 */
@Service
public class HistoricCombinatorService {
    
    private HistoricCombinatorRepository historicCombinatorRepository;

    /**
     * Instantiates a new HistoricCombinator service.
     *
     * @param historicCombinatorRepository the historicCombinator repository
     */
    public HistoricCombinatorService(HistoricCombinatorRepository historicCombinatorRepository) {
        this.historicCombinatorRepository = historicCombinatorRepository;
    }

    /**
     * Find historicCombinator by name historicCombinator.
     *
     * @param name the name
     * @return the historicCombinator
     */
    public HistoricCombinator findHistoricCombinatorByName(String name) {
        return historicCombinatorRepository.findHistoricCombinatorByName(name);
    }

    /**
     * Save historicCombinator historicCombinator.
     *
     * @param historicCombinator the historicCombinator
     * @return the historicCombinator
     */
    public HistoricCombinator saveHistoricCombinator(HistoricCombinator historicCombinator) {

        if (historicCombinatorRepository.findHistoricCombinatorByName(historicCombinator.getName()) != null)
            throw new HistoricCombinatorNameAlreadyInUseException(historicCombinator.getName());

        return historicCombinatorRepository.save(historicCombinator);
    }

    /**
     * Find historicCombinator by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<HistoricCombinator> findHistoricCombinatorById(Long id) {
        return historicCombinatorRepository.findById(id);
    }

    /**
     * Find all historicCombinators list.
     *
     * @return the list
     */
    public List<HistoricCombinator> findAllHistoricCombinators() {
        return historicCombinatorRepository.findAll();
    }
}
