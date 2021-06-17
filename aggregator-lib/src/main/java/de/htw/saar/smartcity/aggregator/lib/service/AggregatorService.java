package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.repository.AggregatorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Aggregator service.
 */
@Service
public class AggregatorService {
    
    private AggregatorRepository aggregatorRepository;


    /**
     * Instantiates a new Aggregator service.
     *
     * @param aggregatorRepository the aggregator repository
     */
    public AggregatorService(AggregatorRepository aggregatorRepository) {
        this.aggregatorRepository = aggregatorRepository;
    }

    /**
     * Save aggregator aggregator.
     *
     * @param aggregator the aggregator
     * @return the aggregator
     */
    public Aggregator saveAggregator(Aggregator aggregator) {
        return aggregatorRepository.save(aggregator);
    }

    /**
     * Find aggregator by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Aggregator> findAggregatorById(Long id) {
        return aggregatorRepository.findById(id);
    }

    /**
     * Find all aggregators list.
     *
     * @return the list
     */
    public List<Aggregator> findAllAggregators() {
        return aggregatorRepository.findAll();
    }


    /**
     * Find all aggregators to export list.
     *
     * @return the list
     */
    public List<Aggregator> findAllAggregatorsToExport() {
        return aggregatorRepository.findAllByExportAsMetricTrue();
    }

    /**
     * Delete aggregator.
     *
     * @param aggregator the aggregator
     */
    public void deleteAggregator(Aggregator aggregator) {

        aggregator.getGroups().forEach(g -> g.getProducers().removeIf(a -> a.equals(aggregator)));
        aggregator.setGroups(null);
        aggregator.getTags().forEach(t -> t.getProducers().removeIf(a -> a.equals(aggregator)));
        aggregator.setGroups(null);
        if(aggregator.getOwnerGroup() != null) {
            aggregator.getOwnerGroup().getAggregators().removeIf(a -> a.equals(aggregator));
            aggregator.setOwnerGroup(null);
        }
        aggregatorRepository.deleteById(aggregator.getId());
    }

}
