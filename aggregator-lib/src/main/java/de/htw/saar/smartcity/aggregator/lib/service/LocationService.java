package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Location;
import de.htw.saar.smartcity.aggregator.lib.entity.Location;
import de.htw.saar.smartcity.aggregator.lib.exception.LocationNameAlreadyInUseException;
import de.htw.saar.smartcity.aggregator.lib.repository.LocationRepository;
import de.htw.saar.smartcity.aggregator.lib.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Location service.
 */
@Service
public class LocationService {

    private LocationRepository locationRepository;


    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

 
    public Location findLocationByName(String name) {
        return locationRepository.findLocationByName(name);
    }

  
    public Location saveLocation(Location location) {

        if (locationRepository.findLocationByName(location.getName()) != null)
            throw new LocationNameAlreadyInUseException(location.getName());

        return locationRepository.save(location);
    }

    public Location updateLocation(Location location) {

        return locationRepository.save(location);
    }

    /**
     * Find location by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Location> findLocationById(Long id) {
        return locationRepository.findById(id);
    }

    /**
     * Find all locations list.
     *
     * @return the list
     */
    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }

    /**
     * Delete location.
     *
     * @param location the location
     */
    /**public void deleteLocation(Location location) {


    }**/

}
