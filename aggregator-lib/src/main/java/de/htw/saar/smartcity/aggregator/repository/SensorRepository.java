package de.htw.saar.smartcity.aggregator.repository;

import de.htw.saar.smartcity.aggregator.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Sensor findSensorByName(String name);

}
