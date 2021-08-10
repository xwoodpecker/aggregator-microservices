package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Location;
import de.htw.saar.smartcity.aggregator.lib.exception.LocationNameAlreadyInUseException;
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


    /**
     * Instantiates a new Location service.
     *
     * @param locationRepository the location repository
     */
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    /**
     * Find location by name location.
     *
     * @param name the name
     * @return the location
     */
    public Location findLocationByName(String name) {
        return locationRepository.findLocationByName(name);
    }


    /**
     * Save location
     *
     * @param location the location
     * @return the location
     */
    public Location saveLocation(Location location) {

        if (locationRepository.findLocationByName(location.getName()) != null)
            throw new LocationNameAlreadyInUseException(location.getName());

        return locationRepository.save(location);
    }

    /**
     * Update location
     *
     * @param location the location
     * @return the location
     */
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
    public void deleteLocation(Location location) {

        location.getProducers().forEach(p -> p.setLocation(null));
        location.setProducers(null);
        locationRepository.deleteById(location.getId());

    }

}
