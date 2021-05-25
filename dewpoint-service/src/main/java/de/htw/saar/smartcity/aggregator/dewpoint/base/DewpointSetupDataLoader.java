package de.htw.saar.smartcity.aggregator.dewpoint.base;

import de.htw.saar.smartcity.aggregator.dewpoint.properties.DewpointApplicationProperties;
import de.htw.saar.smartcity.aggregator.lib.base.MixedGroupSetupDataLoader;
import de.htw.saar.smartcity.aggregator.lib.entity.GroupType;
import de.htw.saar.smartcity.aggregator.lib.service.DataTypeService;
import de.htw.saar.smartcity.aggregator.lib.service.GroupTypeService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Setup data loader.
 */
@Component
public class DewpointSetupDataLoader extends MixedGroupSetupDataLoader {

    private DewpointApplicationProperties dewpointApplicationProperties;

    public DewpointSetupDataLoader(DewpointApplicationProperties mixedGroupMicroserviceApplicationProperties,
                                   GroupTypeService groupTypeService,
                                   DataTypeService dataTypeService) {

        super(mixedGroupMicroserviceApplicationProperties, groupTypeService, dataTypeService);
        this.dewpointApplicationProperties = mixedGroupMicroserviceApplicationProperties;
    }

    @Override
    @Transactional
    protected GroupType createGroupTypeIfNotFound(String groupTypeName) {

        GroupType groupType = groupTypeService.findGroupTypeByName(groupTypeName);
        if(groupType == null) {
            groupType = new GroupType();
            groupType.setName(groupTypeName);
            /**List<DataType> sensorTypeList = new ArrayList<>();
            sensorTypeList.add(sensorTypeService.findSensorTypeByName(dewpointApplicationProperties.getTemperatureSensorTypeName()));
            sensorTypeList.add(sensorTypeService.findSensorTypeByName(dewpointApplicationProperties.getHumiditySensorTypeName()));
            groupType.setSensorTypes(sensorTypeList);**/
            groupTypeService.saveGroupType(groupType);
        }
        return groupType;
    }
}
