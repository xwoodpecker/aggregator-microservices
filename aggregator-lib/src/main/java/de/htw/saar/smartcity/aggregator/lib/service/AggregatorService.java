package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.repository.AggregatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AggregatorService {
    
    private AggregatorRepository aggregatorRepository;


    public AggregatorService(AggregatorRepository aggregatorRepository) {
        this.aggregatorRepository = aggregatorRepository;
    }

    public Aggregator saveAggregator(Aggregator aggregator) {
        return aggregatorRepository.save(aggregator);
    }

    public Optional<Aggregator> findAggregatorById(Long id) {
        return aggregatorRepository.findById(id);
    }

    public List<Aggregator> findAllAggregators() {
        return aggregatorRepository.findAll();
    }

    public void deleteAggregator(Aggregator aggregator) {

        aggregator.getGroups().forEach(g -> g.getProducers().removeIf(a -> a.equals(aggregator)));
        aggregator.getTags().forEach(t -> t.getProducers().removeIf(a -> a.equals(aggregator)));
        //aggregator.getCombinator().getAggregators().removeIf(a -> a.equals(aggregator));
        aggregator.getOwnerGroup().getAggregators().removeIf(a -> a.equals(aggregator));
        aggregatorRepository.deleteById(aggregator.getId());
    }
}
