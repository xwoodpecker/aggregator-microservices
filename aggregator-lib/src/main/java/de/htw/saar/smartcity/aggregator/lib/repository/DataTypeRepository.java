package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Data type repository.
 */
@Repository
public interface DataTypeRepository extends JpaRepository<DataType, Long> {

    /**
     * Find data type by name data type.
     *
     * @param name the name
     * @return the data type
     */
    DataType findDataTypeByName(String name);

}

