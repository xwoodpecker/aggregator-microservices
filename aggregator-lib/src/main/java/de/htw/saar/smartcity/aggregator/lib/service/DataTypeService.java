package de.htw.saar.smartcity.aggregator.lib.service;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.repository.DataTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataTypeService {

    private DataTypeRepository dataTypeRepository;

    public DataTypeService(DataTypeRepository dataTypeRepository) {
        this.dataTypeRepository = dataTypeRepository;
    }

    public DataType findDataTypeByName(String name) {
        return dataTypeRepository.findDataTypeByName(name);
    }

    public DataType saveDataType(DataType dataType) {
        return dataTypeRepository.save(dataType);
    }

    public Optional<DataType> findDataTypeById(Long id) {
        return dataTypeRepository.findById(id);
    }

    public List<DataType> findAllDataTypes() {
        return dataTypeRepository.findAll();
    }
}
