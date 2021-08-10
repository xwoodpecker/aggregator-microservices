package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import de.htw.saar.smartcity.aggregator.lib.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The type Location id resolver.
 */
public class LocationIdResolver implements ObjectIdResolver {

    /**
     * the location service
     */
    private final LocationService locationService;

    /**
     * Instantiates a new Location id resolver.
     *
     * @param locationService the location service
     */
    public LocationIdResolver(@Autowired LocationService locationService) {
        this.locationService = locationService;
    }

    @Override
    public void bindItem(ObjectIdGenerator.IdKey idKey, Object o) {

    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey idKey) {
        if(idKey.key instanceof Long)
            return locationService.findLocationById((Long)idKey.key).get();
        else
            return null;
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object o) {
        return this;
    }

    @Override
    public boolean canUseFor(ObjectIdResolver objectIdResolver) {
        return false;
    }
}
