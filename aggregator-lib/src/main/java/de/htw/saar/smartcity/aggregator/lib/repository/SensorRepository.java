package de.htw.saar.smartcity.aggregator.lib.repository;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Sensor repository.
 */
@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {

    /**
     * Find sensor by name sensor.
     *
     * @param name the name
     * @return the sensor
     */
    Sensor findSensorByName(String name);

    /**
     * Find all by export as metric true list.
     *
     * @return the list
     */
    List<Sensor> findAllByExportAsMetricTrue();

    /**
     * Find all by data type name and export as metric true list.
     *
     * @param dataTypeName the data type name
     * @return the list
     */
    List<Sensor> findAllByDataTypeNameAndExportAsMetricTrue(String dataTypeName);

    /**
     * Find all by id between and export as metric true list.
     *
     * @param start the start
     * @param end   the end
     * @return the list
     */
    List<Sensor> findAllByIdBetweenAndExportAsMetricTrue(Long start, Long end);

}
