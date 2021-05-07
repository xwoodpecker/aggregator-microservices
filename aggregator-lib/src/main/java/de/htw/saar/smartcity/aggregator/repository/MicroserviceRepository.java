package de.htw.saar.smartcity.aggregator.repository;

import de.htw.saar.smartcity.aggregator.entity.Microservice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MicroserviceRepository extends JpaRepository<Microservice, Long> {

    Microservice findMicroserviceByName(String name);

}

