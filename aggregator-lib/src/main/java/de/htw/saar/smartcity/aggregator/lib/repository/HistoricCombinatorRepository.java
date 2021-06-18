package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.Combinator;
import de.htw.saar.smartcity.aggregator.lib.entity.HistoricCombinator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricCombinatorRepository extends JpaRepository<HistoricCombinator, Long> {

    HistoricCombinator findHistoricCombinatorByName(String name);

}
