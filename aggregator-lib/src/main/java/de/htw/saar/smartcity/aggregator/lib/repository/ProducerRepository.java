package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Producer repository.
 */
@Repository
public interface ProducerRepository extends JpaRepository<Producer, Long> {

    /**
     * Find all by data type list.
     *
     * @param dataType the data type
     * @return the list
     */
    List<Producer> findAllByDataType(DataType dataType);
}
