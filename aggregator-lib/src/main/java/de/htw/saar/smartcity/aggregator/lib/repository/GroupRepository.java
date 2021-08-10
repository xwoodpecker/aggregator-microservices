package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Group repository.
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    /**
     * Find group by name group.
     *
     * @param name the name
     * @return the group
     */
    Group findGroupByName(String name);

    /**
     * Find all by active true list.
     *
     * @return the list
     */
    List<Group> findAllByActiveTrue();
}
