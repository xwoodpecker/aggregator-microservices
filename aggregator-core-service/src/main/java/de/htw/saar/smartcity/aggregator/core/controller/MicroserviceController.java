package de.htw.saar.smartcity.aggregator.core.controller;

import de.htw.saar.smartcity.aggregator.core.exception.MicroserviceNotFoundException;
import de.htw.saar.smartcity.aggregator.lib.entity.Microservice;
import de.htw.saar.smartcity.aggregator.lib.service.MicroserviceService;
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

        return new ResponseEntity(
                microserviceService.findAllMicroservices(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getMicroservice(@PathVariable Long id) {

        return new ResponseEntity(
                microserviceService.findMicroServiceById(id)
                        .orElseThrow(() -> new MicroserviceNotFoundException(id))
                , HttpStatus.OK
        );
    }

}
