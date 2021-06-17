package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * The type Data type service.
 */
@Service
public class DataTypeService {

    private DataTypeRepository dataTypeRepository;

    /**
     * Instantiates a new Data type service.
     *
     * @param dataTypeRepository the data type repository
     */
    public DataTypeService(DataTypeRepository dataTypeRepository) {
        this.dataTypeRepository = dataTypeRepository;
    }

    /**
     * Find data type by name data type.
     *
     * @param name the name
     * @return the data type
     */
    public DataType findDataTypeByName(String name) {
        return dataTypeRepository.findDataTypeByName(name);
    }

    /**
     * Save data type data type.
     *
     * @param dataType the data type
     * @return the data type
     */
    public DataType saveDataType(DataType dataType) {
        return dataTypeRepository.save(dataType);
    }

    /**
     * Find data type by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<DataType> findDataTypeById(Long id) {
        return dataTypeRepository.findById(id);
    }

    /**
     * Find all data types list.
     *
     * @return the list
     */
    public List<DataType> findAllDataTypes() {
        return dataTypeRepository.findAll();
    }
}
