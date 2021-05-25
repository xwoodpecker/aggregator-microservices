package de.htw.saar.smartcity.aggregator.lib.base;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

public abstract class RawSetupDataLoader extends SetupDataLoader {

    private final RawMicroserviceApplicationProperties rawMicroserviceApplicationProperties;
    private final DataTypeService dataTypeService;

    public RawSetupDataLoader(DataTypeService dataTypeService,
                              RawMicroserviceApplicationProperties rawMicroserviceApplicationProperties) {

        this.dataTypeService = dataTypeService;
        this.rawMicroserviceApplicationProperties = rawMicroserviceApplicationProperties;
    }

    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(alreadySetup)
            return;

        createDataTypeIfNotFound(rawMicroserviceApplicationProperties.getMicroServiceSensorType());

        alreadySetup = true;
    }

    @Transactional
    DataType createDataTypeIfNotFound(String sensorTypeName) {

        DataType dataType = dataTypeService.findDataTypeByName(sensorTypeName);
        if(dataType == null) {
            dataType = new DataType();
            dataType.setName(sensorTypeName);
            dataTypeService.saveDataType(dataType);
        }
        return dataType;
    }

}