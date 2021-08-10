package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Location repository.
 */
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    /**
     * Find location by name location.
     *
     * @param name the name
     * @return the location
     */
    Location findLocationByName(String name);

}
