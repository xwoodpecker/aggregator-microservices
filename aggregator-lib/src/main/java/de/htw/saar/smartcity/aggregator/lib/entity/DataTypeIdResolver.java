package de.htw.saar.smartcity.aggregator.lib.entity;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdResolver;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import org.springframework.beans.factory.annotation.Autowired;

public class DataTypeIdResolver implements ObjectIdResolver {
    private final DataTypeService dataTypeService;

    public DataTypeIdResolver(@Autowired DataTypeService dataTypeService) {
        this.dataTypeService = dataTypeService;
    }

    @Override
    public void bindItem(ObjectIdGenerator.IdKey idKey, Object o) {

    }

    @Override
    public Object resolveId(ObjectIdGenerator.IdKey idKey) {
        if(idKey.key instanceof Long)
            return dataTypeService.findDataTypeById((Long)idKey.key).get();
        else
            return null;
    }

    @Override
    public ObjectIdResolver newForDeserialization(Object o) {
        return this;
    }

    @Override
    public boolean canUseFor(ObjectIdResolver objectIdResolver) {
        return false;
    }
}
