package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.Aggregator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Aggregator repository.
 */
@Repository
public interface AggregatorRepository extends JpaRepository<Aggregator, Long> {

    /**
     * Find all by export as metric true list.
     *
     * @return the list
     */
    List<Aggregator> findAllByExportAsMetricTrue();

    /**
     * Find all by data type name and export as metric true list.
     *
     * @param dataTypeName the data type name
     * @return the list
     */
    List<Aggregator> findAllByDataTypeNameAndExportAsMetricTrue(String dataTypeName);

    /**
     * Find all by id between and export as metric true list.
     *
     * @param start the start
     * @param end   the end
     * @return the list
     */
    List<Aggregator> findAllByIdBetweenAndExportAsMetricTrue(Long start, Long end);
}
