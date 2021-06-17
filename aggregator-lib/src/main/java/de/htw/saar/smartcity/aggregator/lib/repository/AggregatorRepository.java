package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggregatorRepository extends JpaRepository<Aggregator, Long> {

    List<Aggregator> findAllByExportAsMetricTrue();
}
