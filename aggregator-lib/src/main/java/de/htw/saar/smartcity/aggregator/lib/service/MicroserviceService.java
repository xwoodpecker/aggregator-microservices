package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Microservice;
import de.htw.saar.smartcity.aggregator.lib.repository.MicroserviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MicroserviceService {

    private MicroserviceRepository microserviceRepository;

    public MicroserviceService(MicroserviceRepository microserviceRepository) {
        this.microserviceRepository = microserviceRepository;
    }

    public Optional<Microservice> findMicroServiceById(Long id) {
        return microserviceRepository.findById(id);
    }

    public List<Microservice> findAllMicroservices() {
        return microserviceRepository.findAll();
    }

    public Microservice findMicroserviceByName(String name) {
        return microserviceRepository.findMicroserviceByName(name);
    }

    public Microservice saveMicroservice(Microservice microservice) {
        return microserviceRepository.save(microservice);
    }
}
