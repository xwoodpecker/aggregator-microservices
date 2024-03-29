package de.htw.saar.smartcity.aggregator.lib.base;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.properties.RawMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Raw setup data loader.
 */
public abstract class RawSetupDataLoader extends SetupDataLoader {

    /**
     * the application settings for raw microservices
     */
    private final RawMicroserviceApplicationProperties rawMicroserviceApplicationProperties;
    /**
     * the data type service
     */
    private final DataTypeService dataTypeService;

    /**
     * Instantiates a new Raw setup data loader.
     *
     * @param dataTypeService                      the data type service
     * @param rawMicroserviceApplicationProperties the raw microservice application properties
     */
    public RawSetupDataLoader(DataTypeService dataTypeService,
                              RawMicroserviceApplicationProperties rawMicroserviceApplicationProperties) {

        this.dataTypeService = dataTypeService;
        this.rawMicroserviceApplicationProperties = rawMicroserviceApplicationProperties;
    }

    /**
     * Triggers on application startup
     *
     * @param event the ContextRefreshedEvent
     */
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(alreadySetup)
            return;

        createDataTypeIfNotFound(rawMicroserviceApplicationProperties.getMicroServiceDataType());

        alreadySetup = true;
    }

    /**
     * Create data type if not found data type.
     *
     * @param dataTypeName the data type name
     * @return the data type
     */
    @Transactional
    protected DataType createDataTypeIfNotFound(String dataTypeName) {

        DataType dataType = dataTypeService.findDataTypeByName(dataTypeName);
        if(dataType == null) {
            dataType = new DataType();
            dataType.setName(dataTypeName);
            dataTypeService.saveDataType(dataType);
            log.info("Created DataType " + dataTypeName);
        }
        return dataType;
    }

}