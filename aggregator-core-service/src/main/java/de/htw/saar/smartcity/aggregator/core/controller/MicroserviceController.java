package de.htw.saar.smartcity.aggregator.core.controller;

import de.htw.saar.smartcity.aggregator.entity.Group;
import de.htw.saar.smartcity.aggregator.entity.GroupMember;
import de.htw.saar.smartcity.aggregator.entity.Microservice;
import de.htw.saar.smartcity.aggregator.entity.Sensor;
import de.htw.saar.smartcity.aggregator.service.MicroserviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/microservices")
public class MicroserviceController {

    private final MicroserviceService microserviceService;

    public MicroserviceController(MicroserviceService microserviceService) {
        this.microserviceService = microserviceService;
    }


    @GetMapping("/")
    public ResponseEntity getMicroservices() {
        List<Microservice> microservices = microserviceService.findAllMicroservices();

        return new ResponseEntity(microservices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getMicroservice(@PathVariable Long id) {
        ResponseEntity response;
        Optional<Microservice> microservice = microserviceService.findMicroServiceById(id);

        if(microservice.isPresent())
            response = new ResponseEntity(microservice.get(), HttpStatus.OK);
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No microservice with the given id found.");

        return response;
    }

}
