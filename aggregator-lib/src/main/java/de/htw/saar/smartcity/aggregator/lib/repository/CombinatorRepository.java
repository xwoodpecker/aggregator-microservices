package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.Combinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Combinator repository.
 */
@Repository
public interface CombinatorRepository extends JpaRepository<Combinator, Long> {

    /**
     * Find combinator by name combinator.
     *
     * @param name the name
     * @return the combinator
     */
    Combinator findCombinatorByName(String name);

}
