package de.htw.saar.smartcity.aggregator.lib.base;

import de.htw.saar.smartcity.aggregator.lib.entity.DataType;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import de.htw.saar.smartcity.aggregator.lib.entity.Tag;
import de.htw.saar.smartcity.aggregator.lib.properties.GroupMicroserviceApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

public abstract class GroupSetupDataLoader extends SetupDataLoader {

    protected final GroupMicroserviceApplicationProperties groupMicroserviceApplicationProperties;
    protected final GroupTypeService groupTypeService;
    protected final DataTypeService dataTypeService;

    public GroupSetupDataLoader(GroupMicroserviceApplicationProperties groupMicroserviceApplicationProperties,
                                     GroupTypeService groupTypeService,
                                     DataTypeService dataTypeService) {

        this.groupMicroserviceApplicationProperties = groupMicroserviceApplicationProperties;
        this.groupTypeService = groupTypeService;
        this.dataTypeService = dataTypeService;
    }

    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(alreadySetup)
            return;

        createGroupTypeIfNotFound(groupMicroserviceApplicationProperties.getMicroserviceGroupTypeName());
        createDataTypeIfNotFound(groupMicroserviceApplicationProperties.getMicroserviceDataTypeName());

        alreadySetup = true;
    }


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
