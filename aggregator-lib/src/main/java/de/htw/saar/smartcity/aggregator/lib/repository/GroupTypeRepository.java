package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Group type repository.
 */
@Repository
public interface GroupTypeRepository extends JpaRepository<GroupType, Long> {

    /**
     * Find group type by name group type.
     *
     * @param name the name
     * @return the group type
     */
    GroupType findGroupTypeByName(String name);

}