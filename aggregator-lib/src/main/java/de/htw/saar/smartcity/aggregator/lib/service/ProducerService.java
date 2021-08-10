package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.Producer;
import de.htw.saar.smartcity.aggregator.lib.repository.ProducerRepository;
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
     * Save producer
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

    /**
     * Find all producers by data type list.
     *
     * @param dataType the data type
     * @return the list
     */
    public List<Producer> findAllProducersByDataType(DataType dataType) {
        return producerRepository.findAllByDataType(dataType);
    }
}
