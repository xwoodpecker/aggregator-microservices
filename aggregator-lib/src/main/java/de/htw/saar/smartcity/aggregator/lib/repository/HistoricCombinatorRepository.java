package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.HistoricCombinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Historic combinator repository.
 */
@Repository
public interface HistoricCombinatorRepository extends JpaRepository<HistoricCombinator, Long> {

    /**
     * Find historic combinator by name historic combinator.
     *
     * @param name the name
     * @return the historic combinator
     */
    HistoricCombinator findHistoricCombinatorByName(String name);

}
