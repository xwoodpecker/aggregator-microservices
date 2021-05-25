package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Combinator;
import de.htw.saar.smartcity.aggregator.lib.repository.CombinatorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CombinatorService {
    
    private CombinatorRepository combinatorRepository;

    public CombinatorService(CombinatorRepository combinatorRepository) {
        this.combinatorRepository = combinatorRepository;
    }

    public Combinator findCombinatorByName(String name) {
        return combinatorRepository.findCombinatorByName(name);
    }

    public Combinator saveCombinator(Combinator combinator) {
        return combinatorRepository.save(combinator);
    }

    public Optional<Combinator> findCombinatorById(Long id) {
        return combinatorRepository.findById(id);
    }
}
