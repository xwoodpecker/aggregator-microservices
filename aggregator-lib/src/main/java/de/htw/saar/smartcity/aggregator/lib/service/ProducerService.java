package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.repository.ProducerRepository;
import de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProducerService {

    private ProducerRepository producerRepository;


    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public Producer saveProducer(Producer producer) {
        return producerRepository.save(producer);
    }

    public Optional<Producer> findProducerById(Long id) {
        return producerRepository.findById(id);
    }

    public List<Producer> findAllProducers() {
        return producerRepository.findAll();
    }

    /**public void deleteProducer(Producer producer) {

        producer.getGroups().forEach(g -> g.getProducers().removeIf(p -> p.getId() == producer.getId()));
        producer.getTags().forEach(t -> t.getTags().removeIf(p -> p.getId() == producer.getId()));
        producerRepository.deleteById(producer.getId());
    }**/
}
