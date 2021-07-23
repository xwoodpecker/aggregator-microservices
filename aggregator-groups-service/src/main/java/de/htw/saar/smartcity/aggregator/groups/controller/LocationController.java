package de.htw.saar.smartcity.aggregator.groups.controller;

import de.htw.saar.smartcity.aggregator.groups.exception.LocationInUseException;
import de.htw.saar.smartcity.aggregator.groups.exception.LocationNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.entity.Location;
import de.htw.saar.smartcity.aggregator.lib.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {

        this.locationService = locationService;
    }

    @GetMapping("/")
    public ResponseEntity getLocations() {

        return new ResponseEntity(
                locationService.findAllLocations(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getLocation(@PathVariable Long id) {

        return new ResponseEntity(
                locationService.findLocationById((id))
                        .orElseThrow(() -> new LocationNotFoundException(id)),
                HttpStatus.OK
        );
    }

    @PostMapping("/")
    public ResponseEntity postLocation(@RequestBody Location location) {

        return new ResponseEntity(locationService.saveLocation(location), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteLocation(@PathVariable Long id) {

        Location location = locationService.findLocationById(id)
                .orElseThrow(() -> new LocationNotFoundException(id));

        //PRIO 2 - todo: verify
        if(location.getProducers().isEmpty())
            locationService.deleteLocation(location);
        else
            throw new LocationInUseException(id);

        return new ResponseEntity(HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity putLocation(@RequestBody Location location, @PathVariable Long id) {

        Optional<Location> optOldLocation = locationService.findLocationById(id);
        Location saved;

        if(optOldLocation.isPresent()) {

            Location oldLocation = optOldLocation.get();
            oldLocation.setName(location.getName());
            oldLocation.setX(location.getX());
            oldLocation.setY(location.getY());
            saved = locationService.updateLocation(oldLocation);

        } else {

            location.setId(id);
            saved = locationService.saveLocation(location);
        }

        return new ResponseEntity(saved, HttpStatus.OK);
    }
   
}
