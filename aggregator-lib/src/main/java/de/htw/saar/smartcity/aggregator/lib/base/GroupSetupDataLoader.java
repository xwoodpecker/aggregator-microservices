package de.htw.saar.smartcity.aggregator.lib.base;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Group setup data loader.
 */
public abstract class GroupSetupDataLoader extends SetupDataLoader {

    /**
     * The Group microservice application properties.
     */
    protected final GroupApplicationProperties applicationProperties;
    /**
     * The Group type service.
     */
    protected final GroupTypeService groupTypeService;
    /**
     * The Data type service.
     */
    protected final DataTypeService dataTypeService;

    /**
     * Instantiates a new Group setup data loader.
     *
     * @param applicationProperties the group microservice application properties
     * @param groupTypeService                       the group type service
     * @param dataTypeService                        the data type service
     */
    public GroupSetupDataLoader(GroupApplicationProperties applicationProperties,
                                GroupTypeService groupTypeService,
                                DataTypeService dataTypeService) {

        this.applicationProperties = applicationProperties;
        this.groupTypeService = groupTypeService;
        this.dataTypeService = dataTypeService;
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

        createGroupTypeIfNotFound(applicationProperties.getGroupTypeName());
        createDataTypeIfNotFound(applicationProperties.getApplicationDataType());

        alreadySetup = true;
    }


    /**
     * Create group type if not found group type.
     *
     * @param groupTypeName the group type name
     * @return the group type
     */
    @Transactional
    protected GroupType createGroupTypeIfNotFound(String groupTypeName) {

        GroupType groupType = groupTypeService.findGroupTypeByName(groupTypeName);
        if(groupType == null) {
            groupType = new GroupType();
            groupType.setName(groupTypeName);
            /**List<DataType> dataTypeList = new ArrayList<>();
             dataTypeList.add(dataTypeService.findDataTypeByName(dewpointApplicationProperties.getTemperatureDataTypeName()));
             dataTypeList.add(dataTypeService.findDataTypeByName(dewpointApplicationProperties.getHumidityDataTypeName()));
             groupType.setDataTypes(dataTypeList);**/
            groupTypeService.saveGroupType(groupType);
            log.info("Created GroupType " + groupTypeName);
        }
        return groupType;
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
