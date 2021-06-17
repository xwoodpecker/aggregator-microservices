package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import de.htw.saar.smartcity.aggregator.lib.repository.ProducerRepository;
import de.htw.saar.smartcity.aggregator.lib.repository.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Producer service.
 */
@Service
public class ProducerService {

    private ProducerRepository producerRepository;


    /**
     * Instantiates a new Producer service.
     *
     * @param producerRepository the producer repository
     */
    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    /**
     * Save producer producer.
     *
     * @param producer the producer
     * @return the producer
     */
    public Producer saveProducer(Producer producer) {
        return producerRepository.save(producer);
    }

    /**
     * Find producer by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<Producer> findProducerById(Long id) {
        return producerRepository.findById(id);
    }

    /**
     * Find all producers list.
     *
     * @return the list
     */
    public List<Producer> findAllProducers() {
        return producerRepository.findAll();
    }
}
